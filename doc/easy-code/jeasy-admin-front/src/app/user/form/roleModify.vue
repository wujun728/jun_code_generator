<template>
  <div>
    <Modal v-model="roleModifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <List ref="roleSelection" :loading="userStore.roleLoading" :pageNo="rolePage.pageNo" :crossPageSelect="true"
            :crossPageSelection="crossPageSelection"
            :pageSize="rolePage.pageSize"
            :columns="columns"
            :data="userStore.roleItems" :total="userStore.roleTotal" :hasPermission="true"
            @on-page-change="handlePageChange" @on-selection-change="handleSelectionChange"
            style="padding-bottom: 20px;">
      </List>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="roleModifyLoading" @click="handleOk">
          <span v-if="!roleModifyLoading">{{okText}}</span>
          <span v-else>{{okText}}</span>
        </Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import { mapState } from 'vuex'
  import List from '@/components/List'

  export default {
    name: 'roleModifyModal',
    props: {
      id: { type: Number, default: 0 },
      modal: { type: Boolean, default: false },
      title: { type: String, default: '角色配置' },
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    components: {
      List
    },
    computed: mapState([
      'userStore'
    ]),
    created () {},
    mounted () {},
    data () {
      return {
        roleModifyModal: false,
        roleModifyLoading: false,
        roleModifyForm: {
          userId: 0,
          roleIds: []
        },
        searchForm: {
          userId: 0
        },
        rolePage: {
          pageNo: 1,
          pageSize: 5
        },
        columns: [
          { type: 'selection', title: '全选', key: 'id', width: 60, align: 'center', fixed: 'left' },
          { title: '角色名称', key: 'name', align: 'center' },
          { title: '角色编码', key: 'code', align: 'center' },
          { title: '备注', key: 'remark', align: 'center' }
        ],
        crossPageSelection: {}
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.roleModifyForm.userId = this.id
          this.searchForm.userId = this.id
          this.roleModifyModal = val
          this.pageUserRole()
          this.listUserRole()
        }
      },
      roleModifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', this.id, val)
        }
      }
    },
    methods: {
      handleOk () {
        this.roleModifyLoading = true
        this.$store.dispatch('modifyUserRole', {
          data: this.roleModifyForm
        }).then(() => {
          this.handleCancel()
          this.$Message.success(this.title + '成功！')
        }, (res) => {
          this.roleModifyLoading = false
          this.$Message.error(res.data.message)
        })
      },
      handleCancel () {
        this.resetFields()
        this.roleModifyModal = false
        this.roleModifyLoading = false
      },
      resetFields () {
        this.roleModifyForm.userId = 0
        this.roleModifyForm.roleIds = []
        this.searchForm.userId = 0
        this.crossPageSelection = {}
      },
      handlePageChange (pageNo) {
        this.pageUserRole(pageNo)
      },
      handleSelectionChange (selections) {
        this.roleModifyForm.roleIds = selections.map(function (item) {
          return item.id ? item.id : item.roleId
        })
      },
      pageUserRole (pageNo = 1) {
        this.rolePage.pageNo = pageNo

        this.$store.dispatch('pageUserRole', {
          params: {
            pageNo: this.rolePage.pageNo,
            pageSize: this.rolePage.pageSize,
            ...this.searchForm
          }
        })
      },
      listUserRole () {
        this.$store.dispatch('listUserRole', {
          params: {
            ...this.searchForm
          }
        }).then(() => {
          this.$refs.roleSelection.$emit('on-selection-change', this.userStore.userRoles)
        })
      }
    }
  }
</script>
