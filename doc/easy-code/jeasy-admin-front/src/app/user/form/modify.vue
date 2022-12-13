<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="12">
          <Form-item label="用户名称" prop="name">
            <Input v-model="modifyForm.name" size="small" placeholder="请输入用户名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="手机号码" prop="mobile">
            <Input v-model="modifyForm.mobile" size="small" placeholder="请输入手机号码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
          <Form-item label="登录名称" prop="loginName">
            <Input v-model="modifyForm.loginName" size="small" placeholder="请输入登录名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="登录密码" prop="pwd">
            <Input v-model="modifyForm.pwd" size="small" placeholder="请输入登录密码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
          <Form-item label="用户状态" prop="statusCode">
            <Select size="small" placeholder="请选择" v-model="modifyForm.statusCode">
              <Option v-for="option in dictionaryStore.dictionarys" :value="option.code" :key="option.code">
                {{ option.name }}
              </Option>
            </Select>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="加密盐" prop="salt">
            <Input v-model="modifyForm.salt" size="small" placeholder="请输入加密盐"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注" prop="remark">
            <Input v-model="modifyForm.remark" type="textarea" size="small" placeholder="请输入备注"/>
          </Form-item>
          </Col>
        </Row>
      </Form>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="modifyLoading" @click="handleOk">
          <span v-if="!modifyLoading">{{okText}}</span>
          <span v-else>{{okText}}</span>
        </Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import { mapState } from 'vuex'
  import validate from '@/utils/helpers/validate'

  export default {
    name: 'modifyModal',
    props: {
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '用户编辑' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      'dictionaryStore',
      'userStore'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
          id: 0,
          name: '',
          loginName: '',
          pwd: '',
          salt: '',
          mobile: '',
          statusCode: '',
          remark: ''
        },
        ruleValidate: {
          name: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' },
            { type: 'string', max: 18, message: '用户名称不能多于18个字符', trigger: 'blur' }
          ],
          loginName: [
            { required: true, message: '登录名称不能为空', trigger: 'blur' },
            { type: 'string', max: 18, message: '登录名称不能多于18个字符', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' },
            { type: 'string', max: 11, message: '手机号码不能多于11个字符', trigger: 'blur' }
          ],
          statusCode: [
            { required: true, message: '请选择用户状态', trigger: 'change' }
          ]
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.initUserStatus()
          this.showUser()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      initUserStatus () {
        this.$store.dispatch('listDictionary', {
          params: {
            type: 'YHZT'
          }
        })
      },
      showUser () {
        this.$store.dispatch('showUser', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.userStore.item
        })
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch('modifyUser', {
              data: this.modifyForm
            }).then(() => {
              this.handleCancel()
              this.$Message.success(this.title + '成功！')
              this.$emit('on-refresh-page')
            }, (res) => {
              this.modifyLoading = false
              this.$Message.error(res.data.message)
            })
          }
        })
      },
      handleCancel () {
        this.resetFields()
        this.modifyModal = false
        this.modifyLoading = false
      },
      resetFields () {
        this.$refs.modifyForm.resetFields()
      }
    }
  }
</script>
