<template>
    <div class="app-container">
<!--        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">-->
<!--            <el-form-item label="用户" prop="userId">-->
<!--                <el-input-->
<!--                        v-model="queryParams.userId"-->
<!--                        placeholder="请输入用户"-->
<!--                        clearable-->
<!--                        @keyup.enter="handleQuery"-->
<!--                />-->
<!--            </el-form-item>-->
<!--            <el-form-item>-->
<!--                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>-->
<!--                <el-button icon="Refresh" @click="resetQuery">重置</el-button>-->
<!--            </el-form-item>-->
<!--        </el-form>-->

        <div class="mb8">
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
                    :disabled="single || isCompleted(selectedRow)"
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
<!--            <div class="right-toolbar">-->
<!--                <el-button @click="showSearch = !showSearch">-->
<!--                    {{ showSearch ? '隐藏搜索' : '显示搜索' }}-->
<!--                </el-button>-->
<!--            </div>-->
        </div>

        <el-table v-loading="loading" :data="simulationList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
<!--            <el-table-column label="用户账号" align="center" prop="userName" />-->
            <el-table-column label="联系电话" align="center" prop="phone" />
            <el-table-column label="面试官" align="center" prop="nickName" />
            <el-table-column label="面试类型" align="center" prop="interviewType">
                <template #default="scope">
                    <el-tag :type="getInterviewTypeTag(scope.row.interviewType)">
                        {{ formatInterviewType(scope.row.interviewType) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="面试状态" align="center" prop="interviewStatus">
                <template #default="scope">
                    <el-tag :type="getInterviewStatusTag(scope.row.interviewStatus)">
                        {{ formatInterviewStatus(scope.row.interviewStatus) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="面试预约时间" align="center" prop="appointmentTime" width="110">
                <template #default="scope">
                    <span>{{ formatDate(scope.row.appointmentTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="面试附件" align="center" prop="appendix" />
            <el-table-column label="面试完成时间" align="center" prop="completeTime" width="110">
                <template #default="scope">
                    <span>{{ formatDate(scope.row.completeTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="支付状态" align="center" prop="paymentStatus">
                <template #default="scope">
                    <el-tag :type="getPaymentStatusTag(scope.row.paymentStatus)">
                        {{ formatPaymentStatus(scope.row.paymentStatus) }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="面试情况" align="center" prop="interviewResult" />
            <el-table-column label="操作" align="center" width="180">
                <template #default="scope">
                    <el-button
                            link
                            type="primary"
                            size="small"
                            @click="handleUpdate(scope.row)"
                            :disabled="isCompleted(scope.row)"
                    >修改</el-button>
                    <el-button
                            link
                            type="danger"
                            size="small"
                            @click="handleDelete(scope.row)"
                            :disabled="isCompleted(scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

<!--        <el-pagination-->
<!--                v-show="total>0"-->
<!--                :total="total"-->
<!--                v-model:current-page="queryParams.pageNum"-->
<!--                v-model:page-size="queryParams.pageSize"-->
<!--                @current-change="getList"-->
<!--                @size-change="getList"-->
<!--                layout="total, sizes, prev, pager, next, jumper"-->
<!--        />-->

        <!-- 添加或修改面试模拟记录对话框 -->
        <el-dialog :title="title" v-model="open" width="60%">
            <el-form ref="simulationRef" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="面试类型" prop="interviewType">
                    <el-select v-model="form.interviewType" placeholder="请选择面试类型">
                        <el-option
                                v-for="item in interviewTypeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="面试官" prop="nickName">
                    <el-input v-model="form.nickName" placeholder="请输入用户" />
                </el-form-item>
                <el-form-item label="预约时间" prop="appointmentTime">
                    <el-date-picker clearable
                                    v-model="form.appointmentTime"
                                    type="date"
                                    value-format="YYYY-MM-DD"
                                    placeholder="请选择面试预约时间">
                    </el-date-picker>
                </el-form-item>
<!--                <el-form-item label="面试情况">-->
<!--                    <el-input v-model="form.interviewResult" type="textarea" :rows="6" />-->
<!--                </el-form-item>-->
                <el-form-item label="附件上传">
                    <el-upload
                            action="#"
                            :auto-upload="false"
                            :show-file-list="true"
                    >
                        <el-button type="primary">选择文件</el-button>
                    </el-upload>
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
    import { listSimulation, getSimulation, delSimulation, addSimulation, updateSimulation } from "@/api/www/simulation"
    import userStore from "@/store/modules/user"
    const user = userStore();
    const currentUserId = ref(user.id)

    // 数据定义
    const simulationList = ref([])
    const open = ref(false)
    const loading = ref(true)
    const showSearch = ref(true)
    const ids = ref([])
    const single = ref(true)
    const multiple = ref(true)
    const total = ref(0)
    const title = ref("")

    const isCompleted = (row) => {
        return row && row.interviewStatus === 3 // 假设3表示"已完成"
    }

    // 表单和查询参数
    const queryParams = reactive({
        userId: currentUserId.value,
        interviewerId: null,
        nickName: null,
    })

    const form = reactive({
        id: null,
        userId: null,
        phone: null,
        interviewerId: null,
        interviewType: null,
        interviewStatus: null,
        appointmentTime: null,
        completeTime: null,
        paymentStatus: null,
        interviewResult: null,
        creator: null,
        createTime: null,
        modifier: null,
        updateTime: null
    })

    const rules = reactive({
        userId: [{ required: true, message: "用户不能为空", trigger: "blur" }],
        interviewerId: [{ required: true, message: "面试官不能为空", trigger: "change" }],
        interviewType: [{ required: true, message: "面试类型不能为空", trigger: "change" }],
        interviewStatus: [{ required: true, message: "面试状态不能为空", trigger: "change" }],
        appointmentTime: [{ required: true, message: "面试预约时间不能为空", trigger: "blur" }],
        interviewResult: [{ required: true, message: "面试结果/评价不能为空", trigger: "blur" }],
    })

    // 选项数据
    const interviewTypeOptions = [
        { value: 1, label: '技术面试' },
        { value: 2, label: 'HR面试' },
        { value: 3, label: '综合面试' }
    ]

    const interviewStatusOptions = [
        { value: 1, label: '待安排' },
        { value: 2, label: '已预约' },
        { value: 3, label: '已完成' },
        { value: 4, label: '已取消' }
    ]

    const paymentStatusOptions = [
        { value: 0, label: '未支付' },
        { value: 1, label: '已支付' },
        { value: 2, label: '已退款' }
    ]

    // 格式化方法
    const formatInterviewType = (type) => {
        const item = interviewTypeOptions.find(item => item.value === type)
        return item ? item.label : '未知类型'
    }

    const formatInterviewStatus = (status) => {
        const item = interviewStatusOptions.find(item => item.value === status)
        return item ? item.label : '未知状态'
    }

    const formatPaymentStatus = (status) => {
        const item = paymentStatusOptions.find(item => item.value === status)
        return item ? item.label : '未知状态'
    }

    const formatDate = (date) => {
        if (!date) return ''
        return new Date(date).toLocaleDateString()
    }

    // 标签类型方法
    const getInterviewTypeTag = (type) => {
        const types = ['', 'success', 'warning', 'danger']
        return types[type] || 'info'
    }

    const getInterviewStatusTag = (status) => {
        const types = ['', 'info', 'primary', 'success', 'danger']
        return types[status] || 'info'
    }

    const getPaymentStatusTag = (status) => {
        const types = ['danger', 'success', 'info']
        return types[status] || 'info'
    }

    // 方法定义
    const getList = () => {
        loading.value = true
        listSimulation(queryParams).then(response => {
            simulationList.value = response.rows
            total.value = response.total
            loading.value = false
        }).catch(() => {
            loading.value = false
        })
    }

    const reset = () => {
        form.id = null
        form.userId = currentUserId.value
        form.phone = null
        form.interviewerId = null
        form.interviewType = null
        form.interviewStatus = null
        form.appointmentTime = null
        form.completeTime = null
        form.paymentStatus = null
        form.interviewResult = null
        form.creator = null
        form.createTime = null
        form.modifier = null
        form.updateTime = null
    }

    const cancel = () => {
        open.value = false
        reset()
    }

    const handleQuery = () => {
        queryParams.pageNum = 1
        getList()
    }

    const resetQuery = () => {
        queryParams.userId = null
        queryParams.interviewerId = null
        queryParams.nickName = null
        handleQuery()
    }

    const handleSelectionChange = (selection) => {
        ids.value = selection.map(item => item.id)
        single.value = selection.length != 1
        multiple.value = !selection.length
    }

    const handleAdd = () => {
        reset()
        open.value = true
        title.value = "添加面试模拟记录"
    }

    const handleUpdate = (row) => {
        const targetRow = row || selectedRow.value
        if (isCompleted(targetRow)) {
            ElMessage.warning('已完成的面试记录不能修改')
            return
        }

        reset()
        const id = targetRow.id || ids.value[0]
        getSimulation(id).then(response => {
            Object.assign(form, response.data)
            open.value = true
            title.value = "修改面试模拟记录"
        })
    }

    // 提交表单时也需要检查
    const submitForm = () => {
        if (form.id && isCompleted({ interviewStatus: form.interviewStatus })) {
            ElMessage.warning('已完成的面试记录不能修改')
            return
        }

        if (form.id) {
            updateSimulation(form).then(() => {
                ElMessage.success("修改成功")
                open.value = false
                getList()
            })
        } else {
            addSimulation(form).then(() => {
                ElMessage.success("新增成功")
                open.value = false
                getList()
            })
        }
    }

    const handleDelete = (row) => {
        const targetRow = row || selectedRow.value
        if (isCompleted(targetRow)) {
            ElMessage.warning('已完成的面试记录不能删除')
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
            return delSimulation(id)
        }).then(() => {
            ElMessage.success('删除成功')
            getList()
        })
    }

    const handleExport = () => {
        ElMessage.info('导出功能需要根据实际API实现')
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