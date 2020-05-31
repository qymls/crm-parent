<template>
  <div style="height: calc(100vh - 84px);">
    <el-card class="box-card" style="height: 100%">
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
      <el-table  v-loading="loading" border :data="tableData" style="width: 100%" @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="100" align="center" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="realName" label="真实姓名" />
        <el-table-column prop="tel" label="联系方式" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="inputTime" label="录入时间" />
        <el-table-column prop="department.name" label="所属部门" />
        <el-table-column prop="tenant.companyName" label="所属租户" />
        <el-table-column prop="state" label="状态" :formatter="formatState">
          <template slot-scope="scope">
            <span v-if=" scope.row.state=== 0 " style="color: #2acd33">在职</span>
            <span v-else style="color: red">离职</span>
          </template>
        </el-table-column>
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
      <el-dialog  title="员工管理" :visible.sync="dialogFormVisible" maxheight="1000px" :close-on-click-modal="false" width="35%">
        <hr style="margin-right: auto"/>
        <el-form ref="addForm" :model="addForm" label-width="80px">
          <el-form-item v-show="false" prop="id">
            <el-input v-model="addForm.id" />
          </el-form-item>
          <el-form-item  label="用户名" prop="username" minlength="20">
            <el-col :span="14">
            <el-input v-model="addForm.username" autocomplete="off"/>
            </el-col>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-col :span="14">
            <el-input v-model="addForm.password" autocomplete="off"/>
            </el-col>
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-col :span="14">
            <el-input v-model="addForm.realName" autocomplete="off" />
            </el-col>
          </el-form-item>
          <el-form-item label="邮箱地址" prop="email">
            <el-col :span="14">
            <el-input v-model="addForm.email" autocomplete="off" />
            </el-col>
          </el-form-item>
          <el-form-item label="联系方式" prop="tel">
            <el-col :span="14">
            <el-input v-model="addForm.tel" autocomplete="off" />
            </el-col>
          </el-form-item>
          <el-form-item label="注册时间" prop="registerTime">
            <el-date-picker
              v-model="addForm.inputTime"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="状态" prop="state">
            <el-radio-group v-model="addForm.state">
              <el-radio :label="0">在职</el-radio>
              <el-radio :label="-1">离职</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="所属部门" prop="department">
            <el-select v-model="addForm.department.id" placeholder="请选择部门">
              <!--key:id -->
              <el-option
                v-for="item in department"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属机构" prop="tenant">
            <el-select v-model="addForm.tenant.id" placeholder="请选择机构">
              <!--key:id -->
              <el-option
                v-for="item in tenant"
                :key="item.id"
                :label="item.companyName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属角色">
          <el-select
            v-model="roleValueList"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择角色">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
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
import ElRadioGroup from "element-ui/packages/radio/src/radio-group";

export default {
  components: {ElRadioGroup},
  data() {
    return {
      roleValueList:[],
      roleList:[],

      //权限
      department: [{
        id: ''
      }],
      tenant: [{
        id: ''
      }],
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
        username: '',
        password: '',
        realName: '',
        tel: '',
        email: '',
        inputTime: '',
        state: 0,
        department: {
          id: '',
          name: ''
        },
        tenant: {
          id: '',
          companyName: ''
        },
      }
    }
  },
  mounted() {
    this.loadListData()
  },
  methods: {

    // 格式化状态
    formatState: function(row, column) {
      return row.state == -1 ? '在职' : '离职'
    },
    // 显示添加弹窗
    handleShowAddDialog() {
      this.dialogFormVisible = true;
      // 清空表单
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()
      })
      if(!this.addForm.tenant){
        this.addForm.tenant = {id:''}
      }
      //清空角色
      this.addForm.department.id ="";
      //清空角色
      this.roleValueList = []
      this.addForm.inputTime="";
      this.addForm.state = 0;
    },
    // 编辑显示弹窗
    handleShowEditDialog(row) {
      // 数据回显
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()/* 清空*/
        var data= Object.assign({}, row)/* 赋值*/
        if(data.role.length>0){
          var tempDtae = []
          for (let i = 0; i < data.role.length; i++) {
            tempDtae.push(data.role[i].id)
          }
          this.roleValueList = tempDtae;
        }
        if (!data.department){
          data.department = {id:''};
        }
        if (!data.tenant){
          data.tenant = {id:''};
        }
        this.addForm = data;
        //回显状态
      })



    },
    submitForm(formName) { /* 确认保存*/
      var refs = this.$refs
      var http = this.$http
      var message = this.$message
      refs[formName].validate((valid) => {
        const param = Object.assign({}, this.addForm)
        var rolees=[]
        for(let i=0;i<this.roleValueList.length;i++){
          var roles={id:this.roleValueList[i]}
          rolees.push(roles);
        }
        param['role']=rolees;
        let url = '/employee/save'
        if (this.addForm.id) {
          url = '/employee/update'
        }
        if (valid) {
          http.post(url, param).then(res => {
            if (res.data.success) {
              this.dialogFormVisible = false;
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
      http.post('/employee/batchDelete', param).then(res => {
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
      http.delete('/employee/delete/' + row.id).then(res => {
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
      //查询角色
      this.$http.get('/role/findAll').then(res=>{
        this.roleList=res.data.data;
      })
      // 查询到部门给参数
      this.$http.get('/department/findAll').then(res => {
        this.department = res.data.data
      }),
      // 查询到部门给参数
      this.$http.get('/tenant/findAll').then(res => {
        this.tenant = res.data.data
      }),
      this.loading = true
      // vue加载完成，发送ajax请求动态获取数据
      const param = {
        'currentPage': this.page,
        'pageSize': this.pageSize,
        'keyword': this.searchForm.name
      }
      var http = this.$http
      var message = this.$message
      http.post('/employee/selectForPage', param).then(res => {
        if (res.data.success) {
          console.log('邮箱' + res.data.email)
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
