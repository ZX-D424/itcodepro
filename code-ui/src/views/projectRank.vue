<template>

    <headerVue />

    <!-- Video Background -->
    <div class="video-background">
      <video autoplay muted loop>
        <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">
        您的浏览器不支持视频标签。
      </video>
    </div>


  <div class="rank">
    <h3 class="rank-h3">  个人榜单：</h3>
    <div class="rank-nav">
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p >
          <b>web前端</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b>java</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> python</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> C++</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> C语言</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> AI</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>

      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> 数据库</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>

      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> uniapp</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>

      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> flutter</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b>产品</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b> ui</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>
      <div class="rank-nav-list">
        <img src="../assets/homePage/images/nebulas.png"/>
        <p>
          <b>操作系统</b><br>
          排名：<span>1</span><br>
          得分：<span>100</span>
        </p>
      </div>

    </div>
    <div class="rank_list">
      <div class="rank-list-item">
        <img src=""/>
        <p>
          <span>HTMl</span>
          排名：<span>1</span>
          得分：<span>100</span>
          总分数：<span>100</span>
          总人数：<span>100</span>
        </p>
      </div>
      <div class="rank-list-item">
        <img src=""/>
        <p>
          <span>CSS</span>
          排名：<span>1</span>
          得分：<span>100</span>
          总分数：<span>100</span>
          总人数：<span>100</span>
        </p>
      </div>

      <div class="rank-list-item">
        <img src=""/>
        <p>
          <span>JavaScript</span>
          排名：<span>1</span>
          得分：<span>100</span>
          总分数：<span>100</span>
          总人数：<span>100</span>
        </p>
      </div>
    </div>

  </div>

    <footerVue />

</template>

<script setup>
import footerVue from "./footer.vue"
import headerVue from "./header.vue"

</script>

<style>
@import  "@/assets/homePage/css/index.css";
@import  "@/assets/homePage/css/rank.css";
</style>
