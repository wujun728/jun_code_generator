<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="60">
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
          <Col span="12">
          <Form-item label="数值" prop="value">
            <Input v-model="modifyForm.value" type="text" size="small" placeholder="请输入数值"/>
          </Form-item>
          </Col>
          <Col span="12">
          <Form-item label="类型" prop="typeFull">
            <Select v-if="this.modifyForm.pid !== 0" v-model="modifyForm.typeFull" size="small" placeholder="请选择">
              <Option v-for="option in dictionaryStore.dictionaryTypes" :value="option.code + '-' + option.name" :key="option.code">
                {{ option.name }}
              </Option>
            </Select>
            <Select v-else v-model="modifyForm.typeFull" size="small" placeholder="请选择">
              <Option v-for="option in dictionaryStore.dictionaryTypes" :value="option.code + '-' + option.name" :key="option.code">
                {{ option.name }}
              </Option>
            </Select>
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
  import {mapState} from 'vuex'
  import validate from '@/utils/helpers/validate'

  export default {
    name: 'modifyModal',
    props: {
      id: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '字典编辑'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'dictionaryStore'
    ]),
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
          id: 0,
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
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.showDictionary()
          this.listDictionaryType()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showDictionary () {
        this.$store.dispatch('showDictionary', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.dictionaryStore.item
          this.modifyForm.typeFull = this.dictionaryStore.item.type + '-' + this.dictionaryStore.item.typeName
          this.modifyForm.value = String(this.modifyForm.value)
        })
      },
      listDictionaryType () {
        this.$store.dispatch('listDictionaryType')
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true

            let type = this.modifyForm.typeFull.split('-')
            this.modifyForm.type = type[0]
            this.modifyForm.typeName = type[1]

            this.$store.dispatch('modifyDictionary', {
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
