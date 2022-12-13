<template>
  <div>
    <Card :dis-hover="true">
      <Row>
        <Col span="8">
        <Button v-if="options.add" style="margin-right: 5px;" type="primary" size="small"
                @click="handleAddButtonClick()">
          新增
        </Button>
        <Button v-if="options.remove" style="margin-right: 5px;" type="primary" size="small"
                :disabled="options.removeDisabled"
                @click="handleRemoveButtonClick()">
          删除
        </Button>
        <RemoveModal :modal="modal.remove" :id="modal.id" @on-modal-change="handleRemoveModalChange"
                     @on-refresh-page="handleRefreshChange(true)"></RemoveModal>
        <Tree ref="resourceTree" :data="resourceStore.items" @on-select-change="handleSelectChange"
              :empty-text="options.page ? (resourceStore.items.length == 0 ? '加载中...' : '') : '未经授权, 请联系管理员'"></Tree>
        </Col>
        <Col span="16">
        <Form ref="resourceForm" :model="resourceForm" :rules="ruleValidate" :label-width="80">
          <Row>
            <Col span="8">
            <Form-item label="名称" prop="name">
              <Input v-model="resourceForm.name" size="small" placeholder="请输入名称"/>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="编码" prop="code">
              <Input v-model="resourceForm.code" size="small" placeholder="请输入编码"/>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="8">
            <Form-item label="URL" prop="url">
              <Input v-model="resourceForm.url" size="small" placeholder="请输入URL"/>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="图标" prop="icon">
              <Input v-model="resourceForm.icon" size="small" placeholder="请输入图标"/>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="8">
            <Form-item label="父节点" prop="pname">
              <Input v-model="resourceForm.pname" size="small" placeholder="请输入名称" readonly/>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="排序号" prop="sort">
              <Input v-model="resourceForm.sort" size="small" placeholder="请输入排序号"/>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="8">
            <Form-item label="菜单节点" prop="isMenu">
              <i-switch v-model="resourceForm.isMenu" :true-value="1" :false-value="0"></i-switch>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="叶子节点" prop="isLeaf">
              <i-switch v-model="resourceForm.isLeaf" :true-value="1" :false-value="0"></i-switch>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="16">
            <Form-item label="备注" prop="remark">
              <Input v-model="resourceForm.remark" type="textarea" size="small" placeholder="请输入备注"/>
            </Form-item>
            </Col>
          </Row>
          <FormItem>
            <Button type="default" style="margin-right: 5px;" size="small" @click="handleCancel">{{cancelText}}</Button>
            <Button v-if="(resourceForm.id !== 0 && options.modify) || (resourceForm.id === 0 && options.add)"
                    type="primary" size="small" :loading="modifyLoading" @click="handleOk">
              <span v-if="!modifyLoading">{{okText}}</span>
              <span v-else>{{okText}}</span>
            </Button>
          </FormItem>
          <Form-item prop="id"></Form-item>
        </Form>
        </Col>
      </Row>
    </Card>
  </div>
</template>

<script>
  import './i18n'
  import { mapState } from 'vuex'
  import RemoveModal from './form/remove'

  export default {
    name: 'resource',
    props: {
      okText: { type: String, default: '确定' },
      cancelText: { type: String, default: '取消' }
    },
    components: {
      RemoveModal
    },
    data () {
      return {
        modifyLoading: false,
        modal: {
          id: 0,
          remove: false
        },
        options: {
          page: false,
          add: true,
          modify: false,
          show: false,
          remove: false,
          removeDisabled: true
        },
        resourceForm: {
          id: 0,
          name: '',
          code: '',
          url: '',
          icon: '',
          remark: '',
          pid: 0,
          pname: '',
          sort: 0,
          isMenu: 0,
          isLeaf: 0
        },
        ruleValidate: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '名称不能多于10个字符', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '编码不能为空', trigger: 'blur' },
            { type: 'string', max: 10, message: '编码不能多于10个字符', trigger: 'blur' }
          ]
        }
      }
    },
    computed: mapState([
      'resourceStore',
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
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:CDZY:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:CDZY:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:CDZY:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:CDZY:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:CDZY:SC') !== -1
      },
      listResource () {
        this.$store.dispatch('listResource')
      },
      showResource () {
        this.$store.dispatch('showResource', {
          params: {
            id: this.resourceForm.id
          }
        }).then(() => {
          this.resourceForm = this.resourceStore.item
        })
      },
      handleSelectChange (selections) {
        if (selections.length > 0) {
          this.options.removeDisabled = false
          this.resourceForm.id = selections[0].id
          this.showResource()
        } else {
          this.options.removeDisabled = true
          this.resourceForm.id = 0
          this.resetFields()
        }
      },
      handleRefreshChange (cleanModifyForm) {
        if (cleanModifyForm) {
          this.resetFields()
        }
        this.handlePageChange()
      },
      handlePageChange () {
        this.listResource()
      },
      handleRemoveModalChange (id, modal) {
        this.modal.id = id
        this.modal.remove = modal
      },
      handleOk () {
        this.$refs.resourceForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch(this.resourceForm.id === 0 ? 'addResource' : 'modifyResource', {
              data: this.resourceForm
            }).then(() => {
              this.$Message.success((this.resourceForm.id === 0 ? '资源新增' : '资源编辑') + '成功！')
              this.handleCancel()
              this.handlePageChange()
            }, (res) => {
              this.modifyLoading = false
              this.$Message.error(res.data.message)
            })
          }
        })
      },
      handleCancel () {
        this.resetFields()
        this.modifyLoading = false
      },
      resetFields () {
        this.$refs.resourceForm.resetFields()
      },
      handleAddButtonClick () {
        this.resetFields()
        let selections = this.$refs.resourceTree.getSelectedNodes()
        if (selections.length === 1) {
          this.resourceForm.pid = selections[0].id
          this.resourceForm.pname = selections[0].title
        }
      },
      handleRemoveButtonClick () {
        this.resetFields()
        let selections = this.$refs.resourceTree.getSelectedNodes()
        if (selections.length === 1) {
          this.modal.id = selections[0].id
          this.modal.remove = true
        }
      }
    }
  }
</script>
