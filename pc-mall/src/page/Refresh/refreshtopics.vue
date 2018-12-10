<template>

</template>
<script>
export default {
  data () {
    this.$router.push({
      path: '/topics',
      query: {
        cid: this.$route.query.id
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
