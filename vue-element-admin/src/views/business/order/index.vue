<template>
  <div style="height: calc(100vh - 84px);">
    <Card style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"/>
        订单信息管理
      </p>
      <Row>
        <Col span="3">
          <Button type="primary" icon="md-add" @click="handleShowAddDialog">添加</Button>
          <Poptip
            confirm
            placement="right"
            transfer
            title="您确认删除这些信息吗?"
            @on-ok="handleBatchRemove"
          >
            <Button v-if="row.length>0" type="error" icon="ios-trash">删除</Button>
          </Poptip>
        </Col>
        <!--查询工具条-->
        <Col span="21">
          <Form ref="searchForm" :model="searchForm" inline style="margin-left: 20px;" @submit.native.prevent class="demo-form-inline">
            <!--客户姓名查询-->
            <FormItem prop="name">
              <Input v-model="searchForm.customer.name" type="text" clearable
                     style="cursor: pointer" placeholder="请输入查找的客户姓名"
                     @on-enter="click_enter">
              </Input>
            </FormItem>
            <!--营销人员查询-->
            <FormItem prop="username">
             <Input v-model="searchForm.seller.username" type="text" clearable
                     style="cursor: pointer" placeholder="请输入查找的营销人员姓名"
                     @on-enter="click_enter">
              </Input>
            </FormItem >
            <FormItem>
              <Button type="info" icon="ios-search" @click="loadListData">查找</Button>
            </FormItem>
          </Form>
        </Col>

      </Row>
      <!--列表-->
      <Row justify="center" align="middle">
        <div style="margin-top:20px">
          <Table border :loading="loading" :columns="columns" :data="tableData" max-height="690"
                 @on-selection-change="handleSelectionChange">
            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px" @click="handleShowEditDialog(row)">编辑
              </Button>
              <Poptip
                confirm
                title="您确认删除这些信息吗?"
                transfer
                @on-ok="handleRemove(row)">
                <Button type="error" size="small">删除</Button>
              </Poptip>
            </template>
            <template slot-scope="{ row, index }" slot="customer">
              {{row.customer.name}}
            </template>
            <template slot-scope="{ row, index }" slot="seller">
              {{row.seller.username}}
            </template>
            <template slot-scope="{ row, index }" slot="tenant">
              {{row.tenant.companyName}}
            </template>
          </Table>
        </div>
        <!--分页-->
        <div style="overflow: hidden">
          <div style="float: right;">
            <Page
              :total="total"
              show-total
              :page-size="pageSize"
              :page-size-opts="[5,10,20]"
              :current="page"
              show-sizer
              transfer
              show-elevator
              class-name="page_class"
              style="margin-top: 10px;"
              @on-change="handleCurrentChange"
              @on-page-size-change="handleSizeChange"
            />
          </div>
        </div>
        <!--添加/编辑弹出框-->
        <Modal v-model="dialogFormVisible" title="添加信息" class-name="vertical-center-modal" footer-hide draggable
               :styles="{top: '200px'}">
          <Form ref="addForm" :model="addForm" :rules="rules" :label-width="80">
            <FormItem v-show="false" prop="id">
              <Input v-model="addForm.id" type="text"></Input>
            </FormItem>
            <FormItem label="客户姓名" prop="name">
              <!--增添客户姓名带搜索下拉框 配置搜索方法 dataFilter-->
              <el-select v-model="addForm.customer" filterable placeholder="请选择客户姓名">
                <el-option
                  v-for="item in customers"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </FormItem>
            <FormItem label="签定时间" prop="signTime">
              <div class="block" >
                <el-date-picker
                  v-model="addForm.signTime"
                  align="right"
                  type="date"
                  placeholder="请选择日期"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </div>
            </FormItem>
            <FormItem label="营销人员" prop="username">
              <!--营销人员姓名搜索选择下拉框-->
              <el-select v-model="addForm.seller.id" filterable placeholder=""  >
                <el-option
                  v-for="item in sellers"
                  :key="item.id"
                  :label="item.username"
                  :value="item.id">
                </el-option>
              </el-select>
              <!--<Input v-model="addForm.seller.username" placeholder="请输入营销人员姓名"></Input>-->
            </FormItem>
            <FormItem label="订金金额" prop="amount">
              <Input v-model="addForm.amount" placeholder="请输入订金金额"></Input>
            </FormItem>
            <FormItem label="摘要" prop="intro">
              <Input v-model="addForm.intro" placeholder="请输入摘要"></Input>
            </FormItem>

            <FormItem>
              <Button type="primary" @click="submitForm('addForm')">确认</Button>
              <Button style="margin-left: 8px" @click="resetForm('addForm')">重置</Button>
            </FormItem>
          </Form>

        </Modal>
      </Row>
    </Card>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        page: 1, // 第几页
        pageSize: 10, // 每页条数
        total: 0,
        tableData: [],
        loading: false,
        row: [],
        customers:[],
        sellers:[],
        customer: {
          id:'',
          name:''
        },
        seller:{
          id:'',
          username:''
        },
        //时间选择
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
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        signTime:'',//签订时间测试
        searchForm: {
          customer:{
            id:'',
            name: ''
          },
          seller:{
            id:'',
            username:''
          },
          signTime:''
        },
        dialogFormVisible: false,

        addForm: {
          id: '',
          customer:{
            id:"",
            name: '',
          },
          signTime:'',
          seller:{
            id:'',
            username:''
          },
          amount:'',
          intro: ''
        },
        columns: [
          {
            type: 'selection',
            width: 0,
            align: 'center'
          },
          {
            title: '序号',
            type: 'index',
            width: 80,
            align: 'center'
          },
          {
            title: '订单编号',
            width: 120,
            align: 'center',
            key: 'sn'
          },
          {
            title: '客户姓名',
            width: 100,
            align: 'center',
            slot: 'customer'
          },
          {
            title: '签订时间',
            width: 180,
            align: 'center',
            key: 'signTime'
          },
          {
            title: '营销人员',
            width: 100,
            align: 'center',
            slot: 'seller'
          },
          {
            title: '订金金额',
            width: 100,
            align: 'center',
            key: 'amount'
          },
          {
            title: '摘要',
            width: 100,
            align: 'center',
            key: 'intro'
          },
          {
            title: '所属公司',
            width: 150,
            align: 'center',
            slot: 'tenant'
          },

          {
            title: '操作',
            slot: 'action',
            align: 'center',
            width: 180
          }
        ],
        rules: {
          // name: [
          //   { required: true, message: '请输入客户姓名', trigger: 'blur' }
          // ],
          // signTime: [
          //   { required: true, message: '请选择签订时间', trigger: 'blur' }
          // ],
          // username: [
          //   { required: true, message: '请输入营销人员姓名', trigger: 'blur' }
          // ],
          // totalAmount: [
          //   { required: true, message: '请输入订金金额', trigger: 'blur' }
          // ],
          intro: [
            { required: true, message: '请输入订单摘要', trigger: 'change' }
          ],
        }
      }
    },
    mounted() {
      this.$Notice.config({/*统一配置右侧弹出的位置，延迟关闭时间*/
        top: 100,
        duration: 3
      })
      this.loadListData()
    },
    methods: {
      click_enter() { /* 键盘事件,调用查找方法*/
        this.loadListData()
      },
      // 显示添加弹窗
      handleShowAddDialog() {
        this.$http.get("/customer/findAll").then(res=>{
          console.debug(res.data.data)
          this.customers = res.data.data;
        })
        this.$http.get("/employee/findAll").then(res=>{
          console.debug(res.data.data)
          this.sellers = res.data.data;
        })
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
      },
      // 编辑显示弹窗
      handleShowEditDialog(row) {
        this.$http.get("/customer/findAll").then(res=>{
          console.debug(res.data.data)
          this.customers = res.data.data;
        })
        this.$http.get("/employee/findAll").then(res=>{
          console.debug(res.data.data)
          this.sellers = res.data.data;
        })
        // 数据回显
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row)/* 复制*/
      },

      submitForm(formName) { /* 确认保存*/
        var refs = this.$refs
        var http = this.$http
        var Message = this.$Message
        refs[formName].validate((valid) => {
          const param = Object.assign({}, this.addForm)
          let url = '/order/save'
          if (this.addForm.id) {
            url = '/order/update'
          }
          if (valid) {
            http.post(url, param).then(res => {
              if (res.data.success) {
                this.dialogFormVisible = false
                this.loadListData()
                Message.success(res.data.message)
              } else {
                Message.error('操作失败[' + res.data.message + ']')
              }
            }).catch(error => {
              Message.error('操作失败[' + error.message + ']')
            })
          } else {
            Message.error('请按照规则填写表单')
          }
        })
      },
      resetForm(formName) { /* 重置*/
        var refs = this.$refs
        refs[formName].resetFields()/* 清空*/
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
        var http = this.$http
        var Notice = this.$Notice
        const param = { ids: ids }
        http.post('/order/batchDelete', param).then(res => {
          if (res.data.success) {
            Notice.success({
              title: '操作成功通知',
              desc: '恭喜你，你已经成功删除该项'
            })
            this.loadListData()
            this.row = [];
          } else {
            Notice.error({
              title: '操作失败通知',
              desc: res.data.message
            })
          }
        }).catch(error => {
          Notice.error({
            title: '操作失败通知',
            desc: error.message
          })
        })
      },

      handleRemove(row) {
        var http = this.$http
        var Notice = this.$Notice
        http.delete('/order/delete/' + row.id).then(res => {
          if (res.data.success) {
            this.loadListData()
            Notice.success({
              title: '操作成功通知',
              desc: '恭喜你，你已经成功删除该项'
            })
          } else {
            Notice.error({
              title: '操作失败通知',
              desc: res.data.message
            })
          }
        }).catch(error => {
          Notice.error({
            title: '操作失败通知',
            desc: error.message
          })
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
      loadListData() {
        var http = this.$http
        var Message = this.$Message
        this.loading = true
        // vue加载完成，发送ajax请求动态获取数据
        //分页
        const param = {
          'currentPage': this.page,
          'pageSize': this.pageSize,
          'keyword': this.searchForm.name
        }
        http.post('/order/selectForPage', param).then(res => {
          if (res.data.success) {
            this.tableData = res.data.data.list
            this.total = res.data.data.totalRows
            this.page = res.data.data.currentPage
            this.loading = false
          } else {
            Message.error('查询失败[' + res.data.message + ']')
          }
        }).catch(error => {
          Message.error('查询失败[' + error.message + ']')
        })
      }
    }

  }
</script>

