<template>

</template>
<script>
export default {
  data () {
    this.$router.push({
      path: '/search',
      query: {
        keyword: this.$route.query.keyword
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
