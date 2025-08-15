<template>
  <div class="question-detail-container">
    <el-card class="question-card">
      <div slot="header" class="detail-header">
        <el-button icon="ArrowLeft" @click="handleBack">返回</el-button>
        <h1>{{ question?.title }}</h1>
      </div>
      
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="question" class="question-detail-content">
        <!-- 题目元信息 -->
        <el-descriptions 
          class="question-meta" 
          :column="4" 
          border
          :labelStyle="{ width: '90px', textAlign: 'right' }"
        >
          <!-- 题型展示优化 -->
          <el-descriptions-item label="题型">
            <template v-if="question?.questionType">
              <span v-if="sys_question_questiontype.loaded">
                {{ questionTypeLabel || '未知题型' }}
              </span>
              <span v-else>
                {{ fallbackMap[question.questionType] || '未知题型' }}
              </span>
            </template>
            <span v-else>未设置</span>
          </el-descriptions-item>
          
          <!-- 类目 -->
          <el-descriptions-item label="类目">{{ question?.category }}</el-descriptions-item>
          
          <!-- 来源 -->
          <el-descriptions-item label="来源">{{ question?.source || '未知' }}</el-descriptions-item>
        </el-descriptions>
        
        <!-- 题目内容 -->
        <div class="question-section">
          <h3 class="section-title">题目内容：</h3>
          <div class="question-content" v-html="question?.content"></div>
        </div>
        
        <!-- 题型专属区域（仅展示，不包含答题功能） -->
        <div class="answer-section" v-if="[1, 2, 3].includes(question?.questionType)">
          <!-- 单选题 & 判断题 & 多选题 - 仅展示选项 -->
          <div v-if="[1, 2, 3].includes(question?.questionType)" class="question-options">
            <h3 class="section-title">选项：</h3>
            <div class="options-group">
              <div 
                v-for="option in question?.options" 
                :key="option?.id" 
                class="option-item"
              >
                <span class="option-code">{{ option?.optionCode }}.</span>
                <span class="option-text" v-html="option?.optionContent"></span>
              </div>
            </div>
          </div>
          
          <!-- 填空题 - 仅展示题目 -->
          <div v-else-if="question?.questionType === 4" class="question-blank">
            <h3 class="section-title">填空题：</h3>
            <div class="blank-content" v-html="formattedBlankContent"></div>
          </div>
          
          <!-- 简答题 - 仅展示标题 -->
          <div v-else-if="question?.questionType === 5" class="question-essay">
            <h3 class="section-title">简答题：</h3>
          </div>
          
          <!-- 编程题 - 仅展示标题 -->
          <div v-else-if="question?.questionType === 6" class="question-code">
            <h3 class="section-title">编程题：</h3>
          </div>
        </div>
        
        <!-- 参考答案和解析（优化展示） -->
        <div class="answer-analysis-section">
          <!-- 答案切换按钮 -->
          <div class="answer-toggle" @click="toggleAnswer">
            <el-button 
              type="primary" 
              size="default"
              :icon="showAnswer ? 'EyeOff' : 'Eye'"
              class="toggle-btn"
            >
              {{ showAnswer ? '隐藏答案' : '查看答案' }}
            </el-button>
          </div>
          
          <!-- 答案内容（带动画效果） -->
          <transition name="fade-slide">
            <div v-if="showAnswer && question?.answer" class="answer-content-wrapper">
              <!-- 参考答案 -->
              <div class="answer-block">
                <h3 class="answer-title">
                  <el-icon name="document" />
                  <span>参考答案</span>
                </h3>
                <div class="answer-content" v-html="question?.answer?.answerContent"></div>
                
                <!-- 正确选项 -->
                <div v-if="[1, 2, 3].includes(question?.questionType)" class="correct-options-container">
                  <h4>正确选项：</h4>
                  <div class="correct-options">
                    <el-tag 
                      v-for="option in question?.options" 
                      :key="option?.id" 
                      v-if="option?.isCorrect"
                      type="success"
                      class="correct-option"
                    >
                      <span class="option-code">{{ option?.optionCode }}.</span>
                      <span v-html="option?.optionContent"></span>
                    </el-tag>
                  </div>
                </div>
              </div>
              
              <!-- 解析部分（如果有） -->
              <div v-if="question?.answer?.analysis" class="analysis-block">
                <h3 class="analysis-title">
                  <el-icon name="HelpFilled" />
                  <span>题目解析</span>
                </h3>
                <div class="analysis-content" v-html="question?.answer?.analysis"></div>
              </div>
            </div>
          </transition>
        </div>
      </div>
      
      <div v-else class="error-state">
        <el-empty description="获取题目详情失败，请刷新页面重试" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { getAnswerByQuestionId } from '@/api/option/option';
import { useDict } from '@/utils/dict';
import { ElMessage } from 'element-plus';
// 修改图标导入方式
// import Eye from '@element-plus/icons-vue/dist/es/icons/eye.vue';
// import EyeOff from '@element-plus/icons-vue/dist/es/icons/eye-off.vue';
// import Document from '@element-plus/icons-vue/dist/es/icons/document.vue';
// import HelpFilled from '@element-plus/icons-vue/dist/es/icons/help-filled.vue';
// import ArrowLeft from '@element-plus/icons-vue/dist/es/icons/arrow-left.vue';
// 路由
const route = useRoute();
const router = useRouter();

// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 数据
const question = ref(null);
const loading = ref(false);
const showAnswer = ref(false); // 控制答案显示/隐藏

