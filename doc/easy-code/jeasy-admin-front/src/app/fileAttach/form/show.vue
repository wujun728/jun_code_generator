<template>
  <div>
    <Modal v-model="showModal" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="showForm" :model="showForm" :label-width="60">
        <Row>
          <Col span="24">
          <Form-item label="主键" prop="id">
            <Input v-model="showForm.id" size="small" placeholder="请输入主键"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="表名称" prop="tableName">
            <Input v-model="showForm.tableName" size="small" placeholder="请输入表名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="记录ID" prop="recordId">
            <Input v-model="showForm.recordId" size="small" placeholder="请输入记录ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件原名称" prop="name">
            <Input v-model="showForm.name" size="small" placeholder="请输入文件原名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件URL" prop="url">
            <Input v-model="showForm.url" size="small" placeholder="请输入文件URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件图标URL" prop="iconUrl">
            <Input v-model="showForm.iconUrl" size="small" placeholder="请输入文件图标URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="文件预览URL" prop="previewUrl">
            <Input v-model="showForm.previewUrl" size="small" placeholder="请输入文件预览URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建时间" prop="createAt">
            <Input v-model="showForm.createAt" size="small" placeholder="请输入创建时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人ID" prop="createBy">
            <Input v-model="showForm.createBy" size="small" placeholder="请输入创建人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人名称" prop="createName">
            <Input v-model="showForm.createName" size="small" placeholder="请输入创建人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新时间" prop="updateAt">
            <Input v-model="showForm.updateAt" size="small" placeholder="请输入更新时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人ID" prop="updateBy">
            <Input v-model="showForm.updateBy" size="small" placeholder="请输入更新人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人名称" prop="updateName">
            <Input v-model="showForm.updateName" size="small" placeholder="请输入更新人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否删除" prop="isDel">
            <Input v-model="showForm.isDel" size="small" placeholder="请输入是否删除"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否测试" prop="isTest">
            <Input v-model="showForm.isTest" size="small" placeholder="请输入是否测试"/>
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
      id: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '文件附件查看'},
      okText: {type: String, default: '确定'}
    },
    computed: mapState([
      'fileAttachStore'
    ]),
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
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
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.showForm.id = this.id
          this.showModal = val
          this.showFileAttach()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showFileAttach () {
        this.$store.dispatch('showFileAttach', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.fileAttachStore.item
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
