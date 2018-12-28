<!--商品详情-->
<template>
  <div class="detail">
    <!--图片播放器-->
    <div class="slide_area">
      <img :src="goodInfo.pictUrl">
    </div>
    <!--描述信息-->
    <div class="content_area">

      <div class="good-price pr">
        <p><span class="prise">￥{{Number(goodInfo.zkFinalPrice).toFixed(2)}}</span></p>
        <p v-if="goodInfo.volume"><span class="volume">月销{{goodInfo.volume}}件</span></p>
      </div>
      <h6 class="good-title" v-html="goodInfo.title">{{goodInfo.title}}</h6>
      <h3 class="shop-title">{{goodInfo.shopTitle}}</h3>

    </div>
    <!--店铺信息-->
    <div class="shop_area"></div>
    <!--踩你喜欢-->
    <div class="recommend_area"></div>
    <!--底部功能栏-->
    <div class="foot_bar">
      <y-button text="领取优惠券"
                @btnClick="grap(msg.couponClickUrl)"
                classStyle="main-btn"
                style="width: 145px;height: 50px;line-height: 48px">

      </y-button>
    </div>
  </div>
</template>
<script>
  import { mapMutations, mapState } from 'vuex'
  import YShelf from '/components/shelf'
  import BuyNum from '/components/buynum'
  import YButton from '/components/YButton'
  export default {
    data () {
      return {
        goodInfo: this.goodInfo,
        productNum: 1,
        userId: '',
        big: ''
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart'])
    },
    methods: {
      ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
      grap (openUrl) {
        window.open(openUrl)
        this.$notify.info({
          title: 'Thanks',
          message: '暂无优惠券可领取，敬请期待~^v^~'
        })
      },
      editNum (num) {
        this.productNum = num
      }
    },
    components: {
      YShelf, BuyNum, YButton
    },
    created () {
    },
    mounted () {
      this.goodInfo = this.$route.query
    }
  }
</script>
<style lang="scss" scoped>
  @import "../../assets/style/mixin";

  .detail {
    clear: both;
    width: 1250px;
    height: auto;
    margin: 0 auto;
    .slide_area {
      position: relative;
      display: block;
      width: 100%;
      height: auto;
      img{
        @include wh(100%)
      }
    }
  }

  .content_area {
    width: 100%;
    margin: 0 20px;

    position: relative;
    opacity: 1;

    .good-price {
      /*margin: 15px 0;*/
      height: 60px;
      text-align: left;
      line-height: 60px;
      font-family: Arial;
      font-size: 4rem;
      .prise{
        padding-top: 20px;
        display: inline;
        color: #d44d44;
      }
      .volume{
        font-size: 24px;
      }
      p{
        display: inline;
        padding: 0 30px;
      }
    }
    .good-title {
      line-height: 1.2;
      font-size: 28px;
      color: #424242;
      margin: 0 auto;
      padding: 10px 20px;
      text-align: center;
      overflow: hidden;
      white-space: nowrap;
      text-overflow:ellipsis;
    }
    h3 {
      text-align: left;
      line-height: 1.2;
      font-size: 24px;
      color: #696969;
      padding: 10px 20px;
    }

  }
  .foot_bar{
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100px;
    z-index: 40;
  }
</style>
