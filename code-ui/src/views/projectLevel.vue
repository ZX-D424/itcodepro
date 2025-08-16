<template>


<levleHeaderVue :index="index"></levleHeaderVue>


  <div class="level">
    <div class="flex">
      <div class="card-list">
        <div class="right-aside">
          <h2> {{moduleName}} 基础闯关</h2>
          <div class="right-aside-item">
            <ul >

              <template v-for="level in levelDataList">
              <li v-if="level.levelType === '1'"   @click="toPassLevel(level.id)">
                <div>第  {{level.levelCode}} 关</div>
                <input type="radio" readonly> &nbsp;&nbsp; {{level.levelTitle}}
              </li>
              </template>

            </ul>

          </div>
        </div>
        <div class="left-aside">
          <div class="left-aside-content">
            <h3>实战证书</h3>
            <hr>
            <ul>
              <li>
                <div class="left-aside-item">
                  <img class="img-title1" src="https://www.w3cschool.cn/statics/images/codecamp/certificate.png" alt="">
                  <ul>
                    <li><B>{{certificateName}} 开发者证书</B></li>
                    <li>通关html、css、js、(react.js/vue3.js)且平均分大于80分，可以申请由我司颁发的证书</li>
                    <li>凭该证书来我司面试，可以优先考虑聘用。</li>
                  </ul>
                </div>
              </li>

              <li class="flex">
                <span class="get" title="只展示TOP100位领取人">证书申请榜</span>
              </li>
              <li class="get-list">
                  <table  width="95%" align="center">
                    <tbody>
                      <tr>
                        <td>头像</td>
                        <td>昵称</td>
                        <td>申请时间</td>
                      </tr>
                    </tbody>

                    <tbody>
                      <tr v-for="i in 5">
                        <td width="20%">头像</td>
                        <td> 大队长</td>
                        <td>2025-06-25 11:22:05</td>
                      </tr>
                    </tbody>

                  </table>
              </li>

            </ul>
          </div>

          <div class="left-aside-content1">
            <h3 title="排行TOP100">排行榜</h3>
            <hr>
            <div class="ranking">
              <table width="95%" align="center">
                <tbody>
                <tr class="ranking-list">
                  <td>排名</td>
                  <td>昵称</td>
                  <td>评分</td>
                </tr>
                <tr v-for="(item, index) in rankList" :key="index">
                  <td>
                    <img v-if="index === 0" src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt="第一名">
                    <img v-else-if="index === 1" src="https://www.w3cschool.cn/statics/images/codecamp/second.png" alt="第二名">
                    <img v-else-if="index === 2" src="https://www.w3cschool.cn/statics/images/codecamp/third.png" alt="第三名">
                    <span v-else style="color: #333c46">{{ index + 1 }}</span>
                  </td>
                  <td>{{ item.nickName }}</td>
                  <td>{{ item.score }}分</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>

        </div>
      </div>
    </div>
    <div v-if="qrCode.length>0" class="levelTypes">
      <p>
      <el-image
          class="imgCss"
          :src=" url+qrCode[0].imgUrl"
          :preview-src-list="[url+qrCode[0].imgUrl] ">
      </el-image>
      </p>
      <p class="html" v-html="qrCode[0].levelInfo"></p>
    </div>
  </div>

  <footerVue></footerVue>


  <el-dialog :title="title"  v-model="open" class="passLevel" width="70%" center>
      <el-form ref="queTonsFormRef" :model="queTonsForm"  :rules="queTonsRules" label-width="80px">
        <el-form-item label="标题" class="levelLabel">
          <p >{{levelData.levelTitle}}</p>
        </el-form-item>
        <el-form-item label="详情" class="levelLabel">
          <div class="contentHtml" v-html="levelData.levelInfo" >
          </div>
        </el-form-item>
        <div class="flex-list">
          <el-form-item label="示列" class="levelLabel" v-if="levelData.imgUrl">
            <el-image
                class="imgCss"
                :src=" url+levelData.imgUrl"
                :preview-src-list="[url+levelData.imgUrl] ">
            </el-image>
          </el-form-item>
          <el-form-item label="效果图" prop="imgUrl">
            <image-upload v-model="queTonsForm.imgUrl" :limit="1" />
          </el-form-item>
          <el-form-item label="附件zip" prop="fileUrl">
            <file-upload v-model="queTonsForm.fileUrl" :limit="1"  />
          </el-form-item>
        </div>
        <el-form-item label="代码" class="levelLabel" prop="mark">
          <editor v-model="queTonsForm.mark" :min-height="192" />
        </el-form-item>
      </el-form>


    <span  class="dialog-footer">
      <el-button @click="open = false">取 消</el-button>
      <el-button type="primary" @click="submitPassLevel"  >提 交</el-button>
    </span>
  </el-dialog>



