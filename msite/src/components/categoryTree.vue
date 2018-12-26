<template>
  <div class="category-tab-content">
    <div class="nav-con">
      <ul class="normal-nav layui-clear"  @mouseleave="leaveMenu()">
        <li class="nav-item"  v-for="(item, i) in category" :key="i">
          <div class="title" @mouseover="setMenu(item.subCategory)">{{item.name}}
            <i class="layui-icon layui-icon-right"></i>
          </div>
        </li>
        <div class="sub_menu" :class="{'item-display': classdisplay}">
          <div class="left-menu">
            <a class="menu-item"  v-for="(iitem,j) in subCategory" :key="j">
              <img :src="iitem.iconUrl"/>
              <span @click="changGoods(iitem)" >{{iitem.name}}</span>
            </a>
          </div>
          <div class="right-menu">
            <div class="menu-ad">
              广告位招商8888888
            </div>
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
        subCategory: {},
        classdisplay: false
      }
    },
    methods: {
      setMenu (obj) {
        this.subCategory = obj
        this.classdisplay = true
      },
      leaveMenu () {
        this.classdisplay = false
      },
      changGoods (item) {
        this.$router.push({
          path: '/refreshgoods',
          query: {
            cid: item.id
          }
        })
       // window.open('//' + window.location.host + '/#/goods?cid=' + item.id)
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>

  .category-tab-content{
    position: absolute;
    .nav-con{
      width: 200px; height: 0;
      .normal-nav{
        z-index:20;
        height: 400px;
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
      .item-display{
        display: flex!important;
      }
      .sub_menu{
        float: left;
        width: 800px;
        height: 300px;
        left: 200px;
        margin-top: 5px;
        display: none;
        position: absolute;
        background: #ffffff;

        .left-menu{
          padding-top:10px;
          float: left;
          position: absolute;
          width: 600px;
          height: 300px;
          /*border-right: 1px solid #825854;*/
        }
        .menu-item{
          float: left;
          margin: 10px;
          width: 180px;
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
          right: 0px;
          width: 200px;
          height: 300px;
          .menu-ad{
            margin: 20px;
            padding-top: 20px;
            width: 160px;
            height: 150px;
            float: left;
            background: #b1bfea;
            text-align: center;
            line-height: 100px;
          }
        }
      }
    }
  }
</style>
