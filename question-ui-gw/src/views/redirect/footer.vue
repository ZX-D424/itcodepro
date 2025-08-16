<template>

  <footer class="site-footer">
    <p>&copy; 湘ICP备2024092148号-3   2025 星云题库平台. 版权所有.</p>
    <p>
      |
      <a href="http://www.nebulas.ink" target="_blank">广东星云科技</a> |
      <a href="http://soft.nebulas.ink"  title="广东星云软件项目发布平台" target="_blank">软件项目发布平台</a> |
      <a  href="http://code.nebulas.ink" title="广东星云编程快速学习平台"  >编程学习平台</a> |
      <a  href="http://nb.nebulas.ink" title="广东星云编程社区交流平台" target="_blank" >星云社区平台</a>|
      <a @click="router.push({path : '/contactUs'})">项目成员</a> |
      <a @click="router.push({path : '/contactUs'})">联系我们</a> |
      <a @click="router.push({path : '/joinUs'})">加入我们</a>|

    </p>
  </footer>
</template>

<script setup>

</script>

<style scoped>
.site-footer {
  background-image: url('@/assets/footer-bg.jpg'); /* 替换为实际背景图片路径 */
  background-size: cover;
  color: #aaa;
  text-align: center;
  padding: 20px;
  margin-top: auto; /* 确保底部固定 */
}

.site-footer p {
  margin: 5px 0;
}

.site-footer a {
  color: #00e6ff;
  text-decoration: none;
}

.site-footer a:hover {
  text-decoration: underline;
}
@media (max-width: 768px) {
}footer.site-footer {
    padding: 20px; /* 覆盖默认的 50px */
}

footer.site-footer p {
    margin: 5px 0; /* 覆盖默认的 10px */
    font-size: inherit; /* 继承父元素字体大小 */
    color: inherit; /* 继承父元素颜色 */
}

footer.site-footer a {
    color: #00e6ff; /* 覆盖变量颜色 */
    margin: 0 10px; /* 调整链接间距 */
}
</style>
