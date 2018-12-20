<template>
  <div class="home">

  <div v-if="!error">
  <div class="w">
    <div class="home_top_area">
      <div class="left_category_bar">
        <category-tree :category="categoryList"></category-tree>
      </div>
      <div class="middle_banner_bar" >
        <div class="bg" ref="bg">
          <transition name="fade">
            <div v-for="(item, i) in banner" v-if="i===mark" :key="i" style="position:absolute" @click="linkTo(item)" @mouseover="stopTimer" @mouseout="startTimer">
              <img v-if="item.url" class="img" :src="item.url"/>
            </div>
          </transition>
        </div>
        <div class="page">
          <ul class="dots">
            <li class="dot-active" v-for="(item, i) in banner" :class="{ 'dot':i!=mark }" :key="i" @click="change(i)"></li>
          </ul>
        </div>
      </div>
      <div class="right_msg_bar">
        <div>
          <!-- 用户头像登陆信息-->
          <div class="login_info" v-if="!login">
            <div class="nav-user-avatar">
              <div>
              <img class="avatar" />
              </div>
              <p class="name">Hi~欢迎来到趣Talk</p>
            </div>
            <!-- 如果已登录 换其他ul -->
            <div class="button_ul">
              <a class="to_login" > <router-link to="/login">登录</router-link></a>
              <a class="to_register"><router-link to="/register">注册</router-link></a>
            </div>
          </div>
          <div class="login_info" v-if="login">
            <div class="nav-user-avatar">
              <div>
                <img class="avatar" :style="{backgroundImage:'url('+userInfo.info.avator+')'}"/>
              </div>
              <p class="name">Hi~{{userInfo.info.userName}},欢迎来到趣Talk</p>
            </div>
            <!-- 如果已登录 换其他ul -->
            <div class="button_ul">
            <a class="to_login"  @click="_loginOut">退出</a>
            </div>
          </div>
          <!-- 促销信息-->
          <div class="sales_info">
            <sales-tabs></sales-tabs>
          </div>
          <!-- 消息条-->
          <div class="msg_info">
            <message></message>
          </div>
        </div>

      </div>
    </div>
      <div class="slider-area">
        <ul class="slider-ul">
          <div class="slider_box">
            <div class="title">排行榜TOP</div>
            <div>
              <slide-top :goods="brandsss"></slide-top>
            </div>
          </div>
          <div class="slider_box middle">
            <div class="title">耍大牌</div>
            <div>
              <slide-model  :goods="hotBrands"></slide-model>
            </div>
          </div>
          <div class="slider_box">
            <div class="title">换新季</div>
            <div>
              <slide-model  :goods="newGoods"></slide-model>
            </div>
          </div>
        </ul>
      </div>
      <div class="floor-goods">
        <div v-for="(item ,i) in floorGoodsList" >
          <floor-goods :title="item.name" :sub-category="item.childCategory" :sub-goods="item.goodsList"  :class="getColor(i)"></floor-goods>
        </div>
      </div>
    <div class="maybe_like">
        <div class="mb_hd">
          <h3 class="mb_title">还没逛够</h3>
        </div>
        <maybe-like :goods="newGoods"></maybe-like>
    </div>
    </div>
  </div>
    <div class="no-info" v-if="error">
      <div class="no-data">
        <img src="/static/images/error.png">
        <br> 抱歉！出错了...
      </div>
    </div>

    <el-dialog
      title="通知"
      :visible.sync="dialogVisible"
      width="30%"
      style="width:70%;margin:0 auto">
      <span>本网站只做商品推荐展示，不向外出售任何商品！！</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import { homeBanner, homeFloors, loginOut } from '/api/index'
  import { getCategory, getNotions } from '/api/goods'
  import YShelf from '/components/shelf'
  import product from '/components/product'
  import mallGoods from '/components/mallGoods'
  import slideModel from '/components/slideModel'
  import { setStore, getStore, removeStore } from '/utils/storage.js'
  import CategoryTree from '/components/categoryTree'
  import SlideTop from '/components/slideTop'
  import message from '/components/message'
  import MaybeLike from '/components/maybeLike'
  import FloorGoods from '/components/floorGoods'
  import 'element-ui/lib/theme-default/index.css'
  import SalesTabs from '/components/salesTabs'
  import { mapState } from 'vuex'
  export default {
    data () {
      return {
        error: false,
        banner: [],
        newGoods: [],
        hotGoods: [],
        hotBrands: [],
        brandsss: [],
        hotTopics: [],
        hotGroupons: [],
        floorGoodsList: [],
        mark: 0,
        bgOpt: {
          px: 0,
          py: 0,
          w: 0,
          h: 0
        },
        notify: '1',
        dialogVisible: false,
        timer: '',
        categoryList: []
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart', 'userInfo'])
    },
    methods: {
      autoPlay () {
        this.mark++
        if (this.mark > this.banner.length - 1) {
          // 当遍历到最后一张图片置零
          this.mark = 0
        }
      },
      play () {
        // 每2.5s自动切换
        this.timer = setInterval(this.autoPlay, 3500)
      },
      change (i) {
        this.mark = i
      },
      startTimer () {
        this.timer = setInterval(this.autoPlay, 3500)
      },
      stopTimer () {
        clearInterval(this.timer)
      },
      openBrand (id) {
        window.open('//' + window.location.host + '/#/brandsDetails?id=' + id)
      },
      linkTo (item) {
        if (item.type === 0 || item.type === 2) {
          // 关联商品
          this.$router.push({
            path: '/goodsDetails',
            query: {
              productId: item.id
            }
          })
        } else {
          // 完整链接
          this.$router.push({
            path: '/goodsDetails',
            query: {
              productId: item.id
            }
          })
        }
      },
      bgOver (e) {
        this.bgOpt.px = e.offsetLeft
        this.bgOpt.py = e.offsetTop
        this.bgOpt.w = e.offsetWidth
        this.bgOpt.h = e.offsetHeight
      },
      bgMove (dom, eve) {
        let bgOpt = this.bgOpt
        let X, Y
        let mouseX = eve.pageX - bgOpt.px
        let mouseY = eve.pageY - bgOpt.py
        if (mouseX > bgOpt.w / 2) {
          X = mouseX - (bgOpt.w / 2)
        } else {
          X = mouseX - (bgOpt.w / 2)
        }
        if (mouseY > bgOpt.h / 2) {
          Y = bgOpt.h / 2 - mouseY
        } else {
          Y = bgOpt.h / 2 - mouseY
        }
        dom.style['transform'] = `rotateY(${X / 50}deg) rotateX(${Y / 50}deg)`
        dom.style.transform = `rotateY(${X / 50}deg) rotateX(${Y / 50}deg)`
      },
      bgOut (dom) {
        dom.style['transform'] = 'rotateY(0deg) rotateX(0deg)'
        dom.style.transform = 'rotateY(0deg) rotateX(0deg)'
      },
      showNotify () {
        var value = getStore('notify')
        if (this.notify !== value) {
          this.dialogVisible = true
          setStore('notify', this.notify)
        }
      },
      _getBanner () {
        homeBanner().then(res => {
          this.banner = res.data.banner
        })
      },
      _getCategory () {
        getCategory().then(res => {
          this.categoryList = res.data.categoryList
        })
      },
      _getHomeFloors () {
        homeFloors().then(res => {
          this.floorGoodsList = res.data.floorGoodsList
        })
      },
      _getNotions () {
        getNotions().then(res => {
          this.newGoods = res.data.newGoods
          this.hotBrands = res.data.hotBrands
          this.brandsss = res.data.brandsss
          this.hotGoods = res.data.hotGoods
          this.hotTopics = res.data.hotTopics
          this.hotGroupons = res.data.hotGroupons
        })
      },
      getColor (i) {
        return 'color' + parseInt(i % 4)
      },
      // 退出登陆
      _loginOut () {
        loginOut().then(res => {
          removeStore('buyCart')
          window.location.href = '/'
        })
      }
    },
    mounted () {
      this._getBanner()
      this._getNotions()
      this._getCategory()
      this._getHomeFloors()
      this.showNotify()
    },
    created () {
      this.play()
    },
    components: {
      SalesTabs,
      FloorGoods,
      MaybeLike,
      SlideTop,
      CategoryTree,
      YShelf,
      product,
      mallGoods,
      slideModel,
      message
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

  .no-info {
    padding: 100px 0;
    text-align: center;
    font-size: 30px;
    display: flex;
    flex-direction: column;
    .no-data{
      align-self: center;
    }
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
  }
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }

  .page {
    position: absolute;
    width: 100%;
    top: 350px;
    z-index: 30;
    .dots {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      .dot-active {
        display: inline-block;
        width: 15px;
        height: 15px;
        background-color: #af0527;
        border-radius: 8px;
        margin-right: 10px;
        cursor: pointer;
      }
      .dot {
        opacity: 0.2;
      }
    }
  }

  .slider-area {
    width: 100%;
    margin: 0 auto;
    position: relative;
    .slider-ul {
      width: 100%;
      /*position: relative;*/
      .slider_box {
        overflow: hidden;
        position: relative;
        z-index: 0;
        width: 400px;
        float: left;
        margin-top: 25px;

        box-sizing: border-box;
        border-radius: 2px;
        background: #fff;
        padding: 0 20px;
        .title {
          float: left;
          height: 54px;
          font-weight: bold;
          line-height: 1.8;
          font-size: 20px;
          color: #424242;
          padding: 10px;
          text-align: center;
          overflow: hidden;
        }
      }
      .middle{
        margin-left: 10px;
        margin-right: 10px;
      }
    }


    .content {
      float: left;
      position: relative;
      box-sizing: border-box;
      width: 25%;
      height: 316px;
      text-align: center;
      border-radius: 10px;
      padding: 0 2px;
      &:hover {
        transform: translateY(-2px);
        box-shadow: 1px 1px 20px #999;
      }
    }
    .content ::before{
      position: absolute;
      top: 0;
      left: 0;
      z-index: 1;
      box-sizing: border-box;
      border-left: 1px solid #f2f2f2;
      border-left: 1px solid rgba(0,0,0,.1);
      width: 100%;
      height: 100%;
      content: "";
      pointer-events: none;
    }
    .i {
      width: 305px;
      height: 316px;
    }
    .cover-link {
      cursor: pointer;
      display: block;
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      z-index: 4;
      background: url(data:image/gif;base64,R0lGODlhAQABAIAAAP///////yH5BAEHAAEALAAAAAABAAEAAAICTAEAOw==) repeat;
    }
    a {
      color: #5079d9;
      cursor: pointer;
      transition: all .15s ease-out;
      text-decoration: none;
    }
    a:hover {
      box-shadow: inset 0 0 38px rgba(0,0,0,.08);
      transition: all .15s ease;
    }
  }
  .floor-goods{
    width: 100%;
    margin: 0 auto;
    position: relative;
    margin-top: 30px;
    float: left;
  }
  .maybe_like{
    width: 100%;
    margin: 0 auto;
    position: relative;
    margin-top: 30px;
    float: left;
    .mb_hd{
      width: 100%;
      height: 60px;

    }
    .mb_title{
      position: relative;
      width: 250px;
      height: 45px;
      font-size: 28px;
      font-family: 'fzzzh', sans-serif;
      font-weight: normal;
      text-align: center;
      line-height: 45px;
      padding: 0 50px;
      margin: 0 auto 20px;
      overflow: hidden;
      color: #222;
      /*font-size: 30px;*/
      /*text-align: center;*/
      &:before{
        right: 0px;
        content: '';
        position: absolute;
        width: 50px;
        height: 3px;
        display: block;
        background: #222;
        top: 19px;
      }
      &:after{
        left: 0px;
        content: '';
        position: absolute;
        width: 50px;
        height: 3px;
        display: block;
        background: #222;
        top: 19px;
      }
    }
  }
  .banner, .banner span, .banner div {
    font-family: "Microsoft YaHei";
    transition: all .3s;
    transition-timing-function: linear;
  }

  .a {
    z-index: 20;
    transform: translateZ(40px);
  }

  .b {
    z-index: 20;
    transform: translateZ(30px);
  }

  .c {
    transform: translateZ(0px);
  }

  .floors {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    .imgbanner {
      width: 50%;
      height: 430px;
      .cover-link {
        cursor: pointer;
        display: block;
        position: absolute;
        top: 60px;
        left: 0;
        width: 50%;
        height: 430px;
        z-index: 4;
        background: url(data:image/gif;base64,R0lGODlhAQABAIAAAP///////yH5BAEHAAEALAAAAAABAAEAAAICTAEAOw==) repeat;
      }
      &:hover {
        transform: translateY(-2px);
        box-shadow: 1px 1px 20px #999;
        border-radius: 18px;
      }
      .cover-link:hover {
        box-shadow: inset 0 0 38px rgba(0,0,0,.08);
        transition: all .15s ease;
      }
    }
    img {
      display: block;
      width: 100%;
      height: 70%;
    }
  }
  .good-title {
    line-height: 1.2;
    font-size: 16px;
    color: #424242;
    margin: 0 auto;
    padding: 0 14px;
    text-align: center;
    overflow: hidden;

  }
  .sub-title {
    text-align: center;
    line-height: 1.2;
    font-size: 12px;
    color: #d0d0d0;
    padding: 10px;
  }
  /*.home .w1220{width: 1220px; margin:0 auto;}*/
  .home_top_area{
    position: relative;
    height: 400px;
    width: 100%;
    .left_category_bar{
      width: 200px;
      height: 100%;
      float: left;
    }
    .middle_banner_bar {
      float: left;
      cursor: pointer;
      perspective: 3000px;
      position: relative;
      z-index: 19;
      margin: 0 10px;
      width: 800px;
      .bg {
        position: relative;
        display: flex;
        width: 100%;
        height: 400px;
        margin: 0px auto;
        background-size: 100% 100%;
        transform-style: preserve-3d;
        transform-origin: 50% 50%;
        transform: rotateY(0deg) rotateX(0deg);
        & div{
          position: relative;
          height: 100%;
          width: 100%;
        }
        .img {
          display: block;
          position: absolute;
          width: 100%;
          height: 100%;
          top: 0;
          /*border-radius: 10px;*/
        }
      }
    }

    .right_msg_bar{
      width: 200px;
      height: 100%;
      position: relative;
      display: flex;
      .login_info{
        background: #f4f4f4;
        width: 200px;
        height: 155px;

        .nav-user-avatar {
          padding-top: 10px;
          > div {
            position: relative;
            margin: 0 auto;
            @include wh(80px);
            text-align: center;
            &:before {
              content: "";
              position: absolute;
              left: 0;
              right: 0;
              top: 0;
              bottom: 0;
              border-radius: 50%;
              box-shadow: inset 0 0 0 1px rgba(0, 0, 0, .06);
            }
            .avatar {
              border-radius: 50%;
              display: block;
              @include wh(100%);
              background: url("/static/images/user-avatar.png");
              background-repeat: no-repeat;
              background-size: 80px;
            }

          }
          .name {
            margin-top: 4px;
            margin-bottom: 10px;
            font-size: 12px;
            line-height: 1.5;
            text-align: center;
            color: #757575;
          }
        }
        .button_ul{
          position: relative;
          margin: 0 auto;
          text-align: center;
          a{
            &:hover{
              color: #c81623;
            }
          }
        }
      }
      .sales_info{
        width: 200px;
        height: 220px;
        .s_title{
          padding: 2px;
        }
        .ali_red{
          position: relative;
          margin: 0 auto;
          @include wh(100%);
          background: url("/static/images/ali_red.png");
          background-repeat: no-repeat;
          background-size: 200px;
        }
      }
      .msg_info{
        width: 200px;
        height: 25px;
      }
    }
  }



</style>
