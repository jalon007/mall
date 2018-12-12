<template>
  <div class="category-tab-content">
    <div class="nav-con">
      <ul class="normal-nav layui-clear">
        <li class="nav-item"  v-for="(item, i) in category" :key="i">
          <div class="title" @mouseover="setMenu(item.subCategory)">{{item.name}}
            <i class="layui-icon layui-icon-right"></i>
          </div>
        </li>
        <div class="sub_menu">
          <div class="left-menu">
            <a class="menu-item" v-for="(iitem,j) in subCategory" :key="j">
              <img :src="iitem.iconUrl"/>
              <span>{{iitem.name}}</span>
            </a>
          </div>
          <div class="right-menu">

          </div>
        </div>
      </ul>
    </div>
  </div>
</template>
<script>
  export default {
    props: [
      'category'
    ],
    data () {
      return {
        subCategory: {}
      }
    },
    methods: {
      setMenu (obj) {
        this.subCategory = obj
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>

  .category-tab-content{
    position: absolute;
    .nav-con{
      width: 220px; height: 0;
      .normal-nav{
        z-index:100;
        height: 500px;
        padding: 1px 0px;
        box-sizing: border-box;
        position: relative;
        background: #424242;
        box-sizing: border-box;
      }
      .nav-item{
        float: left;
        height: 42px;
        width: 100%;
        padding-left: 10px;
        position: relative;
        &:hover{
          background: #c81623;
          .sub_menu{
            display: flex;
          }
        }
        .title{
          font-size: 14px;
          color: #fff;
          height: 42px;
          line-height: 42px;
          cursor: pointer;
          text-align: -webkit-match-parent;
          display: block;
          padding-left: 40px;
          .layui-icon-right:after {
            content: url("/static/images/rigit-icon.png");
          }
        }
        p a{
          font-size: 12px;
          color: #fff;
          margin-left: 4px;
          line-height: 18px;
          &:hover{
            font-weight: bold
          }
        }
        i{
          position: absolute; right: 20px; top: 6px; font-size: 12px;
        }

      }

      .sub_menu{
        float: left;
        width: 800px;
        height: 300px;
        left: 220px;
        margin-top: 5px;
        display: table;
        position: absolute;
        background: #F7F7F7;

        .left-menu{
          top:10px;
          float: left;
          position: absolute;
          width: 600px;
          height: 300px;
          border-right: 1px solid #825854;
        }
        .menu-item{
          float: left;
          margin: 10px;
          width: 170px;
          height: 50px;
          display: flex;
          span{
            line-height: 50px;
            font-weight: bold;
            color: #82543a;
            font-size: 18px;
            text-align: center;
            padding-left: 20px;
          }
        }
        .right-menu{
          position: absolute;

          width: 100px;
          height: 300px;
        }
      }
    }
  }
</style>
