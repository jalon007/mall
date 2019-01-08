<template>
  <div class="slide" v-on:mouseover="stop()" v-on:mouseout="move()">

    <div class="slideshow">
      <button class="slider_control slider_control_prev" @click="prev()">
        <i class="iconfont"></i>
      </button>
      <transition-group tag="ul" name="image">
        <li v-for="(item, i) in goods" v-show="i===mark" :key="i">
          <a href="#">
            <img :src='item.picUrl'>
          </a>
          <div class="title">{{item.name}}</div>
          <div class="sub-title">{{item.desc}}</div>
        </li>
      </transition-group>
      <button class="slider_control slider_control_next" @click="next()">
        <i class="iconfont"></i>
      </button>
    </div>

    <div class="bullet">
      <span v-for="(item, i) in goods" :class="{ 'active':i===mark }"
            @mouseover="change(i)" :key="i"></span>
    </div>
  </div>
</template>

<script>
  export default {
    props: {
      goods: []
    },
    data () {
      return {
        timer: null, // 定时器
        mark: 0, // 比对图片索引的变量
        slideSize: this.goods.length - 1
      }
    },
    methods: {
      autoPlay () {
        if (this.mark === this.goods.length - 1) {
          this.mark = 0
        } else {
          this.mark++
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
      },
      prev () {
        if (this.mark === 0) {
          this.mark = this.slideSize
        } else {
          this.mark--
        }
      },
      next () {
        this.autoPlay()
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
    width: 360px;
    height: 320px;
    margin: 0 auto;
    margin-top: 50px;
    overflow: hidden;
    position: relative;
    .slideshow {
      width: 360px;
      height: 320px;
      li {
        position: absolute;
        img {
          width: 100%;
          height: 100%;
        }
        .title{
          text-align: left;
          font-size: 18px;
          text-shadow: 1px 1px #5e7382;
        }
        .sub-title{
          color: #c88386;
        }
      }
    }
    &:hover{
      .slider_control{
        display: flex;
      }
    }
    .slider_control{
      display: none;
      position: absolute;
      top: 40%;
      /*border-radius: 20px;*/
      width: 20px;
      height: 40px;
      line-height: 40px;
      background: rgba(85, 104, 122, 0.5);
      z-index: 2;
    }
    .slider_control_prev{
        left: 0px;
      .iconfont{
        color: hsla(0,0%,100%,.4);
        display: inline-block;
        border-left: 2px solid;
        border-bottom: 2px solid;
        width: 10px;
        height: 10px;
        margin-left: 6px;
        transform: rotate(45deg);
        &:hover{
          color: #fff;
        }
      }
    }
    .slider_control_next{
      right: 0px;
      .iconfont{
        color: hsla(0,0%,100%,.4);
        display: inline-block;
        border-left: 2px solid;
        border-bottom: 2px solid;
        width: 10px;
        height: 10px;
        margin-right: 6px;
        transform: rotate(-135deg);
        &:hover{
          color: #fff;
        }
      }
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

