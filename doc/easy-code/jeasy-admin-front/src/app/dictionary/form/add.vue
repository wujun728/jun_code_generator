<template>
  <div>
    <Modal v-model="addModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="60">
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
          <Col span="12">
          <Form-item label="数值" prop="value">
            <Input v-model="addForm.value" size="small" placeholder="请输入数值"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="类型" prop="typeFull">
            <Select v-if="this.addForm.pid !== 0" v-model="addForm.typeFull" size="small" placeholder="请选择">
              <Option v-for="option in dictionaryStore.dictionaryTypes" :value="option.code + '-' + option.name"
                      :key="option.code">
                {{ option.name }}
              </Option>
            </Select>
            <Select v-else v-model="addForm.typeFull" size="small" placeholder="请选择">
              <Option v-for="option in dictionaryStore.dictionaryTypes" :value="option.code + '-' + option.name"
                      :key="option.code">
                {{ option.name }}
              </Option>
            </Select>
          </Form-item>
          </Col>
        </Row>
        <Row v-if="this.addForm.pid !== 0">
          <Col span="12">
          <Form-item label="父名称" prop="pname">
            <Input v-model="addForm.pname" size="small" disabled placeholder="请输入名称"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="父编码" prop="pcode">
            <Input v-model="addForm.pcode" size="small" disabled placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row v-if="this.addForm.pid !== 0">
          <Col span="12">
          <Form-item label="父数值" prop="pvalue">
            <Input v-model="addForm.pvalue" size="small" disabled placeholder="请输入数值"/>
          </Form-item>
          <Form-item prop="pid"></Form-item>
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
  import {mapState} from 'vuex'
  import validate from '@/utils/helpers/validate'

  export default {
    name: 'addModal',
    props: {
      parentId: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '字典新增'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'dictionaryStore'
    ]),
    data () {
      return {
        addModal: false,
        addLoading: false,
        addForm: {
          name: '',
          code: '',
          value: '',
          type: '',
          typeName: '',
          typeFull: '',
          pid: 0,
          pname: '',
          pcode: '',
          pvalue: ''
        },
        ruleValidate: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '名称不能多于10个字符', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '编码不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '编码不能多于10个字符', trigger: 'blur'}
          ],
          value: [
            {required: true, message: '数值不能为空', trigger: 'blur'},
            {type: 'string', message: '数值必须为数字', trigger: 'blur', validator: validate.validateInputNumber}
          ],
          typeFull: [
            {required: true, message: '请选择类型', trigger: 'change'}
          ]
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val) {
          this.addForm.pid = this.parentId
          this.addModal = val
          if (this.addForm.pid !== 0) {
            this.showDictionary()
          }
          this.listDictionaryType()
        }
      },
      addModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', val)
        }
      }
    },
    methods: {
      showDictionary () {
        this.$store.dispatch('showDictionary', {
          params: {
            id: this.addForm.pid
          }
        }).then(() => {
          this.addForm.pname = this.dictionaryStore.item.name
          this.addForm.pcode = this.dictionaryStore.item.code
          this.addForm.pvalue = this.dictionaryStore.item.value
          this.addForm.type = this.dictionaryStore.item.type
          this.addForm.typeName = this.dictionaryStore.item.typeName
          this.addForm.typeFull = this.dictionaryStore.item.type + '-' + this.dictionaryStore.item.typeName
        })
      },
      listDictionaryType () {
        this.$store.dispatch('listDictionaryType')
      },
      handleOk () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.addLoading = true

            let typeFull = this.addForm.typeFull.split('-')
            this.addForm.type = typeFull[0]
            this.addForm.typeName = typeFull[1]

            this.$store.dispatch('addDictionary', {
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
