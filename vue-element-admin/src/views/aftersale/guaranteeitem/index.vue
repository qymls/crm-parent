<template>
  <div style="height: calc(100vh - 84px);">
    <Card tyle="height: 100%" dis-hover>
      <!-- 标题 -->
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20" />
        {{ title }}
      </p>

      <!-- 顶部工具条【查询，新增，批量删除】 -->
      <Row>
        <Form :model="searchForm" inline>
          <FormItem>
            <Button type="primary" icon="ios-add-circle-outline" @click="handleShowAddDialog">新增</Button>
          </FormItem>
          <FormItem>
            <Poptip v-if="row.length > 0" confirm title="你确定要删除这些内容吗？" @on-ok="handleBatchRemove">
              <Button type="error" icon="ios-close-circle-outline">删除</Button>
            </Poptip>
          </FormItem>
          <FormItem prop="name">
            <Input type="text" v-model="searchForm.name" placeholder="输入合同单号"/>
          </FormItem>
          <FormItem>
            <Button type="primary" icon="ios-search" @click="loadListData">查询</Button>
          </FormItem>
        </Form>
      </Row>

      <!-- 表中数据 -->
      <Table border ref="selection" :loading="loading" :columns="columns"  :data="tableData" @on-selection-change="handleSelectionChange">
        <!-- 保修单号 -->
        <template slot="guaranteeSn" slot-scope="{ row, index }">
          <span>{{ row.guaranteeSn }}</span>
        </template>
        <!-- 时间 -->
        <template slot="inputDate" slot-scope="{ row, index }">
          <DatePicker v-if="editIndex === index" v-model="editForm.inputDate" type="date" style="width: 200px" />
          <span v-else>{{ row.inputDate }}</span>
        </template>
        <!-- 内容 -->
        <template slot="details" slot-scope="{ row, index }">
          <Input v-if="editIndex === index" v-model="editForm.details" type="text" />
          <span v-else>{{ row.details }}</span>
        </template>
        <!-- 状态 -->
        <template slot="status" slot-scope="{ row, index }">
          <Select v-if="editIndex === index" v-model="editForm.status">
            <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
          <span v-else-if="row.status == 0" style="color: #ff9900">未处理</span>
          <span v-else-if="row.status == 1" style="color: #19be6b">已处理</span>
          <span v-else-if="row.status == 2" style="color: #2db7f5">正在处理</span>
        </template>

        <template slot="action" slot-scope="{ row, index }">
          <div v-if="editIndex === index">
            <el-button size="mini" @click="editIndex = -1, editVisible = true">取消</el-button>
            <el-button type="success" size="mini" @click="handleSave(index)">保存</el-button>
          </div>
          <div v-if="editVisible">
            <el-button type="primary" size="mini" @click="handleEdit(row, index), editForm.id = row.id">编辑</el-button>
            <el-popconfirm
              confirm-button-text="确认"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除当前数据？"
              transition
              placement="top"
              @onConfirm="handleRemove(row)"
            ><el-button slot="reference" type="danger" size="mini">删除</el-button>
            </el-popconfirm>
          </div>
        </template>
      </Table>

      <!-- 分页 -->
      <Page
        :total="total"
        :current="page"
        :page-size="pageSize"
        :page-size-opts="[5,10,20]"
        show-elevator
        show-sizer
        show-total
        styles="float: right; margin: 12px; overflow: hidden"
        @on-change="handleCurrentChange"
        @on-page-size-change="handleSizeChange"
      ></Page>
      <br/>
      <br/>

      <!--  新增-->
      <el-dialog title="添加信息" :visible.sync="dialogFormVisible" :close-on-click-modal="false" width="35%">
        <el-form ref="addForm" :model="addForm" label-width="100px" :rules="rules">
          <el-form-item label="保修单号" prop="sn">
            <el-input v-model="addForm.guaranteeSn" placeholder="输入保修单号" autocomplete="off" />
          </el-form-item>
          <el-form-item label="保修内容" prop="endDate">
            <el-input v-model="addForm.details" placeholder="输入保修内容" autocomplete="off" />
          </el-form-item>

          <el-form-item label="保修状态" prop="status">
            <el-select v-model="addForm.status" placeholder="选择状态">
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitAddForm(addForm)">提交</el-button>
            <el-button @click="resetForm('addForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </Card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      columns: [
        {
          type: 'selection',
          width: 80,
          align: 'center'
        },
        {
          title: '序号',
          type: 'index',
          width: 80,
          align: 'center'
        },
        {
          title: '保修单号',
          slot: 'guaranteeSn',
          width: 200,
          align: 'center'
        },
        {
          title: '保修日期',
          slot: 'inputDate',
          width: 250,
          align: 'center'
        },
        {
          title: '保修内容',
          slot: 'details',
          align: 'center'
        },
        {
          title: '保修状态',
          slot: 'status',
          width: 150,
          align: 'center'
        },
        {
          title: '操作',
          slot: 'action',
          width: 200,
          align: 'center'
        }
      ],//table数据
      statusList: [
        {
          value: '0',
          label: '未处理'
        },
        {
          value: '1',
          label: '已处理'
        },
        {
          value: '2',
          label: '正在处理'
        }
      ],//status状态
      model1: '',

      title: '保修单明细',
      page: 1, // 第几页
      pageSize: 5, // 每页条数
      total: 0,
      tableData: [],
      loading: false,
      row: [],

      editIndex: -1,

      //搜索框
      searchForm: {
        name: ''
      },

      //修改表单
      editForm: {
        id: '',
        inputDate: '',
        details: '',
        status: ''
      },
      //新增表单
      addForm: {
        id: '',
        guaranteeSn: '',
        inputDate: '',
        details: '',
        status: ''
      },

      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },

      dialogFormVisible: false,
      editVisible: true
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {
    // 显示添加弹窗
    handleShowAddDialog() {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
      })
    },
    //编辑回显
    handleEdit(row, index) {
      this.editVisible = false
      this.editForm.inputDate = row.inputDate
      this.editForm.details = row.details
      this.editForm.status = row.status
      this.editIndex = index
    },
    /* 确认保存*/
    submitAddForm(formName) {
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        let url = '/guaranteeItem/save'
        if (valid) {
          http.post(url, param).then(res => {
            if (res.data.success) {
              this.dialogFormVisible = false
              this.loadListData()
              message({ message: res.data.message, center: true, type: 'success', showClose: true })
            } else {
              message.error('操作失败[' + res.data.message + ']')
            }
          }).catch(error => {
            message.error('操作失败[' + error.message + ']')
          })
        } else {
          message.error('请按照规则填写表单')
        }
      })
    },

    /* 编辑修改 */
    handleSave (index) {
      this.editForm.id = this.tableData[index].id
      this.editIndex = -1
      let url = '/guaranteeItem/update'
      this.$http.post(url, this.editForm).then(res => {
        if (res.data.success) {
          this.editVisible = true
          this.loadListData()
          message({ message: res.data.message, center: true, type: 'success', showClose: true })
        } else {
          message.error('操作失败[' + res.data.message + ']')
        }
      }).catch(error => {
        message.error('操作失败[' + error.message + ']')
      })
    },

    //多选
    handleSelectionChange(selection) {
      this.row = selection
    },

    //批量删除
    handleBatchRemove() {
      //把对象数组转成 id数组
      const ids = this.row.map(function(obj, index, arr) {
        return obj.id
      })
      const param = { ids: ids }
      var http = this.$http
      var notify = this.$notify
      http.post('/guaranteeItem/batchDelete', param).then(res => {
        if (res.data.success) {
          notify({ title: '删除成功', message: '恭喜你，你已经成功删除', type: 'success', offset: 100 })
          this.loadListData()
        } else {
          notify({ title: '删除失败', message: res.data.message, type: 'error', offset: 100 })
        }
      }).catch(error => {
        notify({ title: '删除失败', message: error.message, type: 'error', offset: 100 })
      })
    },

    //删除单条
    handleRemove(row) {
      var http = this.$http
      var notify = this.$notify
      http.delete('/guaranteeItem/delete/' + row.id).then(res => {
        if (res.data.success) {
          notify({ title: '删除成功', message: '恭喜你，你已经成功删除', type: 'success', offset: 100 })
          this.loadListData()
        } else {
          notify({ title: '删除失败', message: res.data.message, type: 'error', offset: 100 })
        }
      }).catch(error => {
        notify({ title: '删除失败', message: error.message, type: 'error', offset: 100 })
      })
    },

    //分页
    handleSizeChange(val) {
      this.pageSize = val
      this.loadListData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.loadListData()
    },

    // 加载数据
    loadListData() {
      this.loading = true
      // vue加载完成，发送ajax请求动态获取数据
      const param = {
        'currentPage': this.page,
        'pageSize': this.pageSize,
        'keyword': this.searchForm.name
      }
      var http = this.$http
      var message = this.$message
      http.post('/guaranteeItem/selectForPage', param).then(res => {
        if (res.data.success) {
          this.tableData = res.data.data.list
          this.total = res.data.data.totalRows
          this.page = res.data.data.currentPage
          this.loading = false
        } else {
          message.error('查询失败[' + res.data.message + ']')
        }
      }).catch(error => {
        message.error('查询失败[' + error.message + ']')
      })
    }
  }

}
</script>
