// import { createApp } from 'vue';
// import App from './App.vue';
// import router from './router'; // 引入路由

// createApp(App).use(router).mount('#app'); // 使用路由


import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store'; // 引入Pinia
import ElementPlus from 'element-plus'; // 引入 Element Plus
import 'element-plus/dist/index.css'; // 引入样式
import { ElPagination, ElTree, ElLoading } from 'element-plus';
import { createPinia } from 'pinia';
const pinia = createPinia();
createApp(App)
  .use(router)
  .use(store) // 使用Pinia
  .use(ElementPlus) // 使用 Element Plus
  .use(ElPagination)
  .use(ElTree)
  .use(ElLoading)
  .use(pinia) // 使用Pinia
  .mount('#app');