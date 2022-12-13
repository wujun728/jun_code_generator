<template>
  <div>
    <Modal v-model="permissionModifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Tree ref="permissionTree" :data="rolePermissionList" show-checkbox multiple></Tree>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="permissionModifyLoading" @click="handleOk">
          <span v-if="!permissionModifyLoading">{{okText}}</span>
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
    name: 'permissionModifyModal',
    props: {
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '权限配置' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    computed: mapState([
      'roleStore'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        permissionModifyModal: false,
        permissionModifyLoading: false,
        permissionModifyForm: {
          roleId: 0,
          permissionIds: []
        },
        rolePermissionList: []
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.permissionModifyForm.roleId = this.id
          this.permissionModifyModal = val
          this.listRolePermission()
        }
      },
      permissionModifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      listRolePermission () {
        this.$store.dispatch('listRolePermission', {
          params: {
            ...this.permissionModifyForm
          }
        }).then(() => {
          this.rolePermissionList = this.roleStore.rolePermissionItems
        })
      },
      handleOk () {
        this.permissionModifyLoading = true
        this.permissionModifyForm.permissionIds = this.$refs.permissionTree.getCheckedNodes().map(function (item) {
          return item.id
        })
        this.$store.dispatch('modifyRolePermission', {
          data: this.permissionModifyForm
        }).then(() => {
          this.handleCancel()
          this.$Message.success(this.title + '成功！')
        }, (res) => {
          this.permissionModifyLoading = false
          this.$Message.error(res.data.message)
        })
      },
      handleCancel () {
        this.resetFields()
        this.permissionModifyModal = false
        this.permissionModifyLoading = false
      },
      resetFields () {
        this.permissionModifyForm.roleId = 0
        this.permissionModifyForm.permissionIds = []
        this.rolePermissionList = []
      }
    }
  }
</script>
