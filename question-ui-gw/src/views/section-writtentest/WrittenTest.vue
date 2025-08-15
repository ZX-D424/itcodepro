<template>
  <div class="exam-container">
    <!-- 页面标题和操作区 -->
    <div class="exam-header">
      <h2>笔试题目组卷</h2>
    </div>

    <!-- 类目搜索区域 -->
    <div class="category-search-area">
      <div class="search-header">
        <h3>题目筛选条件</h3>
        <div class="action-buttons">
          <el-button type="primary" size="small" @click="addCategoryRow">
            <el-icon><Plus /></el-icon> 增加类目
          </el-button>
          <el-button 
            type="primary" 
            size="large" 
            @click="fetchQuestions"
          >
            <el-icon><Refresh /></el-icon> 刷新题目
          </el-button>
          <el-button type="primary" @click="saveToPersonalBank">
            <el-icon><Check /></el-icon> 生成
          </el-button>
          <el-button type="success" @click="generatePDF">
            <el-icon><Document /></el-icon> 导出试卷
          </el-button>
        </div>
      </div>

      <!-- 搜索条件行 -->
      <el-table
        :data="categoryRows"
        border
        :row-key="(row) => row.id"
        class="search-table"
      >
        <el-table-column label="类目" prop="categoryId" width="200">
          <template #default="scope">
            <el-tree-select
              v-model="scope.row.categoryId"
              :data="categoryOptions"
              :props="treeSelectProps"
              value-key="id"
              placeholder="请选择类目"
              style="width: 100%"
              class="category-tree-select"
            >
              <template #default="{ data }">
                <span :class="{ 'parent-node': data.children && data.children.length > 0 }">{{ data.name }}</span>
              </template>
            </el-tree-select>
          </template>
        </el-table-column>
        <el-table-column label="标签" prop="tagId" width="180">
          <template #default="scope">
            <el-select v-model="scope.row.tagId" placeholder="请选择标签" clearable style="width: 100%">
              <el-option
                v-for="tag in tagNameList"
                :key="tag.id"
                :label="tag.tagName"
                :value="tag.id"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="120">
          <template #default="scope">
            <el-input 
              v-model.number="scope.row.quantity" 
              type="number" 
              min="1" 
              placeholder="输入数量"
              style="width: 100%"
              @change="validateQuantity"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="scope">
            <el-button 
              link 
              type="danger" 
              size="small" 
              @click="deleteRow(scope.row.id)"
            >
              <el-icon><Delete /></el-icon>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 题目展示区域 -->
    <div class="questions-area">
      <div class="questions-header">
        <h3>笔试题目</h3>
        <el-tag type="info">共 {{ allQuestions.length }} 题</el-tag>
      </div>

      <el-card v-if="loading" class="loading-card">
        <div class="loading-container">
          <el-icon class="is-loading" color="#409eff" size="48">
            <Loading />
          </el-icon>
          <p style="margin-top: 15px; font-size: 16px; color: #606266;">正在加载题目，请稍候...</p>
        </div>
      </el-card>

      <div v-else-if="allQuestions.length === 0" class="empty-state">
        <el-empty description="请设置筛选条件并点击刷新题目" />
      </div>

      <div v-else class="questions-list">
        <!-- 按题型分组展示 -->
        <div v-for="(typeGroup, type) in groupedQuestions" :key="type" class="question-type-group">
          <h4 class="type-title">
            {{ getQuestionTypeName(type) }} (共 {{ typeGroup.length }} 题)
          </h4>
          
          <div class="type-questions">
            <el-card 
              v-for="(question, index) in typeGroup" 
              :key="question.id" 
              class="question-card"
            >
              <div class="question-header">
                <span class="question-number">{{ index + 1 }}.</span>
                <span class="question-title">{{ question.title }}</span>
              </div>
              
              <div class="question-content" v-html="question.content"></div>
              
              <!-- 不同题型的特有内容和答题区域 -->
              
              <!-- 单选题 -->
              <div v-if="type == 1" class="question-options">
                <div 
                  v-for="(option, optIndex) in question.options" 
                  :key="option.id" 
                  class="option-item"
                  @click="selectSingleOption(question.id, optIndex)"
                  :class="{ 'selected-option': userAnswers[question.id] === optIndex }"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + optIndex) }}.</span>
                  <span class="option-content">{{ option.optionContent }}</span>
                </div>
              </div>
              
              <!-- 多选题 -->
              <div v-if="type == 2" class="question-options">
                <div 
                  v-for="(option, optIndex) in question.options" 
                  :key="option.id" 
                  class="option-item"
                  @click="toggleMultipleOption(question.id, optIndex)"
                  :class="{ 'selected-option': userAnswers[question.id]?.includes(optIndex) }"
                >
                  <span class="option-letter">{{ String.fromCharCode(65 + optIndex) }}.</span>
                  <span class="option-content">{{ option.optionContent }}</span>
                </div>
              </div>
              
              <!-- 判断题 -->
              <div v-if="type == 3" class="judge-options">
                <div 
                  class="option-item"
                  @click="setJudgeAnswer(question.id, true)"
                  :class="{ 'selected-option': userAnswers[question.id] === true }"
                >
                  <span class="option-letter">A.</span>
                  <span class="option-content">正确</span>
                </div>
                <div 
                  class="option-item"
                  @click="setJudgeAnswer(question.id, false)"
                  :class="{ 'selected-option': userAnswers[question.id] === false }"
                >
                  <span class="option-letter">B.</span>
                  <span class="option-content">错误</span>
                </div>
              </div>
              
              <!-- 填空题 -->
              <div v-if="type == 4" class="answer-input-area">
                <el-input 
                  v-model="userAnswers[question.id]" 
                  placeholder="请输入答案" 
                  clearable
                />
              </div>
              
              <!-- 简答题 -->
              <div v-if="type == 5" class="answer-input-area">
                <el-input 
                  v-model="userAnswers[question.id]" 
                  type="textarea" 
                  :rows="4" 
                  placeholder="请输入答案" 
                  clearable
                />
              </div>
              
              <!-- 编程题 -->
              <div v-if="type == 6" class="answer-input-area">
                <el-input 
                  v-model="userAnswers[question.id]" 
                  type="textarea" 
                  :rows="8" 
                  placeholder="请输入代码" 
                  clearable
                  monospace
                />
                <p class="code-hint">提示：可使用Tab键缩进代码</p>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 底部统计栏 -->
    <div class="stats-bar">
      <div class="question-count">
        <span>总题数: {{ allQuestions.length }}</span>
        <span>已答题: {{ answeredCount }}</span>
      </div>
      <div>当前时间: {{ currentTime }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted } from 'vue';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';
