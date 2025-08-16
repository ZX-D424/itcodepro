<!-- <template>
  <div>
    <h1>欢迎来到首页</h1>
  </div>
</template>

<script setup>
</script>

<style scoped>
</style> -->



<template>
  <div class="home-container">
    <div class="page-header">
      <h1>题库首页</h1>
      <p>欢迎使用在线题库系统，这里汇总了各类技术题目</p>
    </div>
    
    <div class="category-stats">
      <div class="stat-card" v-for="category in firstLevelCategories" :key="category.id">
        <div class="card-header">
          <h2>{{ category.name }}</h2>
          <span class="category-remark">{{ category.remark || '无描述' }}</span>
        </div>
        <div class="card-body">
          <div class="stat-item">
            <span class="stat-label">总数</span>
            <span class="stat-value total">{{ category.totalCount }}</span>
          </div>
          <div class="stat-item">
            <span class="stat-label">上新</span>
            <span class="stat-value new">{{ category.newCount }}</span>
          </div>
          <div class="stat-item">
            <span class="stat-label">纠错</span>
            <span class="stat-value update">{{ category.updateCount }}</span>
          </div>
        </div>
        <div class="card-footer">
          <el-button type="primary" size="small" @click="goToCategory(category.id, category.name)">
            查看题目
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';

// 路由
const router = useRouter();

// 数据
const categoryList = ref([]);
const firstLevelCategories = ref([]);
const loading = ref(false);

// 获取今天的日期字符串（YYYY-MM-DD）
const getToday = () => {
  const date = new Date();
  return date.toISOString().split('T')[0];
};

// 检查日期是否为今天
const isToday = (dateString) => {
  if (!dateString) return false;
  const date = new Date(dateString);
  return date.toISOString().split('T')[0] === getToday();
};

// 获取类目列表
const getCategoryList = async () => {
  loading.value = true;
  try {
    const response = await listCategory({});
    categoryList.value = response.data || [];
    
    // 筛选一级类目（父类目为顶级类目）
    const topLevelCategories = categoryList.value.filter(
      item => item.parentName === '顶级类目'
    );
    
    // 为每个一级类目计算统计数据
    for (const category of topLevelCategories) {
      await calculateCategoryStats(category);
    }
    
    firstLevelCategories.value = topLevelCategories;
  } catch (error) {
    console.error('获取类目列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 计算类目统计数据
const calculateCategoryStats = async (category) => {
  try {
    // 获取该类目下的所有题目
    const response = await listManage({
      categoryId: category.id,
      pageNum: 1,
      pageSize: 1000 // 假设一次能获取所有题目，实际可能需要分页处理
    });
    
    const questions = response.rows || [];
    const today = getToday();
    
    // 总数
    category.totalCount = questions.length;
    
    // 上新数（创建时间为今天）
    category.newCount = questions.filter(q => 
      q.createTime && q.createTime.startsWith(today)
    ).length;
    
    // 纠错数（修改时间为今天）
    category.updateCount = questions.filter(q => 
      q.updateTime && q.updateTime.startsWith(today)
    ).length;
    
  } catch (error) {
    console.error(`计算类目${category.name}统计数据失败:`, error);
    category.totalCount = 0;
    category.newCount = 0;
    category.updateCount = 0;
  }
};

// 跳转到对应类目
const goToCategory = (categoryId, categoryName) => {
  router.push({
    path: '/section-bank/QuestionBank',
    query: { categoryId, categoryName }
  });
};

// 初始化
onMounted(() => {
  getCategoryList();
});
</script>

<style scoped>
.home-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
  padding: 20px 0;
  border-bottom: 1px solid #eee;
}

.page-header h1 {
  font-size: 28px;
  margin-bottom: 10px;
  color: #333;
}

.page-header p {
  color: #666;
  font-size: 16px;
}

.category-stats {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.stat-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.card-header {
  padding: 15px 20px;
  border-bottom: 1px solid #f5f5f5;
}

.card-header h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.category-remark {
  display: block;
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.card-body {
  padding: 20px;
  display: flex;
  justify-content: space-around;
}

.stat-item {
  text-align: center;
}

.stat-label {
  display: block;
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
}

.stat-value.total {
  color: #409eff;
}

.stat-value.new {
  color: #67c23a;
}

.stat-value.update {
  color: #e6a23c;
}

.card-footer {
  padding: 15px 20px;
  border-top: 1px solid #f5f5f5;
  text-align: right;
}
</style>