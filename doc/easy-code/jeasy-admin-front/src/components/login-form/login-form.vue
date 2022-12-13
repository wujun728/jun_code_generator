<template>
  <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" @keydown.enter.native="handleSubmit">
    <FormItem prop="username">
      <Input v-model="formValidate.username" placeholder="请输入账号">
      <span slot="prepend">
        <Icon :size="16" type="ios-person"></Icon>
      </span>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input type="password" v-model="formValidate.password" placeholder="请输入密码">
      <span slot="prepend">
        <Icon :size="14" type="md-lock"></Icon>
      </span>
      </Input>
    </FormItem>
    <Form-item prop="captcha">
      <Row>
        <Col span="16">
        <Input v-model="formValidate.captcha" placeholder="验证码" :maxlength="4" style="width: 175px">
        <span slot="prepend">
          <Icon :size="14" type="md-key"></Icon>
        </span>
        </Input>
        </Col>
        <Col span="8">
          <img class="captcha" :src="captcha" @click="refreshImg()">
        </Col>
      </Row>
    </Form-item>
    <Form-item lable="记住我" prop="rememberMe">
      <Checkbox v-model="formValidate.rememberMe" true-value="1" false-value="0">记住我</Checkbox>
    </Form-item>
    <FormItem>
      <Button @click="handleSubmit" type="primary" long :loading="loading">登录</Button>
    </FormItem>
  </Form>
</template>
<script>
  export default {
    name: 'LoginForm',
    props: {
      loading: false,
      captcha: '',
      usernameRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '账号不能为空', trigger: 'blur'}
          ]
        }
      },
      passwordRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '密码不能为空', trigger: 'blur'}
          ]
        }
      },
      captchaRules: {
        type: Array,
        default: () => {
          return [
            {required: true, message: '验证码不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    data () {
      return {
        formValidate: {
          username: '',
          password: '',
          captcha: '',
          rememberMe: '0'
        }
      }
    },
    computed: {
      ruleValidate () {
        return {
          username: this.usernameRules,
          password: this.passwordRules,
          captcha: this.captchaRules
        }
      }
    },
    methods: {
      refreshImg () {
        this.$emit('on-refresh-img')
      },
      handleSubmit () {
        this.$refs.formValidate.validate((valid) => {
          if (valid) {
            this.$emit('on-success-valid', this.formValidate)
          }
        })
      }
    }
  }
</script>
