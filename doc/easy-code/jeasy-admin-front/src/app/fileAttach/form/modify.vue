<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="60">
        <Row>
          <Col span="24">
          <Form-item label="主键" prop="id">
            <Input v-model="modifyForm.id" size="small" placeholder="请输入主键"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="表名称" prop="tableName">
            <Input v-model="modifyForm.tableName" size="small" placeholder="请输入表名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="记录ID" prop="recordId">
            <Input v-model="modifyForm.recordId" size="small" placeholder="请输入记录ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件原名称" prop="name">
            <Input v-model="modifyForm.name" size="small" placeholder="请输入文件原名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件URL" prop="url">
            <Input v-model="modifyForm.url" size="small" placeholder="请输入文件URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件图标URL" prop="iconUrl">
            <Input v-model="modifyForm.iconUrl" size="small" placeholder="请输入文件图标URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件预览URL" prop="previewUrl">
            <Input v-model="modifyForm.previewUrl" size="small" placeholder="请输入文件预览URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建时间" prop="createAt">
            <Input v-model="modifyForm.createAt" size="small" placeholder="请输入创建时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人ID" prop="createBy">
            <Input v-model="modifyForm.createBy" size="small" placeholder="请输入创建人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人名称" prop="createName">
            <Input v-model="modifyForm.createName" size="small" placeholder="请输入创建人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新时间" prop="updateAt">
            <Input v-model="modifyForm.updateAt" size="small" placeholder="请输入更新时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人ID" prop="updateBy">
            <Input v-model="modifyForm.updateBy" size="small" placeholder="请输入更新人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人名称" prop="updateName">
            <Input v-model="modifyForm.updateName" size="small" placeholder="请输入更新人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否删除" prop="isDel">
            <Input v-model="modifyForm.isDel" size="small" placeholder="请输入是否删除"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否测试" prop="isTest">
            <Input v-model="modifyForm.isTest" size="small" placeholder="请输入是否测试"/>
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
      id: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '文件附件编辑'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'fileAttachStore'
    ]),
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
          id: 0,
          tableName: '',
          recordId: 0,
          name: '',
          url: '',
          iconUrl: '',
          previewUrl: '',
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
          value: [
            {required: false, message: '数值不能为空', trigger: 'blur'},
            {type: 'string', message: '数值必须为数字', trigger: 'blur', validator: validate.validateInputNumber}
          ],
          id: [
            {required: true, message: '主键不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '主键不能多于10个字符', trigger: 'blur'}
          ],
          tableName: [
            {required: true, message: '表名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '表名称不能多于10个字符', trigger: 'blur'}
          ],
          recordId: [
            {required: true, message: '记录ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '记录ID不能多于10个字符', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '文件原名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '文件原名称不能多于10个字符', trigger: 'blur'}
          ],
          url: [
            {required: true, message: '文件URL不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '文件URL不能多于10个字符', trigger: 'blur'}
          ],
          iconUrl: [
            {required: true, message: '文件图标URL不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '文件图标URL不能多于10个字符', trigger: 'blur'}
          ],
          previewUrl: [
            {required: true, message: '文件预览URL不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '文件预览URL不能多于10个字符', trigger: 'blur'}
          ],
          createAt: [
            {required: true, message: '创建时间不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建时间不能多于10个字符', trigger: 'blur'}
          ],
          createBy: [
            {required: true, message: '创建人ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建人ID不能多于10个字符', trigger: 'blur'}
          ],
          createName: [
            {required: true, message: '创建人名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建人名称不能多于10个字符', trigger: 'blur'}
          ],
          updateAt: [
            {required: true, message: '更新时间不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新时间不能多于10个字符', trigger: 'blur'}
          ],
          updateBy: [
            {required: true, message: '更新人ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新人ID不能多于10个字符', trigger: 'blur'}
          ],
          updateName: [
            {required: true, message: '更新人名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新人名称不能多于10个字符', trigger: 'blur'}
          ],
          isDel: [
            {required: true, message: '是否删除不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否删除不能多于10个字符', trigger: 'blur'}
          ],
          isTest: [
            {required: true, message: '是否测试不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否测试不能多于10个字符', trigger: 'blur'}
          ]
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.showFileAttach()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showFileAttach () {
        this.$store.dispatch('showFileAttach', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.fileAttachStore.item
        })
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch('modifyFileAttach', {
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
