<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="80">
        <Row>
          <Col span="12">
          <Form-item label="名称" prop="name">
            <Input v-model="modifyForm.name" size="small" placeholder="请输入名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="编码" prop="code">
            <Input v-model="modifyForm.code" size="small" placeholder="请输入编码"/>
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
      title: { type: String, default: '角色编辑' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      'roleStore'
    ]),
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
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
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.showRole()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showRole () {
        this.$store.dispatch('showRole', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.roleStore.item
        })
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch('modifyRole', {
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