</template>


<script setup name="projectLevel">
import {getLevelDataList,getLevel} from "@/api/www/level"
import {addQuestions} from "@/api/www/questions"
import userStore from "@/store/modules/user"
import { getModuleRank } from "@/api/www/rank"
import {ref, getCurrentInstance, onMounted,watch} from 'vue';
import footerVue from "./footer.vue"
import levleHeaderVue from "./levelHeader.vue"

const route = useRoute()
const router = useRouter();
const rankList = ref([])

const user = userStore();

const { proxy } = getCurrentInstance()
const query = ref( {moduleId: route.query.id});
const moduleName = ref(route.query.name);
const index = ref(route.query.index);
const levelDataList = ref([]);
const levelData = ref([]);
const qrCode = ref([])
const url = ref(import.meta.env.VITE_APP_BASE_API);

const open = ref(false);
const title = ref("");
const certificateName = ref('');

watch(() => route.query.menuName, (newName) => {
  if(newName) {
    moduleName.value = newName;
    certificateName.value = newName.replace(/基础|入门/g, '').trim();
  }
}, { immediate: true });

function getRankList() {
  getModuleRank(query.value.moduleId).then(response => {
    if (response.code === 200) {
      rankList.value = response.rows
    } else {
      proxy.$modal.msgError("获取排行榜失败")
    }
  }).catch(error => {
    proxy.$modal.msgError("获取排行榜出错")
  })
}

const queTonsForm = ref({
  moduleId: null,
  levelId: null,
  imgUrl: null,
  mark: null,
  fileUrl: null,
})

//必填校验
const queTonsRules = {
  mark: [{ required: true, trigger: "blur", message: "请输入代码" }],
  imgUrl: [{ required: true, trigger: "blur", message: "请上传效果图" }],
 // fileUrl: [{ required: true, trigger: "change", message: "请上传代码文件压缩包" }]
}



// 表单重置
function reset() {
  queTonsForm.value = {
    moduleId: null,
    levelId: null,
    imgUrl: null,
    mark: null,
    fileUrl: null,
  }
  proxy.resetForm("queTonsFormRef")
}

//路由跳转
function toModuleIndex( routerName) {
  router.push({ path: routerName });
}
//加载题关卡
function initLevelDataList() {
  getLevelDataList(query.value).then(response => {
    // 先将响应数据赋值给 levelDataList
    levelDataList.value = response.data;
    // 筛选出 levelType 等于 2 的数据
    qrCode.value = levelDataList.value.filter(item => item.levelType === '2');
    // 从 levelDataList 中移除 levelType 等于 2 的数据
    levelDataList.value = levelDataList.value.filter(item => item.levelType !== '2');
  });
}

//查询关卡详情
function toPassLevel(id) {
  reset();
  getLevel(id).then(response => {
    if(response.code === 200){
      if(user.userType === '00'){
        proxy.$modal.msgError("系统用户不允许闯关")
        return;
      }
      //当前关卡已通关
      if(response.data.atPassLevel){
        proxy.$modal.msgError("当前关卡已通关无需重复闯关")
        return;
        //判断如果上一关未通关则不打开
      }else if(response.data.levelCode >1 && response.data.atPassLevel ===  false &&  response.data.upPassLevel === false){
        proxy.$modal.msgError("不可跳过关卡，请通过上一关")
        return;
      }
      open.value = true;
      levelData.value = response.data;
      title.value = "第 "+levelData.value.levelCode+" 关 ";
      queTonsForm.value.moduleId = levelData.value.moduleId;
      queTonsForm.value.levelId = levelData.value.id;
    }

  });
}

//提交闯关
function submitPassLevel() {
  proxy.$refs.queTonsFormRef.validate(valid => {
    if (valid) {
      addQuestions(queTonsForm.value).then(response => {
         if (response.code === 200){
           proxy.$modal.msgSuccess(response.msg);
           open.value = false
         }else{
           proxy.$modal.msgError(response.msg);
         }
      });
    }
  })
}

//初始化数据
onMounted(() => {
  initLevelDataList()
  getRankList()
})

</script>

<style  >
@import  "@/assets/homePage/css/level.css";
@import  "@/assets/homePage/css/index.css";


</style>
