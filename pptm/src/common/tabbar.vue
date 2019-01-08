<template>
  <div class="tabberWarp" >
    <div class="warp">
      <Item :txt='item.txt' :page='item.page' @change='getVal' v-for='item in tabbarDes':sel='selected'>

        <img :src="item.normalImg" slot='normalImg'>
        <img :src="item.activeImg" slot='activeImg'>
      </Item>
    </div>
  </div>
</template>
<script type="text/javascript">
  import Item from './Item.vue'
  export default{
    components: {
      Item
    },
    data () {
      return {
        selected: 'skin',
        tabbarDes: [
          {
            txt: '首页',
            page: '/',
            normalImg: require('../../static/images/home-f.png'),
            activeImg: require('../../static/images/home-o.png')
          },
          {
            txt: '分类',
            page: 'goods',
            normalImg: require('../../static/images/cate-f.png'),
            activeImg: require('../../static/images/cate-o.png')
          },
          {
            txt: '收藏',
            page: '/login',
            normalImg: require('../../static/images/like-f.png'),
            activeImg: require('../../static/images/like-o.png')
          }
          /*
          ,
          {
            txt: '我的',
            page: '/my',
            normalImg: require('/static/imgbar/home-f.png'),
            activeImg: require('/static/imgbar/home-o.png')
          }
          */
        ]
      }
    },
    methods: {
      getVal (res) {
        this.selected = res
      }
    }
  }
</script>
<style type="text/css">
  .warp{
    width: 100%;
    border-top: 1px solid #eee;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-around;
    font-size: 0;
  }
  .tabberWarp img{
    margin-top: 10px;
    margin-bottom: 5px;
    width: 100px;
    height: 100px;

  }
  .tabberWarp{
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    padding-bottom: 5px;
    background: #fff;
  }
</style>
