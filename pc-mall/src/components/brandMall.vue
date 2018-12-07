<template>
  <div class="brand-mall">
      <div class="brand-img">
        <a @click="openBrand(msg.id)">
        <img v-lazy="msg.picUrl" :alt="msg.name" :key="msg.picUrl">
        </a>
      </div>
      <div class="brand-content">
        <h6 class="brand-title">{{msg.name}} | {{msg.floorPrice}}元起</h6>
        <p class="sub-title">{{msg.desc}}</p>
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
      brandsDetails (id) {
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
    /**750 417**/
    width: 333px;
    height: 185px;
    position: relative;
    margin: 4px 2px;
    &:hover {
      transform: translateY(-1px);
      box-shadow: 1px 1px 20px #f8edf1;
      /*border-top-left-radius: 18px;*/
      /*border-top-right-radius: 18px;*/
      /*border-bottom-left-radius: 8px;*/
      /*border-bottom-right-radius: 8px;*/
      .good-button{
        display: flex;
      }
    }

    .good-button{
      @extend %block-center;
      display: none;
      padding-bottom: 17px;
      a{
        padding: 0 27px;
      }
      .detailbutton{
        &:hover{
          border: 1px solid #e30d0e;
        }
      }
    }
    .brand-img {
      position: absolute;
      left: 0;
      top: 0;
      z-index: 0;
      width: 333px;
      height: 185px;
      overflow: hidden;
      img {
        @include wh(333px,185px);
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
        /*vertical-align: middle;*/
        text-align: center;
        /*height: 63px;*/
        /*line-height: 63px;*/
        font-weight: 700;
        font-size: 20px;
        color: #ffffff;
        text-shadow: 1px 1px #1f2d3d;
        /*margin: 0 auto;*/
        /*padding: 10px 10px;*/
        overflow: hidden;
        margin-top: 50px;
      }
      .sub-title {
        text-align: center;
        line-height: 18px;
        font-size: 13px;
        color: #eeeeee;
        padding: 10px;

        /*height: auto;*/
        /*overflow : hidden;*/
        /*text-overflow: ellipsis;*/
        /*display: -webkit-box;*/
        /*-webkit-line-clamp: 2;*/
        /*-webkit-box-orient: vertical;*/

        &:hover{
          color: #1f2d3d;
          font-weight: bold;
        }

      }
    }

  }
</style>
