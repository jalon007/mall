<template>
  <div class="gray-box">
    <div class="title">
      <h2>{{title}}</h2>
      <div class="right">
        <ul class="nav-list2">
          <li v-for="(item,i) in child" :key="i">
            <a @click="changGoods(i, item)" :class="{active:i===choosePage}">{{item.name}}</a>
          </li>
          <li>
            <a @click="changGoods(-2)" :class="{active:choosePage===-2}">更多</a>
          </li>
        </ul>
      </div>
    </div>
    <!--内容-->
    <div>
      <slot name="content"></slot>
    </div>
  </div>
</template>
<script>
  export default {
    props: [
      'title',
      'child'
    ]
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .gray-box {
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
      height: 60px;
      border-bottom: 1px solid #d4d4d4;
      border-radius: 8px 8px 0 0;
      box-shadow: rgba(0, 0, 0, .06) 0 1px 7px;
      background: #f3f3f3;
      background: -webkit-linear-gradient(#fbfbfb, #ececec);
      background: linear-gradient(#fbfbfb, #ececec);
      line-height: 60px;
      color: #333;
      display: flex;
      justify-content: space-between;
      align-items: center;
      h2 {
        font-size: 18px;
        font-weight: bold;
        /*color: #626262;*/
        display: inline-block;
        &:before{
          content: '';
          position: absolute;
          width: 4px;
          height: 24px;
          top: 18px;
          left: 15px;
          background-repeat: repeat-x;
          /*background-image: -webkit-linear-gradient(45deg, #0087ff, #3bc7ff);*/
          /*background-image: -o-linear-gradient(45deg, #0087ff, #3bc7ff);*/
          /*background-image: linear-gradient(45deg, #0087ff, #3bc7ff);*/
          background: #28ABFF;
        }

      }
      .right{
        width: 1000px;
      }
      .nav-list2 {
        float: right;
        height: 16px;
        line-height: 20px;
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
          float: right;
          padding-left: 2px;
          a {
            display: block;
            padding: 0 10px;
            color: #666;
            &.active {
              font-weight: bold;
            }
          }
          a:hover {
            color: #c80016;
          }
        }
        li:after {
          content: ' ';
          position: absolute;
          left: 0;
          top: 13px;
          width: 2px;
          height: 2px;
          background: #bdbdbd;
        }
      }
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
