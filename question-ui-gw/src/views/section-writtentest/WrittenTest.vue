<template>
  <div class="exam-container">
    <!-- 页面标题和操作区 -->
    <div class="exam-header">
      <h2>笔试题目组卷</h2>
    </div>

    <!-- 类目搜索区域 -->
    <div class="category-search-area rounded-xl bg-white shadow-sm p-4">
      <div class="search-header mb-4">
        <h3 class="text-lg font-semibold">题目筛选条件</h3>
        <div class="action-buttons flex gap-2">
          <el-button type="primary" size="small" @click="addCategoryRow" class="btn-add">
            <el-icon><Plus /></el-icon> 增加类目
          </el-button>
          <el-button 
            type="primary" 
            size="small" 
            @click="fetchQuestions"
            class="btn-refresh"
          >
            <el-icon><Refresh /></el-icon> 刷新题目
          </el-button>
          <el-button type="primary" size="small" @click="saveToPersonalBank" class="btn-save">
            <el-icon><Check /></el-icon> 生成
          </el-button>
          <el-button type="success" size="small" @click="generatePDF" class="btn-export">
            <el-icon><Document /></el-icon> 导出试卷
          </el-button>
        </div>
      </div>

      <!-- 搜索条件行 - 一行一个筛选组 -->
      <div class="filter-container space-y-3">
        <div 
          v-for="(row, rowIndex) in categoryRows" 
          :key="row.id"
          class="filter-row flex items-center gap-6 p-3 rounded-lg hover:bg-gray-50 transition-colors"
        >
          <!-- 类目 -->
          <div class="filter-item flex items-center gap-2 flex-1 min-w-[220px]">
            <label class="text-gray-700 font-medium whitespace-nowrap">类目:</label>
            <el-tree-select
              v-model="row.categoryId"
              :data="categoryOptions"
              :props="treeSelectProps"
              value-key="id"
              placeholder="请选择"
              check-strictly
              style="width: 200px"
              class="category-tree-select rounded-md"
            >
              <template #default="{ data }">
                <span :class="{ 'parent-node': data.children && data.children.length > 0 }">{{ data.name }}</span>
              </template>
            </el-tree-select>
          </div>
          
          <!-- 标签 -->
          <div class="filter-item flex items-center gap-2 flex-1 min-w-[300px]">
            <label class="text-gray-700 font-medium whitespace-nowrap">标签:</label>
            <el-select 
              v-model="row.tagId" 
              placeholder="请选择" 
              clearable 
              style="width: 150px"
              class="rounded-md"
            >
              <el-option
                v-for="tag in tagNameList"
                :key="tag.id"
                :label="tag.tagName"
                :value="tag.id"
              />
            </el-select>
          </div>
          
          <!-- 数量 -->
          <div class="filter-item flex items-center gap-2 flex-1 min-w-[160px]">
            <label class="text-gray-700 font-medium whitespace-nowrap">数量:</label>
            <el-input 
              v-model.number="row.quantity" 
              type="number" 
              min="1" 
              placeholder="输入数量"
              style="width: 80px"
              @change="validateQuantity"
              class="rounded-md"
            />
          </div>
          
          <!-- 操作 -->
          <div class="filter-item flex items-center gap-2 min-w-[80px]">
            <label class="text-gray-700 font-medium whitespace-nowrap">操作:</label>
            <el-button 
              link 
              type="danger" 
              size="small" 
              @click="deleteRow(row.id)"
              class="delete-btn"
            >
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
        </div>
      </div>

      <!-- 空状态提示 -->
      <div v-if="categoryRows.length === 0" class="empty-filter text-center py-6 text-gray-500 border border-dashed rounded-lg mt-3">
        暂无筛选条件，请点击"增加类目"添加
      </div>
    </div>

    <!-- 题目展示区域 -->
    <div class="questions-area mt-6 bg-white rounded-xl shadow-sm p-4">
      <div class="questions-header flex justify-between items-center">
        <h3 class="text-lg font-semibold">笔试题目</h3>
        <el-tag type="info">共 {{ allQuestions.length }} 题</el-tag>
      </div>

      <el-card v-if="loading" class="loading-card border-0 shadow-none bg-transparent">
        <div class="loading-container flex flex-col items-center py-10">
          <el-icon class="is-loading" color="#409eff" size="48">
            <Loading />
          </el-icon>
          <p style="margin-top: 15px; font-size: 16px; color: #606266;">正在加载题目，请稍候...</p>
        </div>
      </el-card>

      <div v-else-if="allQuestions.length === 0" class="empty-state py-10">
        <el-empty description="请设置筛选条件并点击刷新题目" />
      </div>

      <div v-else>
        <!-- 题目列表 -->
        <div class="questions-list space-y-4">
          <el-card 
            v-for="(question, index) in currentPageQuestions" 
            :key="question.id" 
            class="question-card border rounded-lg overflow-hidden transition-all hover:shadow-md"
          >
            <div class="question-header p-4 pb-0">
              <span class="question-number text-primary font-bold mr-2">
                {{ (currentPage - 1) * pageSize + index + 1 }}.
              </span>
              <span class="question-title font-semibold">
                {{ question.title }}
                <span class="question-type-tag text-gray-500 text-sm ml-2 font-normal">
                  ({{ getQuestionTypeName(question.questionType) }})
                </span>
              </span>
            </div>
            
            <div class="question-content p-4 pt-2" v-html="question.content"></div>
            
            <!-- 单选题 -->
            <div v-if="question.questionType == 1" class="question-options p-4 pt-0 pb-4">
              <div 
                v-for="(option, optIndex) in question.options" 
                :key="option.id" 
                class="option-item flex items-center p-3 mb-2 rounded-md border transition-all cursor-pointer hover:bg-gray-50"
                @click="selectSingleOption(question.id, optIndex)"
                :class="{ 'selected-option': userAnswers[question.id] === optIndex }"
              >
                <span class="option-letter font-bold text-primary mr-3">
                  {{ String.fromCharCode(65 + optIndex) }}.
                </span>
                <span class="option-content">{{ option.optionContent }}</span>
              </div>
            </div>
            
            <!-- 多选题 -->
            <div v-if="question.questionType == 2" class="question-options p-4 pt-0 pb-4">
              <div 
                v-for="(option, optIndex) in question.options" 
                :key="option.id" 
                class="option-item flex items-center p-3 mb-2 rounded-md border transition-all cursor-pointer hover:bg-gray-50"
                @click="toggleMultipleOption(question.id, optIndex)"
                :class="{ 'selected-option': userAnswers[question.id]?.includes(optIndex) }"
              >
                <span class="option-letter font-bold text-primary mr-3">
                  {{ String.fromCharCode(65 + optIndex) }}.
                </span>
                <span class="option-content">{{ option.optionContent }}</span>
              </div>
            </div>
            
            <!-- 判断题 -->
            <div v-if="question.questionType == 3" class="judge-options p-4 pt-0 pb-4 flex gap-4">
              <div 
                class="option-item flex items-center p-3 rounded-md border transition-all cursor-pointer hover:bg-gray-50 flex-1"
                @click="setJudgeAnswer(question.id, true)"
                :class="{ 'selected-option': userAnswers[question.id] === true }"
              >
                <span class="option-letter font-bold text-primary mr-3">A.</span>
                <span class="option-content">正确</span>
              </div>
              <div 
                class="option-item flex items-center p-3 rounded-md border transition-all cursor-pointer hover:bg-gray-50 flex-1"
                @click="setJudgeAnswer(question.id, false)"
                :class="{ 'selected-option': userAnswers[question.id] === false }"
              >
                <span class="option-letter font-bold text-primary mr-3">B.</span>
                <span class="option-content">错误</span>
              </div>
            </div>
            
            <!-- 填空题 -->
            <div v-if="question.questionType == 4" class="answer-input-area p-4 pt-0 pb-4">
              <el-input 
                v-model="userAnswers[question.id]" 
                placeholder="请输入答案" 
                clearable
                class="rounded-md"
              />
            </div>
            
            <!-- 简答题 -->
            <div v-if="question.questionType == 5" class="answer-input-area p-4 pt-0 pb-4">
              <el-input 
                v-model="userAnswers[question.id]" 
                type="textarea" 
                :rows="4" 
                placeholder="请输入答案" 
                clearable
                class="rounded-md"
              />
            </div>
            
            <!-- 编程题 -->
            <div v-if="question.questionType == 6" class="answer-input-area p-4 pt-0 pb-4">
              <el-input 
                v-model="userAnswers[question.id]" 
                type="textarea" 
                :rows="8" 
                placeholder="请输入代码" 
                clearable
                monospace
                class="rounded-md"
              />
              <p class="code-hint text-xs text-gray-500 mt-2">提示：可使用Tab键缩进代码</p>
            </div>
          </el-card>
        </div>
        
        <!-- 分页控件 -->
        <div class="pagination-container mt-6 flex justify-center">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="allQuestions.length"
            background
            class="rounded-lg"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    
    <!-- 底部统计栏 -->
    <div class="stats-bar mt-6 bg-white rounded-xl shadow-sm p-4 flex flex-wrap justify-between items-center gap-4">
      <div class="question-count flex gap-6">
        <span class="text-gray-700"><i class="fa fa-list-ul mr-1"></i>总题数: {{ allQuestions.length }}</span>
        <span class="text-gray-700"><i class="fa fa-check-square-o mr-1"></i>已答题: {{ answeredCount }}</span>
      </div>
      <div class="text-gray-700"><i class="fa fa-clock-o mr-1"></i>当前时间: {{ currentTime }}</div>
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
// 分页相关
const currentPage = ref(1);
const pageSize = ref(5);

