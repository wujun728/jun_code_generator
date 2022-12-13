<template>
  <div>
    <Modal v-model="showModal" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="showForm" :model="showForm" :label-width="80">
        <Row>
          <Col span="12">
          <Form-item label="用户名称" prop="name">
            <Input v-model="showForm.name" size="small" readonly/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="手机号码" prop="mobile">
            <Input v-model="showForm.mobile" size="small" readonly/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
          <Form-item label="登录名称" prop="loginName">
            <Input v-model="showForm.loginName" size="small" readonly/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="用户状态" prop="statusName">
            <Input v-model="showForm.statusName" size="small" readonly/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
          <Form-item label="用户角色" prop="roleNames">
            <Input v-model="showForm.roleNames" size="small" readonly/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="用户机构" prop="orgNames">
            <Input v-model="showForm.orgNames" size="small" readonly/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注" prop="remark">
            <Input v-model="showForm.remark" size="small" type="textarea" readonly/>
          </Form-item>
          </Col>
        </Row>
      </Form>
      <div slot="footer">
        <!--<Button type="primary" size="small" @click="handleOk">{{okText}}</Button>-->
      </div>
    </Modal>
  </div>
</template>

<script>
  import { mapState } from 'vuex'

  export default {
    name: 'showModal',
    props: {
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '用户查看' },
      okText: { type: String, default: '确定' }
    },
    computed: mapState([
      'userStore'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
          id: 0,
          name: '',
          loginName: '',
          mobile: '',
          statusName: '',
          remark: '',
          roleNames: '',
          orgNames: ''
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.showForm.id = this.id
          this.showModal = val
          this.showUser()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      showUser () {
        this.$store.dispatch('showUser', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.userStore.item
        })
      },
      handleOk () {
        this.handleCancel()
      },
      handleCancel () {
        this.resetFields()
        this.showModal = false
        this.showLoading = false
      },
      resetFields () {
        this.$refs.showForm.resetFields()
      }
    }
  }
</script>
