<template>
  <div>
    <Modal v-model="modifyModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="modifyForm" :model="modifyForm" :rules="ruleValidate" :label-width="60">
        <Row>
          <Col span="24">
          <Form-item label="主键" prop="id">
            <Input v-model="modifyForm.id" size="small" placeholder="请输入主键"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="名称" prop="name">
            <Input v-model="modifyForm.name" size="small" placeholder="请输入名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="编码" prop="code">
            <Input v-model="modifyForm.code" size="small" placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="URL" prop="url">
            <Input v-model="modifyForm.url" size="small" placeholder="请输入URL"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="图标" prop="icon">
            <Input v-model="modifyForm.icon" size="small" placeholder="请输入图标"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注/描述" prop="remark">
            <Input v-model="modifyForm.remark" size="small" placeholder="请输入备注/描述"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="父ID" prop="pid">
            <Input v-model="modifyForm.pid" size="small" placeholder="请输入父ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="排序" prop="sort">
            <Input v-model="modifyForm.sort" size="small" placeholder="请输入排序"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否菜单:0=否,1=是" prop="isMenu">
            <Input v-model="modifyForm.isMenu" size="small" placeholder="请输入是否菜单:0=否,1=是"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否叶子节点:0=否,1=是" prop="isLeaf">
            <Input v-model="modifyForm.isLeaf" size="small" placeholder="请输入是否叶子节点:0=否,1=是"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建时间" prop="createAt">
            <Input v-model="modifyForm.createAt" size="small" placeholder="请输入创建时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人ID" prop="createBy">
            <Input v-model="modifyForm.createBy" size="small" placeholder="请输入创建人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人名称" prop="createName">
            <Input v-model="modifyForm.createName" size="small" placeholder="请输入创建人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新时间" prop="updateAt">
            <Input v-model="modifyForm.updateAt" size="small" placeholder="请输入更新时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人ID" prop="updateBy">
            <Input v-model="modifyForm.updateBy" size="small" placeholder="请输入更新人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人名称" prop="updateName">
            <Input v-model="modifyForm.updateName" size="small" placeholder="请输入更新人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否删除" prop="isDel">
            <Input v-model="modifyForm.isDel" size="small" placeholder="请输入是否删除"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否测试" prop="isTest">
            <Input v-model="modifyForm.isTest" size="small" placeholder="请输入是否测试"/>
          </Form-item>
          </Col>
        </Row>
      </Form>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="modifyLoading" @click="handleOk">
          <span v-if="!modifyLoading">{{okText}}</span>
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
    name: 'modifyModal',
    props: {
      id: {type: Number, default: 0},
      modal: {type: Boolean, default: false},
      title: {type: String, default: '菜单编辑'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'resourceStore'
    ]),
    data () {
      return {
        modifyModal: false,
        modifyLoading: false,
        modifyForm: {
          id: 0,
          name: '',
          code: '',
          url: '',
          icon: '',
          remark: '',
          pid: 0,
          sort: 0,
          isMenu: 0,
          isLeaf: 0,
          createAt: 0,
          createBy: 0,
          createName: '',
          updateAt: 0,
          updateBy: 0,
          updateName: '',
          isDel: 0,
          isTest: 0
        },
        ruleValidate: {
          value: [
            {required: false, message: '数值不能为空', trigger: 'blur'},
            {type: 'string', message: '数值必须为数字', trigger: 'blur', validator: validate.validateInputNumber}
          ],
          id: [
            {required: true, message: '主键不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '主键不能多于10个字符', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '名称不能多于10个字符', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '编码不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '编码不能多于10个字符', trigger: 'blur'}
          ],
          url: [
            {required: true, message: 'URL不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: 'URL不能多于10个字符', trigger: 'blur'}
          ],
          icon: [
            {required: true, message: '图标不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '图标不能多于10个字符', trigger: 'blur'}
          ],
          remark: [
            {required: true, message: '备注/描述不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '备注/描述不能多于10个字符', trigger: 'blur'}
          ],
          pid: [
            {required: true, message: '父ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '父ID不能多于10个字符', trigger: 'blur'}
          ],
          sort: [
            {required: true, message: '排序不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '排序不能多于10个字符', trigger: 'blur'}
          ],
          isMenu: [
            {required: true, message: '是否菜单:0=否,1=是不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否菜单:0=否,1=是不能多于10个字符', trigger: 'blur'}
          ],
          isLeaf: [
            {required: true, message: '是否叶子节点:0=否,1=是不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否叶子节点:0=否,1=是不能多于10个字符', trigger: 'blur'}
          ],
          createAt: [
            {required: true, message: '创建时间不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建时间不能多于10个字符', trigger: 'blur'}
          ],
          createBy: [
            {required: true, message: '创建人ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建人ID不能多于10个字符', trigger: 'blur'}
          ],
          createName: [
            {required: true, message: '创建人名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '创建人名称不能多于10个字符', trigger: 'blur'}
          ],
          updateAt: [
            {required: true, message: '更新时间不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新时间不能多于10个字符', trigger: 'blur'}
          ],
          updateBy: [
            {required: true, message: '更新人ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新人ID不能多于10个字符', trigger: 'blur'}
          ],
          updateName: [
            {required: true, message: '更新人名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '更新人名称不能多于10个字符', trigger: 'blur'}
          ],
          isDel: [
            {required: true, message: '是否删除不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否删除不能多于10个字符', trigger: 'blur'}
          ],
          isTest: [
            {required: true, message: '是否测试不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否测试不能多于10个字符', trigger: 'blur'}
          ]
        }
      }
    },
    watch: {
      modal: function (val) {
        if (val && this.id !== 0) {
          this.modifyForm.id = this.id
          this.modifyModal = val
          this.showResource()
        }
      },
      modifyModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', 0, val)
        }
      }
    },
    methods: {
      showResource () {
        this.$store.dispatch('showResource', {
          params: {
            id: this.modifyForm.id
          }
        }).then(() => {
          this.modifyForm = this.resourceStore.item
        })
      },
      handleOk () {
        this.$refs.modifyForm.validate((valid) => {
          if (valid) {
            this.modifyLoading = true
            this.$store.dispatch('modifyResource', {
              data: this.modifyForm
            }).then(() => {
              this.handleCancel()
              this.$Message.success(this.title + '成功！')
              this.$emit('on-refresh-page')
            }, (res) => {
              this.modifyLoading = false
              this.$Message.error(res.data.message)
            })
          }
        })
      },
      handleCancel () {
        this.resetFields()
        this.modifyModal = false
        this.modifyLoading = false
      },
      resetFields () {
        this.$refs.modifyForm.resetFields()
      }
    }
  }
</script>
