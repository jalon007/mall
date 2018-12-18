<template>
    <div class="messagebox">
      <transition-group tag="ul" enter-active-class="animated fadeInUp"
                        leave-active-class="animated fadeOutUp">
        <li v-show="idx == isCurrent" v-for="(item, idx) in messageList" :key="idx" :class="['carousel-message']">
          <span class="good_name">{{item.name}}</span>
          <span>成功领取</span>
          <span class="good_msg">{{item.prizeName}}</span>
        </li>
      </transition-group>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        messageList: [
          {
            name: '电风**啦',
            prizeName: '22.05元'
          },
          {
            name: '你***包',
            prizeName: '2.11元'
          },
          {
            name: '一枫****书',
            prizeName: '3.45'
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
  .carousel-message {
    width:200px;
    height: 25px;
    line-height:25px;
    font-family: PingFangSC-Regular;
    font-size: 10px;
    background: url('/static/images/q32.png')  10px center no-repeat rgba(0,0,0,0.6);
    background: url('/static/images/q32.png')  8px center no-repeat rgba(0,0,0,0.6);
    background-size:16px 16px;
    border-radius: 25px;
    padding-left:10px;
    position: absolute;
    color:#fff;
    display: -webkit-box;
    -webkit-box-pack: center;
    -webkit-box-align: center;
  }
  .good_name {
    color: #ffe795;
    /*width: 80px;*/
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: block;
    height: 25px;
    line-height: 25px;
    padding-left:12px;
    padding-right: 3px;
  }
  .good_msg {
    color: #ffe795;
    width: 80px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: block;
    height: 25px;
    line-height: 25px;
    padding-left: 3px;
  }
</style>
