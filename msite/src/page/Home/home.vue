<template>
  <div class="home">

  <div v-if="!error">

    <div class="home_top_area">
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
            <li class="dot" v-for="(item, i) in banner" :class="{ 'dot-active':i===mark }" :key="i" @click="change(i)"></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="menu_area">
      <!--<div class="menu">-->
        <div class="menu_item"v-for="(item,i) in categoryList">
          <img :src="item.iconUrl"/>
          <span>{{item.name}}</span>
        </div>
      <!--</div>-->
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
    <div class="f_b">
      <div class="mb_hd">
        <h3 class="mb_title">这是我的底线</h3>
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
      <span>本网站只做商品导购推荐、分享优惠券，不向外出售任何商品！！</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">知道了</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import { homeBanner, loginOut } from '/api/index'
  import { goodsCoupons } from '/api/tb'
  import { getCategory, getNotions } from '/api/goods'
  import YShelf from '/components/shelf'
  import product from '/components/product'
  import mallGoods from '/components/mallGoods'
  import { setStore, getStore, removeStore } from '/utils/storage.js'
  import CategoryTree from '/components/categoryTree'
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
        goodsCoupons().then(res => {
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
      CategoryTree,
      YShelf,
      product,
      mallGoods
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
    top: 500px;
    z-index: 30;
    .dots {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      .dot {
        display: inline-block;
        width: 20px;
        height: 6px;
        margin-right: 15px;
        cursor: pointer;
        background: rgba(94, 115, 130, 0.5);
      }
      .dot-active {
        width: 30px;
        background-color: #c81623;
      }
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

  }
  .mb_hd{
    width: 100%;
    height: 60px;

  }
  .mb_title{
    position: relative;
    width: 300px;
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
  .f_b{
    width: 100%;
    height: 100px;
    margin: 0 auto;
    position: relative;
    margin-bottom: 200px;
    float: left;
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

  .home_top_area{
    position: relative;
    width: 100%;
    .middle_banner_bar {
      float: left;
      cursor: pointer;
      perspective: 3000px;
      position: relative;
      z-index: 19;
      width: 100%;
      .bg {
        position: relative;
        display: flex;
        width: 100%;
        height: 600px;
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
  }

  .menu_area {
    float: left;
    width: 1200px;
    height: auto;
    margin-top: -50px;
    z-index: 40;
    position: relative;
    background: #ffffff;
    margin-left: 25px;
    margin-right: 25px;
    border-radius: 40px;
    .menu_item {
      /*overflow: hidden;*/
      position: relative;
      z-index: 0;
      width: 220px;
      height: 220px;
      float: left;
      margin: 10px;
      border-radius: 60px;
      img{
        width: 120px;
        margin: 5px auto;
        height: auto;
        display: block;
      }
      span{
        display:block;
        font-size:38px;
        text-align:center;
        margin:0 auto;
        line-height:70px;
        color: #4b4b4b;

      }
    }

  }

</style>
