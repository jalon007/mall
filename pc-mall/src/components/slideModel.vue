<template>
  <div class="slide" v-on:mouseover="stop()" v-on:mouseout="move()">
    <div class="slideshow">
      <transition-group tag="ul" name="image">
        <li v-for="(item, i) in goods" v-show="i===mark" :key="i">
          <a href="#">
            <img :src='item.picUrl'>
          </a>
          <div class="title">{{item.name}}</div>
          <div class="sub-title">{{item.desc}}</div>
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
        if (this.mark === 3) {
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
      li {
        position: absolute;
        img {
          width: 100%;
          height: 100%;
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

