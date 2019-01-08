<template>
  <div class="goods">
    <div class="nav-subs">
      <div class="nav-sub-wrappers">
        <div class="w">
          <ul class="nav-lists">
            <li class="result">
              <a>全部搜索结果</a>
            </li>
            <li v-if="categoryName">
              <a class="result">{{categoryName}}</a>
            </li>
            <li>
            <a class="keyword">"{{keyword}}"</a>
            </li>
            <li>
              <a v-if="searching === true">拼命搜索中...</a>
              <a v-if="searching === false">共为您找到 {{total}} 款商品信息</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="nav-cateory">
      <div class="w">
      <div class="title">分类：</div>
      <a class="cate-item" v-for="(item,i) in categoryList" :class="{active:categoryId===item.id}" @click="searchByCategory(item)" :key="i">

        <div>{{item.name}}</div>
      </a>
      </div>
    </div>
    <div class="nav w">
        <div class="l-filter">
          <a href="javascript:;" :class="{active:sortType===1}" @click="reset()">综合</a>
          <a href="javascript:;" :class="{active:sortType===4}" @click="reset()">人气</a>
          <a href="javascript:;" :class="{active:sortType===5}" @click="reset()">新品</a>
          <a href="javascript:;" @click="sortByPrice(1)" :class="{active:sortType===2}">价格从低到高</a>
          <a href="javascript:;" @click="sortByPrice(-1)" :class="{active:sortType===3}">价格从高到低</a>
          <div class="price-interval">
            <input type="number" class="input" placeholder="价格" v-model="min">
            <span style="margin: 0 5px"> - </span>
            <input type="number" placeholder="价格" v-model="max">
            <y-button class="yes" text="确定" classStyle="main-btn" @btnClick="reset" style="margin-left: 10px;"></y-button>
          </div>
      </div>
    </div>

    <div v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;">
      <div  class="img-item" v-if="!noResult" >
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
      <div class="no-info" v-if="noResult" >
        <div class="no-data">
          <img src="/static/images/no-search.png">
          <br> 抱歉！没有为您找到相关的商品
        </div>
        <section class="section">
          <y-shelf :title="recommendPanel.name">
            <div slot="content" class="recommend">
              <mall-goods :msg="item" v-for="(item,i) in recommendPanel.panelContents" :key="i"></mall-goods>
            </div>
          </y-shelf>
        </section>
      </div>
      <div class="no-info" v-if="error">
        <div class="no-data">
          <img src="/static/images/error.png">
          <br> 抱歉！出错了...
        </div>
        <section class="section">
          <y-shelf :title="recommendPanel.name">
            <div slot="content" class="recommend">
              <mall-goods :msg="item" v-for="(item,i) in recommendPanel.panelContents" :key="i"></mall-goods>
            </div>
          </y-shelf>
        </section>
      </div>
    </div>
  </div>
