<template>

</template>
<script>
export default {
  data () {
    this.$router.push({
      path: '/brands',
      query: {
        cid: this.$route.query.cid
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
