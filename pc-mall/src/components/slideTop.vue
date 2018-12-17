<template>
  <div class="slide" v-on:mouseover="stop()" v-on:mouseout="move()">
    <div class="slideshow">
      <transition-group tag="ul" name="image">
        <li v-for="(item, i) in goods" v-show="i===mark" :key="i">
          <div class="item" v-for="(iitem,j) in item" :key="j">
            <div class="img">
              <a href="#">
                <img :src='iitem.picUrl'>
              </a>
            </div>
            <div class="content">
              <div class="title"><a>{{(i*3+j+1)}} </a>{{iitem.name}}</div>
              <div class="sub-title">{{iitem.desc}}</div>
            </div>
          </div>
        </li>
      </transition-group>
    </div>
    <div class="bullet">
      <span v-for="(item, i) in goods" :class="{ 'active':i===mark }"
            @click="change(i)" :key="i"></span>
    </div>
  </div>
</template>

<script>
  export default {
    props: {
      goods: {}
    },
    data () {
      return {
        timer: null, // 定时器
        mark: 0 // 比对图片索引的变量
      }
    },
    methods: {
      autoPlay () {
        this.mark++
        if (this.mark === 2) {
          this.mark = 0
        }
      },
      play () {
        this.timer = setInterval(this.autoPlay, 2500)
      },
      change (i) {
        this.mark = i
      },
      stop () {
        clearInterval(this.timer)
      },
      move () {
        this.timer = setInterval(this.autoPlay, 2500)
      }
    },
    created () {
      this.play()
    }
  }
</script>

<style lang="scss" rel="stylesheet/scss"  scoped>

  .image-enter-active {
    transform: translateX(0);
    transition: all 1.5s ease;
  }
  .image-leave-active {
    transform: translateX(-100%);
    transition: all 1.5s ease;
  }
  .image-enter {
    transform: translateX(100%);
  }
  .image-leave {
    transform: translateX(0);
  }

  * {
    margin: 0;
    padding: 0;
    list-style: none;
  }
  .slide {
    width: 300px;
    height: 320px;
    margin: 0 auto;
    margin-top: 50px;
    overflow: hidden;
    position: relative;
    .slideshow {
      width: 100%;
      height: 100%;
      .item{
        width: 100%;
        height: 107px;
        img{
          float: left;
          position: relative;
          width: 100px;
          height: auto;
        }
        .content{
          float: left;
          width: 200px;
          height: 100%;
          .title{
            text-align: center;
          }
          .sub-title{
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
      ul{

      }
      /*li {*/
        /*position: absolute;*/
        /*img {*/
          /*width: 100%;*/
          /*height: 100%;*/
        /*}*/
      /*}*/
    }
  }
  .bullet {
    position: absolute;
    width: 100%;
    bottom: 10px;
    margin: 0 auto;
    z-index: 10;
    text-align: center;
  }
  .bullet span {
    width: 10px;
    height: 10px;
    border: 1px solid #5e7382;
    border-radius: 5px;
    background: white;
    display: inline-block;
    margin-right: 10px;
    &.active{
      border: 1px solid red;
      background: red;
      box-shadow: 0 3px 8px -6px #ff6266;
    }
  }
</style>

