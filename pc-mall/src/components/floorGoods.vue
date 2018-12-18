<template>

  <div class="gray-box">
    <!--<div >-->
    <div class="title">
      <h2>{{title}}</h2>
      <div class="right">
        <ul class="nav-list2">
          <li v-for="(iitem,i) in subCategory" :key="i">
            <a @click="changeGoods(i,iitem.id)" :class="{active:i===checked}">{{iitem.name}}</a>
          </li>
          <li>
            <a @click="moreGoods(-2)" :class="{active:checked===-1}">更多</a>
          </li>
        </ul>
      </div>
    </div>
    <!--内容-->
    <div slot="content" class="floors" >
        <div class="imgbanner" v-for="(iitem,j) in Goods"  v-if="j<1" :key="j"  @click="linkTo(iitem)">
          <img v-lazy="iitem.picUrl">
          <h6 class="good-title" v-html="iitem.name">{{iitem.name}}</h6>
          <h3 class="sub-title ellipsis">{{iitem.brief}}</h3>
          <a class="cover-link"></a>
        </div>
        <small-goods :msg="iitem" v-for="(iitem,j) in Goods" v-if="j>0" :key="j+'key'"></small-goods>
      </div>
    </div>
  <!--</div>-->
</template>
<script>
  import smallGoods from '/components/smallGoods'
  import { getAllGoods } from '/api/goods.js'
  export default {
    props: [
      'subCategory',
      'subGoods',
      'title'
    ],
    data () {
      return {
        checked: 0,
        categoryId: '',
        Goods: this.subGoods
      }
    },
    methods: {
      getColor (i) {
        return 'color' + parseInt(i % 4)
      },
      changeGoods (i, cid) {
        this.checked = i
        this._getAllGoods(cid)
      },
      searchByCategory (cid) {
        this.categoryId = cid
        this.loading = true
        this._getAllGoods()
      },
      _getAllGoods (cid) {
        let params = {
          params: {
            categoryId: cid
          }
        }
        getAllGoods(params).then(res => {
          if (res.errno === 0) {
            this.total = res.data.count
            this.Goods = res.data.goodsList
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
      }
    },
    components: {
      smallGoods
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
    border-color: rgba(0, 0, 0, .14);
    box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
    /*>div{*/
      /*margin-top: 20px;*/
    /*}*/
    .title {
      padding-left: 30px;
      position: relative;
      z-index: 10;
      height: 45px;
      border-radius: 2px 2px 0 0;
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

    .floors {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      .imgbanner {
        width: 218px;
        height: 270px;
        .cover-link {
          cursor: pointer;
          display: block;
          position: absolute;
          top: 60px;
          left: 0;
          width: 50%;
          height: 430px;
          z-index: 4;
          background: url(data:image/gif;base64,R0lGODlhAQABAIAAAP///////yH5BAEHAAEALAAAAAABAAEAAAICTAEAOw==) repeat;
        }
        &:hover {
          transform: translateY(-2px);
          /*box-shadow: 1px 1px 20px #999;*/
          border-radius: 18px;
        }
        .cover-link:hover {
          box-shadow: inset 0 0 38px rgba(0,0,0,.08);
          transition: all .15s ease;
        }
      }
      img {
        display: block;
        width: 100%;
        height: 70%;
      }
    }
    .good-title {
      line-height: 1.2;
      font-size: 16px;
      color: #424242;
      margin: 0 auto;
      padding: 0 14px;
      text-align: center;
      overflow: hidden;

    }
    .sub-title {
      text-align: center;
      line-height: 1.2;
      font-size: 12px;
      color: #d0d0d0;
      padding: 10px;
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
