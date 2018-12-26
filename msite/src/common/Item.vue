<template>
  <div class="itemWarp flex_mid" @click='changePage'>
		<span v-show='!bol'>
			<slot name='normalImg'></slot>
		</span>
    <span v-show='bol'>
			<slot name='activeImg'></slot>
		</span>
    <span v-text='txt'></span>
  </div>
</template>
<script type="text/javascript">
  export default{
    name: 'Item',
    props: {
      txt: '',
      page: '',
      sel: ''
    },
    computed: {
      bol () {
        if (this.sel === this.page) {
          return true
        }
        return false
      }
    },
    methods: {
      changePage () {
        // 点击跳转对应的页面
        this.$router.push('/' + this.page)
        this.$emit('change', this.page)
      }
    }
  }
</script>
<style type="text/css">
  .itemWarp{
    flex-grow: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }
  .itemWarp span{
    font-size: 30px;
    font-weight: 400;
    margin-bottom: 20px;
  }

</style>
