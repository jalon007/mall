<template>
  <div class="topic-mall">
    <div class="topic-scan">
      <a>{{msg.readCount}}</a><img src="/static/images/scan-red.png"/>
    </div>
    <a @click="openTopic(msg.id)">
      <div class="topic-img">
        <img v-lazy="msg.picUrl" :alt="msg.title" :key="msg.picUrl">
      </div>
      <div class="topic-content">
        <h6 class="topic-title">{{msg.title}}   <a>￥{{msg.price}}元起</a></h6>
        <p class="sub-title">{{msg.subtitle}}</p>
      </div>
    </a>
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
        this.$router.push({path: 'topicsDetails/id=' + id})
      },
      openTopic (id) {
        window.open('//' + window.location.host + '/#/topicsDetails?id=' + id)
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

  .topic-mall {
    display: block;
    width: 460px;
    height: 340px;
    position: relative;
    margin: 4px 22px;
    &:hover {
      transform: translateY(-1px);
      box-shadow: 1px 1px 20px #5e8d7e;
      /*border-bottom: 1px solid ;*/
      .topic-img{
        @include wh(458px,256px);
      }
    }
    .topic-scan{
      position: absolute;
      right: 2px;
      top: 10px;
      width: 80px;
      height: 32px;
      font-size: 18px;
      line-height: 32px;
      font-weight: bold;
      a{
        color: #827a7b;
        &:hover{
          color: #c81623;
        }
      }
      img{
        width: 24px;
        height: 16px;
      }
    }

    .topic-img {
      float: left;
      left: 0;
      top: 0;
      z-index: 0;
      width: 460px;
      height: 256px;
      overflow: hidden;
      img {
        @include wh(460px,256px);
      }
    }
    .topic-content {
        @include wh(460px,90px);
      left: 0;
      top: 0;
      display: flow-root;
      z-index: 0;
      .topic-title{
        text-align: left;

        font-weight: 400;
        font-size: 18px;
        color: #1f2d3d;
        /*text-shadow: 1px 1px #1f2d3d;*/
        overflow: hidden;
        margin-top: 10px;
        padding-left: 10px;
        a{
          padding-left: 20px;
          font-size: 13px;
          color: #824b4d;
          &:hover{
            color: #b51c27;
          }
        }
      }
      .sub-title {
        text-align: left;
        line-height: 18px;
        font-size: 13px;
        color: #747474;
        padding: 10px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow:ellipsis;
      }
    }

  }
</style>
