<template>
  <div>
    <Modal v-model="showModal" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="showForm" :model="showForm" :label-width="80">
        <Row>
          <Col span="12">
          <Form-item label="名称" prop="name">
            <Input v-model="showForm.name" size="small" placeholder="请输入名称" readonly/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="编码" prop="code">
            <Input v-model="showForm.code" size="small" placeholder="请输入编码" readonly/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注" prop="remark">
            <Input v-model="showForm.remark" type="textarea" size="small" placeholder="请输入备注" readonly/>
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
      title: { type: String, default: '角色查看' },
      okText: { type: String, default: '确定' }
    },
    computed: mapState([
      'roleStore'
    ]),
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
          id: 0,
          name: '',
          code: '',
          remark: '',
          createAt: 0,
          createBy: 0,
          createName: '',
          updateAt: 0,
          updateBy: 0,
          updateName: '',
          isDel: 0,
          isTest: 0
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.showForm.id = this.id
          this.showModal = val
          this.showRole()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showRole () {
        this.$store.dispatch('showRole', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.roleStore.item
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
