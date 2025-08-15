<template>
    <div class="app-container">
<!--        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">-->
<!--            <el-form-item label="申请人" prop="applicant">-->
<!--                <el-input-->
<!--                        v-model="queryParams.applicant"-->
<!--                        placeholder="请输入申请人"-->
<!--                        clearable-->
<!--                        @keyup.enter="handleQuery"-->
<!--                />-->
<!--            </el-form-item>-->
<!--            <el-form-item label="联系电话" prop="phone">-->
<!--                <el-input-->
<!--                        v-model="queryParams.phone"-->
<!--                        placeholder="请输入联系电话"-->
<!--                        clearable-->
<!--                        @keyup.enter="handleQuery"-->
<!--                />-->
<!--            </el-form-item>-->
<!--            <el-form-item>-->
<!--                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>-->
<!--                <el-button icon="Refresh" @click="resetQuery">重置</el-button>-->
<!--            </el-form-item>-->
<!--        </el-form>-->

        <div class="mb8" style="margin-bottom: 16px;">
            <el-button
                    type="primary"
                    plain
                    icon="Plus"
                    @click="handleAdd"
            >新增</el-button>
            <el-button
                    type="success"
                    plain
                    icon="Edit"
                    :disabled="single"
                    @click="handleUpdate"
            >修改</el-button>
            <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="multiple"
                    @click="handleDelete"
            >删除</el-button>
            <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click="handleExport"
            >导出</el-button>
        </div>

        <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="ID" align="center" prop="id" />
            <el-table-column label="证书" align="center" prop="certType">
                <template #default="scope">
                    <span>{{ formatCertType(scope.row.certType) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="申请人" align="center" prop="applicant" />
            <el-table-column label="身份证" align="center" prop="idCard" />
            <el-table-column label="联系电话" align="center" prop="phone" />
            <el-table-column label="收件地址" align="center" prop="receiveAddr" />
            <el-table-column label="备注" align="center" prop="remark" />
            <el-table-column label="支付状态" align="center" prop="payStatus">
                <template #default="scope">
                    <el-tag :type="getPayStatusTagType(scope.row.payStatus)">
                        {{ formatPayStatus(scope.row.payStatus) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="申请状态" align="center" prop="applicationStatus">
                <template #default="scope">
                    <el-tag :type="getAppStatusTagType(scope.row.applicationStatus)">
                        {{ formatAppStatus(scope.row.applicationStatus) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="180">
                <template #default="scope">
                    <el-button
                            size="small"
                            @click="handleUpdate(scope.row)"
                            :disabled="isApproved(scope.row)"
                    >修改</el-button>
                    <el-button
                            size="small"
                            type="danger"
                            @click="handleDelete(scope.row)"
                            :disabled="isApproved(scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 添加或修改证书申请对话框 -->
        <el-dialog :title="title" v-model="open" width="60%">
            <el-form ref="applicationRef" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="申请人" prop="applicant">
                    <el-input v-model="form.applicant" placeholder="请输入申请人" />
                </el-form-item>
                <el-form-item label="身份证号码" prop="idCard">
                    <el-input v-model="form.idCard" placeholder="请输入身份证号码" />
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入联系电话" />
                </el-form-item>
                <el-form-item label="收件地址" prop="receiveAddr">
                    <el-input v-model="form.receiveAddr" type="textarea" placeholder="请输入内容" />
                </el-form-item>
                <el-form-item label="证书类型" prop="certType">
                    <el-select v-model="form.certType" placeholder="请选择证书类型">
                        <el-option
                                v-for="item in certTypeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" placeholder="请输入备注信息" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="cancel">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
    import { ref, reactive, onMounted } from 'vue'
    import { ElMessage, ElMessageBox } from 'element-plus'
    import { listApplication, getApplication, delApplication, addApplication, updateApplication } from "@/api/www/application"
    import userStore from "@/store/modules/user"
    const user = userStore();
    const currentUserId = ref(user.id)

    // 数据定义
    const applicationList = ref([])
    const open = ref(false)
    const loading = ref(true)
    const showSearch = ref(true)
    const ids = ref([])
    const single = ref(true)
    const multiple = ref(true)
    const total = ref(0)
    const title = ref("")
    const selectedRow = ref(null)

    // 表单和查询参数
    const queryParams = reactive({
        applicant: null,
        phone: null,
        userId: currentUserId.value
    })

    const form = reactive({
        id: null,
        applicant: null,
        idCard: null,
        userId: currentUserId.value,
        phone: null,
        receiveAddr: null,
        certType: null,
        remark: null,
        certPrice: null,
        payStatus: 0,
        applicationStatus: 0
    })

    const rules = reactive({
        applicant: [{ required: true, message: "申请人不能为空", trigger: "blur" }],
        idCard: [{ required: true, message: "身份证号码不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "联系电话不能为空", trigger: "blur" }],
        receiveAddr: [{ required: true, message: "收件地址不能为空", trigger: "blur" }],
        certType: [{ required: true, message: "证书类型不能为空", trigger: "change" }],
    })

    // 选项数据
    const certTypeOptions = [
        { value: '1', label: '初级证书' },
        { value: '2', label: '中级证书' },
        { value: '3', label: '高级证书' },
        { value: '4', label: '专家级证书' }
    ]

    const payStatusOptions = [
        { value: 0, label: '未支付' },
        { value: 1, label: '已支付' },
        { value: 2, label: '退款中' },
        { value: 3, label: '已退款' }
    ]

    const appStatusOptions = [
        { value: 0, label: '待审核' },
        { value: 1, label: '已通过' },
        { value: 2, label: '已拒绝' }
    ]

    // 检查申请状态是否为"已通过"
    const isApproved = (row) => {
        return row && row.applicationStatus === 1 // 假设1表示"已通过"
    }

    // 状态格式化方法
    const formatCertType = (type) => {
        const item = certTypeOptions.find(item => item.value === type)
        return item ? item.label : '未知类型'
    }

    const formatPayStatus = (status) => {
        const item = payStatusOptions.find(item => item.value === status)
        return item ? item.label : '未知状态'
    }

    const formatAppStatus = (status) => {
        const item = appStatusOptions.find(item => item.value === status)
        return item ? item.label : '未知状态'
    }

    const getPayStatusTagType = (status) => {
        const types = ['info', 'success', 'warning', 'danger']
        return types[status] || 'info'
    }

    const getAppStatusTagType = (status) => {
        const types = ['info', 'success', 'danger']
        return types[status] || 'info'
    }

    // 方法定义
    const getList = () => {
        loading.value = true
        queryParams.userId = currentUserId.value

        listApplication(queryParams).then(response => {
            applicationList.value = response.rows
            total.value = response.total
            loading.value = false
        }).catch(() => {
            loading.value = false
        })
    }

    const resetQuery = () => {
        queryParams.applicant = null
        queryParams.phone = null
        handleQuery()
    }

    const handleQuery = () => {
        queryParams.pageNum = 1
        getList()
    }

    const handleSelectionChange = (selection) => {
        ids.value = selection.map(item => item.id)
        single.value = selection.length != 1
        multiple.value = !selection.length
        // 更新选中的行
        selectedRow.value = selection.length === 1 ? selection[0] : null
    }
    // 新增：处理行点击事件
    const handleRowClick = (row) => {
        selectedRow.value = row
    }


    const resetForm = () => {
        form.id = null
        form.applicant = null
        form.idCard = null
        form.userId = null
        form.phone = null
        form.receiveAddr = null
        form.certType = null
        form.remark = null
        form.certPrice = null
        form.payStatus = 0
        form.applicationStatus = 0
    }

    const handleAdd = () => {
        resetForm()
        open.value = true
        title.value = "添加证书申请"
    }

    const handleUpdate = (row) => {
        const targetRow = row || selectedRow.value
        if (isApproved(targetRow)) {
            ElMessage.warning('已通过的申请不能修改')
            return
        }

        resetForm()
        const id = targetRow.id || ids.value[0]
        getApplication(id).then(response => {
            Object.assign(form, response.data)
            open.value = true
            title.value = "修改证书申请"
        })
    }

    const submitForm = () => {
        if (form.id && isApproved({ applicationStatus: form.applicationStatus })) {
            ElMessage.warning('已通过的申请不能修改')
            return
        }

        if (form.id) {
            updateApplication(form).then(() => {
                ElMessage.success("修改成功")
                open.value = false
                getList()
            })
        } else {
            addApplication(form).then(() => {
                ElMessage.success("新增成功")
                open.value = false
                getList()
            })
        }
    }
    const handleDelete = (row) => {
        const targetRow = row || selectedRow.value
        if (isApproved(targetRow)) {
            ElMessage.warning('已通过的申请不能删除')
            return
        }

        const id = targetRow?.id || ids.value.join(',')
        if (!id) {
            ElMessage.warning('请选择要删除的数据')
            return
        }

        ElMessageBox.confirm('确定要删除选中的数据吗?', '警告', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            return delApplication(id)
        }).then(() => {
            ElMessage.success('删除成功')
            getList()
        })
    }

    const handleExport = () => {
        // 导出逻辑
        ElMessage.info('导出功能需要根据实际API实现')
    }

    const cancel = () => {
        open.value = false
    }

    // 初始化
    onMounted(() => {
        getList()
    })
</script>

<style scoped>
    .app-container {
        padding: 20px;
    }
    .mb8 {
        margin-bottom: 16px;
    }
    .right-toolbar {
        float: right;
    }
</style>