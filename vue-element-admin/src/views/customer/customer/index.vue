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
              <Input v-model="searchForm.name" type="text" clearable style="cursor: pointer" placeholder="请输入查找的名称"
                     @on-enter="click_enter">
              </Input>
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
               :styles="{top: '200px'}" width="1200">
          <Form ref="addForm" :model="addForm" :rules="rules" :label-width="80" inline>
            <FormItem v-show="false" prop="id">
              <Input v-model="addForm.id" type="text"/>
            </FormItem>
            <Row>
              <form-item label="基本信息" required/>
            </Row>
            <FormItem label="客户姓名" prop="name">
              <Input v-model="addForm.name" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="客户年龄" prop="age">
              <Input v-model="addForm.age" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="客户性别" prop="sex">
              <RadioGroup  v-model="addForm.sex">
                <Radio label="true">男</Radio>
                <Radio label="false">女</Radio>
              </RadioGroup>
            </FormItem>
            <Row>
              <form-item label="联系方式" required/>
            </Row>
            <FormItem label="电话号码" prop="tel">
              <Input v-model="addForm.tel" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="邮箱" prop="email">
              <Input v-model="addForm.email" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="QQ" prop="qq">
              <Input v-model="addForm.qq" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="微信" prop="wechat">
              <Input v-model="addForm.wechat" placeholder="请输入相关值"/>
            </FormItem>
            <Row>
              <form-item label="扩展信息"/>
            </Row>
            <FormItem label="营销人员" prop="seller">
              <Select v-model="addForm.sellerId" style="width:200px" placeholder="请选择相关值">
                <Option v-for="item in employeeList" v-model="item.id" :key="item.id">{{ item.username}}</Option>
              </Select>
            </FormItem>
            <FormItem label="职业" prop="job">
              <Select v-model="addForm.job" style="width:200px" placeholder="请选择相关值">
                <Option v-for="item in jobList" v-model="item.id" :key="item.id">{{ item.name}}</Option>
              </Select>
            </FormItem>
            <FormItem label="收入水平" prop="salarylevel">
              <Input v-model="addForm.salarylevel" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="客户来源" prop="customersource">
              <Input v-model="addForm.customersource" placeholder="请输入相关值"/>
            </FormItem>
            <Row>
              <form-item label="扩展信息"/>
            </Row>
            <FormItem label="所属租户" prop="tenant">
              <Select v-model="addForm.tenantId" style="width:200px" placeholder="请选择相关值">
                <Option v-for="item in tenantList" v-model="item.id" :key="item.id">{{ item.companyName}}</Option>
              </Select>
            </FormItem>

            <FormItem label="顾客状态" prop="status">
              <Select v-model="addForm.status" style="width:200px" placeholder="请选择相关值">
                <Option value="初始录入">初始录入</Option>
                <Option value="初始录入">正常客户</Option>
              </Select>
            </FormItem>
            <Row/>
            <FormItem label="备注" prop="remark">
              <Input v-model="addForm.remark" maxlength="100" show-word-limit type="textarea" placeholder="请输入相关值"/>
            </FormItem>
            <FormItem label="成功率" prop="successrate" style="width: 500px">
              <Slider v-model="addForm.successrate" show-input />
            </FormItem>
            <diV style="text-align: center">
            <FormItem>
              <Button type="primary" @click="submitForm('addForm')">确认保存</Button>
              <Button style="margin-left: 8px" @click="resetForm('addForm')">重置</Button>
            </FormItem>
            </diV>
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
          name: '',
          age: '',
          sex: '',
          tel: '',
          email: '',
          qq: '',
          wechat: '',
          sellerId: '',
          job: '',
          salarylevel: '',
          customersource: '',
          tenantId: '',
          successrate: 0,
          remark: '',
          status: '',
          state: '',
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
            title: '客户姓名',
            key: 'name',
            align: 'center'
          },
          {
            title: '客户年龄',
            key: 'age',
            align: 'center'
          },
          {
            title: '客户性别',
            key: 'sex',
            align: 'center'
          },
          {
            title: '电话号码',
            key: 'tel',
            align: 'center'
          },
          {
            title: '营销人员',
            key: 'seller',
            align: 'center'
          },

          {
            title: '客户来源',
            key: 'customersource',
            align: 'center'
          },
          {
            title: '备注',
            key: 'remark',
            align: 'center'
          },
          {
            title: '客户状态',
            key: 'status',
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
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ],
          tel: [
            {required: true, message: '请填写联系电话', trigger: 'blur'}
          ],
          age: [
            {required: true, message: '请填写年龄', trigger: 'blur'}
          ]
        },
        employeeList:[],
        tenantList:[],
        jobList:[],
      }
    },
    mounted() {
      var Notice = this.$Notice
      Notice.config({/*统一配置右侧弹出的位置，延迟关闭时间*/
        top: 100,
        duration: 3
      })
      this.loadListData()
    },
    methods: {
      getAllJob(){
        var param = "职位"
        this.$http.post('/customer/getAllJob/'+param).then(res => {
          this.jobList = res.data.data;
        })
      },
      getAllEmployeebyDepartmentName(){
        var param = "营销部"
        this.$http.post('/customer/getAllEmployeebyDepartmentName/'+param).then(res => {
          this.employeeList = res.data.data;
        })
      },
      getAllTenant(){
        this.$http.post('/customer/getAllTenant').then(res => {
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
        this.getAllEmployeebyDepartmentName();
        this.getAllTenant();
      },
      // 编辑显示弹窗
      handleShowEditDialog(row) {
        console.log(row)
        // 数据回显
        this.dialogFormVisible = true
        this.$refs['addForm'].resetFields()/* 清空*/
        this.getAllEmployeebyDepartmentName();
        this.getAllTenant();
        row.sex = row.sex.toString();
        row.age = row.age.toString();
        row.successrate = Number(row.successrate)
        if(row.seller){
          row.sellerId = row.seller.id
        }
        if(row.tenant){
          row.tenantId = row.tenant.id
        }

        this.addForm = Object.assign({}, row)/* 复制*/
        console.log(this.addForm)
      },

      submitForm(formName) { /* 确认保存*/
        var refs = this.$refs
        var http = this.$http
        var Message = this.$Message
        refs[formName].validate((valid) => {
          const param = Object.assign({}, this.addForm)
          param['seller'] = {id:param.sellerId}
          param['tenant'] = {id:param.tenantId}
          delete param['tenantId']
          delete param['sellerId']
          let url = '/customer/save'
          if (this.addForm.id) {
            url = '/customer/update'
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
        http.post('/customer/batchDelete', param).then(res => {
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
        http.delete('/customer/delete/' + row.id).then(res => {
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
        http.post('/customer/selectForPage', param).then(res => {
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
