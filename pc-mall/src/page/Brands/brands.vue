<template>
  <div class="brands w">
    <div class="brands-center" v-loading="loading" element-loading-text="加载中..." style="min-height: 35vw;">
      <div class="brand-left">
        <notion :title="notions[0]" :notions="brands" :index="0"></notion>
        <notion :title="notions[1]" :notions="brands" :index="1"></notion>
        <notion :title="notions[2]" :notions="brands" :index="2"></notion>
      </div>
      <div class="brand-item" v-if="!noResult">
        <!--商品-->
        <div class="brands-box">
          <brand-mall v-for="(item,i) in brands" :key="i" :msg="item"></brand-mall>
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
      <div class="no-info" v-if="noResult">
        <div class="no-data">
          <img src="/static/images/no-search.png">
          <br> 抱歉！暂时还没有商品
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
              <brand-mall :msg="item" v-for="(item,i) in recommendPanel.panelContents" :key="i"></brand-mall>
            </div>
          </y-shelf>
        </section>
      </div>
    </div>
  </div>
</template>
<script>
  import { getBrandList } from '/api/goods.js'
  import { recommend } from '/api/index.js'
  import brandMall from '/components/brandMall'
  import notion from '/components/notion'
  import YButton from '/components/YButton'
  import YShelf from '/components/shelf'
  export default {
    data () {
      return {
        notions: ['top', 'hot', 'recommend'],
        brands: [],
        noResult: false,
        error: false,
        min: '',
        max: '',
        loading: true,
        timer: null,
        sortType: 1,
        windowHeight: null,
        windowWidth: null,
        recommendPanel: [],
        sort: '',
        currentPage: 1,
        total: 0,
        pageSize: 21
      }
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this._getBrandList()
        this.loading = true
      },
      handleCurrentChange (val) {
        this.currentPage = val
        this._getBrandList()
        this.loading = true
      },
      _getBrandList () {
        let params = {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        }
        getBrandList(params).then(res => {
          if (res.errno === 0) {
            this.total = res.data.count
            this.brands = res.data.brandList

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
      // 默认排序
      reset () {
        this.sortType = 1
        this.sort = ''
        this.currentPage = 1
        this.loading = true
        this._getBrandList()
      },
      // 价格排序
      sortByPrice (v) {
        v === 1 ? this.sortType = 2 : this.sortType = 3
        this.sort = v
        this.currentPage = 1
        this.loading = true
        this._getBrandList()
      }
    },
    watch: {
      $route (to, from) {
        if (to.fullPath.indexOf('/goods?cid=') >= 0) {
          this.cId = to.query.cid
          this._getBrandList()
        }
      }
    },
    created () {
    },
    mounted () {
      this.windowHeight = window.innerHeight
      this.windowWidth = window.innerWidth
      this._getBrandList()
      recommend().then(res => {
        let data = res.result
        this.recommendPanel = data[0]
      })
    },
    components: {
      brandMall,
      YButton,
      YShelf,
      notion
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../../assets/style/mixin";
  @import "../../assets/style/theme";
  .brands{

    .brands-center{
      width: 1220px;
      margin: 0 auto;
      overflow: hidden;
      .brand-left{
        float: left;
        width: 210px;
        height: 200px;

      }
      .brand-item{
        float: left;
        position: relative;
        display: flex;
        flex-direction: column;
        width: 1000px;
        left: 10px;
        .brands-box {
          width: 1011px;
          > div {
            float: left;
            border: 1px solid #efefef;
          }
        }
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

  .el-pagination{
    align-self: flex-end;
    margin: 1vw 1vw 1vw;
  }

  .section {
    padding-top: 8vw;
    margin-bottom: -5vw;
    width: 1218px;
    align-self: center;
  }

  .recommend {
    display: flex;
    > div {
      flex: 1;
      width: 25%;
    }
  }



</style>
