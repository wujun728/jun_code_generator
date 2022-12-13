<template>
  <div>
    <Modal v-model="addModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="80">
      <#list table.columns as col>
        <Row>
          <Col span="24">
          <Form-item label="${col.comment}" prop="${col.camelName}">
            <Input v-model="addForm.${col.camelName}" size="small" placeholder="请输入${col.comment}"/>
          </Form-item>
          </Col>
        </Row>
      </#list>
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
      modal: { type: Boolean, default: false },
      title: { type: String, default: '${table.comment}新增' },
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
        addModal: false,
        addLoading: false,
        addForm: {
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
            { required: true, message: '${col.comment}不能为空', trigger: 'blur'},
            { type: 'string', max: 10, message: '${col.comment}不能多于10个字符', trigger: 'blur' }
          ]
          </#if>
        </#list>
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
            this.$store.dispatch('add${table.className}', {
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
