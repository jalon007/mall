<template>
  <div class="notion-box" :class="getColor(index)">
    <div class="title">
      <h2>{{title}}</h2>
    </div>
    <div class="notion-content">
      <ul class="notion-img" v-for="(item,i) in notions" v-if="i<index+1" :key="i">
        <a @click="openProduct(item.id)">
          <img class="notion-image" v-lazy="item.picUrl" :alt="item.name" :key="item.picUrl">
        </a>
      </ul>
    </div>
    <div class="more">more>></div>
  </div>
</template>
<script>
  export default {
    props: [
      'title',
      'notions',
      'index'
    ],
    methods: {
      getColor (i) {
        return 'color' + parseInt(i % 6)
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/mixin";
  .notion-box {

    position: relative;
    margin-bottom: 10px;
    overflow: hidden;
    background: #fff;
    border-radius: 8px;
    border-bottom: 1px solid #dcdcdc;
    border-color: rgba(0, 0, 0, .14);
    box-shadow: 0 3px 8px -6px rgba(85, 85, 85, 0.24);
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
          top: 12px;
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
      float: right;
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
        color: #0d6586;
        &:before{
          background: #0d6586;
        }
      }
    }
    .more{
      border-bottom: 2px solid #0d6586;
    }
  }
  .color1{
    .title{
      h2{
        color: #1ac859;
        &:before{
          background: #1ac859;
        }
      }
    }
    .more{
      border-bottom: 2px solid #1ac859;
    }
  }
  .color2{
    .title{
      h2{
        color: #b3623b;
        &:before{
          background: #b3623b;
        }
      }
    }
    .more{
      border-bottom: 2px solid #b3623b;
    }
  }
  .color3{
    .title{
      h2{
        color: #c84680;
        &:before{
          background: #c84680;
        }
      }
    }
    .more{
      border-bottom: 2px solid #c84680;
    }
  }
</style>
