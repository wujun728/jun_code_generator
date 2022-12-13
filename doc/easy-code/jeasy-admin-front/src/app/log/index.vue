<template>
  <div>
    <List :loading="logStore.loading" :singleSelect="true" :pageNo="logPage.pageNo"
          :pageSize="logPage.pageSize"
          :columns="columns"
          :data="logStore.items" :total="logStore.total" :hasPermission="options.page"
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
                    <Form-item label="字段名称" prop="fieldName">
                      <Input v-model="searchForm.fieldName" size="small" placeholder="请输入字段名称"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="日志类型" prop="logTypeVal">
                      <Input v-model="searchForm.logTypeCode" size="small" placeholder="请输入日志类型值"/>
                    </Form-item>
                    </Col>
                    <Col span="6">
                    <Form-item label="操作类型" prop="optTypeCode">
                      <Input v-model="searchForm.optTypeCode" size="small" placeholder="请输入操作类型值"/>
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
                      :loading="logStore.loading" @click="handleSearch">
                <span v-if="!logStore.loading">搜索</span>
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
  import validate from '@/utils/helpers/validate'

  export default {
    name: 'log',
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
        logPage: {
          pageNo: 1,
          pageSize: consts.PAGE_SIZE
        },
        searchForm: {
          id: 0,
          tableName: '',
          fieldName: '',
          logTypeCode: '',
          optTypeCode: '',
          updateStartAt: '',
          updateEndAt: ''
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
          { title: 'ID', key: 'id', width: 60, align: 'center' },
          { title: '表名称', key: 'tableName', align: 'center' },
          { title: '字段名称', key: 'fieldName', align: 'center' },
          { title: '日志类型', key: 'logTypeVal', align: 'center' },
          { title: '操作类型', key: 'optTypeVal', align: 'center' },
          { title: '操作前值', key: 'beforeValue', align: 'center' },
          { title: '操作后值', key: 'afterValue', align: 'center' },
          { title: '备注', key: 'remark', align: 'center' },
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
    computed: mapState([
      'logStore',
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
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('RZJK:CZRZ:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('RZJK:CZRZ:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('RZJK:CZRZ:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('RZJK:CZRZ:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('RZJK:CZRZ:SC') !== -1
        if (!this.options.modify && !this.options.show && !this.options.remove) {
          this.columns.splice(this.columns.length - 1, 1)
        }
      },
      pageLog (pageNo = 1) {
        this.$refs.searchForm.validate((valid) => {
          if (valid) {
            this.logPage.pageNo = pageNo

            if (this.searchForm.updateStartAt !== '') {
              this.searchForm.updateStartAt = time.getDateTime(this.searchForm.updateStartAt)
            }

            if (this.searchForm.updateEndAt !== '') {
              this.searchForm.updateEndAt = time.getDateTime(this.searchForm.updateEndAt)
            }

            this.$store.dispatch('pageLog', {
              params: {
                pageNo: this.logPage.pageNo,
                pageSize: this.logPage.pageSize,
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
        this.pageLog(pageNo)
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
