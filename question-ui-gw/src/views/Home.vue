<!-- <template>
  <div>
    <h1>欢迎来到首页</h1>
  </div>
</template>

<script setup>
</script>

<style scoped>
</style> -->



<!-- <template>
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
</style> -->






<!-- 22222222222222222 -->


<template>
  <div class="home-container">
    <!-- 轮播图区域 -->
    <div class="carousel-wrapper">
      <el-carousel 
        height="200px" 
        :autoplay="true" 
        indicator-position="outside"
      >
        <!-- 可根据实际需求动态渲染轮播图数据，这里先写死演示 -->
        <el-carousel-item v-for="(item, index) in carouselList" :key="index">
          <img 
            :src="item.image" 
            alt="轮播图" 
            class="carousel-img"
          >
          <div class="carousel-text" v-if="item.text">{{ item.text }}</div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 分类统计卡片区域 -->
    <div class="category-stats">
      <div 
        class="stat-card" 
        v-for="category in firstLevelCategories" 
        :key="category.id"
      >
        <!-- 卡片头部（含分类名、分类描述） -->
        <div class="card-header">
          <!-- 分类图片 -->
          <div class="category-img">
            <img 
              :src="category.image || defaultImg" 
              alt="分类封面"
            >
          </div>
          <div class="category-info">
            <h2 class="category-name">{{ category.name }}</h2>
            <p class="category-remark">{{ category.remark || '无描述' }}</p>
          </div>
        </div>
        <!-- 卡片主体（统计数据） -->
        <div class="card-body">
          <div class="stat-item">
            <span class="stat-label">总数</span>
            <span class="stat-value total">{{ category.totalCount }}</span>
          </div>
          <div class="stat-item">
            <span class="stat-label">今日上新</span>
            <span class="stat-value new">{{ category.newCount }}</span>
          </div>
          <div class="stat-item">
            <span class="stat-label">今日纠错</span>
            <span class="stat-value update">{{ category.updateCount }}</span>
          </div>
        </div>
        <!-- 卡片底部（操作按钮） -->
        <div class="card-footer">
          <el-button 
            type="primary" 
            size="small" 
            @click="goToCategory(category.id, category.name)"
          >
            查看题目
          </el-button>
        </div>
      </div>
    </div>

    <!-- 岗位面经区域（可根据实际需求补充逻辑） -->
    <div class="interview-section">
      <h3 class="section-title">岗位面经</h3>
      <!-- 这里可扩展面经列表等内容，暂时留空 -->
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { ElCarousel, ElCarouselItem, ElButton } from 'element-plus';
import { listCategory } from '@/api/category/category';
import { listManage } from '@/api/manage/manage';

// 引入默认分类封面图（需放到项目 assets 目录，可替换成你实际默认图）
import defaultCategoryImg from '@/assets/homePage/images/nebulas.png'; 

// 路由实例
const router = useRouter();

// 响应式数据
const categoryList = ref([]); // 全部分类数据
const firstLevelCategories = ref([]); // 一级分类数据
const carouselList = ref([
  { image: 'http://www.v3wall.com/wallpaper/1920_1200/1207/1920_1200_20120727122504514090.jpg', text: '轮播图1' },
  
]); // 轮播图数据，可根据接口动态获取
const loading = ref(false);
const defaultImg = ref(defaultCategoryImg); // 默认分类封面

// 获取今日日期（YYYY - MM - DD 格式）
const getToday = () => {
  const date = new Date();
  return date.toISOString().split('T')[0];
};

// 判断日期是否为今日
const isToday = (dateString) => {
  if (!dateString) return false;
  const date = new Date(dateString);
  return date.toISOString().split('T')[0] === getToday();
};

// 获取分类列表
const getCategoryList = async () => {
  loading.value = true;
  try {
    const response = await listCategory({});
    categoryList.value = response.data || [];

    // 筛选一级类目（父类目为顶级类目，可根据实际接口返回字段调整判断逻辑）
    const topLevelCategories = categoryList.value.filter(
      (item) => item.parentName === '顶级类目' 
    );

    // 为每个一级类目计算统计数据
    await Promise.all(
      topLevelCategories.map(async (category) => {
        await calculateCategoryStats(category);
      })
    );

    firstLevelCategories.value = topLevelCategories;
  } catch (error) {
    console.error('获取类目列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 计算类目统计数据（今日上新、今日修改、总数 ）
const calculateCategoryStats = async (category) => {
  try {
    // 获取该类目下的所有题目（实际项目中注意分页处理，这里为演示简化）
    const response = await listManage({
      categoryId: category.id,
      pageNum: 1,
      pageSize: 1000 
    });
    const questions = response.rows || [];
    const today = getToday();

    // 总数
    category.totalCount = questions.length;
    // 今日上新（创建时间为今日）
    category.newCount = questions.filter((q) => 
      q.createTime && isToday(q.createTime)
    ).length;
    // 今日修改（修改时间为今日）
    category.updateCount = questions.filter((q) => 
      q.updateTime && isToday(q.updateTime)
    ).length;

  } catch (error) {
    console.error(`计算类目${category.name}统计数据失败:`, error);
    category.totalCount = 0;
    category.newCount = 0;
    category.updateCount = 0;
  }
};

// 跳转到对应类目题目列表
const goToCategory = (categoryId, categoryName) => {
  router.push({
    path: '/section-bank/QuestionBank',
    query: { categoryId, categoryName }
  });
};

// 初始化调用
onMounted(() => {
  getCategoryList();
});
</script>

<style scoped>
/* 容器样式 */
.home-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 轮播图样式 */
.carousel-wrapper {
  margin-bottom: 30px;
}
.carousel-img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}
.carousel-text {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: #fff;
  font-size: 16px;
  background: rgba(0,0,0,0.3);
  padding: 8px 16px;
  border-radius: 4px;
}

/* 分类统计卡片样式 */
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

/* 卡片头部样式（含图片、分类名、描述） */
.card-header {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #f5f5f5;
}
.category-img {
  width: 60px;
  height: 60px;
  margin-right: 12px;
}
.category-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}
.category-info {
  flex: 1;
}
.category-name {
  font-size: 18px;
  color: #333;
  margin: 0 0 4px 0;
}
.category-remark {
  font-size: 14px;
  color: #666;
  margin: 0;
}

/* 卡片主体（统计数据）样式 */
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
  font-size: 20px;
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

/* 卡片底部（按钮）样式 */
.card-footer {
  padding: 15px 20px;
  border-top: 1px solid #f5f5f5;
  text-align: right;
}

/* 岗位面经区域样式（可扩展） */
.interview-section {
  margin-top: 30px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}
.section-title {
  font-size: 18px;
  color: #333;
  margin-bottom: 10px;
}
</style>