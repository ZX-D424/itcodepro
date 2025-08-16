<template>

  <!--导航栏-->
  <nav class="navbar" >
    <div class="logo">
      <a  @click="router.push({path : '/index'})" target="_blank">
        <img src="../assets/homePage/images/nebulas.png" width="80px" height="60px" alt="NeBuLas">
      </a>
    </div>
    <div class="nav-links">
      <template v-for="(item, index) in menuDataList">
        <a
            @click="initModule(item.id, item.name,index)"
            v-bind:class="{ active: activeIndex === index }"
        >
          {{ item.name }}
        </a>
      </template>
      <template v-if="user.token">
        <div id="user-center">
          <el-image
              class="user-avatar"
              :src=" user.avatar"
              :preview-src-list="[user.avatar] " title="头像" >
          </el-image>
          <span @click ="toModule('/userCenter')"  class="user-name" :title="user.nickName" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
          {{user.nickName}}
        </span>
          <!-- 下拉列表 -->
          <ul v-if="showDropdown" class="dropdown-list" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
            <li @click="handleOptionClick('logout')">退  出</li>
          </ul>
        </div>
      </template>
      <template v-else>
        <a @click ="toModule('/userCenter')" class="floating-link">个人中心</a>
      </template>
    </div>
  </nav>

<!--  &lt;!&ndash; 视频容器 &ndash;&gt;-->
<!--  <div class="video-background">-->
<!--    <video autoplay muted loop>-->
<!--      <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">-->
<!--      您的浏览器不支持视频标签。-->
<!--    </video>-->
<!--  </div>-->


  <div class="project">
    <section class="section">
      <h2>{{menuName}} 快速入门</h2>
      <div class="cards">
        <template v-for="item in moduleDataList">
          <div class="card">
            <h3> {{item.name}}</h3>
            <p v-html="item.description"></p>
            <p>
              <a v-if="item.type===1" @click="toLevelLink(item.id,item.name,item.menuName,'/projectRealCombat')">去实战</a>
              <a v-if="item.type===2" @click="toLevelLink(item.id,item.name,item.menuName,'/projectLevel')">去闯关</a>
              <a v-if="item.type===3" @click="proxy.$modal.msgError('建设中')">去学习</a>
            </p>
          </div>
        </template>
      </div>
    </section>
  </div>

  <el-dialog title="用户登录" v-model="open" width="30%" center>
    <login :closeDialog="() => open = false" />
  </el-dialog>

  <el-dialog title="用户注册" v-model="registerOpen" width="50%" center>
    <userRegister :closeDialog="() => registerOpen = false" />
  </el-dialog>

<!--  <rightVue></rightVue>-->

  <footerVue></footerVue>

</template>


<script setup >
import footerVue from "./footer.vue"
import rightVue from "./right.vue"
import login from "./userLogin.vue"
import userRegister from "./userRegister.vue"
import {getMenuDataList} from "@/api/www/menu"
import {getModuleDataListByMenuId} from "@/api/www/module"
import {ref, getCurrentInstance, onMounted, onUnmounted} from 'vue';
import {getToken} from "../utils/auth";
import {isRelogin} from "../utils/userRequest";
import {ElMessageBox} from "element-plus";
import userStore from "@/store/modules/user"

const user = userStore();
const route = useRoute()
const router = useRouter();
const menuDataList = ref([]);
const moduleDataList = ref([]);
const menuName = ref("")
const activeIndex = ref(0);

const open = ref(false);
const registerOpen = ref(false)

const menuIdParam = route.query.menuId;
const menuNameParam = route.query.menuName;
const menuIndexParam = Number(route.query.menuIndex);

const { proxy } = getCurrentInstance()


//加载菜单
function initMenuDataList() {
  getMenuDataList().then(response => {
    menuDataList.value = response.data;
    console.log("!!!!!!!!!!!!")
    console.log(response.data)
    if(menuIdParam > 0){
      initModule(menuIdParam,menuNameParam,menuIndexParam)
    }else{
      initModule(menuDataList.value[0].id,menuDataList.value[0].name,0);
    }
  });
}

async function toModule(routerName) {
  if(!getToken()) {
    try {
      isRelogin.show = true;
      const result = await ElMessageBox.confirm(
              '未登录，请登录后操作',
              '系统提示',
              {
                distinguishCancelAndClose: true,
                confirmButtonText: '去登录',
                cancelButtonText: '去注册',
                type: 'warning'
              }
      );
      if(result === 'confirm') {
        // 用户点击了"去登录"
        open.value = true;
      }
    } catch (action) {
      if (action === 'cancel') {
        // 用户点击了"去注册"
        registerOpen.value = true;
      }
    } finally {
      isRelogin.show = false;
    }
    return; // 未登录，不继续执行跳转
  }
  // 已登录，正常跳转
  const url = router.resolve({ path: routerName }).href;
  window.open(url, '_blank');
}

function toLevelLink(id, name,menuName, path) {
  router.push({
    path: path,
    query: {id: id, name: name,menuName:menuName,index:activeIndex.value}
  });
}
//
function initModule(menuId, name,index) {
  menuName.value = name;
  activeIndex.value = index;
  getModuleDataListByMenuId(menuId).then(response => {
    moduleDataList.value = response.data;
    console.log("@@@@@@@@@")
    console.log(response.data)
  });
}

initMenuDataList();


const showDropdown = ref(false);
function handleOptionClick(option) {
  switch (option) {
    case 'logout':
      // 处理退出逻辑
      user.logOut();
      break;
    default:
      break;
  }
  showDropdown.value = false;
}

</script>

<style >
@import  "@/assets/homePage/css/project.css";
@import  "@/assets/homePage/css/index.css";
</style>
