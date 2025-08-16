NEW_FILE_CODE







<template>
  <div class="question-bank-container">
    <!-- 左侧类目列表 -->
    <div class="category-sidebar">
      <div class="sidebar-header">
        <h2>题库类目</h2>
        <!-- <el-pagination
          small
          :total="categoryTotal"
          :page-size="categoryPageSize"
          :current-page="categoryPageNum"
          @current-change="handleCategoryPageChange"
          layout="prev, pager, next"
          class="category-pagination"
        /> -->
      </div>
      
      <el-tree
        :data="treeCategoryList"
        :props="categoryProps"
        :expand-on-click-node="false"
        class="category-tree"
      >
        <template #default="{ node, data }">
          <span 
            :class="[
              'custom-node', 
              { 'top-level': data.parentName === '顶级类目', 'selected': data.id === selectedCategoryId }
            ]"
            @click="handleCategoryClick(data, node)"
          >
            {{ node.label }}
          </span>
        </template>
      </el-tree>
    </div>
    
    <!-- 右侧题目列表 -->
    <div class="question-content">
      <div v-if="loading" class="loading-container">
        <el-loading spinning />
      </div>
      
      <div v-else-if="selectedCategoryId">
        <div class="content-header">
          <h2>{{ selectedCategoryName }} (共 {{ questionTotal }} 题)</h2>
        
        </div>
        
        <div class="question-list">
          <div 
            v-for="(question, index) in questionList" 
            :key="question.id" 
            class="question-item"
            @click="goToDetail(question.id)"
          >
            <div class="question-number">{{ index + 1 }}. {{ question.title }}</div>
            <div class="question-brief">
              {{ formatQuestionContent(question.content) }}
            </div>
            <div class="question-meta">
              <span class="question-type">
                <dict-tag :options="sys_question_questiontype" :value="question.questionType"/>
              </span>
              <span class="question-status">
                <dict-tag :options="sys_status_question" :value="question.status"/>
              </span>
            </div>
          </div>
        </div>
      </div>



      
      
      <div v-else class="empty-state">
        <p>请选择左侧的类目查看题目</p>
      </div>
    </div>
    <!-- 分页控件：右下角 -->
  <div class="pagination-bottom">
    <el-pagination
      :total="questionTotal"
      :page-size="questionPageSize"
      :current-page="questionPageNum"
      @current-change="handleQuestionPageChange"
      layout="prev, pager, next, jumper"
      background
    />
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch,computed } from 'vue';
import { useRouter } from 'vue-router';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';
import { useDict } from '@/utils/dict';
import { useRoute } from 'vue-router';

// 路由
const router = useRouter();
const route = useRoute();
// 字典
const { sys_question_questiontype, sys_status_question } = useDict('sys_question_questiontype', 'sys_status_question');

// 类目相关
const categoryList = ref([]);
const treeCategoryList = ref([]);
const categoryTotal = ref(0);
const categoryPageNum = ref(1);
const categoryPageSize = ref(10);
const categoryProps = {
  value: 'id',
  label: 'name',
  children: 'children'
};
const selectedCategoryId = ref(null);
const selectedCategoryName = ref('');

// 题目相关
const questionList = ref([]);
const questionTotal = ref(0);
const questionPageNum = ref(1);
const questionPageSize = ref(10);
const loading = ref(false);


// 默认页面

const getFirstSubCategory = (treeData) => {
  if (!treeData || treeData.length === 0) return null;


  // 获取第一个父类目（顶级类目）
  const firstParent = treeData[0];
  
  // 检查是否有子类目
  if (firstParent && firstParent.children && firstParent.children.length > 0) {
    // 返回第一个子类目
    return firstParent.children[0];
  }
  
  return null;
};






// 将平铺数据转换为树形结构
const buildTree = (data) => {
  const map = {};
  const result = [];

  // 构建 id 到节点的映射
  data.forEach(item => {
    map[item.id] = { ...item, children: [] };
  });

  // 根据 parentId 构建树
  data.forEach(item => {
    if (item.parentId && map[item.parentId]) {
      map[item.parentId].children.push(map[item.id]);
    } else if (item.parentId === 0) {
      // 顶级类目
      result.push(map[item.id]);
    }
  });

  return result;
};

// 获取类目列表
const getCategoryList = async () => {
  try {
    const response = await listCategory({
      pageNum: categoryPageNum.value,
      pageSize: categoryPageSize.value
    });
    
    // 处理数据，转换为树形结构
    categoryList.value = response.data;
    treeCategoryList.value = buildTree(response.data);
    
    categoryTotal.value = response.total || 0;


  } catch (error) {
    console.error('获取类目列表失败:', error);
  }
   
  if(route.query.categoryId) {
    const firstChild = getFirstChildOfParent(route.query.categoryId ,route.query.categoryName);
      if (firstChild) {
        selectedCategoryId.value = firstChild.id;
        selectedCategoryName.value = firstChild.name;
        questionPageNum.value = 1;
        getQuestionList();
      }
  }
    else {


      const firstSubCategory = getFirstSubCategory(treeCategoryList.value);
              if (firstSubCategory) {
                selectedCategoryId.value = firstSubCategory.id;
                selectedCategoryName.value = firstSubCategory.name;
              getQuestionList();
                 }
    }
            
};