// 硬编码备选映射
const fallbackMap = {
  1: '单选题',
  2: '多选题', 
  3: '判断题',
  4: '填空题',
  5: '简答题',
  6: '编程题'
};

// 计算属性：题型标签
const questionTypeLabel = computed(() => {
  if (!question.value?.questionType) return null;
  
  const targetValue = String(question.value.questionType);
  const matched = sys_question_questiontype.value?.find(
    item => String(item.dictValue) === targetValue
  );
  
  return matched?.dictLabel || null;
});

// 获取题目详情
const getQuestionDetail = async () => {
  const { id } = route.params;
  if (!id) return;
  
  loading.value = true;
  try {
    // 获取题目基本信息
    const questionRes = await getManage(id);
    question.value = questionRes.data;
    
    // 获取选项
    if ([1, 2, 3].includes(question.value.questionType)) {
      const optionRes = await getOptionByQuestionId(id);
      question.value.options = optionRes.rows
       .sort((a, b) => Number(a.sort) - Number(b.sort)); 
    }
    
    // 获取答案和解析
    const answerRes = await getAnswerByQuestionId(id);
    question.value.answer = answerRes.rows?.[0] || null;
    
  } catch (error) {
    console.error('获取题目详情失败:', error);
    ElMessage.error('获取题目详情失败');
  } finally {
    loading.value = false;
  }
};

// 格式化填空题内容（仅显示占位符）
const formattedBlankContent = computed(() => {
  if (!question.value || question.value.questionType !== 4) return '';
  
  // 根据下划线数量生成对应占位符
  return (question.value.content.match(/_+/g) || []).map((_, index) => {
    return `<span class="blank-placeholder">【${index + 1}】</span>`;
  }).join(' ');
});

// 切换答案显示/隐藏
const toggleAnswer = () => {
  showAnswer.value = !showAnswer.value;
};

// 返回
const handleBack = () => {
  router.back();
};

// 题型调试信息
watch(() => sys_question_questiontype.loaded, (loaded) => {
  if (loaded && question.value?.questionType) {
    const target = String(question.value.questionType);
    const matched = sys_question_questiontype.value?.find(
      item => String(item.dictValue) === target
    );
    console.log('题型匹配结果:', {
      questionType: question.value.questionType,
      matchedLabel: matched?.dictLabel,
      fallback: fallbackMap[question.value.questionType]
    });
  }
});

// 初始化
onMounted(() => {
  getQuestionDetail();
});
</script>

<style scoped>
.question-detail-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.question-card {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 15px;
}

.question-meta {
  margin-bottom: 20px;
}

.question-section {
  margin-bottom: 25px;
}

.section-title {
  margin-bottom: 15px;
  color: #1f2937;
  font-weight: 600;
}

.question-content {
  padding: 15px;
  background-color: #f9fafb;
  border-radius: 8px;
  line-height: 1.8;
  border: 1px solid #e5e7eb;
}

.answer-section {
  margin-top: 20px;
  margin-bottom: 25px;
  padding: 20px;
  background-color: #f8fafc;
  border-radius: 10px;
  border: 1px solid #e5e7eb;
}

.options-group {
  padding: 10px 0;
  gap: 20px;
}

/* 选项容器样式 */
.option-item {
  display: flex;
  align-items: center; 
  margin-bottom: 12px;
  padding: 12px 16px;
  background-color: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.option-item:hover {
  background-color: #f0f9ff;
  border-color: #409eff;
}

.option-code {
  display: inline-block;
  width: 1em;
  text-align: right;
  margin-right: 8px;
  color: #409eff;
  font-weight: bold;
  font-size: x-large;
}

.option-text {
  flex: 1;
  line-height: 1.4;
  font-size: large;
}

/* 填空占位符样式 */
.blank-placeholder {
  display: inline-block;
  min-width: 80px;
  height: 30px;
  margin: 0 5px;
  text-align: center;
  line-height: 30px;
  color: #666;
  border-bottom: 2px solid #ccc;
}

/* 答案和解析区域（优化样式） */
.answer-analysis-section {
  margin-top: 30px;
  margin-bottom: 20px;
}

.answer-toggle {
  margin-bottom: 15px;
}

.toggle-btn {
  transition: all 0.3s ease;
}

.toggle-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.answer-content-wrapper {
  background-color: #f0f9ff;
  border: 1px solid #d0eaff;
  border-radius: 8px;
  overflow: hidden;
}

.answer-block, .analysis-block {
  padding: 20px;
}

.analysis-block {
  background-color: #f8fafc;
  border-top: 1px solid #e5e7eb;
}

.answer-title, .analysis-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  margin-bottom: 15px;
  color: #1f2937;
  font-weight: 600;
}

.answer-title .el-icon, .analysis-title .el-icon {
  margin-right: 8px;
  color: #409eff;
}

.answer-content, .analysis-content {
  line-height: 1.8;
  color: #374151;
  padding: 10px 0;
  font-size: 15px;
}

.analysis-content p {
  margin-bottom: 10px;
}

.analysis-content p:last-child {
  margin-bottom: 0;
}

.correct-options-container {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px dashed #e5e7eb;
}

.correct-options {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.correct-option {
  padding: 8px 12px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.correct-option:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(72, 187, 120, 0.3);
}

/* 动画效果 */
.fade-slide-enter-from,
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.3s ease;
}

/* 加载和错误状态 */
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.error-state {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  color: #ef4444;
}
</style>