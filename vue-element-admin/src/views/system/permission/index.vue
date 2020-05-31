<style>
  .page_class .ivu-icon {
    line-height: unset;
  }
</style>
<template>
  <div style="height: calc(100vh - 84px);">
    <Card  style="height: 100%">
      <p slot="title">
        <Icon type="ios-list-box-outline" size="20"></Icon>
        {{title}}
      </p>

      <Row>
        <i-col span="3">
          <i-button type="primary" icon="md-add" @click="newAdd">添加</i-button>
          <Poptip
            confirm
            placement="right"
            transfer
            title="您确认删除这些信息吗?"
            @on-ok="deletePermission">
            <i-button v-if="rows.length>0" type="error" icon="ios-trash">删除</i-button>
          </Poptip>
        </i-col>

        <i-col span="21">
          <i-Form ref="formInline" :model="formInline" inline style="margin-left: 20px;" @submit.native.prevent>
            <Form-Item prop="name">
              <i-Input type="text" v-model="formInline.name" clearable style="cursor: pointer" placeholder="请输入查找的名称" @on-enter="click_enter">
              </i-Input>
            </Form-Item>

            <Form-Item>
              <i-Button type="info" icon="ios-search" @click="handleSubmit('formInline')">查找</i-Button>
            </Form-Item>
          </i-Form>
        </i-col>

      </Row>

      <Row justify="center" align="middle">
        <div style="margin-top:20px">
          <i-Table :columns="columns" :data="PermissionData" border max-height="650"
                   @on-selection-change="deleteRows"
                   @on-row-dblclick="updateModelShow">
            <template slot-scope="{ row, index }" slot="menuName">
              {{getMenuName(row)}}
            </template>
          </i-Table>
        </div>
        <div style="margin: 10px;overflow: hidden">
          <div style="float: right;">
            <Page :total="total" show-total :page-size="pageSize" :page-size-opts="[5,10,20]" :current="page"
                  show-sizer transfer show-elevator
                  @on-change="changePage" @on-page-size-change="sizeChange"
                  class-name="page_class" style="margin-top: 10px;"></Page>
          </div>
        </div>
        <Modal title="添加信息" v-model="updateModel" class-name="vertical-center-modal" footer-hide draggable>
          <i-Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
            <Form-Item prop="id" v-show=false>
              <input type="text" v-model="formValidate.id"/>
            </Form-Item>
            <Form-Item label="name" prop="name">
              <i-Input v-model="formValidate.name" placeholder="请输入相关值"></i-Input>
            </Form-Item>
            <Form-Item label="url" prop="url">
              <i-Input v-model="formValidate.url" placeholder="请输入相关值"></i-Input>
            </Form-Item>
            <Form-Item label="sn" prop="sn">
              <i-Input v-model="formValidate.sn" placeholder="请输入相关值"></i-Input>
            </Form-Item>
            <Form-Item label="descs" prop="descs">
              <i-Input v-model="formValidate.descs" placeholder="请输入相关值"></i-Input>
            </Form-Item>
            <Form-Item label="menuId">
              <Cascader filterable :data="menuItem" v-model="menuValue"></Cascader>
            </Form-Item>
            <Form-Item>
              <i-Button type="primary" @click="handleSubmitUpdate('formValidate')">确认</i-Button>
              <i-Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</i-Button>
            </Form-Item>
          </i-Form>

        </Modal>

      </Row>

    </Card>
  </div>
