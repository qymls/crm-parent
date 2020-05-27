<template>
  <div style="height: calc(100vh - 84px);">
    <Card class="box-card" tyle="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20" />
        {{ title }}
      </p>

      <!-- 顶部工具条【查询，新增，批量删除】 -->
      <row>
        <el-col :span="3">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="small"
            style="float: left;"
            @click="handleShowAddDialog"
          >新增
          </el-button>
          <el-popconfirm
            v-if="row.length>0"
            icon="el-icon-info"
            icon-color="red"
            title="你确定要离我而去？"
            placement="right"
            @onConfirm="handleBatchRemove"
          >
            <el-button
              slot="reference"
              type="danger"
              icon="el-icon-delete"
              size="small"
              style="float: left;margin: auto 3px 20px 20px;"
            >删除
            </el-button>
          </el-popconfirm>
        </el-col>
        <el-col :span="21" class="toolbar">
          <el-form
            :inline="true"
            :model="searchForm"
            size="small"
            class="demo-form-inline"
            style="float: left;"
          >
            <el-form-item label="保修单号">
              <el-input v-model="searchForm.name" placeholder="保修单号" clearable />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="loadListData">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </row>

      <!-- 表中数据 -->
      <Table :columns="columns" :data="tableData" border>
        <!-- 保修单号 -->
        <template slot="guaranteeSn" slot-scope="{ row, index }">
          <span>{{ row.guaranteeSn }}</span>
        </template>

        <!-- 时间 -->
        <template slot="inputDate" slot-scope="{ row, index }">
          <DatePicker v-if="editIndex === index"v-model="editInputDate" type="datetime" style="width: 200px" />
          <span v-else>{{ row.inputDate }}</span>
        </template>

        <!-- 内容 -->
        <template slot="details" slot-scope="{ row, index }">
          <Input v-if="editIndex === index" v-model="editDetails" type="text" />
          <span v-else>{{ row.details }}</span>
        </template>

        <!-- 状态【需要格式化】 -->
        <template slot="status" slot-scope="{ row, index }">
          <Input v-if="editIndex === index" v-model="editStatus" type="text" />
          <span v-else>{{ row.status }}</span>
        </template>

        <template slot="action" slot-scope="{ row, index }">
          <div v-if="editIndex === index">
            <Button @click="editIndex = -1">取消</Button>
            <Button type="success" @click="handleSave(index)">保存</Button>
          </div>
          <div v-else>
            <Button type="primary" @click="handleEdit(row, index)">编辑</Button>
            <el-popconfirm
              confirm-button-text="确认"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除当前数据？"
              transition
              placement="top"
              @onConfirm="handleRemove(scope.row)"
            ><el-button slot="reference" type="danger" size="small">删除</el-button>
            </el-popconfirm>
          </div>
        </template>
      </Table>

      <!-- 分页条 -->
      <Page
        style="float: right;margin: 20px;overflow: hidden"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="page"
        :page-sizes="[5,10, 20]"
        :page-size="pageSize"
        :total="total"
        show-total
        show-elevator
        show-sizer
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />

      <!--  新增、编辑-->
      <dialog title="信息管理" :visible.sync="dialogFormVisible" :close-on-click-modal="false" width="35%">
        <form ref="addForm" :model="addForm" label-width="50px" :rules="rules">
          <form-item v-show="false" prop="id">
            <input v-model="addForm.id">
          </form-item>
          <form-item label="单号" prop="sn">
            <el-input v-model="addForm.guaranteeSn" autocomplete="off" />
          </form-item>
          <form-item label="内容" prop="details">
            <input v-model="addForm.details" autocomplete="off">
          </form-item>
          <form-item label="状态" prop="status">
            <input v-model="addForm.status" autocomplete="off">
          </form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('addForm')">确认提交</el-button>
            <el-button @click="resetForm('addForm')">重置</el-button>
          </el-form-item>
        </form>
      </dialog>
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
      ],
      title: '保修单明细',
      page: 1, // 第几页
      pageSize: 5, // 每页条数
      total: 0,
      tableData: [],
      loading: false,
      row: [],

      editGuaranteeSn: '',
      editInputDate: '',
      editDetails: '',
      editStatus: '',
      editIndex: -1,

      // 搜索框
      searchForm: {
        name: ''
      },

      dialogFormVisible: false,

      // 新增编辑
      addForm: {
        id: '',
        sn: '',
        date: '',
        details: '',
        status: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {

    handleEdit(row, index) {
      this.editInputDate = row.inputDate
      this.editDetails = row.details
      this.editStatus = row.status
      this.editIndex = index
    },

    // 显示添加弹窗
    handleShowAddDialog() {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
      })
    },

    // 编辑显示弹窗
    handleShowEditDialog(row) {
      // 数据回显
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row)/* 复制*/
      })
    },

    submitForm(formName) { /* 确认保存*/
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        let url = '/guaranteeItem/save'
        if (this.addForm.id) {
          url = '/guaranteeItem/update'
        }
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

    resetForm(formName) { /* 重置*/
      this.$nextTick(() => {
        var refs = this.$refs
        refs[formName].resetFields()/* 清空*/
      })
    },
    handleSelectionChange(selection) {
      this.row = selection
    },
    // 批量删除
    handleBatchRemove() {
      // 把对象数组转成 id数组
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
