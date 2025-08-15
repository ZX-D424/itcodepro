<template>
  <el-upload
    class="upload-demo"
    action="/public/option/option/importData"
    :headers="headers"
    :on-success="handleSuccess"
    :before-upload="beforeUpload"
    :limit="1"
    accept=".xlsx,.xls"
  >
    <el-button type="primary">点击上传</el-button>
    <template #tip>
      <div class="el-upload__tip">
        请上传Excel文件，支持.xls和.xlsx格式
        <el-link type="primary" :href="templateUrl" download>下载模板</el-link>
      </div>
    </template>
  </el-upload>
</template>

<script setup>
import { getToken } from '@/utils/auth'

const headers = {
  Authorization: 'Bearer ' + getToken()
}

const templateUrl = '/template/question_option_template.xlsx'

const beforeUpload = (file) => {
  const isExcel = file.type.includes('excel') || 
                 file.name.endsWith('.xlsx') || 
                 file.name.endsWith('.xls')
  if (!isExcel) {
    ElMessage.error('请上传Excel文件!')
    return false
  }
  return true
}

const handleSuccess = (response) => {
  if (response.code === 200) {
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(response.msg)
  }
}
</script>