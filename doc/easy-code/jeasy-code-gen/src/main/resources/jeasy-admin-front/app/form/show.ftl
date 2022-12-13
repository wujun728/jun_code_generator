<template>
  <div>
    <Modal v-model="showModal" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="showForm" :model="showForm" :label-width="80">
      <#list table.columns as col>
        <Row>
          <Col span="24">
          <Form-item label="${col.comment}" prop="${col.camelName}">
            <Input v-model="showForm.${col.camelName}" size="small" readonly/>
          </Form-item>
          </Col>
        </Row>
      </#list>
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
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '${table.comment}查看' },
      okText: { type: String, default: '确定' }
    },
    computed: mapState([
      '${table.camelName}Store'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        showModal: false,
        showLoading: false,
        showForm: {
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
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.showForm.id = this.id
          this.showModal = val
          this.show${table.className}()
        }
      },
      showModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      show${table.className} () {
        this.$store.dispatch('show${table.className}', {
          params: {
            id: this.showForm.id
          }
        }).then(() => {
          this.showForm = this.${table.camelName}Store.item
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