import { getOptionByQuestionId } from '@/api/option/option';
import { listTag } from '@/api/tag/tag';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Plus, Delete, Refresh, Document, Check, Loading } from '@element-plus/icons-vue';
import html2pdf from 'html2pdf.js';

// 题型定义
const questionTypes = [
  { value: 1, label: '单选题' },
  { value: 2, label: '多选题' },
  { value: 3, label: '判断题' },
  { value: 4, label: '填空题' },
  { value: 5, label: '简答题' },
  { value: 6, label: '编程题' }
];

// 响应式数据
const categoryOptions = ref([]);
const tagNameList = ref([]);
const categoryRows = ref([]);
const loading = ref(false);
const allQuestions = ref([]);
const userAnswers = ref({});
const timer = ref(null);
const currentTime = ref('');

// 树形选择器配置
const treeSelectProps = {
  value: 'categoryId',
  label: 'categoryName',
  children: 'children'
};

// 初始化数据
let rowId = 1;

// 获取题型名称
const getQuestionTypeName = (typeValue) => {
  const typeNum = Number(typeValue); 
  if (isNaN(typeNum)) {
    return '未知题型';
  }
  const type = questionTypes.find(t => t.value === typeNum);
  return type ? type.label : '未知题型';
};

// 按题型分组题目
const groupedQuestions = computed(() => {
  const groups = {};
  allQuestions.value.forEach(question => {
    const type = question.questionType;
    if (!groups[type]) {
      groups[type] = [];
    }
    groups[type].push(question);
  });
  return groups;
});

// 计算已答题数 - 修复了单选题选择选项A的问题
const answeredCount = computed(() => {
  return allQuestions.value.filter(question => {
    const answer = userAnswers.value[question.id];
    
    if (question.questionType === 2) { // 多选题
      return answer && answer.length > 0;
    } else if (question.questionType === 3) { // 判断题
      return answer === true || answer === false;
    } else if (question.questionType === 1) { // 单选题
      return typeof answer === 'number' && answer >= 0;
    } else { // 其他题型（填空/简答/编程）
      return answer && answer.toString().trim() !== '';
    }
  }).length;
});

