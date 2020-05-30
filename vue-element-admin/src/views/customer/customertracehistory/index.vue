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
            <template slot-scope="{ row, index }" slot="traceuser">
              <span v-if="row.traceuser">{{row.traceuser.username}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="tenant">
              <span v-if="row.tenant">{{row.tenant.companyName}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="traceresult">
              <Tag color="success" v-if="row.traceresult =='优秀'">优秀</Tag>
              <Tag color="cyan" v-if="row.traceresult =='中等'">中等</Tag>
              <Tag color="error" v-if="row.traceresult =='较差'">较差</Tag>
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
              <Input v-model="addForm.id" type="text"/>
            </FormItem>
            <FormItem label="顾客" prop="customerId">
              <Select v-model="addForm.customerId"  placeholder="请选择相关值">
                <Option v-for="item in customterList" v-model="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </FormItem>
            <FormItem label="跟进人" prop="traceuserId">
              <Select v-model="addForm.traceuserId"  placeholder="请选择相关值">
                <Option v-for="item in traceuserList" v-model="item.id" :key="item.id">{{ item.username}}</Option>
              </Select>
            </FormItem>
            <FormItem label="跟进时间" prop="tracetime">
              <DatePicker v-model="addForm.tracetime" type="date" show-week-numbers placeholder="请选择时间" format="yyyy-MM-dd HH:mm"/>
            </FormItem>
            <FormItem label="跟进方式" prop="tracetype">
              <Select v-model="addForm.tracetype" >
                <Option  value="电话" >电话</Option>
                <Option  value="微信" >微信</Option>
                <Option  value="QQ" >QQ</Option>
                <Option  value="上门" >上门</Option>
                <Option  value="邮件" >邮件</Option>
              </Select>
            </FormItem>
            <FormItem label="跟进效果" prop="traceresult">
              <RadioGroup  v-model="addForm.traceresult">
                <Radio label="优秀">优秀</Radio>
                <Radio label="中等">中等</Radio>
                <Radio label="较差">较差</Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="跟进主题" prop="title">
              <Input v-model="addForm.title" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="备注" prop="remark">
              <Input v-model="addForm.remark" maxlength="100" show-word-limit type="textarea" placeholder="请输入备注" />
            </FormItem>
            <FormItem label="所属租户" prop="tenantId">
              <Select v-model="addForm.tenantId"  placeholder="请选择相关值">
                <Option v-for="item in tenantList" v-model="item.id" :key="item.id">{{ item.companyName}}</Option>
              </Select>
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
          traceuserId: '',
          tracetime: '',
          tracetype: '',
          traceresult: '',
          title: '',
          remark: '',
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
            title: '跟进人',
            slot: 'traceuser',
            align: 'center'
          },
          {
            title: '跟进时间',
            key: 'tracetime',
            align: 'center'
          },
          {
            title: '跟进方式',
            key: 'tracetype',
            align: 'center'
          },
          {
            title: '跟进效果',
            slot: 'traceresult',
            align: 'center'
          },
          {
            title: '跟进主题',
            key: 'title',
            align: 'center'
          },
          {
            title: '备注',
            key: 'remark',
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
        rules: {},
        customterList:[],
        traceuserList:[],
        tenantList:[]
      }
    },
    mounted() {
      var Notice = this.$Notice
      Notice.config({/*统一配置右侧弹出的位置，延迟关闭时间*/
        top: 100,
        duration: 3
      })
      this.loadListData()
      this.gettraceCustomer();
    },
    methods: {
      getMarketingEmployee (){/*获取市场专员*/
        this.$http.post('/customertracehistory/getMarketingEmployee',{departmentName:"市场部"}).then(res => {
          this.traceuserList = res.data.data;
        })
      },

      gettraceCustomer(){/*获取可跟进的顾客*/
        this.$http.post('/customertracehistory/gettraceCustomer',{status:"初始录入"}).then(res => {
          this.customterList = res.data.data;
        })
      },

      getAllTenant(){/*获取所有租户*/
        this.$http.post('/customertracehistory/getAllTenant').then(res => {
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
        this.getMarketingEmployee();
        this.gettraceCustomer();
        this.getAllTenant();
      },
      // 编辑显示弹窗
      handleShowEditDialog(row) {
        // 数据回显
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
        this.getMarketingEmployee();
        this.gettraceCustomer();
        this.getAllTenant();
        if(row.tenant){
          row.tenantId = row.tenant.id
        }
        if(row.customer){
          row.customerId = row.customer.id
        }
        if(row.traceuser){
          row.traceuserId = row.traceuser.id
        }
        this.addForm = Object.assign({}, row)/* 复制*/
      },

      submitForm(formName) { /* 确认保存*/
        var refs = this.$refs
        var http = this.$http
        var Message = this.$Message
        refs[formName].validate((valid) => {
          const param = Object.assign({}, this.addForm)
          if(param.customerId){
            param['customer'] ={id:param.customerId}
          }
          if(param.traceuserId){
            param['traceuser'] = {id:param.traceuserId}
          }
          if(param.tenantId){
            param['tenant'] = {id:param.tenantId}
          }
          delete param['tenantId'];
          delete param['traceuserId']
          delete param['customerId'];
          let url = '/customertracehistory/save'
          if (this.addForm.id) {
            url = '/customertracehistory/update'
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
        http.post('/customertracehistory/batchDelete', param).then(res => {
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
        http.delete('/customertracehistory/delete/' + row.id).then(res => {
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
        http.post('/customertracehistory/selectForPage', param).then(res => {
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
