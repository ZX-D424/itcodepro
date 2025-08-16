<template>

    <headerVue />

<!--    &lt;!&ndash; Video Background &ndash;&gt;-->
<!--    <div class="video-background">-->
<!--      <video autoplay muted loop>-->
<!--        <source src="@/assets/homePage/video/HDAHKHFEC8afY2e.mp4" type="video/mp4">-->
<!--        您的浏览器不支持视频标签。-->
<!--      </video>-->
<!--    </div>-->


  <div class="rank">
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

    <!-- 二级开始-->
    <div class="rank_list">
      <div class="rank-list-item fixclear">
        <div class="rank-item-left">
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>HTML</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>CSS</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>JavaScript</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>Jquery</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>React.js</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
          <div class="left-aside" >
            <img src="../assets/homePage/images/nebulas.png"/>
            <p >
              <b>Vue3.js</b><br>
              排名：<span>1</span><br>
              得分：<span>100</span>
            </p>
          </div>
        </div>
        <div class="rank-item-right">

          <div class="left-aside-content1">
            <h3 title="排行_TOP_100">HTML基础闯关排行榜</h3>
            <hr>
            <div class="ranking">
              <table width="95%" align="center">
                <tbody>
                <tr class="ranking-list">
                  <td>排名</td>
                  <td>昵称</td>
                  <td>评分</td>
                </tr>
                <tr v-for="(item, index) in 10" :key="index">
                  <td>
                    <img v-if="index === 0" src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt="第一名">
                    <img v-else-if="index === 1" src="https://www.w3cschool.cn/statics/images/codecamp/second.png" alt="第二名">
                    <img v-else-if="index === 2" src="https://www.w3cschool.cn/statics/images/codecamp/third.png" alt="第三名">
                    <span v-else style="color: #333c46">{{ index + 1 }}</span>
                  </td>
                  <td>大队长</td>
                  <td>100分</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>



          <div class="left-aside-content2">
            <h3 title="排行_TOP_100">Web开发者证书申请榜</h3>
            <hr>
            <div class="ranking">
              <table width="95%" align="center">
                <tbody>
                <tr class="ranking-list">
                  <td>排名</td>
                  <td>昵称</td>
                  <td>评分</td>
                </tr>
                <tr v-for="(item, index) in 10" :key="index">
                  <td>
                    <img v-if="index === 0" src="https://www.w3cschool.cn/statics/images/codecamp/first.png" alt="第一名">
                    <img v-else-if="index === 1" src="https://www.w3cschool.cn/statics/images/codecamp/second.png" alt="第二名">
                    <img v-else-if="index === 2" src="https://www.w3cschool.cn/statics/images/codecamp/third.png" alt="第三名">
                    <span v-else style="color: #333c46">{{ index + 1 }}</span>
                  </td>
                  <td>大队长</td>
                  <td>100分</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>

        </div>
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
