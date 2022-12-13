<template>
  <div>
    <Modal v-model="organizationModifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Tree ref="organizationTree" :data="userOrganizationList" show-checkbox multiple></Tree>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="organizationModifyLoading" @click="handleOk">
          <span v-if="!organizationModifyLoading">{{okText}}</span>
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
    name: 'organizationModifyModal',
    props: {
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '机构配置' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      'userStore'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        organizationModifyModal: false,
        organizationModifyLoading: false,
        organizationModifyForm: {
          userId: 0,
          orgIds: []
        },
        userOrganizationList: []
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.organizationModifyForm.userId = this.id
          this.organizationModifyModal = val
          this.listUserOrganization()
        }
      },
      organizationModifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      listUserOrganization () {
        this.$store.dispatch('listUserOrganization', {
          params: {
            ...this.organizationModifyForm
          }
        }).then(() => {
          this.userOrganizationList = this.userStore.userOrganizationItems
        })
      },
      handleOk () {
        this.organizationModifyLoading = true
        this.organizationModifyForm.orgIds = this.$refs.organizationTree.getCheckedNodes().map(function (item) {
          return item.id
        })
        this.$store.dispatch('modifyUserOrganization', {
          data: this.organizationModifyForm
        }).then(() => {
          this.handleCancel()
          this.$Message.success(this.title + '成功！')
        }, (res) => {
          this.organizationModifyLoading = false
          this.$Message.error(res.data.message)
        })
      },
      handleCancel () {
        this.resetFields()
        this.organizationModifyModal = false
        this.organizationModifyLoading = false
      },
      resetFields () {
        this.organizationModifyForm.userId = 0
        this.organizationModifyForm.orgIds = []
        this.userOrganizationList = []
      }
    }
  }
</script>
