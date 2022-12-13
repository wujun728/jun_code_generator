<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="60">
      <#list table.columns as col>
        <Row>
          <Col span="24">
          <Form-item label="${col.comment}" prop="${col.camelName}">
            <Input v-model="modifyForm.${col.camelName}" size="small" placeholder="请输入${col.comment}"/>
          </Form-item>
          </Col>
        </Row>
      </#list>
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
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '${table.comment}编辑' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      '${table.camelName}Store'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
        <#list table.columns as col>
          <#if col_has_next>
            <#if col.javaType=="Long" || col.javaType=="Integer">
          ${col.camelName}: 0,
            <#else>
          ${col.camelName}: '',
            </#if>
          <#else>
            <#if col.javaType=="Long" || col.javaType=="Integer">
          ${col.camelName}: 0
            <#else>
          ${col.camelName}: ''
            </#if>
          </#if>
        </#list>
        },
        ruleValidate: {
          value: [
            { required: false, message: '数值不能为空', trigger: 'blur' },
            { type: 'string', message: '数值必须为数字', trigger: 'blur', validator: validate.validateInputNumber }
          ],
        <#list table.columns as col>
          <#if col_has_next>
          ${col.camelName}: [
            { required: true, message: '${col.comment}不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '${col.comment}不能多于10个字符', trigger: 'blur' }
          ],
          <#else>
          ${col.camelName}: [
            { required: true, message: '${col.comment}不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '${col.comment}不能多于10个字符', trigger: 'blur' }
          ]
          </#if>
        </#list>
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.show${table.className}()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      show${table.className} () {
        this.$store.dispatch('show${table.className}', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.${table.camelName}Store.item
        })
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch('modify${table.className}', {
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
