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
        <Tree ref="organizationTree" :data="organizationStore.items" @on-select-change="handleSelectChange"
              :empty-text="options.page ? (organizationStore.items.length == 0 ? '加载中...' : '') : '未经授权, 请联系管理员'"></Tree>
        </Col>
        <Col span="16">
        <Form ref="organizationForm" :model="organizationForm" :rules="ruleValidate" :label-width="80">
          <Row>
            <Col span="8">
            <Form-item label="名称" prop="name">
              <Input v-model="organizationForm.name" size="small" placeholder="请输入名称"/>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="编码" prop="code">
              <Input v-model="organizationForm.code" size="small" placeholder="请输入编码"/>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="8">
            <Form-item label="父节点" prop="pname">
              <Input v-model="organizationForm.pname" size="small" placeholder="请输入名称" readonly/>
            </Form-item>
            </Col>
            <Col span="8">
            <Form-item label="排序号" prop="sort">
              <Input v-model="organizationForm.sort" size="small" placeholder="请输入排序号"/>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="16">
            <Form-item label="叶子节点" prop="isLeaf">
              <i-switch v-model="organizationForm.isLeaf" :true-value="1" :false-value="0"></i-switch>
            </Form-item>
            </Col>
          </Row>
          <Row>
            <Col span="16">
            <Form-item label="备注" prop="remark">
              <Input v-model="organizationForm.remark" type="textarea" size="small" placeholder="请输入备注"/>
            </Form-item>
            </Col>
          </Row>
          <FormItem>
            <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
            <Button v-if="(organizationForm.id !== 0 && options.modify) || (organizationForm.id === 0 && options.add)"
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
    name: 'organization',
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
        organizationForm: {
          id: 0,
          name: '',
          code: '',
          remark: '',
          pid: 0,
          pname: '',
          sort: 0,
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
      'organizationStore',
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
        this.options.page = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:ZZJG:CX') !== -1
        this.options.add = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:ZZJG:XZ') !== -1
        this.options.modify = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:ZZJG:BJ') !== -1
        this.options.show = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:ZZJG:CK') !== -1
        this.options.remove = this.loginStore.menuOptionMap[this.$route.path].indexOf('YHGL:ZZJG:SC') !== -1
      },
      listOrganization () {
        this.$store.dispatch('listOrganization')
      },
      showOrganization () {
        this.$store.dispatch('showOrganization', {
          params: {
            id: this.organizationForm.id
          }
        }).then(() => {
          this.organizationForm = this.organizationStore.item
        })
      },
      handleSelectChange (selections) {
        if (selections.length > 0) {
          this.options.removeDisabled = false
          this.organizationForm.id = selections[0].id
          this.showOrganization()
        } else {
          this.options.removeDisabled = true
          this.organizationForm.id = 0
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
        this.listOrganization()
      },
      handleRemoveModalChange (id, modal) {
        this.modal.id = id
        this.modal.remove = modal
      },
      handleOk () {
        this.$refs.organizationForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch(this.organizationForm.id === 0 ? 'addOrganization' : 'modifyOrganization', {
              data: this.organizationForm
            }).then(() => {
              this.$Message.success((this.organizationForm.id === 0 ? '机构新增' : '机构编辑') + '成功！')
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
        this.$refs.organizationForm.resetFields()
      },
      handleAddButtonClick () {
        this.resetFields()
        let selections = this.$refs.organizationTree.getSelectedNodes()
        if (selections.length === 1) {
          this.organizationForm.pid = selections[0].id
          this.organizationForm.pname = selections[0].title
        }
      },
      handleRemoveButtonClick () {
        this.resetFields()
        let selections = this.$refs.organizationTree.getSelectedNodes()
        if (selections.length === 1) {
          this.modal.id = selections[0].id
          this.modal.remove = true
        }
      }
    }
  }
</script>
