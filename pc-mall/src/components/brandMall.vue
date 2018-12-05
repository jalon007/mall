<template>
  <div class="brand-mall">
      <div class="brand-img">
        <img v-lazy="msg.picUrl" @click="openBrand(msg.id)" :alt="msg.name" :key="msg.picUrl">
      </div>
      <div class="brand-content">
        <h6 class="brand-title" v-html="msg.name">{{msg.name}}|{{msg.floorPrice}}元起</h6>
        <h3 class="sub-title">{{msg.desc}}</h3>
        <div class="good-button">
          <a @click="openProduct(msg.id)">
            <y-button text="查看详情" style="margin: 0 5px" class="detailbutton"></y-button>
          </a>
        </div>
      </div>
  </div>
</template>
<script>
  import YButton from '/components/YButton'
  import { addCart } from '/api/goods.js'
  import { mapMutations, mapState } from 'vuex'
  import { getStore } from '/utils/storage'
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
      },
      addCart (id, price, name, img) {
        if (!this.showMoveImg) {     // 动画是否在运动
          if (this.login) { // 登录了 直接存在用户名下
            addCart({userId: getStore('userId'), productId: id, productNum: 1}).then(res => {
              // 并不重新请求数据
              this.ADD_CART({productId: id, salePrice: price, productName: name, productImg: img})
            })
          } else { // 未登录 vuex
            this.ADD_CART({productId: id, salePrice: price, productName: name, productImg: img})
          }
          // 加入购物车动画
          var dom = event.target
          // 获取点击的坐标
          let elLeft = dom.getBoundingClientRect().left + (dom.offsetWidth / 2)
          let elTop = dom.getBoundingClientRect().top + (dom.offsetHeight / 2)
          // 需要触发
          this.ADD_ANIMATION({moveShow: true, elLeft: elLeft, elTop: elTop, img: img})
          if (!this.showCart) {
            this.SHOW_CART({showCart: true})
          }
        }
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
        line-height: 100px;
      }
      .brand-title {
        font-size: 18px;
        color: #424242;
        margin: 0 auto;
        padding: 10px 10px;
        text-align: center;
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
