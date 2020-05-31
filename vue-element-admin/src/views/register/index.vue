<template>
  <!--:model="tenant" 数据双向绑定-->
  <!--ref="tenantForm" id="tenantForm",给form去一个名字-->
  <!--:rules="formRules" 校验规则-->
  <el-form :model="employee" ref="tenantForm" :rules="formRules" label-position="left" label-width="100px"
           class="demo-ruleForm login-container">
    <h3 class="title">公司入驻</h3>
    <el-form-item prop="companyName" label="公司名称">
      <el-input type="text" v-model="employee.companyName" auto-complete="off" placeholder="请输入公司名称！"></el-input>
    </el-form-item>
    <el-form-item prop="companyNum" label="信用代码">
      <el-input type="text" v-model="employee.companyNum" auto-complete="off" placeholder="请输入信用代码！"></el-input>
    </el-form-item>
    <el-form-item prop="address" label="公司地址">
      <el-input type="text" v-model="employee.address" auto-complete="off" placeholder="请输入地址！"></el-input>
    </el-form-item>
    <el-form-item prop="logo" label="公司Logo">
      <el-upload
        class="upload-demo"
        action="/settleIn/upload"
        :on-success="handleUploadSuccess"
        :on-remove="handleUploadRemove"
        list-type="picture"
        :name="settleIn">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </el-form-item>
    <el-form-item prop="username" label="公司账号">
      <el-input type="text" v-model="employee.username" auto-complete="off" placeholder="请输入账号！"></el-input>
    </el-form-item>
    <el-form-item prop="tel" label="手机号码">
      <el-input type="text" v-model="employee.tel" auto-complete="off" placeholder="请输入电话！"></el-input>
    </el-form-item>
    <el-form-item prop="email" label="电子邮件">
      <el-input type="text" v-model="employee.email" auto-complete="off" placeholder="请输入邮件！"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="密码">
      <el-input type="password" v-model="employee.password" auto-complete="off" placeholder="请输入密码！"></el-input>
    </el-form-item>
    <el-form-item prop="comfirmPassword" label="确认密码">
      <el-input type="password" v-model="employee.comfirmPassword" auto-complete="off"
                placeholder="请输入确认密码！"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="settledIn">入驻</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    data() {
      var validatePass2 = (rule, value, callback) => {
        console.log(value); //确认密码
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.employee.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      return {
        //employee:tenant 为了做数据表单校验不要嵌套对象
        employee: {
          companyName: '',
          companyNum: '',
          address: '',
          logo: '',

          username: '',
          tel: '',
          email: '',
          password: ''
        },
        formRules: {
          companyName: [
            {required: true, message: '请输入公司名称!', trigger: 'blur'}
          ],
          companyNum: [
            {required: true, message: '请输入公司信用代码!', trigger: 'blur'}
          ],
          address: [
            {required: true, message: '请输入公司地址!', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入公司logo!', trigger: 'blur'}
          ],
          username: [
            {required: true, message: '请输入账号!', trigger: 'blur'}
          ],
          tel: [
            {required: true, message: '请输入电话!', trigger: 'blur'}
          ],
          /*email: [
            {type: 'email', required: true, message: '请输入邮箱!', trigger: 'blur'}
          ],*/
          password: [
            {required: true, message: '请输入密码!', trigger: 'blur'}
          ],
          comfirmPassword: [
            {required: true, validator: validatePass2, trigger: 'blur'} //自定义校验规则
          ]
        }
      };
    },
    methods: {
      handleUploadSuccess(res, file) {
        this.employee.logo = res.data;
      },
      handleUploadRemove(res, file) {
      },
      settledIn() {
        this.$refs.tenantForm.validate((valid) => {
          //校验表单成功后才做一下操作
          if (valid) {
            this.$confirm('确认入驻吗？', '提示', {}).then(() => {
              //拷贝后面对象的值到新对象,防止后面代码改动引起模型变化
              let data = Object.assign({}, this.employee); //employee
              let para = {
                tenant: {
                  companyName: data.companyName,
                  companyNum: data.companyNum,
                  address: data.address,
                  sysName: data.username,
                  logo: data.logo,
                },
                employee: {
                  username: data.username,
                  tel: data.tel,
                  email: data.email,
                  password: data.password
                }
              };
              //判断是否有id有就是修改,否则就是添加
              this.$http.post("/settleIn/settleIn", para).then((res) => {
                if (res.data.success) {
                  this.$message({
                    message: '操作成功!',
                    type: 'success'
                  });
                  //重置表单
                  this.$refs['tenantForm'].resetFields();
                  //跳转登录页面
                  this.$router.push({path: '/login'});
                } else {
                  this.$message({
                    message: res.data.message,
                    type: 'error'
                  });
                }

              });
            });
          }
        })
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 500px;
    padding: 35px 35px 15px 35px;
    background: #1de9ff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #0f29ea;
  }

  .remember {
    margin: 0px 0px 35px 0px;
  }
</style>
