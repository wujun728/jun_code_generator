<template>
  <div>
    <List :loading="${table.camelName}Store.loading" :singleSelect="true" :pageNo="${table.camelName}Page.pageNo"
          :pageSize="${table.camelName}Page.pageSize"
          :columns="columns"
          :data="${table.camelName}Store.items" :total="${table.camelName}Store.total" :hasPermission="options.page"
          @on-page-change="handlePageChange" @on-current-change="handleCurrentChange">
      <ListHeader>
        <ListOperations>
          <Card :padding="5" v-if="options.add">
            <Button v-if="options.add" style="margin-right: 5px;" type="primary" size="small"
                    @click="handleAddButtonClick()">
              新增
            </Button>
            <AddModal :modal="modal.add" @on-modal-change="handleAddModalChange"
                      @on-refresh-page="handleRefreshChange(true)"></AddModal>
            <ModifyModal :modal="modal.modify" :id="modal.id" @on-modal-change="handleModifyModalChange"
                         @on-refresh-page="handleRefreshChange(false)"></ModifyModal>
            <ShowModal :modal="modal.show" :id="modal.id" @on-modal-change="handleShowModalChange"></ShowModal>
            <RemoveModal :modal="modal.remove" :id="modal.id" @on-modal-change="handleRemoveModalChange"
                         @on-refresh-page="handleRefreshChange(false)"></RemoveModal>
          </Card>
        </ListOperations>
        <ListSearch>
          <Card :padding="5">
            <Tabs size="small" style="z-index: 5; overflow: visible;">
              <Tab-pane label="简单搜索">
                <Form ref="searchForm" :model="searchForm" :label-width="80" :rules="ruleValidate">
                <#list table.columns as col>
                  <Row>
                    <Col span="24">
                    <Form-item label="${col.comment}" prop="${col.camelName}">
                      <Input v-model="searchForm.${col.camelName}" size="small" placeholder="请输入${col.comment}" @on-enter="handleSearch"/>
                    </Form-item>
                    </Col>
                  </Row>
                </#list>
                </Form>
              </Tab-pane>
              <Button v-if="options.page" type="primary" icon="md-search" size="small" slot="extra"
                      style="margin-right: 5px;"
                      :loading="${table.camelName}Store.loading" @click="handleSearch">
                <span v-if="!${table.camelName}Store.loading">搜索</span>
                <span v-else>搜索</span>
              </Button>
              <Button v-if="options.page" type="warning" icon="md-refresh" size="small" slot="extra" @click="resetFields">
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
  import {mapState} from 'vuex'
  import consts from '@/utils/consts'
  import time from '@/utils/helpers/timeLite'
  import List, {ListHeader, ListOperations, ListSearch} from '@/components/List'
  import AddModal from './form/add'
  import ModifyModal from './form/modify'
  import ShowModal from './form/show'
  import RemoveModal from './form/remove'
  import validate from '@/utils/helpers/validate'

  export default {
    name: '${table.camelName}',
    components: {
      List,
      ListHeader,
      ListOperations,
      ListSearch,
      AddModal,
      ModifyModal,
      ShowModal,
      RemoveModal
    },
    computed: mapState([
      '${table.camelName}Store',
      'loginStore'
    ]),
    created () {
      if (this.loginStore.menuOptionMap[this.$route.path]) {
        this.initOptionsStatus()
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
          remove: false
        },
        ${table.camelName}Page: {
          pageNo: 1,
          pageSize: consts.PAGE_SIZE
        },
        searchForm: {
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
          ]
        },
        options: {
          page: false,
          add: false,
          modify: false,
          show: false,
          remove: false
        },
        columns: [
          { type: 'selection', title: '全选', key: 'id', width: 60, align: 'center', fixed: 'left' },
          <#list table.columns as col>
          { title: '${col.comment}', key: '${col.camelName}', align: 'center' },
          </#list>
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
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:GGMB:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:GGMB:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:GGMB:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:GGMB:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:GGMB:SC') !== -1
        if (!this.options.modify && !this.options.show && !this.options.remove) {
          this.columns.splice(this.columns.length - 1, 1)
        }
      },
      page${table.className} (pageNo = 1) {
        this.$refs.searchForm.validate((valid) => {
          if (valid) {
            this.${table.camelName}Page.pageNo = pageNo

            if (this.searchForm.updateStartAt !== '') {
              this.searchForm.updateStartAt = time.getDateTime(this.searchForm.updateStartAt)
            }

            if (this.searchForm.updateEndAt !== '') {
              this.searchForm.updateEndAt = time.getDateTime(this.searchForm.updateEndAt)
            }

            this.$store.dispatch('page${table.className}', {
              params: {
                pageNo: this.${table.camelName}Page.pageNo,
                pageSize: this.${table.camelName}Page.pageSize,
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
        this.handlePageChange()
      },
      handlePageChange (pageNo) {
        this.page${table.className}(pageNo)
      },
      handleCurrentChange (currentRow, oldCurrentRow) {
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
      handleAddButtonClick () {
        this.modal.add = true
      }
    }
  }
</script>
<style>
</style>