</template>
<script>
  export default({
    data: function () {
      return {
        title: "信息管理",
        rows: [],
        updateModel: false,
        formValidate: {
          id: '',
          name: '',
          url: '',
          descs: '',
          sn: '',
          menuId: '',

        },
        ruleValidate: {
          name: [
            {required: true, message: '请输入对应的值', trigger: 'blur'},
          ],
          url: [
            {required: true, message: '请输入对应的值', trigger: 'blur'},
          ],
          sn: [
            {required: true, message: '请输入对应的值', trigger: 'blur'},
          ],
        },
        formInline: {
          name: '',
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
            align: 'center',
          },
          {
            title: 'name',
            key: 'name',
          },
          {
            title: 'url',
            key: 'url',
          },
          {
            title: 'descs',
            key: 'descs',
          },
          {
            title: 'sn',
            key: 'sn',
          },
          {
            title: '菜单名称',
            slot: 'menuName',
          },
        ],
        PermissionData: [],
        total: 0,
        page: 1,/*当前页默认为1*/
        pageSize: 10,/* 默认5条*/
        menuItem: [],/*查询所有最后一级菜单*/
        menuValue:[]
      }
    },
    created() {
      this.$Notice.config({/*统一配置右侧弹出的位置，延迟关闭时间*/
        top: 100,
        duration: 3
      })
      this.getFirstMenuData(this.page, this.pageSize);
    },
    methods: {
      findMenuItem() {/*查询所有最后一级菜单*/
        var $page = this;
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/permission/findAllMenuItem",
          dataType: 'json',
          async: false,/*取消异步加载*/
          success: function (result) {
            $page.formatallmenudata(result);
            $page.menuItem = result;
          }
        });
      },
      formatallmenudata(data){
        for (let i = 0; i < data.length; i++) {
          if (data[i].children && data[i].children.length > 0) {
            data[i] = $.extend({},data[i],{label:data[i].name,value:data[i].id.toString()});
            this.formatallmenudata(data[i].children);
          } else {
            data[i] = $.extend({},data[i],{label:data[i].name,value:data[i].id.toString()});
          }
        }
      },
      getMenuName(row) {//格式化菜单
        if (row.menu) {
          return row.menu.name
        } else {
          return "暂无菜单"
        }
      },
      click_enter() {/*键盘事件,调用查找方法*/
        this.handleSubmit();
      },
      updateModelShow(data) {
        this.$refs['formValidate'].resetFields();/*清除model的表单数据,打开model就清空*/
        this.menuValue = [];
        this.findMenuItem();/*所有最后一级菜单*/
        this.updateModel = true;
        this.formValidate = data;
        if (data.menu) {/*如果有菜单，就回显*/
          //this.formValidate.menuId = data.menu.id
          var parentValueList = this.getAllMenuParent(data.menu.id);
          parentValueList.push(data.menu.id.toString())
          this.$nextTick(() => {/*必须放在这个里面，不然值不会刷新的*/
            this

          })
        }
      },
      getAllMenuParent(id){/*获取该菜单的所有父菜单*/
        var parentValueList = [];
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/permission/getAllMenuParent",
          data:{id:id},
          dataType: 'json',
          async: false,/*取消异步加载*/
          success: function (result) {
            for (let i = 0; i < result.length; i++) {
              parentValueList.push(result[i].toString())
            }
          }
        });
        return parentValueList;
      },
      handleSubmitUpdate: function (name) {//提交方法
        var refs = this.$refs;
        refs[name].validate((valid) => {
          if (valid) {
            var $page = this;
            var messagePage = this.$Message;
            var param = $.extend({}, this.formValidate)/*复制一份，因为要删除*/
            delete param["menu"]
            if (this.menuValue.length >0) {
              param["menu"] = {id:this.menuValue[this.menuValue.length-1]}
            }
            delete param["menuId"]
            var url;
            if (this.formValidate.id) {/*修改*/
              url = "/permission/update"
              param.action = "update"
            } else {/*添加*/
              var url = "/permission/save";
              param.action = "save";
            }
            $.ajax({
              type: "POST",
              contentType: "application/json",
              url: url,
              data: JSON.stringify(param),
              dataType: "json",
              async: false,/*取消异步加载*/
              traditional: true,//防止深度序列化
              success: function (result) {
                if (result.msg) {/*操作失败，无权限*/
                  messagePage.error(result.msg);
                } else {
                  $page.$Message.success('操作数据成功');
                  $page.updateModel = false;
                  $page.getFirstMenuData($page.page, $page.pageSize);/*修改完成后,刷新数据*/
                }
              }
            });
          } else {
            this.$Message.error("请按照表单要求填写");
          }
        })
      },

      handleReset: function (name) {//重置方法
        this.menuValue = [];
        var ref = this.$refs;
        ref[name].resetFields();
      },

      handleSubmit() {
        this.getFirstMenuData(this.page, this.pageSize)
      },
      changePage(page) {
        this.page = page/*改变就设置值*/
        this.getFirstMenuData(page, this.pageSize);
      },
      sizeChange(pageSize) {/*改变就设置值*/
        this.pageSize = pageSize
        this.getFirstMenuData(this.page, pageSize);/*改变后page默认会变成1*/
      },

      getFirstMenuData(page, pageSize) {
        var $page = this;
        var notice = this.$Notice;
        $.ajax({
          type: "POST",
          contentType: "application/json",
          url: "/permission/selectForPage",
          data: JSON.stringify({
            "keyword": this.formInline.name,
            "currentPage": page,
            "pageSize": pageSize
          }),
          dataType: 'json',
          traditional: true,//防止深度序列化
          async: false,/*取消异步加载*/
          success: function (result) {/*用了框架的*/
            if (result.msg) {/*操作失败，无权限*/
              notice.error({
                title: '通知提醒',
                desc: result.msg,
              });
            } else {
              $page.PermissionData = result.data.list;
              $page.total = result.data.totalRows;
              $page.page = result.data.currentPage/*处理一个小bug*/
            }

          }
        });
      },

      newAdd: function () {
        this.$refs['formValidate'].resetFields();/*清除model的表单数据,打开model就清空*/
        this.menuValue = [];
        this.findMenuItem();/*所有最后一级菜单*/
        this.updateModel = true;
      },
      deleteRows: function (selection) {
        this.rows = [];
        for (let i = 0; i < selection.length; i++) {
          this.rows.push(selection[i].id)
        }
      },

      deletePermission() {
        var $page = this;
        var notice = this.$Notice;
        $.ajax({
          type: "POST",
          contentType: "application/json",
          url: "/permission/batchDelete",
          data: JSON.stringify({"ids": this.rows}),
          dataType: 'json',
          traditional: true,//防止深度序列化
          async: false,/*取消异步加载*/
          success: function (result) {/*用了框架的*/
            if (result.msg) {/*操作失败，无权限*/
              notice.error({
                title: '通知提醒',
                desc: result.msg,
              });
            } else {
              if (result.success){
                notice.success({
                  title: '通知提醒',
                  desc: "删除成功",
                });
                $page.getFirstMenuData($page.page, $page.pageSize);/*修改完成后,刷新数据*/
                $page.rows = [];
              }else {
                notice.error({
                  title: '通知提醒',
                  desc: "删除失败"+result.message,
                });
              }

            }
          }
        });
      }
    }

  });
</script>
