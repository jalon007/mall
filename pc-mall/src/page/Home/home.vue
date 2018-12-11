<template>
  <div class="home">

  <div v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;" v-if="!error">

    <div class="category-con">
      <div class="category-inner-con w1220">
        <div class="category-type">
          <h3>全部分类</h3>
        </div>
        <div class="category-tab-content">
          <div class="nav-con">
            <ul class="normal-nav layui-clear">
              <li class="nav-item"  v-for="(item, i) in categoryList" :key="i">
                <div class="title">{{item.name}}</div>
                <p><a href="#">奶粉</a><a href="#">捕食</a><a href="#">营养品</a></p>
                <i class="layui-icon layui-icon-right"></i>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="banner" >
        <div class="bg" ref="bg"
             @mouseover="bgOver($refs.bg)" @mousemove="bgMove($refs.bg,$event)" @mouseout="bgOut($refs.bg)">
          <transition name="fade">
            <div v-for="(item, i) in banner" v-if="i===mark" :key="i" style="position:absolute" @click="linkTo(item)" @mouseover="stopTimer" @mouseout="startTimer">
              <img v-if="item.url" class="img1" :src="item.url"/>
            </div>
          </transition>
        </div>
        <div class="page">
          <ul class="dots">
            <li class="dot-active" v-for="(item, i) in banner" :class="{ 'dot':i!=mark }" :key="i" @click="change(i)"></li>
          </ul>
        </div>
      </div>
    </div>




      <div class="activity-panel">
        <ul class="box">
          <li class="content" v-for="(iitem,j) in brandList" :key="j" @click="openBrand(iitem.id) ">
            <div class="title">{{iitem.name}}</div>
            <img class="i" :src="iitem.picUrl" :alt="iitem.name">
            <a class="cover-link"></a>
          </li>
        </ul>
      </div>

    <div v-if="item=home" >
      <section class="w mt30 clearfix">
        <y-shelf :title="i">
          <div slot="content" class="hot">
            <mall-goods :msg="iitem" v-for="(iitem,j) in item.newGoodsList" v-if="j<4" :key="j"></mall-goods>
          </div>
        </y-shelf>
      </section>
    </div>

    <div v-for="(item ,i) in floorGoodsList" >
      <section class="w mt30 clearfix">
        <y-shelf :title="item.name" :child="item.childCategory" :class="getColor(i)">
          <div slot="content" class="floors" >
            <div class="imgbanner" v-for="(iitem,j) in item.goodsList"  v-if="j<1" :key="j"  @click="linkTo(iitem)">
              <img v-lazy="iitem.picUrl">
              <h6 class="good-title" v-html="iitem.name">{{iitem.name}}</h6>
              <h3 class="sub-title ellipsis">{{iitem.brief}}</h3>
              <a class="cover-link"></a>
            </div>
            <mall-goods :msg="iitem" v-for="(iitem,j) in item.goodsList" v-if="j>0" :key="j+'key'"></mall-goods>
          </div>
        </y-shelf>
      </section>

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
  import { productHome } from '/api/index'
  import { category } from '/api/goods'
  import YShelf from '/components/shelf'
  import product from '/components/product'
  import mallGoods from '/components/mallGoods'
  import { setStore, getStore } from '/utils/storage.js'
  export default {
    data () {
      return {
        error: false,
        banner: [],
        newGoodsList: [],
        brandList: [],
        floorGoodsList: [],
        mark: 0,
        bgOpt: {
          px: 0,
          py: 0,
          w: 0,
          h: 0
        },
        home: [],
        loading: true,
        notify: '1',
        dialogVisible: false,
        timer: '',
        categoryList: []
      }
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
        this.timer = setInterval(this.autoPlay, 2500)
      },
      change (i) {
        this.mark = i
      },
      startTimer () {
        this.timer = setInterval(this.autoPlay, 2500)
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
          // window.location.href = item.fullUrl
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
      _getCategory () {
        category().then(res => {
          this.categoryList = res.data.categoryList
        })
      },
      getColor (i) {
        return 'color' + parseInt(i % 4)
      }
    },
    mounted () {
      productHome().then(res => {
        if (res.success === false) {
          this.error = true
          return
        }
        let data = res.data
        this.home = data
        this.loading = false
        this.banner = data.banner
        this.newGoodsList = data.newGoodsList
        this.categoryList = data.channel
        this.brandList = data.brandList
        this.floorGoodsList = data.floorGoodsList
      })
      this._getCategory()
      this.showNotify()
    },
    created () {
      this.play()
    },
    components: {
      YShelf,
      product,
      mallGoods
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
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
    top: 470px;
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
        background-color: whitesmoke;
        border-radius: 8px;
        margin-right: 10px;
        cursor: pointer;
      }
      .dot {
        opacity: 0.2;
      }
    }
  }

  .activity-panel {
    width: 1220px;
    margin: 0 auto;
    .box {
      overflow: hidden;
      position: relative;
      z-index: 0;
      margin-top: 25px;
      box-sizing: border-box;
      border: 1px solid rgba(0,0,0,.14);
      border-radius: 8px;
      background: #fff;
      box-shadow: 0 3px 8px -6px rgba(0,0,0,.1);
    }
    .title{
      height: 54px;
      font-weight: bold;
      line-height: 1.8;
      font-size: 20px;
      color: #424242;
      padding: 10px;
      text-align: left;
      overflow: hidden;
      a {
        width: 32px;
        height: 32px;
        float: right;
        right: 5px;
        background-color: #fff;
        background: url(/static/images/more.png) no-repeat center;
        &:hover{
          background: url(/static/images/more-hover.png) no-repeat center;
          background-color: #1e88e5;
        }
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

  .banner, .banner span, .banner div {
    font-family: "Microsoft YaHei";
    transition: all .3s;
    transition-timing-function: linear;
  }

  .banner {
    cursor: pointer;
    perspective: 3000px;
    position: relative;
    z-index: 19;
    margin: 0 auto;
    width: 1220px;
  }

  .bg {
    position: relative;
    display: flex;
    /*width: 1220px;*/
    width: 100%;
    height: 500px;
    margin: 0px auto;
    background-size: 100% 100%;
    /*border-radius: 10px;*/
    transform-style: preserve-3d;
    transform-origin: 50% 50%;
    transform: rotateY(0deg) rotateX(0deg);
    & div{
      position: relative;
      height: 100%;
      width: 100%;
    }
  }

  .img1 {
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    border-radius: 10px;
  }

  .img2 {
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    bottom: 5px;
    left: 0;
    background-size: 95% 100%;
    border-radius: 10px;
  }

  .img3 {
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    border-radius: 10px;
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

  .sk_item {
    width: 170px;
    height: 225px;
    padding: 0 14px 0 15px;
    > div {
      width: 100%;
    }
    a {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      transition: all .3s;
      &:hover {
        transform: translateY(-5px);
      }
    }
    img {
      width: 130px;
      height: 130px;
      margin: 17px 0;
    }
    .sk_item_name {
      color: #999;
      display: block;
      max-width: 100%;
      _width: 100%;
      overflow: hidden;
      font-size: 12px;
      text-align: left;
      height: 32px;
      line-height: 16px;
      word-wrap: break-word;
      word-break: break-all;
    }
    .sk_item_price {
      padding: 3px 0;
      height: 25px;
    }
    .price_new {
      font-size: 18px;
      font-weight: 700;
      margin-right: 8px;
      color: #f10214;
    }
    .price_origin {
      color: #999;
      font-size: 12px;
    }
  }

  .box {
    overflow: hidden;
    position: relative;
    z-index: 0;
    margin-top: 29px;
    box-sizing: border-box;
    /*border: 1px solid rgba(0, 0, 0, .14);*/
    border-radius: 8px;
    background: #fff;
    box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);

  }

  ul.box {
    display: flex;
    li {
      flex: 1;
      img {
        display: block;
        width: 305px;
        height: 200px;
      }
    }
  }

  .mt30 {
    margin-top: 30px;
  }

  .hot {
    display: flex;
    > div {
      flex: 1;
      width: 25%;
    }
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
  .home .w1220{width: 1220px; margin:0 auto;}
  .home .category-con{position: relative; height: 500px; width: 100%;}
  .home .category-con .category-type{
    width: 200px;
    height:50px;
    line-height: 50px;
    font-size: 14px;
    background: #8b93f6;
    text-align: center;
    color: #fff;
    cursor: pointer;
    margin-top: -50px;
    position: absolute;
    z-index: 100;
    border-bottom: 2px solid #a00006;
  }
  .home .category-con .category-type h3{font-size: 14px;}
  .home .category-tab-content .nav-con{width: 200px; height: 0;
  }
  .home .category-tab-content .nav-con .normal-nav{
    z-index:100;height: 500px; padding: 1px 0px;
    box-sizing: border-box; position: relative; background: rgba(0,0,0,.55);
    border-bottom: 1px solid #e9e9e9; box-sizing: border-box;}
  .home .category-tab-content .nav-con .nav-item{
    padding-left: 10px;
    border-bottom: 1px solid #e7e7e7;
    padding-bottom: 4px;
    position: relative;
    &:hover{
      background: #ffffff;
      .title{
        color: rgba(216, 0, 32, 0.93);
      }
      p a{
        color: rgba(216, 0, 32, 0.93);;
      }
    }
  }
  .home .category-tab-content .nav-con .nav-item .title{font-size: 14px; color: #fff; line-height: 24px; font-weight: bold; cursor: pointer;}
  .home .category-tab-content .nav-con .nav-item  p a{
    font-size: 12px; color: #fff; margin-left: 4px; line-height: 18px;}
  .home .category-tab-content .nav-con .nav-item i{position: absolute; right: 2px; top: 16px; font-size: 12px; }
  .home .category-tab-content .nav-con .nav-item  p a:hover{
    font-weight: bold
  }
  .home .category-banner{background:#ffc6dd;}
</style>
