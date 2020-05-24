<template>
  <div>
    <el-button type="primary" icon="el-icon-plus" size="small" style="margin-left: 20px;
  margin-top: 10px;" @click="newAdd">新增
    </el-button>
    <el-button type="danger" v-if="row.length>0" icon="el-icon-delete" size="small" style="
  margin-top: 10px;">删除
    </el-button>
    <el-card class="box-card" style="margin: 20px;;">
      <div slot="header" class="clearfix">
        <span>产品管理</span>
      </div>
      <el-table :data="tableData" stripe style="width: 100%" @selection-change="handleSelectionChange"
                @row-dblclick="updateSubmit" max-height="650" border>
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="productName" label="产品名称" width="180" align="center"></el-table-column>
        <el-table-column prop="supplier" label="供应商" width="180" align="center"></el-table-column>
        <el-table-column prop="brand" label="品牌" align="center"></el-table-column>
        <el-table-column prop="cutoff" label="最低限额" align="center"></el-table-column>
        <el-table-column prop="costPrice" label="成本" align="center"></el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="添加产品" :visible.sync="dialogVisible" width="30%">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="id" prop="id">
          <el-input v-model="ruleForm.id"></el-input>
        </el-form-item>

        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="ruleForm.productName"></el-input>
        </el-form-item>

        <el-form-item label="供应商" prop="supplier">
          <el-input v-model="ruleForm.supplier"></el-input>
        </el-form-item>

        <el-form-item label="品牌" prop="brand">
          <el-input v-model="ruleForm.brand"></el-input>
        </el-form-item>

        <el-form-item label="最低限额" prop="cutoff">
          <el-input v-model="ruleForm.cutoff"></el-input>
        </el-form-item>

        <el-form-item label="成本" prop="costPrice">
          <el-input v-model="ruleForm.costPrice"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        ruleForm: {
          id: '',
          productName: "",
          supplier: "",
          brand: "",
          cutoff: "",
          costPrice: "",
        },
        rules: {
          name: [
            {required: true, message: '请输入活动名称', trigger: 'blur'},
          ],
          region: [
            {required: true, message: '请选择活动区域', trigger: 'change'}
          ],
        },
        tableData: [],
        row: [],
        dialogVisible: false
      }
    },
    created() {
      this.getTableData();
    },
    methods: {
      getTableData() {
        //发送请求到后台查询数据 axios
        this.$http.get('/Product/findAll').then(res => {
          //打印返回结果
          console.log(res.data);
          this.tableData = res.data;
        })
      },
      newAdd() {
        this.dialogVisible = true;
        this.$nextTick(() => {
          this.$refs['ruleForm'].resetFields();/*清空*/
        })
      },
      updateSubmit(row) {
        this.dialogVisible = true;
        this.$nextTick(() => {
          this.$refs['ruleForm'].resetFields();/*清空*/
          this.ruleForm = $.extend({}, row);
        })


      },

      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$nextTick(() => {
          this.$refs['ruleForm'].resetFields();/*清空*/
        })
      },
      handleSelectionChange(selection) {
        this.row = selection
        console.log(selection)
      }
    }
  }
</script>
