<template>
  <div>
    <Modal v-model="addModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="12">
          <Form-item label="名称" prop="name">
            <Input v-model="addForm.name" size="small" placeholder="请输入名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="编码" prop="code">
            <Input v-model="addForm.code" size="small" placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注" prop="remark">
            <Input v-model="addForm.remark" type="textarea" size="small" placeholder="请输入备注"/>
          </Form-item>
          </Col>
        </Row>
      </Form>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="addLoading" @click="handleOk">
          <span v-if="!addLoading">{{okText}}</span>
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
    name: 'addModal',
    props: {
      modal: { type: Boolean, default: false },
      title: { type: String, default: '角色新增' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      'roleStore'
    ]),
    data () {
      return {
        addModal: false,
        addLoading: false,
        addForm: {
          name: '',
          code: '',
          remark: ''
        },
        ruleValidate: {
          name: [
            { required: true, message: '角色名称不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '角色名称不能多于10个字符', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '角色编码不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '角色编码不能多于10个字符', trigger: 'blur' }
          ]
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val) {
          // 对话框显示时执行
          this.addModal = val
        }
      },
      addModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', val)
        }
      }
    },
    methods: {
      handleOk () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$store.dispatch('addRole', {
              data: this.addForm
            }).then(() => {
              this.$Message.success(this.title + '成功！')
              this.handleCancel()
              this.$emit('on-refresh-page')
            }, (res) => {
              this.addLoading = false
              this.$Message.error(res.data.message)
            })
          }
        })
      },
      handleCancel () {
        this.resetFields()
        this.addModal = false
        this.addLoading = false
      },
      resetFields () {
        this.$refs.addForm.resetFields()
      }
    }
  }
</script>
