<template>
  <div style="height: calc(100vh - 84px);">
    <Card style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"/>
        信息管理
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
        <Col span="21">
          <Form ref="searchForm" :model="searchForm" inline style="margin-left: 20px;" @submit.native.prevent>
            <FormItem prop="name">
              <Select v-model="searchForm.name" style="width: 200px" placeholder="请选择相关值" clearable>
                <Option v-for="item in customterList" v-model="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </FormItem>

            <FormItem>
              <Button type="info" icon="ios-search" @click="loadListData">查找</Button>
            </FormItem>
          </Form>
        </Col>

      </Row>

      <Row justify="center" align="middle">
        <div style="margin-top:20px">
          <Table border :loading="loading" :columns="columns" :data="tableData" max-height="690"
                 @on-selection-change="handleSelectionChange">
            <template slot-scope="{ row, index }" slot="customer">
              <span v-if="row.customer">{{row.customer.name}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="transuser">
              <span v-if="row.transuser">{{row.transuser.username}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="oldseller">
              <span v-if="row.oldseller">{{row.oldseller.username}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="newseller">
              <span v-if="row.newseller">{{row.newseller.username}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="tenant">
              <span v-if="row.tenant">{{row.tenant.companyName}}</span>
            </template>
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
          </Table>
        </div>
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
        <Modal v-model="dialogFormVisible" title="添加信息" class-name="vertical-center-modal" footer-hide draggable
               :styles="{top: '200px'}">
          <Form ref="addForm" :model="addForm" :rules="rules" :label-width="80">
            <FormItem v-show="false" prop="id">
              <Input v-model="addForm.id" type="text"></Input>
            </FormItem>
            <FormItem label="顾客" prop="customerId">
              <Select v-model="addForm.customerId" placeholder="请选择相关值">
                <Option v-for="item in customterList" v-model="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </FormItem>
            <FormItem label="移交人员" prop="transuserId">
              <Select v-model="addForm.transuserId" placeholder="请选择相关值">
                <Option v-for="item in transuserList" v-model="item.id" :key="item.id">{{ item.username }}</Option>
              </Select>
            </FormItem>
            <FormItem label="老专员" prop="oldsellerId">
              <Select v-model="addForm.oldsellerId" placeholder="请选择相关值">
                <Option v-for="item in employeeList" v-model="item.id" :key="item.id">{{ item.username }}</Option>
              </Select>
            </FormItem>
            <FormItem label="新专员" prop="newsellerId">
              <Select v-model="addForm.newsellerId" placeholder="请选择相关值">
                <Option v-for="item in employeeList" v-model="item.id" :key="item.id">{{ item.username }}</Option>
              </Select>
            </FormItem>
            <FormItem label="所属租户" prop="tenantId">
              <Select v-model="addForm.tenantId" placeholder="请选择相关值">
                <Option v-for="item in tenantList" v-model="item.id" :key="item.id">{{ item.companyName}}</Option>
              </Select>
            </FormItem>
            <FormItem label="移交时间" prop="transtime">
              <DatePicker v-model="addForm.transtime" type="date" show-week-numbers placeholder="请选择时间"
                          format="yyyy-MM-dd HH:mm"/>
            </FormItem>
            <FormItem label="移交原因" prop="transreason">
              <Input v-model="addForm.transreason" maxlength="100" show-word-limit type="textarea"
                     placeholder="请输入相关值"/>
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
        searchForm: {
          name: ''
        },
        dialogFormVisible: false,
        addForm: {
          id: '',
          customerId: '',
          transuserId: '',
          transtime: '',
          oldsellerId: '',
          newsellerId: '',
          transreason: '',
          tenantId: '',
        },
        columns: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            title: '序号',
            type: 'index',
            width: 100,
            align: 'center'
          },
          {
            title: '顾客',
            slot: 'customer',
            align: 'center'
          },
          {
            title: '移交人员',
            slot: 'transuser',
            align: 'center'
          },
          {
            title: '移交时间',
            key: 'transtime',
            align: 'center'
          },
          {
            title: '老市场专员',
            slot: 'oldseller',
            align: 'center'
          },
          {
            title: '新市场专员',
            slot: 'newseller',
            align: 'center'
          },
          {
            title: '移交原因',
            key: 'transreason',
            align: 'center'
          },
          {
            title: '所属租户',
            slot: 'tenant',
            align: 'center'
          },
          {
            title: '操作',
            slot: 'action',
            align: 'center',
            width: 150
          }
        ],
        rules: {
          customerId: [
            { required: true, message: '顾客不能为空', trigger: 'change' }
          ],
          transuserId: [
            { required: true, message: '移交人员不能为空', trigger: 'change' }
          ],
          oldsellerId: [
            { required: true, message: '老专员不能为空', trigger: 'change' }
          ],
          newsellerId: [
            { required: true, message: '新专员不能为空', trigger: 'change' }
          ],
          tenantId: [
            { required: true, message: '所属租户不能为空', trigger: 'change' }
          ],
        },
        customterList: [],
        transuserList: [],/*移交人员*/
        employeeList: [],/*市场部人员*/
        tenantList: [],
      }
    },
    mounted() {
      var Notice = this.$Notice
      Notice.config({/*统一配置右侧弹出的位置，延迟关闭时间*/
        top: 100,
        duration: 3
      })
      this.loadListData();
      this.gettraceCustomerNoResource();
    },
    methods: {
      getTransuserEmployee() {/*获取市场部经理*/
        this.$http.post('/customertransfer/getTransuserEmployee', {departmentName: "市场部"}).then(res => {
          for (let i = 0; i < res.data.data.length; i++) {
            res.data.data[i].id =  res.data.data[i].id.toString();
          }
          this.transuserList = res.data.data;
        })
      },
      gettraceCustomerNoResource() {/*获取不再资源池的顾客*/
        this.$http.post('/customertransfer/gettraceCustomerNoResource', {state: true}).then(res => {
          for (let i = 0; i < res.data.data.length; i++) {
            res.data.data[i].id =  res.data.data[i].id.toString();
          }
          this.customterList = res.data.data;
        })
      },
      getMarketingEmployee() {/*获取市场专员*/
        this.$http.post('/customertransfer/getMarketingEmployee', {departmentName: "市场部"}).then(res => {
          for (let i = 0; i < res.data.data.length; i++) {
            res.data.data[i].id =  res.data.data[i].id.toString();
          }
          this.employeeList = res.data.data;
        })
      },
      getAllTenant() {/*获取所有租户*/
        this.$http.post('/customertransfer/getAllTenant').then(res => {
          for (let i = 0; i < res.data.data.length; i++) {
            res.data.data[i].id =  res.data.data[i].id.toString();
          }
          this.tenantList = res.data.data;
        })
      },
      click_enter() { /* 键盘事件,调用查找方法*/
        this.loadListData()
      },
      // 显示添加弹窗
      handleShowAddDialog() {
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
        this.getTransuserEmployee();
        this.gettraceCustomerNoResource();
        this.getMarketingEmployee();
        this.getAllTenant();
      },
      // 编辑显示弹窗
      handleShowEditDialog(row) {
        // 数据回显
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
        this.getTransuserEmployee();
        this.gettraceCustomerNoResource();
        this.getMarketingEmployee();
        this.getAllTenant();
        if (row.tenant) {
          row.tenantId = row.tenant.id.toString()
        }
        if (row.customer) {
          row.customerId = row.customer.id.toString()
        }
        if (row.transuser) {
          row.transuserId = row.transuser.id.toString()
        }
        if (row.oldseller) {
          row.oldsellerId = row.oldseller.id.toString()
        }
        if (row.newseller) {
          row.newsellerId = row.newseller.id.toString()
        }
        this.addForm = Object.assign({}, row)/* 复制*/
      },

      submitForm(formName) { /* 确认保存*/
        var refs = this.$refs
        var http = this.$http
        var Message = this.$Message
        refs[formName].validate((valid) => {
          const param = Object.assign({}, this.addForm)
          if (param.customerId) {
            param['customer'] = {id: param.customerId}
          }
          if (param.tenantId) {
            param['tenant'] = {id: param.tenantId}
          }
          if (param.transuserId) {
            param['transuser'] = {id: param.transuserId}
          }
          if (param.oldsellerId) {
            param['oldseller'] = {id: param.oldsellerId}
          }
          if (param.newsellerId) {
            param['newseller'] = {id: param.newsellerId}
          }
          delete param['tenantId'];
          delete param['customerId'];
          delete param['transuserId'];
          delete param['oldsellerId'];
          delete param['newsellerId'];
          let url = '/customertransfer/save'
          if (this.addForm.id) {
            url = '/customertransfer/update'
          }
          if (valid) {
            http.post(url, param).then(res => {
              if (res.data.msg) {
                Message.error(res.data.msg)
              } else {
                if (res.data.success) {
                  this.dialogFormVisible = false
                  this.loadListData()
                  Message.success(res.data.message)
                } else {
                  Message.error('操作失败[' + res.data.message + ']')
                }
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
        const ids = this.row.map(function (obj, index, arr) {
          return obj.id
        })
        var http = this.$http
        var Notice = this.$Notice
        const param = {ids: ids}
        http.post('/customertransfer/batchDelete', param).then(res => {
          if (res.data.msg) {
            Notice.error({
              title: '操作失败通知',
              desc: res.data.msg
            })
          } else {
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
        http.delete('/customertransfer/delete/' + row.id).then(res => {
          if (res.data.msg) {
            Notice.error({
              title: '操作失败通知',
              desc: res.data.msg
            })
          } else {
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
        var Notice = this.$Notice
        this.loading = true
        // vue加载完成，发送ajax请求动态获取数据
        const param = {
          'currentPage': this.page,
          'pageSize': this.pageSize,
          'keyword': this.searchForm.name
        }
        http.post('/customertransfer/selectForPage', param).then(res => {
          if (res.data.msg) {
            Notice.error({
              title: '操作失败通知',
              desc: res.data.msg
            })
          } else {
            if (res.data.success) {
              this.tableData = res.data.data.list
              this.total = res.data.data.totalRows
              this.page = res.data.data.currentPage
              this.loading = false
            } else {
              Message.error('查询失败[' + res.data.message + ']')
            }
          }
        }).catch(error => {
          Message.error('查询失败[' + error.message + ']')
        })
      }
    }

  }
</script>
