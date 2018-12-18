<template>
  <div class="sales_tabs">
    <ul class="tabs">
      <li class="li-tab" v-for="(item,index) in tabsTitle">
        <a @click="toggleTabs(index)" :class="{active:index===nowIndex}">{{item}}</a>
      </li>
    </ul>
    <div class="divTab" v-show="nowIndex===0">
      <img class="wx_award"/>
    </div>
    <div class="divTab" v-show="nowIndex===1">
      <img class="ali_red"/>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        tabsTitle: ['打赏', '领红包'],
        nowIndex: 0
      }
    },
    methods: {
      toggleTabs (index) {
        this.nowIndex = index
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/theme";
  @import "../assets/style/mixin";
  .sales_tabs{
    width: 100%;
    height: 100%;
    .tabs{
      width: 100%;
      height: 20px;
      li:first-child {
        border-right: 1px solid #dfe0e1;
      }
      li {
        height: 100%;
        position: relative;
        display:inline-block;
        padding: 0 8px;
        font-size: 12px;
        line-height: 18px;
        a {
          display: block;
          color: #666;
          &.active {
            border-bottom: 2px solid #c81623 ;
          }
        }
      }

    }
    .divTab{
      margin-top: 5px;
      width: 200px;
      height: 200px;
      .ali_red{
        position: relative;
        margin: 0 auto;
        @include wh(100%);
        background: url("/static/images/ali_red.png");
        background-repeat: no-repeat;
        background-size: 200px;
      }
      .wx_award{
        position: relative;
        margin: 0 auto;
        @include wh(100%);
        background: url("/static/images/wx_award.png");
        background-repeat: no-repeat;
        background-size: 200px;
      }
    }
  }



</style>
