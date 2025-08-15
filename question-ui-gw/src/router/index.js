import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue'; // 假设这是你的首页组件
import { isAuthenticated } from '../utils/auth';
// import Profile from '../views/Profile.vue'; // 假设这是你的个人中心组件
import QuestionBank from '@/views/section-bank/QuestionBank.vue'

import QuestionDetail from '@/views/section-bank/QuestionDetail.vue'

// 个人中心相关页面
import PersonalCenter from '../views/personal/PersonalCenter.vue';
import Login from '../views/personal/Login.vue';
import Register from '../views/personal/Register.vue';
import PersonalQuestionBank from '../views/personal/PersonalQuestionBank.vue';
import ExamRecords from '../views/personal/ExamRecords.vue';
import ProjectMembers from '../views/personal/ProjectMembers.vue';
import WrittenTest from '../views/section-writtentest/WrittenTest.vue'



// const routes = [
//   {
//     path: '/',
//     name: 'Home',
//     component: Home
//   },
//   {
//     path: '/profile',
//     name: 'PersonalCenter',
//     component: PersonalCenter,
//     // meta: { requiresAuth: true } // 需要登录验证
//   },
//   {
//     path: '/login',
//     name: 'Login',
//     component: Login
//   },
//   {
//     path: '/register',
//     name: 'Register',
//     component: Register
//   },
//   {
//     path: '/personal/question-bank',
//     name: 'PersonalQuestionBank',
//     component: PersonalQuestionBank,
//     meta: { requiresAuth: true } // 需要登录验证
//   },
//   {
//     path: '/personal/exam-records',
//     name: 'ExamRecords',
//     component: ExamRecords,
//     meta: { requiresAuth: true } // 需要登录验证
//   },
//   {
//     path: '/personal/project-members',
//     name: 'ProjectMembers',
//     component: ProjectMembers
//   }
//   // {
//   //   path: '/profile',
//   //   name: 'Profile',
//   //   component: Profile
//   // },
//   // 其他动态路由可以根据实际情况添加
// ];

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/upload',
    name: 'Upload',
    component: () => import('../views/updatedown/up.vue')
  },

  {
    path: '/profile',
    name: 'PersonalCenter',
    component: PersonalCenter,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/personal/question-bank',
    name: 'PersonalQuestionBank',
    component: PersonalQuestionBank,
    meta: { requiresAuth: true }
  },
  {
    path: '/personal/exam-records',
    name: 'ExamRecords',
    component: ExamRecords,
    meta: { requiresAuth: true }
  },
  {
    path: '/personal/project-members',
    name: 'ProjectMembers',
    component: ProjectMembers
  },
  {
    path: '/section-bank/questionBank',
    name: 'QuestionBank',
    component: QuestionBank,
    meta: { title: '题库' }
  },
  {
    path: '/section-bank/questionDetail/:id',
    name: 'QuestionDetail',
    component: QuestionDetail,
    meta: { title: '题目详情' }
  }
  ,
  {
    path: '/section-writtentest/writtenTest',
    name: 'WrittenTest',
    component: WrittenTest,
    meta: { title: '笔试部分' }
  }
];

const router = createRouter({
  history: createWebHistory('/'), // 使用静态字符串 '/' 替换 process.env.BASE_URL
  routes
});



// router.beforeEach((to, from, next) => {
//   // 检查路由是否需要认证
//   if (to.meta.requiresAuth) {
//     // 检查用户是否已登录（检查是否存在token）
//     const token = localStorage.getItem('Admin-Token');
//     if (token) {
//       // 已登录，允许访问
//       next();
//     } else {
//       // 未登录，重定向到登录页面
//       next({
//         path: '/login',
//         query: { redirect: to.fullPath } // 保存用户原本想访问的页面
//       });
//     }
//   } else {
//     // 不需要认证的页面，直接访问
//     next();
//   }
// });

// 添加路由守卫
router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth;
  const hasToken = isAuthenticated();
  
  // 需要认证的页面
  if (requiresAuth && !hasToken) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    });
  } 
  // 保护登录/注册页面
  else if (['/login', '/register'].includes(to.path) && hasToken) {
    next('/profile');
  } 
  // 其他情况正常导航
  else {
    next();
  }
});



export default router;