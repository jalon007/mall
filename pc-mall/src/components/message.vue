<template>
  <div class="msg-box">
    <div class="carousel-messagebox">
      <transition-group tag="ul" enter-active-class="animated fadeInUp"
                        leave-active-class="animated fadeOutUp">
        <li v-show="idx == isCurrent" v-for="(item, idx) in messageList" :key="idx" :class="['carousel-message']">
          <span class="good">{{item.name}}</span>
          <span>开通了</span>
          <span class="good">{{item.prizeName}}</span>
        </li>
      </transition-group>
  </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        messageList: [
          {
            name: '电风**啦',
            prizeName: '腾讯超高级会员'
          },
          {
            name: '你***包',
            prizeName: '苹果高级会员'
          },
          {
            name: '一枫****书',
            prizeName: '百度高级会员'
          }
        ],
        isCurrent: 0
      }
    },
    methods: {
      autoPlay () {
        let length = this.messageList.length
        if (this.isCurrent >= length) {
          this.isCurrent = 0
          this.autoPlay()
        } else {
          setTimeout(() => {
            this.isCurrent++
            this.autoPlay()
          }, 1000)
        }
      }
    },
    mounted () {
      this.autoPlay()
    }
  }

</script>

<style   lang="scss" rel="stylesheet/scss"  scoped>
  .box{
    width: 300px;
    height: 200px;
    border: 1px dashed pink;
    margin: 0 auto;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .carousel-messagebox {
    width:260px;
    height: 300px;
  }
  .carousel-message {
    width:220px;
    height: 30px;
    line-height:30px;
    font-family: PingFangSC-Regular;
    font-size: 12px;
    background: url('/static/images/q32.png') 16px center no-repeat rgba(0,0,0,0.6);
    background-size:20px 16px;
    border-radius: 30px;
    padding-left:30px;
    position: absolute;
    color:#fff;
    display: -webkit-box;
    -webkit-box-pack: center;
    -webkit-box-align: center;
  }
  .good {
    color: #ffe795;
    width: 80px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: block;
    height: 30px;
    line-height: 30px;
    margin-left: 8px;
  }
</style>