// 更新当前时间
const updateTime = () => {
  const now = new Date();
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false
  });
};

// 加载类目和标签数据
const loadCategoryAndTags = async () => {
  try {
    // 加载类目树
    const categoryRes = await listCategory({});
    let categories = categoryRes.data || [];
    console.log('类目原始数据:', categories); // 添加调试日志
    // 构建树形结构，兼容多种顶级类目标识
    const buildTree = (data, parentId = null) => {
      return data
        .filter(item => {
          // 处理多种可能的顶级类目标识
          const isTopLevel = [null, undefined, 0, -1, '0', '-1', ''].includes(item.parentId);
          if (parentId === null) {
            return isTopLevel;
          }
          // 转换为相同类型比较
          return Number(item.parentId) === Number(parentId);
        })
        .map(item => ({
          ...item,
          children: buildTree(data, item.id) || [] // 确保children始终是数组
        }));
    };
    categoryOptions.value = buildTree(categories);
    console.log('构建后的类目树:', categoryOptions.value); // 添加调试日志
    
    // 加载标签列表
    const tagRes = await listTag({});
    tagNameList.value = tagRes.rows || [];
  } catch (error) {
    console.error('加载类目和标签失败:', error);
    ElMessage.error('加载类目和标签失败');
  }
};

// 添加类目行（添加重复校验）
const addCategoryRow = () => {
  // 检查是否有重复条件
  const hasDuplicate = categoryRows.value.some(row => {
    return categoryRows.value.some(otherRow => {
      if (row.id === otherRow.id) return false; // 不与自己比较
      return row.categoryId === otherRow.categoryId && row.tagId === otherRow.tagId;
    });
  });
  
  if (hasDuplicate) {
    ElMessage.warning('已有相同的类目和标签组合，请勿重复添加');
    return;
  }
  
  categoryRows.value.push({
    id: rowId++,
    categoryId: null,
    tagId: null,
    quantity: 5 // 默认数量
  });
};

// 删除行
const deleteRow = (id) => {
  categoryRows.value = categoryRows.value.filter(row => row.id !== id);
};

// 校验数量总和（不超过100）
const validateTotalQuantity = () => {
  const total = categoryRows.value.reduce((sum, row) => {
    return sum + (row.quantity || 0);
  }, 0);
  
  if (total > 100) {
    ElMessage.error(`题目总数不能超过100题，当前总数为: ${total}`);
    return false;
  }
  
  return true;
};

// 校验单个数量输入
const validateQuantity = () => {
  if (!validateTotalQuantity()) {
    // 如果超过100，将最后一个修改的数量重置为1
    const lastIndex = categoryRows.value.length - 1;
    if (lastIndex >= 0) {
      categoryRows.value[lastIndex].quantity = 1;
    }
  }
};

