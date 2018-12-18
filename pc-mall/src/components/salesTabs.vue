<template>
  <div class="gray-box">
    <div class="title">
      <h2>{{title}}</h2>
      <div class="right">
        <ul class="nav-list2">
          <li v-for="(item,i) in child" :key="i">
            <a @click="changeGoods(i,item)" :class="{active:i===checked}">{{item.name}}</a>
          </li>
          <li>
            <a @click="moreGoods(-2)" :class="{active:checked===-1}">更多</a>
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
    ],
    data () {
      return {
        checked: 0
      }
    },
    methods: {
      changeGoods (i, item) {
        this.checked = i
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .gray-box {
    position: relative;
    margin-bottom: 30px;
    overflow: hidden;
    background: #fff;
    border-radius: 3px;
    /*border: 1px solid #dcdcdc;*/
    border-color: rgba(0, 0, 0, .14);
    box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    .title {
      padding-left: 30px;
      position: relative;
      z-index: 10;
      height: 45px;
      /*border-bottom: 1px solid #d4d4d4;*/
      border-radius: 2px 2px 0 0;
      /*box-shadow: rgba(0, 0, 0, .06) 0 1px 7px;*/
      /*background: #f3f3f3;*/
      /*background: -webkit-linear-gradient(#fbfbfb, #ececec);*/
      /*background: linear-gradient(#fbfbfb, #ececec);*/
      line-height: 45px;
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
          height: 20px;
          top: 14px;
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
        height: 100%;
      }
      .nav-list2 {
        float: right;
        /*height: 16px;*/
        /*line-height: 20px;*/
        display: flex;
        align-items: flex-end;
        height: 100%;
        li:first-child {
          padding-left: 0;
          a {
            padding-left: 10px;
          }
        }
        li {
          height: 30px;
          line-height: 30px;
          position: relative;
          float: right;
          padding-left: 5px;
          bottom: 0px;
          a {
            display: block;
            padding: 0 10px;
            color: #666;
            &.active {
              font-weight: bold;
            }
          }
        }
      }
    }
  }
  .color0{
    .title{
      border-bottom: 1px solid #c81623;
      h2{
        color: #c81623;
        &:before{
          background: #c81623;
        }
      }
      li {
          a.active{
          color: #ffffff;
          background: #c81623;
          }
      }
    }
  }
  .color1{
    .title{
      border-bottom: 1px solid #1756c8;
      h2{
        color: #1756c8;
        &:before{
          background: #1756c8;
        }
      }
      li {
        a{
          &.active{
            color: #ffffff;
            background: #1756c8;
          }
        }
      }
    }
  }
  .color2{
    .title{
      border-bottom: 1px solid #c81cc3;
      h2{
        color: #c81cc3;
        &:before{
          background: #c81cc3;
        }
      }
      li {
        a{
          &.active{
            color: #ffffff;
            background: #c81cc3;
          }
        }
      }
    }
  }
  .color3{
    .title{
      border-bottom: 1px solid #c87f2a;
      h2{
        color: #c87f2a;
        &:before{
          background: #c87f2a;
        }
      }
      li {
        a{
          &.active{
            color: #ffffff;
            background: #c87f2a;
          }
        }
      }
    }
  }
</style>
