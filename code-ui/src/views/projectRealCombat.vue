<template>
  <levleHeader></levleHeader>

  <div class="practice-grid">
    <template v-for="(item, index) in practiceDataList" :key="index">
      <div class="practice-card">
        <div class="card-header">
          <h2 class="name">{{item.name}}</h2>
        </div>

        <div class="card-body">
          <div class="price-section">
            <span v-if="item.price ===0" class="free">免费</span>
            <span v-if="item.price > 0" class="paid">{{item.price}}元</span>
          </div>

          <div class="contact-info">
            <div><span class="label">QQ：</span>{{item.qqNumber}}</div>
            <div><span class="label">邮箱：</span>{{item.emailNumber}}</div>
            <div class="attachment">
              <span class="label">附件：</span>
              <a v-if="item.fileUrl" @click="previewFile(item.fileUrl)" class="file-link">
                {{formatFilePath(item.fileUrl)}}
              </a>
            </div>
          </div>

          <div class="description">
            <p v-html="item.content"></p>
          </div>

          <div class="image-container" v-if="item.imgUrl || item.qrCode">
            <div class="left-image" v-if="item.imgUrl">
              <el-image
                      class="cover"
                      :src="url+item.imgUrl"
                      :preview-src-list="[url+item.imgUrl]">
              </el-image>
            </div>
            <div class="right-image" v-if="item.qrCode">
              <el-image
                      :src="url+item.qrCode"
                      :preview-src-list="[url+item.qrCode]">
              </el-image>
              <p v-html="item.mark"></p>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>

  <!-- 文件预览弹窗 -->
  <el-dialog v-model="filePreviewVisible" title="附件预览" width="80%">
    <iframe :src="previewFileUrl" frameborder="0" style="width:100%;height:70vh;"></iframe>
  </el-dialog>
</template>

<script setup name="projectRealCombat">
  import levleHeader from "./levelHeader.vue"
  import { ref } from 'vue'
  const route = useRoute()
  const router = useRouter();
  import {getPracticeDataList} from "@/api/www/practice"

  const query = ref({moduleId: route.query.id});
  const practiceDataList = ref([]);
  const url = ref(import.meta.env.VITE_APP_BASE_API);
  const filePreviewVisible = ref(false);
  const previewFileUrl = ref('');

  // 格式化文件路径，隐藏/profile/upload/
  const formatFilePath = (path) => {
    if (!path) return '';
    return path.replace('/profile/upload/', '');
  };

  // 预览文件
  const previewFile = (filePath) => {
    previewFileUrl.value = url.value + filePath;
    filePreviewVisible.value = true;
  };

  // 加载实战数据
  function initPracticeDataList() {
    getPracticeDataList(query.value).then(response => {
      practiceDataList.value = response.data;
    });
  }
  initPracticeDataList();
</script>

<style>
  @import  "@/assets/homePage/css/index.css";
  .practice-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    padding: 20px;
    max-width: 90%;
    margin: 0 auto;
  }

  .practice-card {
    border: 1px solid #eaeaea;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    transition: box-shadow 0.3s ease;
    background: white;
  }

  .practice-card:hover {
    box-shadow: 0 8px 24px rgba(0,0,0,0.15);
  }

  .card-header {
    padding: 15px;
    background: #f5f7fa;
    border-bottom: 1px solid #eaeaea;
  }

  .card-header .name {
    margin: 0;
    color: #333;
    font-size: 18px;
  }

  .card-body {
    padding: 15px;
  }

  .price-section {
    margin-bottom: 10px;
  }

  .price-section .free {
    color: #409eff;
    font-weight: bold;
  }

  .price-section .paid {
    color: #f56c6c;
    font-weight: bold;
  }

  .contact-info {
    margin-bottom: 10px;
    font-size: 14px;
    color: #333;
  }

  .contact-info .label {
    font-weight: bold;
    color: #666;
  }

  .attachment .file-link {
    color: #409eff;
    cursor: pointer;
    text-decoration: none;
  }

  .attachment .file-link:hover {
    text-decoration: underline;
  }

  .description {
    font-size: 14px;
    color: #333;
    line-height: 1.6;
    margin-bottom: 15px;
  }

  .image-container {
    display: flex;
    gap: 15px;
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px dashed #eee;
  }

  .left-image {
    flex: 7; /* 7:3比例 */
    width: 70%;
  }

  .right-image {
    flex: 3; /* 7:3比例 */
    text-align: center;
  }

  .left-image .el-image,
  .right-image .el-image {
    width: 100%;
    height: auto;
    border-radius: 4px;
    border: 1px solid #eee;
  }

  .right-image p {
    font-size: 12px;
    color: #999;
    margin-top: 5px;
  }

  @media (max-width: 768px) {
    .practice-grid {
      grid-template-columns: 1fr;
    }

    .image-container {
      flex-direction: column;
    }

    .left-image, .right-image {
      width: 100%;
      flex: none;
    }
  }
</style>