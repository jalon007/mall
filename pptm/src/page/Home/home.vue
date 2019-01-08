<template>
  <div class="home">

    <div class="home_top_area">
      <div class="title">【品牌特卖】</div>
      <div class="pptm" v-for="(item,i) in pptmList" :key="i">
        <a v-html="" @click="getpp(item.url)">{{item.name}}</a>
      </div>
    </div>

  </div>
</template>
<script>

  import { getpptm } from '/api/goods'
  import 'element-ui/lib/theme-default/index.css'

  import { mapState } from 'vuex'
  export default {
    data () {
      return {
        page: 1,
        size: 20,
        pptmList: []
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart', 'userInfo'])
    },
    methods: {
      getpp (param) {
        this.$router.push({name: 'brandsDetails', query: param})
        window.open('https://h5.xxcang.com/webapp/share/goods.html?v=2&params=' + param)
      },
      _getBanner () {
        getpptm().then(res => {
          this.pptmList = res.data.pptmList
        })
      }
    },
    mounted () {
      this._getBanner()
    },
    created () {
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../../assets/style/theme";
  @import "../../assets/style/mixin";
  .home {
    display: flex;
    flex-direction: column;
  }
  .home_top_area{
    width: 1200px;
    margin: 0 auto;
    .title{
      width: 100%;
      height: 200px;
      font-size: 100px;
    }
    .pptm{
      position: relative;
      float: left;
      width: 600px;
      text-align: center;
      height: 100px;
      line-height: 100px;
      font-size: 60px;
    }
  }

</style>
