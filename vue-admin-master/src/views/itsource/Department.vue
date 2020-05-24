<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-input v-model="filters.name" placeholder="部门名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getListData">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表111-->
        <el-table :data="listData"
                  highlight-current-row v-loading="listLoading"
                  @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="sn" label="编号" sortable>
            </el-table-column>
            <el-table-column prop="name" label="部门名称" sortable>
            </el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
            <el-pagination layout="prev, pager, next"
                           @current-change="handleCurrentChange"
                           :page-size="pageSize"
                           :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--编辑界面-->
        <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
                <el-form-item label="部门名称" prop="name">
                    <el-input v-model="editForm.name" auto-complete="off"></el-input>
                </el-form-item>
				<el-form-item label="部门编号" prop="sn">
                    <el-input v-model="editForm.sn" auto-complete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
                <el-form-item label="部门名称" prop="name">
                    <el-input v-model="addForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="部门编号" prop="sn">
                    <el-input v-model="addForm.sn" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>

    export default {
        data() {
            return {
                filters: {
                    name: ''
                },
                listData: [],	//列表数据
                total: 0,
                currentPage: 1,
                pageSize: 10,
                listLoading: false,
                sels: [],//列表选中列

                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    name: [
                        { required: true, message: '请输入部门名称', trigger: 'blur' }
                    ],
                    sn: [
                        { required: true, message: '请输入部门编号', trigger: 'blur' }
                    ]
                },
                //编辑界面数据
                editForm: {
                    id: 0,
                    name: '',
                    sn: ''
                },
                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    name: [
                        {required: true, message: '请输入部门名称', trigger: 'blur'}
                    ],
                    sn: [
                        {required: true, message: '请输入部门编号', trigger: 'blur'}
                    ],
                },
                //新增界面数据
                addForm: {
                    name: '',
                    sn: '',
                }
            }
        },
        methods: {
            //性别显示转换
            formatSex: function (row, column) {
                return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
            },
            //提交分页
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getListData();
            },
            //获取用户列表
            getListData() {
                let para = {
                    currentPage: this.currentPage,	//分页，当前页
                    pageSize: this.pageSize,	//每页条数
                    keyword: this.filters.name	//查询条件
                };
                this.listLoading = true;
                this.$http.post("/dept/selectForPage", para).then(res => {
                    var JSONResult = res.data;
                    if (JSONResult.success) {
                        this.listData = JSONResult.data.result;
                        this.total = JSONResult.data.total;
                    } else {
                        this.$message({message: JSONResult.message, type: 'error'});
                    }
                    this.listLoading = false;

                }).catch(error => {
                    this.listLoading = false;
                    this.$message({message: '服务器异常[' + error.message + ']', type: 'error'});
                })
                //NProgress.start();
                /*
                getUserListPage(para).then((res) => {
                    this.total = res.data.total;
                    this.users = res.data.users;
                    this.listLoading = false;
                    //NProgress.done();
                });
                */
            },
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    this.$http.delete("/dept/delete/" + row.id, {}).then(res => {
                        let JSONResult = res.data;
                        if (JSONResult.success) {
                            this.$message({message: '删除成功', type: 'success'});
                            this.getListData();
                        } else {
                            this.$message({message: JSONResult.message, type: 'error'});
                        }
                        this.listLoading = false;
                    }).catch(error => {
                        this.$message({message: '删除失败[' + error.message + ']', type: 'error'});
                        this.listLoading = false;
                    });

                    /*
                    let para = { id: row.id };
                    removeUser(para).then((res) => {
                        this.listLoading = false;
                        //NProgress.done();
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getListData();
                    });
                     */
                }).catch(() => {

                });
            },
            //显示编辑界面
            handleEdit: function (index, row) {
                this.editFormVisible = true;
                this.editForm = Object.assign({}, row);
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    name: '',
                    sn: ''
                };
            },
            //编辑
            editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							this.$http.post("/dept/edit",para).then(res=>{
								let JSONResult = res.data;
								if (JSONResult.success){
									this.$message({message: '修改成功',type: 'success'});
									this.$refs.editForm.resetFields();
									this.editFormVisible = false;
									//重新加载列表数据
									this.getListData();
									this.editLoading = false;
								}else {
									this.$message({message: '修改失败',type: 'error'});
								}
							}).catch(error => {
								this.$message({message: '修改失败[' + error.message + ']',type: 'error'});
								this.editLoading = false;
							})
						});
					}
				});
            },
            //新增
            addSubmit: function () {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            //按钮加载状态
                            this.addLoading = true;
                            //准备参数
                            let para = Object.assign({}, this.addForm);
                            this.$http.post("/dept/add", para).then(res => {
                                let JSONResult = res.data;
                                if (JSONResult.success) {
                                    this.$message({message: '保存成功', type: 'success'});
                                    this.$refs['addForm'].resetFields();
                                    this.addFormVisible = false;
                                    this.getListData();
                                } else {
                                    this.$message({message: JSONResult.message, type: 'error'});
                                }
                                this.addLoading = false;
                            }).catch(error => {
                                this.$message({message: '保存失败[' + error.message + ']', type: 'error'});
                                this.addLoading = false;
                            });

                        });
                    }
                });
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            //批量删除
            batchRemove: function () {
                //map可以遍历一个数组 ，item就是每个元素 ，
                // var ids =  this.sels.map(function(item){
                // 	return item.id; //返回每个元素的id 1 , 2 ,3
                // });
                var ids = this.sels.map(item => item.id); // [1,2,3]
                this.$confirm('确认删除选中记录吗？', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    //NProgress.start();
                    let para = {ids: ids};	//{ids: [3, 4, 5, 6, 18, 21, 22, 23, 24, 25]}
                    this.$http.post("/dept/batchDelete", para).then(res => {
                        let JSONResult = res.data;
                        if (JSONResult.success) {
                            this.$message({message: '删除成功', type: 'success'});
                            this.getListData();
                        } else {
                            this.$message({message: JSONResult.message, type: 'error'});
                        }
                        this.listLoading = false;
                    }).catch(error => {
                        this.$message({message: '删除失败[' + error.message + ']', type: 'error'});
                        this.listLoading = false;
                    });

                }).catch(() => {

                });
            }
        },
        mounted() {
            this.getListData();
        }
    }

</script>

<style scoped>

</style>