<template>
  <div>
    <List :loading="fileAttachStore.loading" :singleSelect="true" :pageNo="fileAttachPage.pageNo"
          :pageSize="fileAttachPage.pageSize"
          :columns="columns"
          :data="fileAttachStore.items" :total="fileAttachStore.total" :hasPermission="options.page"
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
                  <Row>
                    <Col span="6">
                    <Form-item label="表名称" prop="tableName">
                      <Input v-model="searchForm.tableName" size="small" placeholder="请输入表名称"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="文件名称" prop="name">
                      <Input v-model="searchForm.name" size="small" placeholder="请输入文件原名称"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="文件URL" prop="url">
                      <Input v-model="searchForm.url" size="small" placeholder="请输入文件URL"/>
                    </Form-item>
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
                      :loading="fileAttachStore.loading" @click="handleSearch">
                <span v-if="!fileAttachStore.loading">搜索</span>
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
  import { mapState } from 'vuex'
  import consts from '@/utils/consts'
  import time from '@/utils/helpers/timeLite'
  import List, { ListHeader, ListOperations, ListSearch } from '@/components/List'
  import AddModal from './form/add'
  import ModifyModal from './form/modify'
  import ShowModal from './form/show'
  import RemoveModal from './form/remove'
  import validate from '@/utils/helpers/validate'

  export default {
    name: 'fileAttach',
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
    data () {
      return {
        modal: {
          id: 0,
          add: false,
          modify: false,
          show: false,
          remove: false
        },
        fileAttachPage: {
          pageNo: 1,
          pageSize: consts.PAGE_SIZE
        },
        searchForm: {
          id: 0,
          tableName: '',
          name: '',
          url: '',
          updateStartAt: '',
          updateEndAt: ''
        },
        ruleValidate: {
          value: [
            {required: false, message: '数值不能为空', trigger: 'blur'},
            {type: 'string', message: '数值必须为数字', trigger: 'blur', validator: validate.validateInputNumber}
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
          {type: 'selection', title: '全选', key: 'id', width: 60, align: 'center', fixed: 'left'},
          {title: 'ID', key: 'id', width: 60, align: 'center'},
          {title: '表名称', key: 'tableName', align: 'center'},
          {title: '记录ID', key: 'recordId', align: 'center'},
          {title: '文件名称', key: 'name', align: 'center'},
          {title: '文件URL', key: 'url', align: 'center'},
          {title: '图标URL', key: 'iconUrl', align: 'center'},
          {title: '预览URL', key: 'previewUrl', align: 'center'},
          {title: '更新时间', key: 'updateAt', align: 'center'},
          {title: '更新人', key: 'updateName', align: 'center'},
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
    computed: mapState([
      'fileAttachStore',
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
    methods: {
      initOptionsStatus () {
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:WJGL:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:WJGL:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:WJGL:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:WJGL:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('JCSJ:WJGL:SC') !== -1
        if (!this.options.modify && !this.options.show && !this.options.remove) {
          this.columns.splice(this.columns.length - 1, 1)
        }
      },
      pageFileAttach (pageNo = 1) {
        this.$refs.searchForm.validate((valid) => {
          if (valid) {
            this.fileAttachPage.pageNo = pageNo

            if (this.searchForm.updateStartAt !== '') {
              this.searchForm.updateStartAt = time.getDateTime(this.searchForm.updateStartAt)
            }

            if (this.searchForm.updateEndAt !== '') {
              this.searchForm.updateEndAt = time.getDateTime(this.searchForm.updateEndAt)
            }

            this.$store.dispatch('pageFileAttach', {
              params: {
                pageNo: this.fileAttachPage.pageNo,
                pageSize: this.fileAttachPage.pageSize,
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
        this.pageFileAttach(pageNo)
      },
      handleCurrentChange (currentRow, oldCurrentRow) {
      },
      handleSearch () {
        this.handlePageChange()
      },
      resetFields () {
        this.$refs.searchForm.resetFields()
      },
      handleAddModalChange (id, modal) {
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
