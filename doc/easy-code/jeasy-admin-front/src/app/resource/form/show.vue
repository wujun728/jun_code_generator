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
          <Form-item label="名称" prop="name">
            <Input v-model="showForm.name" size="small" placeholder="请输入名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="编码" prop="code">
            <Input v-model="showForm.code" size="small" placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="URL" prop="url">
            <Input v-model="showForm.url" size="small" placeholder="请输入URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="图标" prop="icon">
            <Input v-model="showForm.icon" size="small" placeholder="请输入图标"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注/描述" prop="remark">
            <Input v-model="showForm.remark" size="small" placeholder="请输入备注/描述"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="父ID" prop="pid">
            <Input v-model="showForm.pid" size="small" placeholder="请输入父ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="排序" prop="sort">
            <Input v-model="showForm.sort" size="small" placeholder="请输入排序"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否菜单:0=否,1=是" prop="isMenu">
            <Input v-model="showForm.isMenu" size="small" placeholder="请输入是否菜单:0=否,1=是"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否叶子节点:0=否,1=是" prop="isLeaf">
            <Input v-model="showForm.isLeaf" size="small" placeholder="请输入是否叶子节点:0=否,1=是"/>
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
      title: {type: String, default: '菜单查看'},
      okText: {type: String, default: '确定'}
    },
    computed: mapState([
      'resourceStore'
    ]),
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
          id: 0,
          name: '',
          code: '',
          url: '',
          icon: '',
          remark: '',
          pid: 0,
          sort: 0,
          isMenu: 0,
          isLeaf: 0,
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
          this.showResource()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showResource () {
        this.$store.dispatch('showResource', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.resourceStore.item
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
