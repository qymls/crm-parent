<style>
  .process_style {
    margin-top: 23px;
  }

  .upload_style .ivu-icon {
    line-height: unset;
  }

  .demo-upload-list {
    display: inline-block;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
    margin-right: 4px;
  }

  .imgLook_style .ivu-modal-body {
    max-height: 360px;
    overflow: auto;
  }

  ::-webkit-scrollbar { /*不要滚动条*/
    width: 0;
  }

  .demo-upload-list img {
    width: 100%;
    height: 100%;
  }

  .demo-upload-list-cover {
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, .6);
  }

  .demo-upload-list:hover .demo-upload-list-cover {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .demo-upload-list-cover i {
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }
</style>
<template>
  <div style="height: calc(100vh - 84px);">
    <el-card class="box-card" style="height: 100%">
      <div slot="header" class="clearfix">
        <span>信息管理</span>
      </div>
      <el-row>
        <el-col :span="3">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="small"
            style="float: left;"
            @click="handleShowAddDialog"
          >新增
          </el-button>
          <el-popconfirm
            v-if="row.length>0"
            icon="el-icon-info"
            icon-color="red"
            title="你确定要离我而去？"
            placement="right"
            @onConfirm="handleBatchRemove"
          >
            <el-button
              slot="reference"
              type="danger"
              icon="el-icon-delete"
              size="small"
              style="float: left;margin: auto 3px 20px 20px;"
            >
              删除
            </el-button>
          </el-popconfirm>
        </el-col>
        <el-col :span="21" class="toolbar">
          <el-form
            :inline="true"
            :model="searchForm"
            size="small"
            class="demo-form-inline"
            style="float: left;"
          >
            <el-form-item label="用户名">
              <el-input v-model="searchForm.name" placeholder="用户名" clearable />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="loadListData">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-table
        v-loading="loading"
        border
        :data="tableData"
        style="width: 100%"
        max-height="690"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column type="index" width="50" label="序号" align="center" />
        <el-table-column prop="companyName" label="公司名称" />
        <el-table-column prop="companyTel" label="联系电话" />
        <el-table-column prop="companyNum" label="信用代码" />
        <el-table-column prop="email" label="邮箱地址" />
        <el-table-column prop="sysName" label="公司账户" />
        <el-table-column prop="registerTime" label="注册时间" />
        <el-table-column prop="state" label="状态" :formatter="stateFormatter" />
        <el-table-column prop="address" label="公司地址" />
        <el-table-column prop="logo" label="logo" >
          <template   slot-scope="scope">
            <img :src="scope.row.logo"  min-width="70" height="70" />
          </template>
        </el-table-column>
        <el-table-column prop="meal.name" label="套餐" />
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleShowEditDialog(scope.row)">编辑</el-button>
            <el-popconfirm
              confirm-button-text="确认"
              cancel-button-text="取消"
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除当前数据？"
              transition
              placement="top"
              @onConfirm="handleRemove(scope.row)"
            >
              <el-button slot="reference" type="danger" size="small">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="float: right;margin: 20px;overflow: hidden"
        background
        :current-page="page"
        :page-sizes="[5,10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
      <!--  新增、编辑-->
      <Modal title="信息管理" v-model="dialogFormVisible" :close-on-click-modal="false" width="35%" :z-index="50">
        <el-form ref="addForm" :model="addForm" label-width="100px" :rules="rules">
          <el-form-item v-show="false" prop="id">
            <el-input v-model="addForm.id" />
          </el-form-item>
          <el-form-item label="公司名称" prop="companyName">
            <el-input v-model="addForm.companyName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="联系电话" prop="companyTel">
            <el-input v-model="addForm.companyTel" autocomplete="off" />
          </el-form-item>
          <el-form-item label="信用代码" prop="companyNum">
            <el-input v-model="addForm.companyNum" autocomplete="off" />
          </el-form-item>
          <el-form-item label="邮箱地址" prop="email">
            <el-input v-model="addForm.email" autocomplete="off" />
          </el-form-item>
          <el-form-item label="公司账户" prop="sysName">
            <el-input v-model="addForm.sysName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="注册时间" prop="registerTime">
            <el-date-picker
              v-model="addForm.registerTime"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="状态" prop="state">
            <el-radio-group v-model="addForm.state">
              <el-radio :label="0">注册</el-radio>
              <el-radio :label="1">付费</el-radio>
              <el-radio :label="2">欠费</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="公司地址" prop="address">
<!--            <el-input v-model="addForm.address" autocomplete="off" />-->
            <baidu-map :center="{lng: 116.403765, lat: 39.914850}" :zoom="11">
              <bm-auto-complete v-model="addForm.address" :sug-style="{zIndex: 2100}">
                <div style="margin-bottom:10px">
                  <el-input id="searchInput" v-model="addForm.address" type="text" placeholder="请输入关键字" class="searchinput"/>
                  <el-button type="success" @click="confirmAdd">确定</el-button>
                </div>
              </bm-auto-complete>
              <bm-view class="bmap" />
              <bm-local-search :keyword="addForm.address" :auto-viewport="true" :panel="false" />
            </baidu-map>
          </el-form-item>

          <el-form-item prop="logo" v-show="false">
            <i-input type="text" v-model="addForm.logo"/>
          </el-form-item>

          <el-form-item label="logo">
          <div class="demo-upload-list"
               v-if="addForm.logo||uploadfile.status==='start'||uploadfile.status==='finished'">
            <template v-if="uploadfile.status === 'finished'||uploadfile.defaultshow">
              <img :src="addForm.logo">
              <div class="demo-upload-list-cover">
                <Icon type="ios-eye-outline"
                      @click.native="handleView(addForm.logo)"/>
                <Icon type="ios-trash-outline"
                      @click.native="handleRemoveImg(addForm.logo)"/>
              </div>
            </template>
            <template v-else>
              <i-Progress class="process_style" v-if="uploadfile.showProgress"
                          :percent="uploadfile.percentage" hide-info
                          :stroke-color="['#108ee9', '#87d068']"/>
            </template>
          </div>
          <Upload ref="upload"
                  action="/upload/tenant"
                  style="width: 58px;display: inline-block;"
                  class="upload_style" :show-upload-list="false" name="file" type="drag"
                  :format="['jpg','jpeg','png']"
                  :on-format-error="handleFormatError"
                  :on-progress="handleProgress"
                  :on-success="upload_success"
                  :before-upload="handleBeforeUpload"
                  :data="{'logo':addForm.logo}"
          >
            <div style="width: 58px;height:58px;line-height: 58px;">
              <Icon type="ios-camera" size="20"/>
            </div>
          </Upload>
          <Modal title="预览图片" class="imgLook_style" v-model="visible" draggable :z-index="100">
            <img :src="addForm.logo" v-if="visible" style="width: 100%">
          </Modal>
        </el-form-item>
          <el-form-item label="套餐" prop="meal">
            <el-select v-model="addForm.meal.id" placeholder="请选择套餐">
              <!--key:id -->
              <el-option
                v-for="item in meals"
                :key="item.id"
                :value="item.id"
                :label="item.name"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('addForm')">确认提交</el-button>
            <el-button @click="resetForm('addForm')">重置</el-button>
          </el-form-item>
        </el-form>

      </Modal>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      meals: [],
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
        companyName: '',
        companyTel: '',
        companyNum: '',
        email: '',
        sysName: '',
        registerTime: '',
        state: '',
        address: '',
        logo: '',
        meal: {
          id: ''
        }
      },
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      visible: false,/*预览图片*/
      uploadfile: {
        status: '',
        showProgress: false,
        percentage: 0,
        defaultshow: true,
      },/*上传的文件属性*/
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {
    handleUploadSuccess(res, file) {
      this.employee.logo = res.data;
    },
    handleUploadRemove(res, file) {
    },
    // 显示添加弹窗
    handleShowAddDialog() {
      this.$http.get('/meal/findAll').then(res => {
        this.meals = res.data.data
      });
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
      })
    },
    // 编辑显示弹窗
    handleShowEditDialog(row) {
      this.$http.get('/meal/findAll').then(res => {
        this.meals = res.data.data
      });
      // 数据回显
      this.dialogFormVisible = true
      console.log(row);
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row);
        console.log("数据"+row.data);
        this.addForm.state = row.state;
      })
    },

    submitForm(formName) { /* 确认保存*/
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        let url = '/tenant/save'
        if (this.addForm.id) {
          url = '/tenant/update'
        }
        if (valid) {
          http.post(url, param).then(res => {
            if (res.data.success) {
              // console.log(res.data.data);
              // this.meal = res.data.data;
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
      http.post('/tenant/batchDelete', param).then(res => {
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
      http.delete('/tenant/delete/' + row.id).then(res => {
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
      http.post('/tenant/selectForPage', param).then(res => {
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
    },
    stateFormatter(row, column) {
      const state = row.state
      if (state == 0) {
        return '注册'
      } else if (state == 1) {
        return '付费'
      } else {
        return '欠费'
      }
    },
    // 地图点击事件
    confirmAdd() {
      this.addForm.address = document.getElementById('searchInput').value
      this.mapDialogVisible = false
    },

    /*图片上传的相关方法*/
    upload_success(response, file, fileList) {
      this.addForm.logo = response.data;
      this.uploadfile.status = 'finished';/*上传完成*/
    },

    handleProgress(event, file, fileList) {/*没有调试好，无法使用*/
      // 手动设置显示上传进度条 以及上传百分比
      // 调用监听 上传进度 的事件
      let uploadPercent = parseFloat(((event.loaded / event.total) * 100).toFixed(2))	// 保留两位小数，具体根据自己需求做更改
      this.uploadfile.percentage = uploadPercent/*进度*/
      console.log(uploadPercent)
    },
    handleView(name) {
      this.addForm.logo = name
      this.visible = true;
    },
    handleRemoveImg(path) {
      var page = this;
      $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: "/upload/deleteImg",
        data: {"path": path},
        dataType: 'json',
        traditional: true,//防止深度序列化
        async: false,/*取消异步加载*/
        success: function (result) {/*用了框架的*/
          page.addForm.logo = '';/*删除了重置headimg*/
          page.uploadfile = ''/*上传文件为初始值*/
        }
      });
    },
    handleFormatError(file) {
      this.$Notice.error({
        title: '文件类型错误',
        desc: '文件 ' + file.name + '类型错误,请选择jpg，jepg，png类型'
      });
      this.uploadfile = {
        status: '',
        showProgress: false,/*上传前就开始显示进度条了*/
        percentage: 0,
        defaultshow: true
      }
    },
    handleBeforeUpload() {/*因为上传单个，每次上传前回复到默认状态*/
      this.uploadfile = {
        status: 'start',
        showProgress: true,/*上传前就开始显示进度条了*/
        percentage: 0
      }
      return true;
    }


}


}
</script>