// 获取题目列表
const getQuestionList = async () => {
  if (!selectedCategoryId.value) return;
  
  loading.value = true;
  try {
    const response = await listManage({
      categoryId: selectedCategoryId.value,
      pageNum: questionPageNum.value,
      pageSize: questionPageSize.value
    });
    questionList.value = response.rows;
    questionTotal.value = response.total || 0;
  } catch (error) {
    console.error('获取题目列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 处理类目点击
const handleCategoryClick = (data, node) => {
  // 如果是顶级类目（parentName为"顶级类目"），则不处理点击事件
  if (data.parentName === '顶级类目') {
    return;
  }
  
  selectedCategoryId.value = data.id;
  selectedCategoryName.value = data.name;
  questionPageNum.value = 1; // 重置页码
  getQuestionList();
};

// 处理类目分页变化
const handleCategoryPageChange = (page) => {
  categoryPageNum.value = page;
  getCategoryList();
};

// 处理题目分页变化
const handleQuestionPageChange = (page) => {
  questionPageNum.value = page;
  getQuestionList();
};

// 格式化题目内容（显示部分内容）
const formatQuestionContent = (content) => {
  if (!content) return '';
  // 移除HTML标签
  const plainText = content.replace(/<[^>]+>/g, '');
  // 限制显示长度
  return plainText.length > 100 ? plainText.substring(0, 100) + '...' : plainText;
};

// 跳转到详情页
const goToDetail = (id) => {
  router.push(`/section-bank/questionDetail/${id}`);
};



// 获取指定父类的第一个子类
const getFirstChildOfParent = (parentId, parentName) => {
  // 检查树形类目列表是否存在
  if (!treeCategoryList.value || treeCategoryList.value.length === 0) {
    console.warn('类目列表为空，无法获取子类');
    return null;
  }

  // 递归查找父节点
  const findParentNode = (nodes) => {
    for (const node of nodes) {
      // 匹配父类ID或父类名称
      if (node.id === parentId || node.name === parentName) {
        return node;
      }
      // 递归查找子节点
      if (node.children && node.children.length > 0) {
        const found = findParentNode(node.children);
        if (found) return found;
      }
    }
    return null;
  };

  // 查找对应的父节点
  const parentNode = findParentNode(treeCategoryList.value);
  
  if (!parentNode) {
    console.warn(`未找到ID为${parentId}或名称为${parentName}的父类`);
    return null;
  }

  // 检查是否有子节点
  if (parentNode.children && parentNode.children.length > 0) {
    // 返回第一个子节点的ID和名称
    const firstChild = parentNode.children[0];
    return {
      id: firstChild.id,
      name: firstChild.name
    };
  } else {
    console.warn(`父类${parentName}没有子类目`);
    return null;
  }
};

// // 监听路由参数并自动加载第一个子类
// watch(
//   () => [route.query.categoryId, route.query.categoryName],
//   async ([parentId, parentName]) => {
//     if (parentId && parentName) {
//       // 确保类目列表已加载
//       if (treeCategoryList.value.length === 0) {
//         await getCategoryList();
//       }
      
//       // 获取第一个子类
//       const firstChild = getFirstChildOfParent(parentId, parentName);
//       if (firstChild) {
//         selectedCategoryId.value = firstChild.id;
//         selectedCategoryName.value = firstChild.name;
//         questionPageNum.value = 1;
//         getQuestionList();
//       }
//     }
//   },
//   { immediate: true }
// );


// 初始化
onMounted(() => {
  getCategoryList();
  // 监听路由参数变化

});
</script>

<style scoped>
.question-bank-container {
  display: flex;
  min-height: calc(100vh - 60px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.category-sidebar {
  width: 200px;
  border-right: 1px solid #e5e7eb;
  padding: 25px 10px;
  background-color: #ffffff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.05);
}

.sidebar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e5e7eb;
}

.category-pagination :deep(.el-pager li) {
  background-color: #f9fafb;
}

.category-pagination :deep(.el-pager li.active) {
  background-color: #409eff;
  color: #fff;
}

.category-tree {
  height: calc(100% - 80px);
  overflow-y: auto;
}

.custom-node {
  flex: 1;
  padding: 8px 12px;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.top-level {
  font-weight: bold;
  color: #1f2937;
  cursor: default;
}

.custom-node:hover {
  background-color: #e8f4ff;
}

.custom-node.selected {
  background-color: #e0f2ff;
  font-weight: bold;
}

.question-content {
  flex: 1;
  padding: 30px 40px;
  overflow-y: auto;
  position: relative; /* 添加此行 */
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e5e7eb;
}

.question-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.question-item {
  padding: 20px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.04);
}

.question-item:hover {
  border-color: #409eff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
}

.question-number {
  font-weight: 600;
  margin-bottom: 10px;
  color: #1f2937;
  font-size: 16px;
}

.question-brief {
  color: #4b5563;
  margin-bottom: 12px;
  line-height: 1.5;
  font-size: 14px;
}

.question-meta {
  display: flex;
  gap: 12px;
  font-size: 13px;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}

.empty-state {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  color: #6b7280;
  font-size: 16px;
}


.pagination-bottom {
  position: absolute;
  bottom: 20px;
  right: 40px;
  z-index: 10;
}

/* 可选：调整分页按钮大小 */
.pagination-bottom .el-pagination {
  display: flex;
  justify-content: flex-end;
}
</style>