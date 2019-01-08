<template>
  <div class="good-item">
    <!--<div class="good-so">-->
      <!--<img src="/static/images/so-red.png">-->
    <!--</div>-->
    <div style="">
      <div class="good-img">
        <a @click="openProduct(msg.id)">
          <img v-lazy="msg.picUrl" :alt="msg.name" :key="msg.picUrl">
        </a>
      </div>
      <div class="good-content">
        <h6 class="good-title" v-html="msg.name">{{msg.name}}</h6>
        <h3 class="sub-title">{{msg.brief}}</h3>
        <div class="good-price pr">
          <p><span style="font-size:14px;color:#000">原价:￥</span><span class="yj">{{Number(msg.retailPrice).toFixed(2)}}</span></p>
          <p><span style="font-size:14px;color:#000">券后价格:￥</span><span class="jhjg">{{Number(msg.retailPrice).toFixed(2)}}</span></p>
        </div>
        <div class="good-button">
          <a @click="openProduct(msg.id)">
            <y-button text="查看详情" style="margin: 0 5px" class="detailbutton"></y-button>
          </a>
          <!-- <y-button text="加入购物车"
                    style="margin: 0 5px"
                    @btnClick="addCart(msg.id,msg.retailPrice,msg.name,msg.picUrl)"
                    classStyle="main-btn"
          ></y-button> -->
          <y-button text="领取优惠券"
                    style="margin: 0 5px"
                    @btnClick="addCart(msg.id,msg.retailPrice,msg.name,msg.picUrl)"
                    classStyle="main-btn"main-btn
          ></y-button>
        </div>
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
        this.$router.push({path: 'goodsDetails/productId=' + id})
      },
      openProduct (id) {
        window.open('//' + window.location.host + '/#/goodsDetails?productId=' + id)
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

  .good-item {
    background: #fff;
    width: 25%;
    transition: all .5s;
    height: 430px;
    padding: 2px;
    &:hover {
      /*transform: translateY(-5px);*/
      /*box-shadow: 1px 6px 20px #5c81e3;*/
      /*border-top-left-radius: 18px;
      border-top-right-radius: 18px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;*/
      .good-content{
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
      }
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
          border: 1px solid #af0527;
        }
      }
    }
    .good-img {
      img {
        margin: 20px auto 10px;
        @include wh(206px);
        display: block;
      }
    }
    .good-content {
        @include wh(100%,50%);
        display: block;
        background: #dcdcdc;
      padding-top: 10px;
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
    .good-title {
      line-height: 1.2;
      font-size: 18px;
      color: #424242;
      margin: 0 auto;
      padding: 10px 10px;
      text-align: center;
      overflow: hidden;
    }
    h3 {
      text-align: center;
      line-height: 1.2;
      font-size: 12px;
      color: #696969;
      padding: 10px;
    }

  }
</style>
