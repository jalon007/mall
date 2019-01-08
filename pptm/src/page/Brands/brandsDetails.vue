<!--商品详情-->
<template>
  <div class="brand-detail">
  <div v-html="url"></div>
  </div>
</template>
<script>
  export default {
    props: [
      'url'
    ],
    mounted () {
      this.url = this.$route.query
    }
  }
</script>
<style lang="scss" scoped>
  @import "../../assets/style/mixin";
  .brand-detail{
    width: 100%;
    height: 100%;
  }

</style>