</template>
<script>
  import { getSearchGoods } from '/api/goods.js'
  import { recommend } from '/api/index.js'
  import mallGoods from '/components/mallGoods'
  import YButton from '/components/YButton'
  import YShelf from '/components/shelf'
  import YHeader from '/common/header'
  import YFooter from '/common/footer'
  export default {
    data () {
      return {
        goods: [],
        categoryList: [],
        noResult: false,
        error: false,
        min: '',
        max: '',
        loading: true,
        searching: true,
        timer: null,
        sortType: 1,
        windowHeight: null,
        windowWidth: null,
        recommendPanel: [],
        currentPage: 1,
        pageSize: 20,
        total: 0,
        keyword: '',
        brandId: '',
        categoryId: '',
        categoryName: '',
        sort: 'add_time',
        order: 'desc'
      }
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this._getSearchGoods()
        this.loading = true
      },
      handleCurrentChange (val) {
        this.currentPage = val
        this._getSearchGoods()
        this.loading = true
      },
      _getSearchGoods () {
        let params = {
          params: {
            keyword: this.keyword,
            brandId: this.brandId,
            categoryId: this.categoryId,
            order: this.order,
            size: this.pageSize,
            page: this.currentPage,
            sort: this.sort,
            min: this.min,
            max: this.max
          }
        }
        getSearchGoods(params).then(res => {
          if (res.errno === 0) {
            this.goods = res.data.goodsList
            this.total = res.data.count
            this.categoryList = res.data.filterCategoryList
            this.noResult = false
            if (this.total === 0) {
              this.noResult = true
            }
            this.error = false
          } else {
            this.error = true
          }
          this.loading = false
          this.searching = false
        })
      },
      // 默认排序
      reset () {
        this.sortType = 1
        this.sort = ''
        this.currentPage = 1
        this.loading = true
        this._getSearchGoods()
      },
      // 价格排序
      sortByPrice (v) {
        v === 1 ? this.sortType = 2 : this.sortType = 3
        this.sort = v
        this.currentPage = 1
        this.loading = true
        this._getSearchGoods()
      },
      searchByCategory (item) {
        this.categoryId = item.id
        this.categoryName = item.name
        this.loading = true
        this._getSearchGoods()
      }
    },
    created () {
    },
    mounted () {
      this.windowHeight = window.innerHeight
      this.windowWidth = window.innerWidth
      this.keyword = this.$route.query.keyword
      this._getSearchGoods()
      recommend().then(res => {
        let data = res.result
        this.recommendPanel = data[0]
      })
    },
    components: {
      mallGoods,
      YButton,
      YShelf,
      YHeader,
      YFooter
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../../assets/style/mixin";
  @import "../../assets/style/theme";

  .nav {
    height: 40px;
    line-height: 30px;
    padding: 6px 8px;

    .l-filter {
      display: flex;
      align-items: center;
      margin-right: 2px;
      a {
        border: 1px solid #5e7382;
        margin-right: -1px;
        padding: 0 15px;
        height: 25px;
        line-height: 25px;
        //@extend %block-center;
        font-size: 12px;
        color: #999;
        &.active {
          color: #FFFFFF;
          background: #c81623;
          border: 1px solid #c81623;
        }
        &:hover {
          border-bottom: 1px solid #c81623;
        }
      }
      input {
        @include wh(80px, 25px);
        border: 1px solid #ccc;
      }
      input + input {
        margin-left: 10px;
        height: 28px;
      }
    }
    .price-interval {
      padding: 0 15px;
      @extend %block-center;
      input[type=number] {
        border: 1px dashed #5683EA;
        text-align: center;
        background: none;
        /*border-radius: 5px;*/
      }

    }
  }

  .goods-box {
    > div {
      float: left;
      border: 1px solid #efefef;
    }
  }

  .nav-cateory{
    position: relative;
    z-index: 15;
    height: 60px;
    line-height: 60px;
    background: #f7f7f7;
    .title{
      float: left;
      padding-top: 15px;
      height: 60px;
      width: 100px;
      line-height: 30px;
      color: #666;
      /*text-align: center;*/
      padding-left: 10px;
      background: #f3f3f3;
      font-weight: bold;
    }
    .cate-item{
        float: left;
        width: 80px;
        margin-top: 15px;
        margin-right: 20px;
        height: 30px;
        line-height: 30px;
        border-radius: 2px;
        color: #23658f;
        text-align: center;
      &:hover{
        border: 1px dashed #d44d44;
      }
      &.active {
        border: 1px dashed #d44d44;
        border-bottom: 1px solid #d44d44;
        color: #d44d44;
      }
    }
  }
  .nav-subs {
    position: relative;
    /**margin-top: -40px; */
    z-index: 15;
    height: 40px;
    background: #f7f7f7;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .04);
    .nav-sub-wrappers {
      /*padding: 5px 0;*/
      height: 40px;
      position: relative;
    }
    .w {
      height: 40px;
      display: flex;
      justify-content: space-between;
    }
    .nav-lists {
      height: 28px;
      line-height: 28px;
      display: flex;
      align-items: center;
      height: 100%;
      li:first-child {
        padding-left: 0;
        a {
          padding-left: 10px;
        }
      }
      li {
        position: relative;
        float: left;
        padding-left: 2px;
        a {
          /*display: block;*/
          // cursor: default;
          padding: 0 10px;
          color: #666;
          &.active {
            font-weight: bold;
          }
        }
        a:hover {
          color: #d44d44;
        }
      }
      .result:after {
        content: ' >';
        position: absolute;
        right: -3px;
        width: 10px;
      }
      .keyword{
        font-weight: bold;
      }
    }
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

  .section {
    padding-top: 8vw;
    margin-bottom: -5vw;
    width: 1218px;
    align-self: center;
  }

  @media (min-width: 1px) {
    .nav-subs .nav-sub-wrappers:after {
      display: block;
    }
  }

  .recommend {
    display: flex;
    > div {
      flex: 1;
      width: 25%;
    }
  }

  .img-item{
    display: flex;
    flex-direction: column;
  }

  .el-pagination{
    align-self: flex-end;
    margin: 3vw 10vw 2vw;
  }


</style>
