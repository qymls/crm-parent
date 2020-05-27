
<style>
  .page_class .ivu-icon {
    line-height: unset;
  }

  .roleTable_style .ivu-table-cell-expand .ivu-icon {
    line-height: unset;
  }

  .ivu-table .demo-table-info-cell-style {
    background-color: #2db7f5;
    color: #fff;
  }

  .header_close_style { /*Modal框右上角关闭按钮框*/
    font-size: 31px;
    color: #999;
    -webkit-transition: color .2s ease;
    transition: color .2s ease;
    position: relative;
    top: 1px;
    cursor: pointer;
  }

  .header_class_close {
    z-index: 1;
    font-size: 12px;
    position: absolute;
    right: 8px;
    top: 8px;
    overflow: hidden;
    cursor: pointer;
  }

  .rolelook_style .ivu-modal-body {
    max-height: 390px;
    overflow: auto;
  }

  /*    ::-webkit-scrollbar { !*不要滚动条*!
          width: 0;
      }*/
</style>
<template>
<div style="height: calc(100vh - 84px);">

  <Card style="height: 100%">
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
          @on-ok="deleteRole">
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
      <div style="margin-top:20px" class="roleTable_style">
        <i-Table :columns="columns" :data="RoleData" border max-height="650"
                 @on-selection-change="deleteRows"
                 @on-row-dblclick="updateModelShow">
          <template slot-scope="{ row, index }" slot="action">
            <Tooltip content="权限配置" transfer placement="right" style="cursor: pointer;">
              <Icon type="md-cog" @click="addRole(row.id,index)"></Icon>
            </Tooltip>
          </template>
          <template slot-scope="{ row, index }" slot="permission_list">
            <div style="overflow:hidden;text-overflow:ellipsis">{{getPermissionName(row)}}</div>
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
      <Modal v-model="updateModel" class-name="vertical-center-modal" draggable
             :scrollable="true" width="1250" :styles="{top: '20px'}" :closable="false">
        <div slot="header">
          <div class="header_class_close" @click="close_modal">
            <Icon type="ios-close" class="header_close_style"></Icon>
          </div>
          <h2>
            <Icon type="md-options"></Icon>
            权限控制
          </h2>
        </div>
        <i-Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" inline>
          <Form-Item prop="id" v-show=false>
            <Input type="text" v-model="formValidate.id"/>
          </Form-Item>
          <Form-Item label="名称" prop="name">
            <i-Input v-model="formValidate.name" placeholder="请输入相关值"></i-Input>
          </Form-Item>
          <Form-Item label="编码" prop="sn">
            <i-Input v-model="formValidate.sn" placeholder="请输入相关值"></i-Input>
          </Form-Item>
          <div style="margin-left: 80px;margin-left: 30px;margin-bottom: 20px;">
          <Input type="text" v-model="permissionSearchName" clearable style="cursor: pointer;width: 235px;" placeholder="请输入查找的权限名称"
                 @on-enter="search_permission" />
            <i-Button type="info" style="margin-left: 37px;" icon="ios-search" @click="handleSubmitPermission">查找</i-Button>
          </div>
          <Row>
            <i-col span="12">
              <div>
                <i-Table ref="selection" :columns="columnSource" :data="sourceData" border height="539"
                         @on-row-dblclick="changSetting" @on-select="changeSourceDataSelect"
                         @on-select-cancel="cancelSourceDataSelect"
                         @on-select-all="allSourceDataSelect"
                         @on-select-all-cancel="allCancelSourceDataSelect">
                </i-Table>
              </div>
            </i-col>
            <i-col span="12">
              <div>
                <i-Table :columns="columnTraget" :data="TragetData" border height="539"
                         class="tragetTable"
                         @on-row-click="changSettingReturn">
                </i-Table>
              </div>
            </i-col>

          </Row>
          <div style="margin: 10px;overflow: hidden">
            <div style="float: left;">
              <Page :total="roleSettingTotal" show-total :page-size="roleSettingPageSize"
                    :page-size-opts="[5,10,20]" :current="roleSettingPage"
                    show-sizer transfer show-elevator
                    @on-change="roleSettingChangePage" @on-page-size-change="roleSettingSizeChange"
                    class-name="page_class" style="margin-top: 10px;"></Page>
            </div>
          </div>

        </i-Form>
        <div slot="footer" style="text-align: center">
          <i-Button type="primary" @click="handleSubmitUpdate('formValidate')">确认保存</i-Button>
          <i-Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</i-Button>
        </div>
      </Modal>

    </Row>
    <Modal v-model="authority" draggable :scrollable="true" width="700" class="rolelook_style">
      <div slot="header">
        <h2>
          <Icon type="md-options"></Icon>
          菜单权限控制
        </h2>
      </div>
      <Row>
        <i-col span="12">
          <div>
            <Tree :data="authorityTree" transfer show-checkbox ref="tree"
                  @on-check-change="getAuthority" :render="renderContent"></Tree>
          </div>
        </i-col>
        <Divider type="vertical" style="height: auto;width: 2px"/>
        <i-col span="12">
          <div>
            <Tree :data="authorityTreeShow" :render="renderContent"></Tree>
          </div>
        </i-col>

      </Row>

      <div slot="footer">
        <i-button type="primary" @click="saveChang">确认保存</i-button>
      </div>

    </Modal>

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
          sn: '',
        },
        ruleValidate: {
          name: [
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
            type: 'expand',
            width: 50,
            render: (h, params) => {
              var permissions = this.getPermissionName(params.row).split(";")
              return h('div',
                [
                  permissions.map((item, index) => {
                    return h('Tag',
                      {
                        props: {color: "blue"},
                        style: {cursor: "pointer"}
                      }
                      , item)
                  })
                ]);
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
            align: 'center',
          },
          {
            title: 'name',
            key: 'name',
          },
          {
            title: 'sn',
            key: 'sn',
          },
          {
            title: '权限列表',
            slot: "permission_list",
            width: 200,
            ellipsis: true
          },
          {
            title: "操作",
            slot: "action",
            align: "center",
            width: 100
          },
        ],
        RoleData: [],
        total: 0,
        page: 1,/*当前页默认为1*/
        pageSize: 5,/* 默认5条*/
        roleSetting: false,
        columnSource: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            type: 'index',
            width: 60,
            align: 'center'
          },
          {
            title: '名称',
            key: 'name',
          },
          {
            title: '对应的资源',
            key: 'url',
          },
          {
            title: '对象的权限',
            key: 'sn',
          },
        ],
        sourceData: [],
        columnTraget: [
          {
            title: '名称',
            key: 'name',
          },
          {
            title: '对应的资源',
            key: 'url',
          },
          {
            title: '对象的权限',
            key: 'sn',
          },
        ],
        TragetData: [],
        roleSettingTotal: 0,/*权限选择时定的分页*/
        roleSettingPage: 1,
        roleSettingPageSize: 10,
        authority: false,
        /*权限树*/
        authorityTree: [],
        authorityTreeShow: [],
        menuIds: [],
        temp: [],/*临时数组*/
        tempindex: '',/*临时index用于记录当前点击的第几行*/
        roleid: '',
        permissionSearchName:''/*用于搜索权限*/

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
      handleSubmitPermission(){
        this.search_permission();
      },
      search_permission(){
        this.getAllPermission(this.roleSettingPage,this.roleSettingPageSize)
      },
      getPermissionName(row) {/*基础列表回显权限*/
        var roleMsg = '';
        if (row.permissionList.length > 0) {
          $.each(row.permissionList, function (i, o) {
            if (i == row.permissionList.length - 1) {
              roleMsg += o.name
            } else {
              roleMsg += o.name + ';'
            }
          })
          return roleMsg;
        }
        return "暂无权限"
      },
      changeSourceDataSelect(selection, row) {/*勾选多选框触发*/
        this.changSetting(row);
      },
      cancelSourceDataSelect(selection, row) {/*取消勾选，取消勾选后，目标的权限也应该删除*/
        for (let i = 0; i < this.TragetData.length; i++) {
          if (this.TragetData[i].id == row.id) {
            this.TragetData.splice(i, 1);/*!*移除*!*/

          }
        }

      },
      allSourceDataSelect(selection) {/*设置全选*/
        loop:for (let i = 0; i < selection.length; i++) {
          for (let j = 0; j < this.TragetData.length; j++) {
            if (selection[i].id == this.TragetData[j].id) {/*重复了，不添加*/
              continue loop;/*跳出到loop的地方去*/
            }
          }
          this.changSetting(selection[i]);/*未重复的新添加过去*/
        }
      },
      allCancelSourceDataSelect(selection) {/*取消全选,只有设置了全选才能取消全选*/
        for (let i = 0; i < this.sourceData.length; i++) {
          for (let j = 0; j < this.TragetData.length; j++) {
            if (this.sourceData[i].id == this.TragetData[j].id) {/*重复了，不添加*/
              this.TragetData.splice(j, 1);/*!*移除*!*/
            }
          }
        }
      },
      roleSettingChangePage(roleSettingPage) {/*权限翻页*/
        this.roleSettingPage = roleSettingPage/*改变就设置值*/
        this.getAllPermission(roleSettingPage, this.roleSettingPageSize);
      },
      roleSettingSizeChange(roleSettingPageSize) {/*权限的pageSize*/
        this.roleSettingPageSize = roleSettingPageSize
        this.getAllPermission(this.roleSettingPage, roleSettingPageSize);/*改变后page默认会变成1*/
      },
      changSetting(sourceData) {/*双击移动数据*/
        for (let i = 0; i < this.TragetData.length; i++) {
          if (this.TragetData[i].id == sourceData.id) {
            this.$Message['error']({
              background: true,
              content: '不能重复添加 [' + sourceData.name + '] 权限'
            });
            return
          }
        }
        this.TragetData.push(sourceData)/*添加到目标中,*/
        this.changeStyleAdd(sourceData)
        this.scrollToBottom();/*添加数据滚动条到底部*/
        this.getAllPermission(this.roleSettingPage, this.roleSettingPageSize);/*再次查询，同步权限勾选框*/
      },
      changeStyleAdd(sourceData) {/*给添加的数据添加一个样式*/
        for (let i = 0; i < this.TragetData.length; i++) {
          if (sourceData.id == this.TragetData[i].id) {
            this.TragetData[i] = $.extend({}, this.TragetData[i], {
              cellClassName: {
                name: "demo-table-info-cell-style",
                url: "demo-table-info-cell-style",
                sn: "demo-table-info-cell-style"
              }
            });
          }
        }
      },
      changSettingReturn(tragetData, index) {/*双击又返回去*/
        this.TragetData.splice(index, 1);
        this.getAllPermission(this.roleSettingPage, this.roleSettingPageSize);/*再次查询，同步权限勾选框*/
      },
      getAllPermission(roleSettingPage, roleSettingPageSize,) {/*获取所有权限*/
        var $page = this;
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/role/permission/findPageByQuery",
          data: {
            "keyword":this.permissionSearchName,
            "currentPage": roleSettingPage,
            "pageSize": roleSettingPageSize
          },
          dataType: "json",
          async: false,/*取消异步加载*/
          traditional: true,//防止深度序列化
          success: function (result) {
            if (result.list && result.list.length>0 ){
              var dataSource = [];
              for (let i = 0; i < result.list.length; i++) {
                result.list[i]._checked = false;
                dataSource.push(result.list[i])
              }
              $page.sourceData = dataSource;
              $page.roleSettingTotal = result.totalRows;
              $page.roleSettingPage = result.currentPage/*处理一个小bug*/
              $page.checkBooks();/*勾选上有权限的列表*/
            }else {/*这里需要特使出来pagelist全部为空*/
              $page.sourceData = []/*清空*/
              }
          }
        });
      },
      click_enter() {/*键盘事件,调用查找方法*/
        this.handleSubmit();
      },
      checkBooks() {/*对比源资源框，和以拥有框对比，有的话就勾选上*/
        for (let i = 0; i < this.sourceData.length; i++) {
          for (let j = 0; j < this.TragetData.length; j++) {
            if (this.sourceData[i].id == this.TragetData[j].id) {
              //this.$refs.selection.objData[i]._isChecked = true;/*只能用这个方式，给数据直接加无效,如果初始值没有该属性的话*/
              this.sourceData[i]._checked = true;
            }
          }
        }

      },
      updateModelShow(data, index) {
        this.$refs['formValidate'].resetFields();/*清除model的表单数据,打开model就清空*/
        this.permissionSearchName = ""
        this.TragetData = [];/*清空目标权限*/
        this.updateModel = true;
        this.formValidate = data;
        this.TragetData = data.permissionList
        this.roleSettingPage = 1;/*默认的第一页*/
        this.getAllPermission(this.roleSettingPage, this.roleSettingPageSize);/*源权限的数据加载了在查询，为了勾选上权限框*/
        this.tempindex = index;
      },
      close_modal() {/*modal框关闭触发*/
        this.$refs.selection.selectAll(false);/*全部设置成未选中的状态*/
        this.updateModel = false;
      },
      handleSubmitUpdate: function (name) {//提交方法
        var refs = this.$refs;
        refs['formValidate'].validate((valid) => {
          if (valid) {
            var $page = this;
            var messagePage = this.$Message;
            var param = $.extend({}, this.formValidate)/*复制一份，应为要删除*/
            var url;
            delete param["permissionList"];
            var tempDate = []
            for (let i = 0; i < this.TragetData.length; i++) {
              var permission = {id:this.TragetData[i].id}
              tempDate.push(permission)
            }
            param["permissionList"] = tempDate;
            if (this.formValidate.id) {/*修改*/
              url = "/role/update"
              param["action"] = 'update';
            } else {/*添加*/
              url = "/role/save";
              param["action"] = 'save';
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
                  if (result.success){
                    $page.updateModel = false;
                    $page.$Message.success("操作数据成功");
                    $page.getFirstMenuData($page.page, $page.pageSize);/*修改完成后,刷新数据*/
                    $page.$refs.selection.selectAll(false);/*全部设置成未选中的状态*/
                    if (param.action == 'update') {
                      $page.RoleData[$page.tempindex] = $.extend({}, $page.RoleData[$page.tempindex], {_expanded: true})
                    } else {/*保存就展开最后一个即可*/
                      //$page.RoleData[$page.RoleData.length-1] = $.extend({}, $page.RoleData[$page.RoleData.length-1], {_expanded: true})
                      console.log("新增数据不展开")
                    }
                  }else{
                    $page.$Message.error("操作数据失败"+result.message);
                  }

                }
              }
            });
          } else {
            this.$Message.error("请按照表单要求填写");
          }
        })
      },

      handleReset: function (name) {//重置方法
        this.$refs['formValidate'].resetFields();
        this.$refs.selection.selectAll(false);/*全部设置成未选中的状态*/
        this.TragetData = [];/*清空目标权限*/
        this.permissionSearchName = ""
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
          url: "/role/selectForPage",
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
              $page.RoleData = result.data.list;
              $page.total = result.data.totalRows;
              $page.page = result.data.currentPage/*处理一个小bug*/
            }
          }
        });
      },

      newAdd: function () {
        this.$refs['formValidate'].resetFields();/*清除model的表单数据,打开model就清空*/
        this.TragetData = [];/*清空目标权限*/
        this.permissionSearchName = ""
        this.updateModel = true;
        this.roleSettingPage = 1;/*默认的第一页*/
        this.getAllPermission(this.roleSettingPage, this.roleSettingPageSize);/*查询所有权限*/
      },
      deleteRows: function (selection) {
        this.rows = [];
        for (let i = 0; i < selection.length; i++) {
          this.rows.push(selection[i].id)
        }
      },

      deleteRole() {
        var $page = this;
        var notice = this.$Notice;
        $.ajax({
          type: "POST",
          contentType: "application/json",
          url: "/role/batchDelete",
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
                  desc: "删除失败,"+result.message,
                });
              }
            }
          }
        });
      },
      scrollToBottom: function () {/*滚动条到底部的方法,出现滚动条之后下一次点击才会到底部*/
        this.$nextTick(() => {
          // 当滚动条从没有到有时，不加setTimeout滚动条将不会滚动到底部
          setTimeout(() => {
            let overflowY = $(".tragetTable .ivu-table-body")[0];
            if (!overflowY) {
              return
            }
            overflowY.scrollTop = overflowY.scrollHeight + 34
          }, 50)
        })
      },
      /*权限菜单页面*/
      addRole(id,index) {
        this.temp = []/*执行一次就清空一次*/
        this.menuIds = [];
        this.getAllMenu();
        this.authority = true;
        this.initialData(id);
        this.roleid = id;/*用于配置基础菜单权限*/
        this.tempindex = index;

      },
      initialData(id) {
        var roleauthority = [];
        $.ajax({/*获取有权限的菜单*/
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/role/Menu/findAllRolePermissionMenuByRoleId",
          dataType: 'json',
          data: {"id": id},
          async: false,/*取消异步加载*/
          success: function (result) {
            roleauthority = result
          }
        });
        this.getrigthTreeData(roleauthority);/*特殊处理这个数据一下，用于显示*/
        this.authorityTreeShow = roleauthority
        this.getleftselectdata(roleauthority)/*循环出来，比较勾选*/
        this.getAuthorityDateInfo(this.authorityTree, this.temp);/*默认勾选已有权限的菜单*/
      },
      getleftselectdata(data) {//特殊处理一下菜单数据，加入一些其他的属性
        for (let i = 0; i < data.length; i++) {
          if (data[i].children && data[i].children.length > 0) {
            this.temp.push(data[i])
            this.menuIds.push(data[i].id)
            this.getleftselectdata(data[i].children);
          } else {
            this.temp.push(data[i])
            this.menuIds.push(data[i].id)
          }
        }
      },
      renderContent(h, {root, node, data}) {/*自定义显示tree的图标，render函数*/
        return h('span', {
          style: {
            display: 'inline-block',
            width: '100%'
          }
        }, [
          h('span', [
            h('Icon', {
              props: {
                type: data.icon
              },
              style: {
                marginRight: '8px'
              }
            }),
            h('span', data.title)
          ]),
        ]);
      },
      getAuthorityDateInfo: function (data, roleauthority) {//递归菜单,获取已经有权限的菜单并且打开，选中
        for (let i = 0; i < data.length; i++) {
          if (data[i].children && data[i].children.length > 0) {/*传过来的都是当前修改菜单的父菜单*/
            for (let k = 0; k < roleauthority.length; k++) {
              if (roleauthority[k].id == data[i].id) {/*当最后一层的id等于原来的权限id，就选中*/
                data[i].expand = true/*将有children的展开*/
              }
            }
            this.getAuthorityDateInfo(data[i].children, roleauthority)
          } else {
            for (let j = 0; j < roleauthority.length; j++) {
              if (roleauthority[j].id == data[i].id) {/*当最后一层的id等于原来的权限id，就选中*/
                data[i] = $.extend({}, data[i], {checked: true});/*选中原来的权限*/
              }
            }
          }
        }
      },
      saveChang() {/*保存权限修改*/
        console.log(this.menuIds)
        var $page = this;
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/role/Menu/getLastMenuByRoleSave",
          data: {"ids": this.menuIds.toString(), "roleid": this.roleid},
          dataType: 'json',
          async: false,/*取消异步加载*/
          success: function (result) {
            $page.authority = false;
            $page.getFirstMenuData($page.page, $page.pageSize);/*修改完成后,刷新数据*/
            $page.RoleData[$page.tempindex] = $.extend({}, $page.RoleData[$page.tempindex], {_expanded: true})/*打开*/
          }
        });
      },
      getlangData(data) {//特殊处理一下菜单数据，加入一些其他的属性
        for (let i = 0; i < data.length; i++) {
          if (data[i].children != undefined && data[i].children.length > 0) {
            data[i] = $.extend({}, data[i], {title: data[i].name, expand: false});
            this.getlangData(data[i].children);
          } else {
            data[i] = $.extend({}, data[i], {title: data[i].name});
          }
        }
      },
      getAuthority() {
        let checkedNode = this.$refs.tree.getCheckedAndIndeterminateNodes()/*勾选复选框时触发，获取勾选和半勾选的状态的*/
        var copyArr = $.extend(true, [], checkedNode);//数组的深度复制,不影响原数组
        var newMenuList = this.getNewChildren(copyArr)
        this.authorityTreeShow = newMenuList;
      },
      getNewChildren(copyArr) {/*封装方法，前面也要用的*/
        var $page = this;
        var newMenuList = [];
        $page.menuIds = []/*每次改变都重新获取值*/
        $.each(copyArr, function (i, object) {
          $page.menuIds.push(object.id)
        });
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/role/Menu/newTreeDate",
          data: {"ids": this.menuIds.toString()},
          dataType: 'json',
          async: false,/*取消异步加载*/
          success: function (result) {
            $page.getrigthTreeData(result)
            newMenuList = result
          }
        });
        return newMenuList;
      },
      getrigthTreeData(data) {//特殊处理一下菜单数据，加入一些其他的属性
        for (let i = 0; i < data.length; i++) {
          if (data[i].children && data[i].children.length > 0) {
            data[i] = $.extend({}, data[i], {title: data[i].name, expand: true});
            this.getrigthTreeData(data[i].children);
          } else {
            data[i] = $.extend({}, data[i], {title: data[i].name});
          }
        }
      },
      getAllMenu() {
        var $page = this;
        $.ajax({
          type: "POST",
          contentType: "application/x-www-form-urlencoded",
          url: "/role/Menu/findAll",
          dataType: 'json',
          async: false,/*取消异步加载*/
          success: function (result) {
            $page.getlangData(result)
            $page.authorityTree = result;

          }
        });
      }
    }

  });
</script>
