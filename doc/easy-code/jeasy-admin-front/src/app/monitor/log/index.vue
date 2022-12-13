<template>
  <div>
    <iframe ref="iframe" frameborder="0" width="100%" scrolling="auto" :src="url" @load="loaded"></iframe>
  </div>
</template>

<script>
  export default {
    name: 'log',
    data () {
      return {
        url: 'http://localhost:8080/log'
      }
    },
    methods: {
      loaded () {
        this.$refs.iframe.height = document.documentElement.clientHeight
      }
    }
  }
</script>
