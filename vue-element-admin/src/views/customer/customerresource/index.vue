<template>
  <div style="height: calc(100vh - 84px);">
    <Card style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"/>
        信息管理
      </p>
      <Row>
        <Col span="3">
          <Poptip
            confirm
            placement="right"
            transfer
            title="您确认将其移动到客户资源池吗?"
            @on-ok="movetoCustomter"
          >
            <Button type="info" icon="ios-trash">移出资源池</Button>
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
            <template slot-scope="{ row, index }" slot="status">
              <Tag color="success" v-if="row.status=='初始录入'">{{row.status}}</Tag>
              <Tag color="error" v-if="row.status=='正常客户'">{{row.status}}</Tag>
            </template>
            <template slot-scope="{ row, index }" slot="seller">
              <span v-if="row.seller">{{row.seller.username}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="sex">
              <span v-if="row.sex ==true">男</span>
              <span v-if="row.sex ==false">女</span>
            </template>
            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px" @click="handleShowEditDialog(row,index)">查看
              </Button>
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

      </Row>
    </Card>
  </div>
</template>
<script>
  import expandRow from './table-expand.vue';
  export default {
    components: { expandRow },
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
        columns: [
          {
            type: 'expand',
            width: 50,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  row: params.row
                }
              })
            }
          },
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
            slot: 'sex',
            align: 'center'
          },
          {
            title: '电话号码',
            key: 'tel',
            align: 'center'
          },
          {
            title: '营销人员',
            slot: 'seller',
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
            slot: 'status',
            align: 'center'
          },

          {
            title: '操作',
            slot: 'action',
            align: 'center',
            width: 150
          }
        ],
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
      movetoCustomter(){
        if (this.row.length>0){
          const ids = this.row.map(function (obj, index, arr) {
            return obj.id
          })
          this.$http.post('/customer/movetoCustomter',{ids:ids}).then(res => {
            if (res.data.success){
              this.$Message.success('移入成功');
              this.loadListData();
            }else {
              this.$Message.error('移入失败'+res.data.message);
            }
          })
        }else {
          this.$Message.error('请选择需要移回到顾客管理的客户');
        }
      },

      click_enter() { /* 键盘事件,调用查找方法*/
        this.loadListData()
      },
      handleShowEditDialog(row, index) {
        this.tableData.splice();
        this.tableData[index]._expanded = true
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].id != row.id) {/*将其他的都设置成false*/
            this.tableData[i]._expanded = false;
          }
        }
      },

      handleSelectionChange(selection) {
        this.row = selection
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
          'state':false,/*只查询在资源池的用户*/
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
