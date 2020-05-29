<template>
  <div style="height: calc(100vh - 84px);">
    <Card style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"/>
        信息管理
      </p>
      <Row>
        <Col span="5">
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
        <Col span="19">
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


      </Row>

      <Row justify="center" align="middle">
        <div style="margin-top:20px">
          <Table border :loading="loading" :columns="columns" :data="tableData" max-height="690"
                 @on-selection-change="handleSelectionChange">
            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px" @click="handleShowEditDialog(row,index)">查看
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

      </Row>
    </Card>
  </div>
</template>
<script>
  import expandRow from './table-expand.vue';

  export default {
    components: {expandRow},
    data() {
      return {
        page: 1, // 第几页
        pageSize: 5, // 每页条数
        total: 0,
        tableData: [],
        loading: false,
        row: [],
        searchForm: {
          name: ''
        },

        columns: [
          {
            align: "center",
            width: 50,
            type: 'expand',
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
            title: '操作人',
            key: 'opuser',
            align: 'center'
          },
          {
            title: '操作IP地址',
            key: 'opip',
            align: 'center'
          },
          {
            title: '请求路径',
            key: 'requesturi',
            align: 'center',
            ellipsis: true,
            width:150

          },
          {
            title: '执行方法',
            key: 'method',
            align: 'center',
            ellipsis: true,
            width:150
          },
          {
            title: '请求参数',
            key: 'params',
            align: 'center',
            ellipsis: true,
            width:150
          },
          {
            title: '请求时间',
            key: 'optime',
            align: 'center'
          },
          {
            title: '请求结果',
            key: 'result',
            align: 'center',
            ellipsis: true,
            width:150
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
      handleShowEditDialog(row,index){
      this.tableData[index]= $.extend({}, this.tableData[index],{_expanded:true})
      console.log(this.tableData)
      },
      click_enter() { /* 键盘事件,调用查找方法*/
        this.loadListData()
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
        http.post('/systemlog/batchDelete', param).then(res => {
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
        http.delete('/systemlog/delete/' + row.id).then(res => {
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
        const param = {
          'currentPage': this.page,
          'pageSize': this.pageSize,
          'keyword': this.searchForm.name
        }
        http.post('/systemlog/selectForPage', param).then(res => {
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
