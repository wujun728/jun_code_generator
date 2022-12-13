<template>
  <div>
    <Modal v-model="showModal" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="showForm" :model="showForm" :label-width="60">
        <Row>
          <Col span="12">
          <Form-item label="名称" prop="name">
            <Input v-model="showForm.name" size="small" readonly placeholder="请输入名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="编码" prop="code">
            <Input v-model="showForm.code" size="small" readonly placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
          <Form-item label="数值" prop="value">
            <Input v-model="showForm.value" type="text" readonly size="small" placeholder="请输入数值"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="类型" prop="typeName">
            <Input v-model="showForm.typeName" type="text" readonly size="small" placeholder="请输入类型"/>
          </Form-item>
          </Col>
        </Row>
        <Row v-if="this.showForm.pid !== 0">
          <Col span="12">
          <Form-item label="父名称" prop="pname">
            <Input v-model="showForm.pname" size="small" readonly placeholder="请输入名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="父编码" prop="pcode">
            <Input v-model="showForm.pcode" size="small" readonly placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row v-if="this.showForm.pid !== 0">
          <Col span="12">
          <Form-item label="父数值" prop="pvalue">
            <Input v-model="showForm.pvalue" size="small" readonly placeholder="请输入数值"/>
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
  import {mapState} from 'vuex'

  export default {
    name: 'showModal',
    props: {
      id: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '字典查看'},
      okText: {type: String, default: '确定'}
    },
    computed: mapState([
      'dictionaryStore'
    ]),
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
          id: 0,
          name: '',
          code: '',
          value: '',
          type: '',
          typeName: '',
          pid: 0,
          pname: '',
          pcode: '',
          pvalue: ''
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.showForm.id = this.id
          this.showModal = val
          this.showDictionary()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showDictionary () {
        this.$store.dispatch('showDictionary', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.dictionaryStore.item
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
