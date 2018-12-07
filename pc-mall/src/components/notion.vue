<template>
  <div class="notion-box">
    <div class="title">
      <h2>{{title}} Top10</h2>
    </div>
    <div class="notion-content">
      <ul class="notion-img" v-for="(item,i) in notions" v-if="i<2" :key="i">
        <a @click="openProduct(item.id)">
          <img class="notion-image" v-lazy="item.picUrl" :alt="item.name" :key="item.picUrl">
        </a>
      </ul>
    </div>
    <div class="more"><h6>more>></h6></div>
  </div>
</template>
<script>
  export default {
    props: [
      'title',
      'notions'
    ]
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/mixin";
  .notion-box {

    position: relative;
    margin-bottom: 30px;
    overflow: hidden;
    background: #fff;
    border-radius: 8px;
    border: 1px solid #dcdcdc;
    border-color: rgba(0, 0, 0, .14);
    box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    .title {
      padding-left: 30px;
      position: relative;
      z-index: 10;
      height: 50px;
      border-bottom: 1px solid #d4d4d4;
      border-radius: 8px 8px 0 0;
      box-shadow: rgba(0, 0, 0, .06) 0 1px 7px;
      background: #f3f3f3;
      background: -webkit-linear-gradient(#fbfbfb, #ececec);
      background: linear-gradient(#fbfbfb, #ececec);
      line-height: 50px;
      color: #333;
      display: flex;
      justify-content: space-between;
      align-items: center;
      h2 {
        font-size: 18px;
        font-weight: bold;
        /*color: #626262;*/
        display: inline-block;
        color: #28ABFF;
        &:before{
          content: '';
          position: absolute;
          width: 4px;
          height: 24px;
          top: 18px;
          left: 15px;
          background-repeat: repeat-x;
          background: #28ABFF;
        }
      }
    }
    .notion-content{
      display: block;
      position: relative;
      .notion-img {
        width: 220px;
        float: left;
        z-index: 0;
        overflow: hidden;
        .notion-image {
          @include wh(220px,auto);
        }
      }
    }
    .more{
      display: flex;
      width: 100%;
      height: 30px;
      border-bottom: 2px solid #28ABFF;
      border-bottom-left-radius: 10px;
      border-bottom-right-radius: 10px;
      font-weight: bold;
      font-size: 16px;
      line-height: 30px;
      text-align: right;
    }
  }

  .color0{
    .title{
      h2{
        color: #c81623;
        &:before{
          background: #c81623;
        }
      }
    }
  }
  .color1{
    .title{
      h2{
        color: #1756c8;
        &:before{
          background: #1756c8;
        }
      }
    }
  }
  .color2{
    .title{
      h2{
        color: #c81cc3;
        &:before{
          background: #c81cc3;
        }
      }
    }
  }
  .color3{
    .title{
      h2{
        color: #c87f2a;
        &:before{
          background: #c87f2a;
        }
      }
    }
  }
</style>
