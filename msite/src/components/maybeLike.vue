<template>
  <div class="good">
      <div class="good-item" v-for="(item,i) in goods" :key="i">
      <div class="good-img">
        <a @click="openProduct(item.id)">
          <img v-lazy="item.picUrl" :alt="item.name" :key="item.picUrl">
        </a>
      </div>
      <div class="good-content">
        <h6 class="good-title" v-html="item.name">{{item.name}}</h6>
        <h3 class="sub-title">{{item.brief}}</h3>
        <div class="good-price pr">
          <p><span class="yj">￥{{Number(item.retailPrice).toFixed(2)}}</span></p>
          <p><span class="jhjg">￥{{Number(item.retailPrice).toFixed(2)}}</span></p>
        </div>
      </div>
      <div class="button_lay">
        <div class="good-button">
          <a @click="openProduct(item.id)">
            <y-button text="查看详情" style="margin: 0 5px" class="detailbutton"></y-button>
          </a>
          <!-- <y-button text="加入购物车"
                    style="margin: 0 5px"
                    @btnClick="addCart(msg.id,msg.retailPrice,msg.name,msg.picUrl)"
                    classStyle="main-btn"
          ></y-button> -->
          <y-button text="趣领券"
                    style="margin: 0 5px"
                    @btnClick="addCart(item.id,item.retailPrice,item.name,item.picUrl)"
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
      goods: {
        salePrice: 0
      }
    },
    data () {
      return {
      }
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
    float: left;
    position: relative;
    background: #fff;
    width: 230px;
    transition: all .5s;
    height: 305px;
    margin:7px;
    &:hover {
      .button_lay{
        display: flex;
      }
    }
    .button_lay{
      display: none;
      @include wh(100%);
      position: absolute;
      top:0;
      z-index: 11;
      background: rgba(206, 199, 197, 0.85);
      .good-button{
        @include wh(100%,100px);
        display: flex;
        /*padding-bottom: 17px;*/
        position: relative;
        margin: 0 auto;
        padding: 15px;
        top:170px;
        .detailbutton{
          background: #5e7382;
          color: #ffffff;
          &:hover{
            border: 1px solid #af0527;
          }
        }
          input{
            width: 70px;
            height: 70px;
            border-radius: 50px;
            line-height: 28px;
            vertical-align: middle;

        }
      }
    }

    .good-img {
      img {
        /*margin: 0 10px;*/
        @include wh(230px);
        display: block;
      }
    }
    .good-content {
      top:-55px;
      position: relative;
        @include wh(230px,130px);
        display: block;
        /*background: #dcdcdc;*/
      /*padding-top: 10px;*/
    }
    .good-price {
      /*margin: 15px 0;*/
      height: 30px;
      text-align: left;
      line-height: 20px;
      font-family: Arial;
      font-size: 18px;
      font-weight: 300;
      .yj{
        color: #d44d44;
        padding-left: 20px;
        font-size: 12px;
        text-decoration:line-through
      }
      p{
        /*display: inline;*/
        padding: 0 10px;
      }
    }
    .good-title {
      line-height: 1.2;
      font-size: 16px;
      color: #424242;
      margin: 0 auto;
      padding: 5px 10px;
      text-align: center;
      overflow: hidden;
      white-space: nowrap;
      text-overflow:ellipsis;
    }
    h3 {
      text-align: center;
      line-height: 1.2;
      font-size: 12px;
      color: #696969;
      padding: 5px 10px;
    }

  }
</style>
