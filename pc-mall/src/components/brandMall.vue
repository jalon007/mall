<template>
  <div class="brand-mall">
      <div class="brand-img">
        <a @click="openBrand(msg.id)">
        <img v-lazy="msg.picUrl" :alt="msg.name" :key="msg.picUrl">
        </a>
      </div>
      <div class="brand-content">
        <h6 class="brand-title" v-html="msg.name">{{msg.name}}|{{msg.floorPrice}}元起</h6>
        <h3 class="sub-title">{{msg.desc}}</h3>
        <div class="good-button">
          <a @click="openBrand(msg.id)">
            <y-button text="查看详情" style="margin: 0 5px" class="detailbutton"></y-button>
          </a>
        </div>
      </div>
  </div>
</template>
<script>
  import YButton from '/components/YButton'
  import { mapMutations, mapState } from 'vuex'
  export default {
    props: {
      msg: {
        salePrice: 0
      }
    },
    data () {
      return {}
    },
    methods: {
      ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
      goodsDetails (id) {
        this.$router.push({path: 'brandsDetails/id=' + id})
      },
      openBrand (id) {
        window.open('//' + window.location.host + '/#/brandsDetails?id=' + id)
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart'])
    },
    mounted () {
    },
    components: {
      YButton
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/mixin";
  @import "../assets/style/theme";

  .brand-mall {
    display: block;
    width: 500px;
    height: 277px;
    position: relative;
    margin-bottom: 4px;
    &:hover {
      transform: translateY(-5px);
      box-shadow: 1px 6px 20px #5c81e3;
      border-top-left-radius: 18px;
      border-top-right-radius: 18px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
    }

    .good-button{
      @extend %block-center;
      display: flex;
      padding-bottom: 17px;
      a{
        padding: 0 27px;
      }
      .detailbutton{
        &:hover{
          border: 1px solid #5c81e3;
        }
      }
    }
    .brand-img {
      position: absolute;
      left: 0;
      top: 0;
      z-index: 0;
      width: 500px;
      height: 277px;
      overflow: hidden;
      img {
        @include wh(500px,277px);
      }
    }
    .brand-content {
        @include wh(100%,100%);
      position: absolute;
      left: 0;
      top: 0;
      display: table;
      z-index: 0;
      .brand-title{
        /*display: table-cell;*/
        vertical-align: middle;
        text-align: center;
        height: 63px;
        line-height: 63px;
        font-size: 20px;
        color: #ffffff;
        text-shadow: 1px 1px rgba(0, 0, 0, 0.32);
        margin: 0 auto;
        padding: 10px 10px;
        overflow: hidden;
      }
      h3 {
        text-align: center;
        line-height: 15px;
        font-size: 15px;
        color: #696969;
        padding: 10px;
      }
    }
    .good-price {
      margin: 15px 0;
      height: 30px;
      text-align: center;
      line-height: 30px;
      font-family: Arial;
      font-size: 18px;
      font-weight: 300;
      .yj{
        text-decoration:line-through
      }
      p{
        color: #d44d44;
        display: inline;
        padding: 0 10px;
      }
    }


  }
</style>
