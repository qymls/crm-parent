<template>
  <div style="height: calc(100vh - 84px);">
    <Card style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"/>
        合同明细信息管理
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
            <Button v-if="row.length>0" type="error" icon="ios-trash">批量删除</Button>
          </Poptip>
        </Col>
        <!--查询操作展示列-->
        <Col span="21">
          <Form ref="searchForm" :model="searchForm" inline style="margin-left: 20px;" @submit.native.prevent>
            <!-- 所属合同编号-->
            <FormItem prop="sn">
              <Input v-model="searchForm.contractSn" type="text" clearable style="cursor: pointer" placeholder="请输入查找合同编号" @on-enter="click_enter">
              </Input>
            </FormItem>
            <FormItem>
              <Button type="info" icon="ios-search" @click="loadListData">查找</Button>
            </FormItem>
          </Form>
        </Col>

      </Row>
      <!--列表-->
      <Row justify="center" align="middle" >
        <div style="margin-top:20px">
          <Table border :loading="loading" :columns="columns" :data="tableData"
                 @on-selection-change="handleSelectionChange"
                 :default-sort = "{key: 'payTime', order: 'descending'}"
                 max-height="690"
          >

            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px"
                      @click="handleShowEditDialog(row)">编辑</Button>
              <Poptip
                confirm
                title="您确认删除这些信息吗?"
                transfer
                @on-ok="handleRemove(row)">
                <Button type="error" size="small">删除</Button>
              </Poptip>

            </template>
            <template slot-scope="{ row, index }" slot="isPayment">
              <span v-if="row.isPayment ==0" style="color: red">未支付</span>
              <span v-if="row.isPayment ==1" style="color: green">已支付</span>
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
        <Modal v-model="dialogFormVisible" title="合同明细管理" class-name="vertical-center-modal" footer-hide draggable
               :styles="{top: '90px'}">
          <Form ref="addForm" :model="addForm" :rules="rules" :label-width="80">
            <!--id 值隐藏-->
            <FormItem v-show="false" prop="id">
              <Input v-model="addForm.id" type="text"></Input>
            </FormItem>
            <FormItem label="合同编号" prop="sn">
              <Input v-model="addForm.contractSn" placeholder="请输入合同编号"></Input>
            </FormItem>
            <FormItem label="付款时间" prop="payTime">
              <div class="block">
                <el-date-picker
                  v-model="addForm.payTime"
                  type="datetime"
                  placeholder="选择日期时间"
                  default-time="12:00:00">
                </el-date-picker>
              </div>
            </FormItem>
            <FormItem label="所占比例" prop="scale">
              <Input v-model="addForm.scale" placeholder="请输入所占比例(%)"></Input>
            </FormItem>
            <FormItem label="是否支付" prop="isPayment" >
              <RadioGroup  v-model="addForm.isPayment">
                <Radio label="0">未支付</Radio>
                <Radio label="1">已支付</Radio>
              </RadioGroup>
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
  import ElRadioGroup from "element-ui/packages/radio/src/radio-group";
  export default {
    components: {ElRadioGroup},
    data() {
      return {
        page: 1, // 第几页
        pageSize: 10, // 每页条数
        total: 0,
        tableData: [],
        loading: false,
        row: [],
        //时间选择
        payTime:'',//付款时间测试

        isPayment: 0,
        //查询
        searchForm: {
          contractSn:''
        },
        contract:{
          id:'',
          sn:''
        },
        dialogFormVisible: false,
        addForm: {
          id: '',
          contractSn:'',
          payTime:'',
          scale:'',
          isPayment:''

        },
        columns: [
          {
            type: 'selection',
            width: 60,
            align: 'center',
          },
          {
            title: '序号',
            type: 'index',
            width: 70,
            align: 'center'
          },
          {
            title: '合同编号',
            width: 250,
            align: 'center',
            key: 'contractSn',
            sortable:'true'
          },
          {
            title: '付款时间',
            width: 250,
            align: 'center',
            key: 'payTime',
            sortable:'true'
          },
          {
            title: '所占比例(%)',
            width: 180,
            align: 'center',
            key: 'scale',
            sortable:'true'
          },
          {
            title: '是否支付',
            width: 120,
            align: 'center',
            slot: 'isPayment',
            sortable:'true'
          },
          {
            title: '操作',
            slot: 'action',
            align: 'center',
          }
        ],
        /*添加 编辑规则*/
        rules: {}
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
      // 格式化支付状态
      formatterPay: function(row, column) {
        return row.isPayment == 0 ? '未支付' : '已支付'
      },
      click_enter() { /* 键盘事件,调用查找方法*/
        this.loadListData()
      },
      // 显示添加弹窗
      handleShowAddDialog() {
        // 清空表单
        this.dialogFormVisible = true;
        //重置数据
        // this.addForm.isPayment = 0;
        this.addForm.contractSn = ""
        this.$refs['addForm'].resetFields()/* 清空*/
      },
      // 编辑显示弹窗
      handleShowEditDialog(row) {
        this.dialogFormVisible = true
        //回显状态
        row.isPayment = row.isPayment.toString();

        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row)/* 复制*/
      },
      /*添加、更新 提交*/
      submitForm(formName) { /* 确认保存*/
        var refs = this.$refs
        var http = this.$http
        var Message = this.$Message
        refs[formName].validate((valid) => {
          const param = Object.assign({}, this.addForm)
          let url = '/contractitem/save'
          if (this.addForm.id) {//id存在则更新
            url = '/contractitem/update'
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
        this.$nextTick(() => {
          var refs = this.$refs
          refs[formName].resetFields()/* 清空*/
        })
      },
      handleSelectionChange(selection) {
        this.row = selection
      },
      //批量删除事件
      handleBatchRemove() {
        // 把对象数组转成 id数组
        const ids = this.row.map(function(obj, index, arr) {
          return obj.id
        })
        var http = this.$http
        var Notice = this.$Notice
        const param = { ids: ids }
        http.post('/contractitem/batchDelete', param).then(res => {
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
      /*列表删除数据*/
      handleRemove(row) {
        var http = this.$http
        var Notice = this.$Notice
        http.delete('/contractitem/delete/' + row.id).then(res => {
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
      /*处理每页展示页数改变*/
      handleSizeChange(val) {
        this.pageSize = val
        this.loadListData()
      },
      /*处理当前页改变*/
      handleCurrentChange(val) {
        this.page = val
        this.loadListData()
      },
      /*列表*/
      loadListData() {
        var http = this.$http
        var Message = this.$Message
        this.loading = true
        // vue加载完成，发送ajax请求动态获取数据 分页查询
        const param = {
          'currentPage': this.page,
          'pageSize': this.pageSize,
          'keyword': this.searchForm.contractSn
        }
        http.post('/contractitem/selectForPage', param).then(res => {
          if (res.data.success) {
            console.log(res);
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

