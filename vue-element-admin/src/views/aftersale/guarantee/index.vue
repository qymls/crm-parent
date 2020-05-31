<template>
  <div style="height: calc(100vh - 84px);">
    <Card tyle="height: 100%" dis-hover>
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20" />
        {{ title }}
      </p>
      <!-- 顶部工具 -->
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
            <div>
              <el-select v-model="searchForm.name" filterable placeholder="输入合同单号">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in contractData"
                  :key="item.id"
                  :label="item.label"
                  :value="item.sn">
                </el-option>
              </el-select>
            </div>
          </FormItem>
          <FormItem>
            <Button type="primary" icon="ios-search" @click="loadListData">查询</Button>
          </FormItem>
        </Form>
      </Row>

      <!-- 表格数据 -->
      <el-table border v-loading="loading" :data="tableData" style="width: 100%" max-height="690" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="60" align="center" />
        <el-table-column type="index" label="序号" width="80" align="center" />
        <el-table-column prop="sn" label="保修单号" align="center" />
        <el-table-column prop="contract.signTime" label="保修开始日期" align="center" />
        <el-table-column prop="endDate" label="保修截止日期" align="center" />
        <el-table-column prop="contract.sn" label="所属合同单号" align="center" />
        <el-table-column prop="contract.customer.name" label="所属客户" align="center" />
        <el-table-column prop="contract.tenant.companyName" label="所属租户" align="center" />
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <!-- 查看明细 -->
            <el-button type="primary" size="mini" @click="handleShowEditDialog(scope.row)">编辑</el-button>
            <el-popconfirm
              confirm-button-text="确认"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除当前数据？"
              transition
              placement="top"
              @onConfirm="handleRemove(scope.row)"
            ><el-button slot="reference" type="danger" size="mini">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div style="float: right; margin: 12px; overflow: hidden">
        <Page
          :total="total"
          :current="page"
          :page-size="pageSize"
          :page-size-opts="[5,10,20]"
          show-elevator
          show-sizer
          show-total
          @on-change="handleCurrentChange"
          @on-page-size-change="handleSizeChange"
        ></Page>
      </div>
      <br/>
      <br/>

      <!-- 新增编辑窗口 -->
      <el-dialog title="添加信息" :visible.sync="dialogFormVisible" :close-on-click-modal="false" width="35%">
        <el-form ref="addForm" label-width="80px" :model="addForm" :rules="rules">
          <el-form-item v-show="false" prop="id">
            <el-input v-model="addForm.id" />
          </el-form-item>
          <el-form-item v-show="false" label="开始日期" prop="signTime">
            <el-date-picker
              v-model="addForm.contract.signTime"
              type="date"
              placeholder="保修开始日期">
            </el-date-picker>
          </el-form-item>

          <el-form-item v-if="addForm.id" label="截止日期" prop="endDate">
            <el-date-picker
              v-model="addForm.endDate"
              align="right"
              type="date"
              placeholder="保修截止日期"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-button type="primary" @click="changeEndDate(addForm.contract.signTime)">续费</el-button>
          </el-form-item>
          <el-form-item label="合同单号" prop="contractSn">
            <el-select v-model="addForm.contract.sn" filterable placeholder="选择合同单号">
              <el-option
                v-for="item in contractData"
                :key="item.id"
                :label="item.label"
                :value="item.sn">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('addForm')">提交</el-button>
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
      //日期选择器
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '加时一年',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 365);
            picker.$emit('pick', date);
          }
        }]
      },

      title: '保修单管理',
      page: 1, // 第几页
      pageSize: 5, // 每页条数
      total: 0,//总条数
      //table【表头】
      columns1: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          type: 'index',
          title: '编号',
          width: 80,
          align: 'center'
        },
        {
          title: '保修单号',
          key: 'sn',
          align: 'center'
        },
        {
          title: '到期时间',
          key: 'endDate',
          align: 'center'
        },
        /*{
          title: '所属合同',
          key: "contract.sn",
          align: 'center'
        },
        {
          title: '所属客户',
          key: 'contract.customer.name',
          align: 'center'
        },
        {
          title: '所属租户',
          key: 'contract.tenant.companyName',
          align: 'center'
        },*/
        {
          title: '操作',
          slot: 'action',
          width: 200,
          align: 'center',
          /*render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.handleShowEditDialog(params.row)
                  }
                }
              }, '编辑'),
              h('Poptip',{
                props: {
                  confirm,
                  title: '你确定要删除这些内容吗？',
                },
                on: {
                  ok: () => {
                    this.handleBatchRemove()
                  }
                }
              },h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.handleRemove(params.row)
                  }
                }
              }, '删除')),
            ]);
          }*/
        }
      ],
      //table【数据】
      tableData: [],
      //下拉列表数据
      contractData: [],

      row: [],
      //搜索条件
      searchForm: {
        name: ''
      },
      //添加编辑表单
      addForm: {
        id: '',
        endDate: '',
        contract: {
          id: '',
          sn: '',
          signTime: ''
        },
        customer: {
          id: '',
          name: ''
        },
        tenant: {
          id: '',
          companyName: ''
        },
      },
      //新增编辑框 提示
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },

      loading: false,//是否显示加载效果
      dialogFormVisible: false,//添加编辑窗口 是否显示
      showDateVisible: false
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {
    //续费endDate
    changeEndDate(endDate) {
      var str2 = Date.parse(new Date(endDate))
      const date = new Date(str2 + 3600 * 1000 * 24 * 365)
      this.addForm.endDate = date
    },

    // 显示添加弹窗
    handleShowAddDialog() {
      this.showDateVisible = false
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
      })
    },
    //显示编辑弹窗
    handleShowEditDialog(row) {
      this.showDateVisible = true
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row)/* 复制*/
      })
    },
    //确认保存
    submitForm(formName) {
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        let url = '/guarantee/save'
        if (this.addForm.id) {
          url = '/guarantee/update'
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
      http.post('/guarantee/batchDelete', param).then(res => {
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
      http.delete('/guarantee/delete/' + row.id).then(res => {
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
      http.post('/guarantee/selectForPage', param).then(res => {
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
      http.get('/contract/findAll').then(res => {
        if (res.data.success) {
          this.contractData = res.data.data
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