// 树形选择器配置
const treeSelectProps = {
  value: 'id',
  label: 'name',
  children: 'children',
  // 禁用父节点（非叶子节点）
  disabled: (data) => {
    return data.children && data.children.length > 0;
  }
};

// 初始化数据
let rowId = 1;

// 获取当前页的题目
const currentPageQuestions = computed(() => {
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = startIndex + pageSize.value;
  return allQuestions.value.slice(startIndex, endIndex);
});

// 获取题型名称
const getQuestionTypeName = (typeValue) => {
  const typeNum = Number(typeValue); 
  if (isNaN(typeNum)) {
    return '未知题型';
  }
  const type = questionTypes.find(t => t.value === typeNum);
  return type ? type.label : '未知题型';
};

// 计算已答题数
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
    // 构建树形结构
    const buildTree = (data, parentId = null) => {
      return data
        .filter(item => {
          const isTopLevel = [null, undefined, 0, -1, '0', '-1', ''].includes(item.parentId);
          if (parentId === null) {
            return isTopLevel;
          }
          return Number(item.parentId) === Number(parentId);
        })
        .map(item => ({
          ...item,
          children: buildTree(data, item.id) || []
        }));
    };
    categoryOptions.value = buildTree(categories);
    
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

// 获取题目数据
const fetchQuestions = async () => {
  if (categoryRows.value.length === 0) {
    ElMessage.warning('请添加至少一个筛选条件');
    return;
  }

// 校验是否所有筛选行的类目和标签组合唯一
  const seen = new Set();

  for (const row of categoryRows.value) {
    const key = `${row.categoryId}-${row.tagId}`; // 用字符串表示组合

    if (seen.has(key)) {
      ElMessage.warning('存在重复的类目和标签组合，请勿重复添加');
      return;
    }

    seen.add(key);
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
  currentPage.value = 1; // 重置为第一页

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

// 保存到个人题库
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
  
  ElMessageBox.confirm(
    `确定要将 ${allQuestions.value.length} 道题目保存到个人题库吗？`,
    '保存到个人题库',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'info'
    }
  ).then(() => {
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

// 分页相关方法
const handleSizeChange = (val) => {
  pageSize.value = val;
  currentPage.value = 1; // 重置为第一页
};

const handleCurrentChange = (val) => {
  currentPage.value = val;
  // 滚动到页面顶部
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

// 按题型分组题目（PDF生成用）
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

// 生命周期钩子
onMounted(() => {
  loadCategoryAndTags();
  // 初始化一行筛选条件
  addCategoryRow();
  // 启动定时器更新时间
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
/* 按钮样式优化 */
.action-buttons {
  display: flex;
  gap: 8px;
}

.btn-add, .btn-refresh, .btn-save, .btn-export {
  flex: 1;
  min-width: 100px;
  border-radius: 6px !important;
}

/* 筛选行样式 */
/* 筛选行样式 */
.filter-container {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.filter-row {
  display: flex;
  align-items: center;
  width: 100%;
  transition: all 0.2s;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 0 12px;
}



/* 题型标签样式 */
.question-type-tag {
  color: #606266;
  font-size: 14px;
  margin-left: 8px;
  font-weight: normal;
}

/* 分页容器样式 */
.pagination-container {
  margin-top: 20px;
  text-align: center;
}

/* 选中选项样式 */
.selected-option {
  background-color: #ecf5ff;
  border-color: #91d5ff;
}

/* 题目卡片样式 */
.question-card {
  border: 1px solid #e5e7eb;
  border-radius: 8px !important;
  overflow: hidden;
  transition: all 0.3s;
}

.question-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transform: translateY(-2px);
}

/* 选项样式优化 */
.option-item {
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  transition: all 0.2s;
}

.option-item:hover {
  background-color: #f5f7fa;
}

/* 空筛选条件样式 */
.empty-filter {
  color: #909399;
  padding: 20px;
  text-align: center;
  border: 1px dashed #dcdfe6;
  border-radius: 8px;
}

/* 其他样式 */
.exam-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 40px);
}

.exam-header {
  margin-bottom: 20px;
  color: #1f2329;
}

.search-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.questions-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.loading-card {
  text-align: center;
  padding: 40px 0;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.empty-state {
  padding: 40px 0;
  text-align: center;
}

.question-content {
  line-height: 1.7;
  color: #303133;
}

.code-hint {
  font-size: 12px;
  color: #909399;
  margin-top: 8px;
}

.stats-bar {
  color: #606266;
  font-size: 14px;
}

/* 响应式调整 */
@media (max-width: 1024px) {
  .filter-row {
    flex-wrap: wrap;
  }
}

@media (max-width: 768px) {
  .filter-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .action-buttons {
    flex-wrap: wrap;
  }
  
  .btn-add, .btn-refresh, .btn-save, .btn-export {
    flex: 1 0 45%;
  }
  
  .stats-bar {
    flex-direction: column;
    align-items: flex-start;
  }
}



/* 按钮通用优化 */
.el-button {
  transition: all 0.2s ease;
}
.el-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 筛选行内元素间距和对齐优化 */
.filter-item label {
  font-size: 14px;
  color: #555;
}
.filter-item .el-select,
.filter-item .el-input {
  font-size: 14px;
}
.filter-item .el-tree-select {
  width: 200px; /* 保持原有宽度，优化字体适配 */
}

/* 操作按钮颜色加深，hover 更明显 */
.delete-btn {
  color: #dc3545; 
}
.delete-btn:hover {
  color: #c82333;
}

/* 题目卡片阴影优化，hover 效果更柔和 */
.question-card {
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.06);
}
.question-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* 分页组件样式优化 */
.el-pagination {
  padding: 10px 0;
}
.el-pagination__sizes {
  margin: 0 10px;
}
.el-pagination__total {
  color: #666;
}

/* 底部统计栏图标与文字对齐优化 */
.stats-bar i {
  vertical-align: middle;
  margin-top: -2px;
}

/* 空状态提示文字颜色和间距优化 */
.empty-filter {
  color: #888;
  border-color: #eee;
}

/* 加载动画区域居中优化（当题目加载时） */
.loading-container {
  min-height: 200px;
  justify-content: center;
}

/* 代码提示文字颜色调整，更柔和 */
.code-hint {
  color: #777;
}
</style>