<template>

</template>
<script>
export default {
  data () {
    this.$router.push({
      path: '/goods',
      query: {
        cid: this.$route.query.cid,
        pId: this.$route.query.pId
      }
    })
    return {
    }
  },
  methods: {
  }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>

</style>
