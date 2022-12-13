<template>
  <div>
    <List :loading="userStore.loading" :singleSelect="true" :pageNo="userPage.pageNo"
          :pageSize="userPage.pageSize"
          :columns="columns"
          :data="userStore.items" :total="userStore.total" :hasPermission="options.page"
          @on-page-change="handlePageChange" @on-current-change="handleCurrentChange">
      <ListHeader>
        <ListOperations>
          <Card :padding="5" v-if="options.add">
            <Button v-if="options.add" style="margin-right: 5px;" type="primary" size="small"
                    @click="handleAddButtonClick()">
              新增
            </Button>
            <Button v-if="options.roleModify" style="margin-right: 5px;" type="primary" size="small"
                    @click="handleRoleModifyButtonClick()" :disabled="options.roleModifyDisabled">
              角色配置
            </Button>
            <Button v-if="options.organizationModify" style="margin-right: 5px;" type="primary" size="small"
                    @click="handleOrganizationModifyButtonClick()" :disabled="options.organizationModifyDisabled">
              机构配置
            </Button>
            <AddModal :modal="modal.add" @on-modal-change="handleAddModalChange"
                      @on-refresh-page="handleRefreshChange(true)"></AddModal>
            <ModifyModal :modal="modal.modify" :id="modal.id" @on-modal-change="handleModifyModalChange"
                         @on-refresh-page="handleRefreshChange(false)"></ModifyModal>
            <ShowModal :modal="modal.show" :id="modal.id" @on-modal-change="handleShowModalChange"></ShowModal>
            <RemoveModal :modal="modal.remove" :id="modal.id" @on-modal-change="handleRemoveModalChange"
                         @on-refresh-page="handleRefreshChange(false)"></RemoveModal>
            <RoleModifyModal :modal="modal.roleModify" :id="modal.id" @on-modal-change="handleRoleModifyModalChange"
                             @on-refresh-page="handleRefreshChange(false)"></RoleModifyModal>
            <OrganizationModifyModal :modal="modal.organizationModify" :id="modal.id"
                                     @on-modal-change="handleOrganizationModifyModalChange"
                                     @on-refresh-page="handleRefreshChange(false)"></OrganizationModifyModal>
          </Card>
        </ListOperations>
        <ListSearch>
          <Card :padding="5">
            <Tabs size="small" style="z-index: 5; overflow: visible;">
              <Tab-pane label="简单搜索">
                <Form ref="searchForm" :model="searchForm" :label-width="80" :rules="ruleValidate">
                  <Row>
                    <Col span="6">
                    <Form-item label="用户名称" prop="name">
                      <Input v-model="searchForm.name" size="small" placeholder="请输入用户名称" @on-enter="handleSearch"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="登录名称" prop="loginName">
                      <Input v-model="searchForm.loginName" size="small" placeholder="请输入登录名称"
                             @on-enter="handleSearch"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="手机号码" prop="mobile">
                      <Input v-model="searchForm.mobile" size="small" placeholder="请输入手机号码" @on-enter="handleSearch"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <FormItem label="用户状态" prop="statusCode">
                      <Select size="small" placeholder="请选择" v-model="searchForm.statusCode">
                        <Option v-for="option in dictionaryStore.dictionarys" :value="option.code" :key="option.code">
                          {{ option.name }}
                        </Option>
                      </Select>
                    </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="11">
                    <Form-item label="更新时间" prop="updateStartAt">
                      <Date-picker :editable="false" type="datetime" placeholder="选择起始时间" format="yyyy-MM-dd HH:mm:ss"
                                   style="width: 45%;" size="small" v-model="searchForm.updateStartAt">
                      </Date-picker>
                      -
                      <Date-picker :editable="false" type="datetime" placeholder="选择结束时间" format="yyyy-MM-dd HH:mm:ss"
                                   style="width: 45%;" size="small" v-model="searchForm.updateEndAt">
                      </Date-picker>
                    </Form-item>
                    <Form-item style="margin-bottom: 2px;" prop="updateEndAt"></Form-item>
                    </Col>
                  </Row>
                </Form>
              </Tab-pane>
              <Button v-if="options.page" type="primary" icon="md-search" size="small" slot="extra"
                      style="margin-right: 5px;"
                      :loading="userStore.loading" @click="handleSearch">
                <span v-if="!userStore.loading">搜索</span>
                <span v-else>搜索</span>
              </Button>
              <Button v-if="options.page" type="warning" icon="md-refresh" size="small" slot="extra"
                      @click="resetFields">
                清空
              </Button>
            </Tabs>
          </Card>
        </ListSearch>
      </ListHeader>
    </List>
  </div>
</template>

