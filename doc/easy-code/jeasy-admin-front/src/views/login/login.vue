<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form :loading="loading" :captcha="captcha" @on-success-valid="handleSubmit"
                      @on-refresh-img="refreshImg()"></login-form>
          <p class="login-tip">请输入已注册用户名和密码登录</p>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
  import LoginForm from '_c/login-form'
  import { mapActions, mapState, mapMutations } from 'vuex'
  import config from '@/config'

  export default {
    name: 'Login',
    components: {
      LoginForm
    },
    computed: mapState([
      'loginStore'
    ]),
    data () {
      return {
        loading: false,
        captcha: (process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro) + 'captcha.jpg?t=' + new Date().getTime()
      }
    },
    methods: {
      ...mapActions([
        'login'
      ]),
      ...mapMutations([
        'setToken',
        'setAvatar',
        'setUserName',
        'setAccess',
        'setHasGetInfo'
      ]),
      refreshImg () {
        this.captcha = this.captcha.substring(0, this.captcha.indexOf('?t=') + 3) + new Date().getTime()
      },
      handleSubmit (formValidate) {
        this.loading = true
        this.login({
          data: formValidate
        }).then(() => {
          this.loading = false
          this.setToken(this.loginStore.user.token)
          this.setAvatar(this.loginStore.user.avatar)
          this.setUserName(this.loginStore.user.name)
          this.setAccess(this.loginStore.user.access)
          this.setHasGetInfo(true)
          this.$router.push({
            name: 'home'
          })
        }, (res) => {
          this.loading = false
          this.refreshImg()
          this.$Message.error(res.data.message)
        })
      }
    }
  }
</script>

<style>

</style>
