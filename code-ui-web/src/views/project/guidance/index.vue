<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['project:guidance:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:guidance:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:guidance:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:guidance:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="guidanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户账号" align="center" prop="userName" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="指导人" align="center" prop="nickName" />
      <el-table-column label="简历类型" align="center" prop="resumeType">
        <template #default="scope">
          <dict-tag :options="pro_interview_type" :value="scope.row.resumeType"/>
        </template>
      </el-table-column>
      <el-table-column label="指导状态" align="center" prop="guidanceStatus">
        <template #default="scope">
          <dict-tag :options="pro_guidance_status" :value="scope.row.guidanceStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="指导预约时间" align="center" prop="appointmentTime" width="110">
        <template #default="scope">
          <span>{{ parseTime(scope.row.appointmentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="附件" align="center" prop="appendix" />
      <el-table-column label="指导完成时间" align="center" prop="completeTime" width="110">
        <template #default="scope">
          <span>{{ parseTime(scope.row.completeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="paymentStatus">
        <template #default="scope">
          <dict-tag :options="sys_pay_status" :value="scope.row.paymentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="指导反馈" align="center" prop="feedback" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:guidance:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:guidance:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改简历指导记录对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="guidanceRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户" />
        </el-form-item>
        <el-form-item label="简历类型" prop="resumeType">
          <el-select v-model="form.resumeType" placeholder="请选择简历类型">
            <el-option
              v-for="dict in pro_interview_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="指导人" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入用户" />
        </el-form-item>
        <el-form-item label="指导状态" prop="guidanceStatus">
          <el-radio-group v-model="form.guidanceStatus">
            <el-radio
              v-for="dict in pro_guidance_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker clearable
            v-model="form.appointmentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择指导预约时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="指导反馈">
          <editor v-model="form.feedback" :min-height="192"/>
        </el-form-item>
        <el-form-item label="上传附件">
          <editor v-model="form.appendix" :min-height="192"/>
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

<script setup name="Guidance">
import { listGuidance, getGuidance, delGuidance, addGuidance, updateGuidance } from "@/api/project/guidance"

const { proxy } = getCurrentInstance()
const { sys_pay_status, pro_guidance_status, pro_interview_type } = proxy.useDict('sys_pay_status', 'pro_guidance_status', 'pro_interview_type')

const guidanceList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    userId: null,
    guideId: null,
    nickName: null,
  },
  rules: {
    userId: [
      { required: true, message: "用户不能为空", trigger: "blur" }
    ],
    phone: [
      { required: true, message: "联系电话不能为空", trigger: "blur" }
    ],
    guideId: [
      { required: true, message: "指导人不能为空", trigger: "change" }
    ],
    resumeType: [
      { required: true, message: "简历类型不能为空", trigger: "change" }
    ],
    guidanceStatus: [
      { required: true, message: "指导状态不能为空", trigger: "change" }
    ],
    appointmentTime: [
      { required: true, message: "指导预约时间不能为空", trigger: "blur" }
    ],
    feedback: [
      { required: true, message: "指导反馈不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询简历指导记录列表 */
function getList() {
  loading.value = true
  listGuidance(queryParams.value).then(response => {
    guidanceList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    userId: null,
    phone: null,
    guideId: null,
    resumeType: null,
    guidanceStatus: null,
    appointmentTime: null,
    completeTime: null,
    paymentStatus: null,
    feedback: null,
    creator: null,
    createTime: null,
    modifier: null,
    updateTime: null
  }
  proxy.resetForm("guidanceRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加简历指导记录"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getGuidance(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改简历指导记录"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["guidanceRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateGuidance(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addGuidance(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除简历指导记录编号为"' + _ids + '"的数据项？').then(function() {
    return delGuidance(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/guidance/export', {
    ...queryParams.value
  }, `guidance_${new Date().getTime()}.xlsx`)
}

getList()
</script>