<script>
  import './i18n'
  import { mapState } from 'vuex'
  import consts from '@/utils/consts'
  import time from '@/utils/helpers/timeLite'
  import List, { ListHeader, ListOperations, ListSearch } from '@/components/List'
  import AddModal from './form/add'
  import ModifyModal from './form/modify'
  import ShowModal from './form/show'
  import RemoveModal from './form/remove'
  import RoleModifyModal from './form/roleModify'
  import OrganizationModifyModal from './form/organizationModify'

  export default {
    name: 'user',
    components: {
      List,
      ListHeader,
      ListOperations,
      ListSearch,
      AddModal,
      ModifyModal,
      ShowModal,
      RemoveModal,
      RoleModifyModal,
      OrganizationModifyModal
    },
    computed: mapState([
      'dictionaryStore',
      'userStore',
      'loginStore'
    ]),
    created () {
      if (this.loginStore.menuOptionMap[this.$route.path]) {
        this.initOptionsStatus()
        this.initUserStatus()
      }
    },
    mounted () {
      if (this.options.page) {
        this.handlePageChange()
      }
    },
    data () {
      return {
        modal: {
          id: 0,
          add: false,
          modify: false,
          show: false,
          remove: false,
          roleModify: false,
          organizationModify: false
        },
        userPage: {
          pageNo: 1,
          pageSize: consts.PAGE_SIZE
        },
        searchForm: {
          name: '',
          loginName: '',
          mobile: '',
          statusCode: '',
          updateStartAt: '',
          updateEndAt: ''
        },
        ruleValidate: {},
        options: {
          page: false,
          add: false,
          modify: false,
          show: false,
          remove: false,
          roleModify: false,
          organizationModify: false,
          roleModifyDisabled: true,
          organizationModifyDisabled: true
        },
        columns: [
          { type: 'selection', title: '全选', key: 'id', width: 60, align: 'center', fixed: 'left' },
          { title: 'ID', key: 'id', width: 60, align: 'center' },
          { title: '用户名称', key: 'name', align: 'center' },
          { title: '登录名称', key: 'loginName', align: 'center' },
          { title: '用户状态', key: 'statusName', align: 'center' },
          { title: '手机号', key: 'mobile', align: 'center' },
          { title: '更新时间', key: 'updateAt', align: 'center' },
          { title: '更新人', key: 'updateName', align: 'center' },
          {
            title: '操作',
            key: 'action',
            width: 160,
            align: 'center',
            fixed: 'right',
            render: (h, params) => {
              return h('div', [
                this.options.show ? h('Button', {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.handleShowModalChange(params.row.id, true)
                    }
                  }
                }, '查看') : null,
                this.options.modify ? h('Button', {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.handleModifyModalChange(params.row.id, true)
                    }
                  }
                }, '编辑') : null,
                this.options.remove ? h('Button', {
                  props: {
                    type: 'text',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.handleRemoveModalChange(params.row.id, true)
                    }
                  }
                }, '删除') : null
              ])
            }
          }
        ]
      }
    },
    methods: {
      initOptionsStatus () {
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:SC') !== -1
        this.options.roleModify = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:JSPZ') !== -1
        this.options.organizationModify = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:RYGL:JGPZ') !== -1
        if (!this.options.modify && !this.options.show && !this.options.remove && !this.options.roleModify && !this.options.organizationModify) {
          this.columns.splice(this.columns.length - 1, 1)
        }
      },
      initUserStatus () {
        this.$store.dispatch('listDictionary', {
          params: {
            type: 'YHZT'
          }
        })
      },
      pageUser (pageNo = 1) {
        this.$refs.searchForm.validate((valid) => {
          if (valid) {
            this.userPage.pageNo = pageNo

            if (this.searchForm.updateStartAt !== '') {
              this.searchForm.updateStartAt = time.getDateTime(this.searchForm.updateStartAt)
            }

            if (this.searchForm.updateEndAt !== '') {
              this.searchForm.updateEndAt = time.getDateTime(this.searchForm.updateEndAt)
            }

            this.$store.dispatch('pageUser', {
              params: {
                pageNo: this.userPage.pageNo,
                pageSize: this.userPage.pageSize,
                ...this.searchForm
              }
            })
          }
        })
      },
      handleRefreshChange (cleanSearchForm) {
        if (cleanSearchForm) {
          this.resetFields()
        }
        this.options.roleModifyDisabled = true
        this.options.organizationModifyDisabled = true
        this.handlePageChange()
      },
      handlePageChange (pageNo) {
        this.pageUser(pageNo)
      },
      handleCurrentChange (currentRow, oldCurrentRow) {
        if (currentRow) {
          this.modal.id = currentRow.id
          this.options.roleModifyDisabled = false
          this.options.organizationModifyDisabled = false
        } else {
          this.modal.id = 0
          this.options.roleModifyDisabled = true
          this.options.organizationModifyDisabled = true
        }
      },
      handleSearch () {
        this.handlePageChange()
      },
      resetFields () {
        this.$refs.searchForm.resetFields()
      },
      handleAddModalChange (modal) {
        this.modal.add = modal
      },
      handleModifyModalChange (id, modal) {
        this.modal.id = id
        this.modal.modify = modal
      },
      handleShowModalChange (id, modal) {
        this.modal.id = id
        this.modal.show = modal
      },
      handleRemoveModalChange (id, modal) {
        this.modal.id = id
        this.modal.remove = modal
      },
      handleRoleModifyModalChange (id, modal) {
        this.modal.id = id
        this.modal.roleModify = modal
      },
      handleOrganizationModifyModalChange (id, modal) {
        this.modal.id = id
        this.modal.organizationModify = modal
      },
      handleAddButtonClick () {
        this.modal.add = true
      },
      handleRoleModifyButtonClick () {
        this.modal.roleModify = true
      },
      handleOrganizationModifyButtonClick () {
        this.modal.organizationModify = true
      }
    }
  }
</script>
<style>
</style>
