<!--商品详情-->
<template>
  <div class="w brand-detail">
    <div class="brand-mall">
      <div class="brand-cate">
        <div class="brand-cate-ul" v-for="(item,j) in brandCate" :key="j">
          <div :class="getColor(j)">{{item.name}}</div>
        </div>
        <!--<ul class="cate-list" v-for="(item,j) in brandCate" :key="j">-->
          <!--<li >-->
            <!--<a @click="changGoods(j, item)" :class="{active:j===choosePage}">{{item.name}}</a>-->
          <!--</li>-->
          <!--<li >-->
            <!--<a @click="changGoods(j, item)" :class="{active:j===choosePage}">{{item.name}}</a>-->
          <!--</li>-->
        <!--</ul>-->
      </div>

      <div class="brand-img">
        <img v-lazy="brand.picUrl" :alt="brand.name" :key="brand.picUrl">
      </div>
      <div class="brand-content">
        <h6 class="brand-title">{{brand.name}} | {{brand.floorPrice}}元起</h6>
        <p class="sub-title">{{brand.desc}}</p>
      </div>
    </div>

    <!--产品信息-->
    <div v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;">
      <div class="goods-item" v-if="!noResult">
        <!--商品-->
        <div class="goods-box w">
          <mall-goods v-for="(item,i) in goods" :key="i" :msg="item"></mall-goods>
        </div>

        <el-pagination
          v-if="!noResult&&!error"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[8, 20, 40, 80]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
  import { getBrandDetail, getAllGoods, addCart } from '/api/goods'
  import { mapMutations, mapState } from 'vuex'
  import YShelf from '/components/shelf'
  import BuyNum from '/components/buynum'
  import YButton from '/components/YButton'
  import mallGoods from '/components/mallGoods'
  import { getStore } from '/utils/storage'
  export default {
    data () {
      return {
        noResult: false,
        error: false,
        productMsg: {},
        small: [],
        big: '',
        brand: {
          salePrice: 0
        },
        brandCate: [],
        productNum: 1,
        userId: '',
        currentPage: 1,
        total: 0,
        pageSize: 21
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart'])
    },
    methods: {
      ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
      _brandDet (id) {
        getBrandDetail({params: {id}}).then(res => {
          let result = res.data
          this.brand = result.brand
        })
      },
      _getAllGoods (id) {
        let params = {
          params: {
            page: this.currentPage,
            size: this.pageSize,
            brandId: id
          }
        }
        getAllGoods(params).then(res => {
          if (res.errno === 0) {
            this.total = res.data.count
            this.goods = res.data.goodsList
            this.brandCate = res.data.filterCategoryList
            this.noResult = false
            // if (this.total === 0) {
            //   this.noResult = true
            // }
            this.error = false
          } else {
            this.error = true
          }
          this.loading = false
        })
      },
      addCart (id, price, name, img) {
        if (!this.showMoveImg) {     // 动画是否在运动
          if (this.login) { // 登录了 直接存在用户名下
            addCart({userId: this.userId, productId: id, productNum: this.productNum}).then(res => {
              // 并不重新请求数据
              this.ADD_CART({
                productId: id,
                salePrice: price,
                productName: name,
                productImg: img,
                productNum: this.productNum
              })
            })
          } else { // 未登录 vuex
            this.ADD_CART({
              productId: id,
              salePrice: price,
              productName: name,
              productImg: img,
              productNum: this.productNum
            })
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
      },
      checkout (productId) {
        this.$router.push({path: '/checkout', query: {productId, num: this.productNum}})
      },
      editNum (num) {
        this.productNum = num
      },
      getColor (i) {
        return 'color' + parseInt(i % 6)
      }
    },
    components: {
      YShelf, BuyNum, YButton, mallGoods
    },
    created () {
      let id = this.$route.query.id
      this._brandDet(id)
      this._getAllGoods(id)
      this.userId = getStore('userId')
    }
  }
</script>
<style lang="scss" scoped>
  @import "../../assets/style/mixin";
  .brand-detail {
    clear: both;
    width: 1220px;
    min-height: 600px;
    padding: 0 0 25px;
    margin: 0 auto;
    .brand-mall {
      display: block;
      position: relative;
      margin-bottom: 4px;
      width: 1220px;
      min-height: 417px;
      padding: 0 0 25px;
      margin: 0 auto;
      .brand-cate{
        width: 300px;
        float: left;
        height: 417px;
        .brand-cate-ul{
          float: left;
          width: 140px;
          margin: 5px;
          height: 30px;
          line-height: 30px;
          border: 1px solid ;
          border-radius: 3px;
          color: #FFF;
          text-align: center;
          .color0{
            @include wh(100%);
            background: #b3623b
          }
          .color1{
            @include wh(100%);
            background: #c84680;
          }
          .color2{
            @include wh(100%);
            background: #9547c8;
          }
          .color3{
            @include wh(100%);
            background: #1ac859;
          }
          .color4{
            @include wh(100%);
            background: #0d6586;
          }
          .color5{
            @include wh(100%);
            background: #91151a;
          }
        }
      }
      .brand-img {
        position: relative;
        left: 10px;
        z-index: 0;
        overflow: hidden;
        img {
          @include wh(900px,417px);
        }
      }
      .brand-content {
        @include wh(100%,100%);
        position: absolute;
        right: 0;
        width: 900px;
        top: 0;
        display: table;
        z-index: 0;
        .brand-title{
          text-align: center;
          font-weight: 700;
          font-size: 20px;
          color: #ffffff;
          text-shadow: 1px 1px #1f2d3d;
          overflow: hidden;
          margin-top: 150px;
        }
        .sub-title {
          text-align: center;
          line-height: 18px;
          font-size: 13px;
          color: #eeeeee;
          padding: 10px;

        }
      }

    }
  }
  .goods-item{
    display: flex;
    flex-direction: column;
    .goods-box {
      > div {
        float: left;
        border: 1px solid #efefef;
      }
    }
    .el-pagination{
      align-self: flex-end;
      margin: 1vw 1vw 1vw;
    }
  }

</style>