// 获取题目数据（添加数量总和校验）
const fetchQuestions = async () => {
  if (categoryRows.value.length === 0) {
    ElMessage.warning('请添加至少一个筛选条件');
    return;
  }

  // 验证筛选条件
  for (const row of categoryRows.value) {
    if (!row.categoryId) {
      ElMessage.warning('请选择类目');
      return;
    }
    if (!row.quantity || row.quantity < 1) {
      ElMessage.warning('请输入有效的题目数量');
      return;
    }
  }
  
  // 校验数量总和
  if (!validateTotalQuantity()) {
    return;
  }

  loading.value = true;
  allQuestions.value = [];
  userAnswers.value = {};

  try {
    for (const row of categoryRows.value) {
      const queryParams = {
        categoryId: row.categoryId,
        tagIds: row.tagId,
        pageSize: 1000
      };

      const questionRes = await listManage(queryParams);
      let questions = questionRes.rows || [];

      if (questions.length === 0) {
        ElMessage.warning(`类目 ${getCategoryName(row.categoryId)} 下没有找到符合条件的题目`);
        continue;
      }

      // 随机排序
      questions = questions.sort(() => Math.random() - 0.5);

      // 截取需要的数量
      const takeCount = Math.min(row.quantity, questions.length);
      const selectedQuestions = questions.slice(0, takeCount);

      for (const question of selectedQuestions) {
        const questionId = question.id;
        
        // 根据题型初始化答案
        if (question.questionType === 1 || question.questionType === 3) {
          userAnswers.value[questionId] = null; // 单选和判断初始为null
        } else if (question.questionType === 2) {
          userAnswers.value[questionId] = []; // 多选初始为数组
        } else {
          userAnswers.value[questionId] = ''; // 其他题型初始为空字符串
        }

        // 获取选项（单选/多选/判断）
        if ([1, 2, 3].includes(question.questionType)) {
          const optionRes = await getOptionByQuestionId(questionId);
          question.options = optionRes.rows
            .sort((a, b) => Number(a.sort) - Number(b.sort));
        }
        allQuestions.value.push(question);
      }
    }

    if (allQuestions.value.length > 0) {
      ElMessage.success(`共加载 ${allQuestions.value.length} 道题目`);
    } else {
      ElMessage.warning('未找到符合条件的题目');
    }
  } catch (error) {
    console.error('获取题目失败:', error);
    ElMessage.error('获取题目失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

// 获取类目名称
const getCategoryName = (categoryId) => {
  const findCategory = (categories, id) => {
    for (const category of categories) {
      if (category.id === id) return category.name;
      if (category.children) {
        const found = findCategory(category.children, id);
        if (found) return found;
      }
    }
    return null;
  };
  return findCategory(categoryOptions.value, categoryId) || '未知类目';
};

// 单选题选择答案
const selectSingleOption = (questionId, optIndex) => {
  userAnswers.value[questionId] = optIndex;
};

// 多选题切换选项
const toggleMultipleOption = (questionId, optIndex) => {
  if (!userAnswers.value[questionId]) {
    userAnswers.value[questionId] = [];
  }
  
  const index = userAnswers.value[questionId].indexOf(optIndex);
  if (index > -1) {
    userAnswers.value[questionId].splice(index, 1);
  } else {
    userAnswers.value[questionId].push(optIndex);
    userAnswers.value[questionId].sort((a, b) => a - b);
  }
};

// 判断题设置答案
const setJudgeAnswer = (questionId, isCorrect) => {
  userAnswers.value[questionId] = isCorrect;
};

// 保存到个人题库（添加校验）
const saveToPersonalBank = () => {
  if (allQuestions.value.length === 0) {
    ElMessage.warning('请先加载题目');
    return;
  }
  
  // 检查是否所有题目都已完成
  if (answeredCount.value < allQuestions.value.length) {
    ElMessage.warning('请完成所有题目后再保存');
    return;
  }
  
  // 这里预留保存到个人题库的功能
  // 实际实现时会将当前筛选条件保存为个人题库
  ElMessageBox.confirm(
    `确定要将 ${allQuestions.value.length} 道题目保存到个人题库吗？`,
    '保存到个人题库',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'info'
    }
  ).then(() => {
    // 这里将实现保存逻辑
    console.log('保存题目到个人题库', allQuestions.value, userAnswers.value);
    ElMessage.success('题目已保存到个人题库');
  }).catch(() => {});
};

// 生成PDF
const generatePDF = () => {
  if (allQuestions.value.length === 0) {
    ElMessage.warning('请先加载题目');
    return;
  }

  const pdfContainer = document.createElement('div');
  pdfContainer.style.width = '100%';
  pdfContainer.style.padding = '20px';
  
  const titleElement = document.createElement('h1');
  titleElement.textContent = '笔试题目';
  titleElement.style.textAlign = 'center';
  titleElement.style.marginBottom = '30px';
  pdfContainer.appendChild(titleElement);
  
  Object.entries(groupedQuestions.value).forEach(([type, questions]) => {
    const typeTitle = document.createElement('h2');
    typeTitle.textContent = getQuestionTypeName(Number(type));
    typeTitle.style.marginTop = '20px';
    pdfContainer.appendChild(typeTitle);
    
    questions.forEach((question, index) => {
      const questionElement = document.createElement('div');
      questionElement.style.margin = '15px 0';
      
      const questionHeader = document.createElement('div');
      questionHeader.style.fontWeight = 'bold';
      questionHeader.style.marginBottom = '10px';
      questionHeader.textContent = `${index + 1}. ${question.title}`;
      questionElement.appendChild(questionHeader);
      
      const contentElement = document.createElement('div');
      contentElement.innerHTML = question.content;
      contentElement.style.marginBottom = '10px';
      questionElement.appendChild(contentElement);
      
      if ([1, 2, 3].includes(question.questionType) && question.options) {
        const optionsContainer = document.createElement('div');
        optionsContainer.style.marginLeft = '20px';
        
        question.options.forEach((option, optIndex) => {
          const optionElement = document.createElement('div');
          optionElement.style.margin = '5px 0';
          optionElement.textContent = `${String.fromCharCode(65 + optIndex)}. ${option.optionContent}`;
          optionsContainer.appendChild(optionElement);
        });
        
        questionElement.appendChild(optionsContainer);
      }
      
      pdfContainer.appendChild(questionElement);
    });
  });
  
  document.body.appendChild(pdfContainer);
  
  const opt = {
    margin: 10,
    filename: `笔试题目_${new Date().getTime()}.pdf`,
    image: { type: 'jpeg', quality: 0.98 },
    html2canvas: { scale: 2 },
    jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
  };
  
  html2pdf().from(pdfContainer).set(opt).save().then(() => {
    document.body.removeChild(pdfContainer);
  });
};

// 初始化
onMounted(() => {
  loadCategoryAndTags();
  addCategoryRow();
  
  // 启动时间更新
  updateTime();
  timer.value = setInterval(updateTime, 1000);
});

onUnmounted(() => {
  if (timer.value) {
    clearInterval(timer.value);
  }
});
</script>

<style scoped>
.exam-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 60px);
}

