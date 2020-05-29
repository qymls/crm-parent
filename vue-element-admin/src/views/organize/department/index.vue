<template>
  <div style="height: calc(100vh - 84px);">
    <el-card class="box-card" style="height: 100%">
      <div slot="header" class="clearfix">
        <span>部门管理</span>
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
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="small" style="float: left;margin: auto 3px 20px 20px;">
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
            <el-form-item label="部门名称">
              <el-input v-model="searchForm.name" placeholder="部门名称" clearable />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="loadListData">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-table v-loading="loading" border :data="tableData" style="width: 100%" max-height="690" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="sn" label="部门编号" />
        <el-table-column prop="manager.realName" label="部门经理" />
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
      <el-dialog title="信息管理" :visible.sync="dialogFormVisible" :close-on-click-modal="false" width="35%">
        <el-form ref="addForm" :model="addForm" label-width="50px" :rules="rules">
          <el-form-item v-show="false" prop="id">
            <el-input v-model="addForm.id" />
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="addForm.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="编号" prop="sn">
            <el-input v-model="addForm.sn" autocomplete="off" />
          </el-form-item>
          <el-form-item label="经理" prop="manager" style="width: 400px">
            <el-select v-model="addForm.manager.id" placeholder="请选择经理">
              <!--key:id -->
              <el-option
                v-for="item in manager"
                :key="item.id"
                :label="item.realName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('addForm')">确认提交</el-button>
            <el-button @click="resetForm('addForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      //部门
      parentId:[{
          id:'',
          children:{
            id:''
          }
      }],
      manager: [{
        id: ''
      }],
      page: 1, // 第几页
      pageSize: 5, // 每页条数
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
        sn: '',
        manager: {
          id: '',
          realName: ''
        }
      },
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {
    // 显示添加弹窗

    handleShowAddDialog() {
      this.dialogFormVisible = true

      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        // 清空下拉框
      })
    },
    // 编辑显示弹窗
    handleShowEditDialog(row) {
      // 数据回显
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        this.addForm = Object.assign({}, row)/* 复制*/
      })
    },
    submitForm(formName) { /* 确认保存*/
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        let url = '/department/save'
        if (this.addForm.id) {
          url = '/department/update'
        }
        if (valid) {
          http.post(url, param).then(res => {
            if (res.data.success) {
              // 赋值管理员
              this.manager = res.data.data
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
      http.post('/department/batchDelete', param).then(res => {
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
      http.delete('/department/delete/' + row.id).then(res => {
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
      // 查询到部门给参数
      this.$http.get('/employee/findAll').then(res => {
        this.manager = res.data.data
      }),
      this.$http.get('/department/findTreeData').then(res => {
        console.debug(res.data.data)
      })
      // 发送ajax请求
      this.loading = true
      // vue加载完成，发送ajax请求动态获取数据
      const param = {
        'currentPage': this.page,
        'pageSize': this.pageSize,
        'keyword': this.searchForm.name
      }
      var http = this.$http
      var message = this.$message
      http.post('/department/selectForPage', param).then(res => {
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
    }
  }

}
</script>
