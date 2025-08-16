<template>
  <headerVue></headerVue>
  <div class="member-container">
    <div class="member-grid">
      <div class="member-card" v-for="item in memberDataList" :key="item.id">
        <div class="card-header">
          <h2 class="name">{{item.nickName}}
            <el-tag type="danger" class="role-tag">
              {{
              item.memberType == 1 ? '发起人' :
              item.memberType == 5 ? '后端协作' :
              item.memberType == 10 ? '前端协作' :
              item.memberType == 15 ? 'UI设计协作' :
              item.memberType == 20 ? 'AI算法协作' :
              item.memberType == 25 ? '运维协作' :'其他协作'
              }}
            </el-tag>
          </h2>
        </div>

        <div class="card-content">
          <div class="avatar-container">
            <img :src="url+item.profilePicture" class="avatar"/>
          </div>

          <div class="info-section">
            <div class="contact-info">
              <div class="email">{{item.email}}</div>
              <div class="education">
                {{item.schoolName}} —
                <el-tag class="specialty-tag">
                  {{
                  item.specialty == 1 ? '计算机科学与技术‌' :
                  item.specialty ==2 ? '软件工程' :
                  '其他'
                  }}
                </el-tag> —
                <el-tag class="degree-tag">
                  {{
                  item.degree == 1 ? '专科' :
                  item.degree == 5 ? '本科' :
                  item.degree == 10 ? '硕士' :
                  '其他'
                  }}
                </el-tag>
              </div>
            </div>

            <div class="expertise">
              <div class="title">当前方向：{{item.proficiencyArea}}</div>
            </div>

            <div class="project-experience">
              <div class="experience-item" v-html="item.contentInfo"></div>
            </div>

            <div class="social-links">
              <div class="link-item" v-if="item.giteeLink">
                <span class="link-label">码云：</span>{{item.giteeLink}}
              </div>
              <div class="link-item" v-if="item.githubLink">
                <span class="link-label">GitHub：</span>{{item.githubLink}}
              </div>
              <div class="link-item" v-if="item.personalSpace">
                <span class="link-label">个人空间：</span>{{item.personalSpace}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <footerVue></footerVue>
</template>

<script setup name="projectMember">
  import footerVue from "./footer.vue"
  import headerVue from "./header.vue"
  import {getMemberDataList} from "@/api/www/member"
  import {ref} from "vue"

  const memberDataList = ref([]);
  const url = ref(import.meta.env.VITE_APP_BASE_API);

  // 加载成员数据
  function initMemberDataList() {
    getMemberDataList().then(response => {
      memberDataList.value = response.data;
    });
  }
  initMemberDataList();
</script>

<style>
  @import  "@/assets/homePage/css/index.css";
  .member-container {
    max-width: 90%;
    margin: 0 auto;
    padding: 20px;
  }

  .member-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
  }

  .member-card {
    border: 1px solid #eaeaea;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  }

  .card-header {
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
    margin-bottom: 15px;
  }

  .name {
    font-size: 18px;
    margin: 0;
    display: flex;
    align-items: center;
    gap: 10px;
    color: #004d8c;
  }

  .role-tag {
    font-size: 12px;
  }

  .card-content {
    display: flex;
    gap: 20px;
  }

  .avatar-container {
    flex-shrink: 0;
  }

  .avatar {
    width: 150px;
    height: 150px;
    border-radius: 5%;
    object-fit: cover;
    border: 1px solid #eee;
  }

  .info-section {
    flex: 1;
  }

  .contact-info {
    margin-bottom: 10px;
  }

  .email {
    font-size: 14px;
    color: #666;
    margin-bottom: 5px;
  }

  .education {
    font-size: 14px;
    color: #666;
  }

  .specialty-tag, .degree-tag {
    margin: 0 5px;
    font-size: 12px;
    height: 22px;
    line-height: 20px;
  }

  .expertise {
    margin-bottom: 15px;
  }

  .title {
    font-size: 15px;
    font-weight: bold;
    color: #333;
  }

  .project-experience {
    margin-bottom: 15px;
  }

  .experience-item {
    font-size: 14px;
    line-height: 1.6;
    color: #444;
  }

  .social-links {
    font-size: 13px;
  }

  .link-item {
    margin-bottom: 5px;
    word-break: break-all;
    color: #333;
  }

  .link-label {
    font-weight: bold;
    color: #333;
  }

  @media (max-width: 768px) {
    .member-grid {
      grid-template-columns: 1fr;
    }
  }
</style>