.exam-header {
  margin-bottom: 20px;
  padding: 15px 20px;
  color: #1f2329;
}

.category-search-area {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.search-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.action-buttons {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.search-table {
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
}

.questions-area {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.questions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}

.loading-card {
  text-align: center;
  padding: 50px 0;
  background: transparent;
  border: none;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.empty-state {
  padding: 50px 0;
  text-align: center;
  background-color: #fff;
  border-radius: 8px;
}

.questions-list {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.question-type-group {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.type-title {
  font-size: 18px;
  color: #1f2329;
  font-weight: 600;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid #409eff;
}

.type-questions {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.question-card {
  transition: all 0.3s;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  background-color: #fafbfc;
}

.question-card:hover {
  border-color: #409eff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
  transform: translateY(-2px);
}

.question-header {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
}

.question-number {
  font-weight: bold;
  margin-right: 8px;
  color: #409eff;
  font-size: 16px;
  min-width: 24px;
}

.question-title {
  font-weight: 600;
  flex: 1;
  color: #1f2329;
  font-size: 16px;
}

.question-content {
  margin-bottom: 20px;
  line-height: 1.7;
  color: #303133;
  font-size: 15px;
}

.question-options {
  margin-left: 20px;
  margin-bottom: 15px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 12px;
}

.option-item {
  display: flex;
  align-items: flex-start;
  padding: 12px 15px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid #dcdfe6;
  background-color: #fafbfc;
}

.option-item:hover {
  background-color: #f0f7ff;
  border-color: #c6e2ff;
}

.option-letter {
  font-weight: bold;
  margin-right: 10px;
  min-width: 22px;
  color: #409eff;
}

.option-content {
  flex: 1;
}

.selected-option {
  background-color: #e6f7ff;
  border-color: #91d5ff;
  box-shadow: inset 0 0 0 1px #91d5ff;
}

.judge-options {
  display: flex;
  gap: 20px;
  margin-left: 20px;
  margin-bottom: 15px;
}

.answer-input-area {
  margin-top: 10px;
}

.code-hint {
  margin-top: 8px;
  font-size: 13px;
  color: #909399;
  text-align: right;
}

.stats-bar {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  background-color: white;
  border-top: 1px solid #ebeef5;
  font-size: 14px;
  color: #606266;
  border-radius: 0 0 8px 8px;
}

.question-count {
  display: flex;
  gap: 20px;
}

@media (max-width: 768px) {
  .search-header {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }
  
  .action-buttons {
    width: 100%;
    justify-content: center;
  }
  
  .questions-header {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }
  
  .stats-bar {
    flex-direction: column;
    gap: 10px;
    align-items: center;
  }
  
  .question-count {
    justify-content: center;
  }
}

/* 树形选择器样式 - 加粗父节点 */
:deep(.category-tree-select) .el-tree-node__content .el-tree-node__label {
  font-weight: normal;
}

:deep(.category-tree-select) .el-tree-node.is-disabled > .el-tree-node__content .el-tree-node__label {
  font-weight: bold !important;
  color: #1f2329 !important;
}

:deep(.category-tree-select) .el-tree-node.is-disabled {
  cursor: not-allowed;
}
.parent-node {
  font-weight: bold;
}
</style>