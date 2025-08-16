<template>
  <header class="header">
    <div class="logo">
      <img src="@/assets/homePage/images/nebulas.png" alt="Logo" />
    </div>
    <nav class="nav">
      <ul class="nav-links">
        <li :class="{ active: isActive('home'), hover: isHover('home') }" @mouseover="hover('home')" @mouseout="unhover('home')" @click="active('home'); router.push('/')">
          <a>首页</a>
        </li>
        <!-- 动态生成的导航项 -->
        <li v-for="(item, index) in dynamicNavItems" :key="index" :class="{ active: isActive(item.link), hover: isHover(item.link) }" @mouseover="hover(item.link)" @mouseout="unhover(item.link)" @click="active(item.link); router.push(item.link)">
          <a>{{ item.name }}</a>
        </li>
        <li :class="{ active: isActive('upload'), hover: isHover('upload') }" @mouseover="hover('upload')" @mouseout="unhover('upload')" @click="active('upload'); router.push('/upload')">
          <a>题库上传</a>
        </li>
        <!-- 固定的个人中心 -->
        <li :class="{ active: isActive('profile'), hover: isHover('profile') }" @mouseover="hover('profile')" @mouseout="unhover('profile')" @click="active('profile'); router.push('/profile')">
          <a>个人中心</a>
        </li>  
      </ul>
    </nav>
  </header>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { listSection } from '@/api/section';
// import request from '@/axios'; // 引入配置好的 Axios 实例
// import { getSectionList } from '../../api/question/section';
// listSection().then((response) => {
//   dynamicNavItems.value = response.data;
// });
const router = useRouter();
const dynamicNavItems = ref([]);


// // 检查用户是否已登录
// const isLoggedIn = () => {
//   return !!localStorage.getItem('Admin-Token');
// };

// 处理导航点击事件
// const handleNavigation = (path) => {
//   // 对于需要登录的页面进行检查
//   if (path.startsWith('/section/') || path === '/profile') {
//     if (!isLoggedIn()) {
//       // 如果未登录，跳转到登录页面或显示提示
//       alert('请先登录');
//       router.push('/login');
//       return;
//     }
//   }
  
//   // 更新活动状态并跳转
//   active(path);
//   router.push(path);
// };


// onMounted(async () => {
//   try {
//     const response = await listSection(queryParams); // 调用 listSection 方法获取数据
//     if (response && response.data && response.data.rows) {
//       dynamicNavItems.value = response.data.rows.map(item => ({
//         name: item.name,
//         link: `/section/${item.externalUrl}/${item.id}` // 根据实际需求生成链接
//       }));
//     } else {
//       console.warn('Response data format is not as expected:', response);
//     }
//   } catch (error) {
//     console.error('Failed to fetch nav items:', error);
//   }
// });


// onMounted(async () => {
//   try {
//     const response = await listSection({
//       pageNum: 1,
//       pageSize: 10
//     });
    
//     if (response && response.data && response.data.rows) {
//       dynamicNavItems.value = response.data.rows.map(item => ({
//         name: item.name,
//         link: `/section/${item.externalUrl}/${item.id}` // 根据实际需求生成链接
//       }));
//     } else {
//       console.warn('Response data format is not as expected:', response);
//     }
//   } catch (error) {
//     console.error('Failed to fetch nav items:', error);
//   }
// });

onMounted(async () => {
  try {
    const response = await listSection({ pageNum: 1, pageSize: 10 });
    // 修正：直接从response中获取rows，而非response.data.rows
    if (response && response.code === 200 && response.rows) {
      dynamicNavItems.value = response.rows.map(item => ({
        name: item.name,
        link: `/${item.externalUrl}` // 与后端字段匹配
      }));
    } else {
      console.warn('Response data format is not as expected:', response);
    }
  } catch (error) {
    console.error('Failed to fetch nav items:', error);
  }
});


// onMounted(async () => {
//   try {
//     const response = await listSection(queryParams);

//     if (response.code === 200 && response.data && response.data.rows) {
//       dynamicNavItems.value = response.data.rows.map(item => ({
//         name: item.name,
//         link: `/section/${item.externalUrl}/${item.id}`
//       }));
//     } else {
//       console.warn('Response data format is not as expected:', response);
//     }
//   } catch (error) {
//     console.error('Failed to fetch nav items:', error);
//   }
// });

const activeItem = ref(null);
const hoverItem = ref(null);

const isActive = (item) => activeItem.value === item;
const isHover = (item) => hoverItem.value === item;

const active = (item) => {
  activeItem.value = item;
};

const hover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = item;
  }
};

const unhover = (item) => {
  if (!isActive(item)) {
    hoverItem.value = null;
  }
};

// 引入 section.js 中的 listSection 方法

</script>

<style scoped>
@import "@/assets/homePage/css/navbar.css"; /* 封装并引入 Navbar 的样式 */

.header {
  display: flex;
  align-items: center;
  /* justify-content: space-between; */
   justify-content: space-evenly;
  padding: 10px 20px;
  background-color: rgba(255, 255, 255, 0.8); /* 添加半透明背景 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1; /* 确保导航栏在视频上方 */
  border-radius: 10px; /* 添加圆角 */
  height: 70px;
}

.logo img {
  height: 50px;
}



.nav-links {
  list-style: none;
  display: flex;
  justify-content: center;
}

.nav-links li {
  margin: 0 15px;
  padding: 5px; /* 所有状态保持相同内边距 */
  transition: background-color 0.3s; /* 平滑过渡效果 */
}

.nav-links a {
  text-decoration: none;
  color: #333;
  font-weight: bold;
  padding: 5px 10px;
  display: block; /* 确保点击区域稳定 */
}

.nav-links li.active,
.nav-links li.hover {
  background-color: #0066ff;
  color: #fff;
  border-radius: 4px;
  /* 移除额外的padding，保持尺寸不变 */
}
.nav-links li a {
  color: inherit;
  cursor: pointer; /* 始终显示手势光标（推荐） */
}
</style>