<template>
  <div>
    <Modal v-model="addModal" :mask-closable="false" @on-cancel="handleCancel">
      <p slot="header">
        <span>{{title}}</span>
      </p>
      <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="60">
        <Row>
          <Col span="24">
          <Form-item label="主键" prop="id">
            <Input v-model="addForm.id" size="small" placeholder="请输入主键"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="名称" prop="name">
            <Input v-model="addForm.name" size="small" placeholder="请输入名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="编码" prop="code">
            <Input v-model="addForm.code" size="small" placeholder="请输入编码"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="地址" prop="address">
            <Input v-model="addForm.address" size="small" placeholder="请输入地址"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="机构类型值" prop="typeVal">
            <Input v-model="addForm.typeVal" size="small" placeholder="请输入机构类型值"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="机构类型编码:字典" prop="typeCode">
            <Input v-model="addForm.typeCode" size="small" placeholder="请输入机构类型编码:字典"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="排序" prop="sort">
            <Input v-model="addForm.sort" size="small" placeholder="请输入排序"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否叶子节点:0=否,1=是" prop="isLeaf">
            <Input v-model="addForm.isLeaf" size="small" placeholder="请输入是否叶子节点:0=否,1=是"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="父ID" prop="pid">
            <Input v-model="addForm.pid" size="small" placeholder="请输入父ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="图标" prop="icon">
            <Input v-model="addForm.icon" size="small" placeholder="请输入图标"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注/描述" prop="remark">
            <Input v-model="addForm.remark" size="small" placeholder="请输入备注/描述"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建时间" prop="createAt">
            <Input v-model="addForm.createAt" size="small" placeholder="请输入创建时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人ID" prop="createBy">
            <Input v-model="addForm.createBy" size="small" placeholder="请输入创建人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="创建人名称" prop="createName">
            <Input v-model="addForm.createName" size="small" placeholder="请输入创建人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新时间" prop="updateAt">
            <Input v-model="addForm.updateAt" size="small" placeholder="请输入更新时间"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人ID" prop="updateBy">
            <Input v-model="addForm.updateBy" size="small" placeholder="请输入更新人ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="更新人名称" prop="updateName">
            <Input v-model="addForm.updateName" size="small" placeholder="请输入更新人名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否删除" prop="isDel">
            <Input v-model="addForm.isDel" size="small" placeholder="请输入是否删除"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="是否测试" prop="isTest">
            <Input v-model="addForm.isTest" size="small" placeholder="请输入是否测试"/>
          </Form-item>
          </Col>
        </Row>
      </Form>
      <div slot="footer">
        <Button type="default" size="small" @click="handleCancel">{{cancelText}}</Button>
        <Button type="primary" size="small" :loading="addLoading" @click="handleOk">
          <span v-if="!addLoading">{{okText}}</span>
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
    name: 'addModal',
    props: {
      modal: {type: Boolean, default: false},
      title: {type: String, default: '机构新增'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'organizationStore'
    ]),
    data () {
      return {
        addModal: false,
        addLoading: false,
        addForm: {
          id: 0,
          name: '',
          code: '',
          address: '',
          typeVal: 0,
          typeCode: '',
          sort: 0,
          isLeaf: 0,
          pid: 0,
          icon: '',
          remark: '',
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
          address: [
            {required: true, message: '地址不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '地址不能多于10个字符', trigger: 'blur'}
          ],
          typeVal: [
            {required: true, message: '机构类型值不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '机构类型值不能多于10个字符', trigger: 'blur'}
          ],
          typeCode: [
            {required: true, message: '机构类型编码:字典不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '机构类型编码:字典不能多于10个字符', trigger: 'blur'}
          ],
          sort: [
            {required: true, message: '排序不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '排序不能多于10个字符', trigger: 'blur'}
          ],
          isLeaf: [
            {required: true, message: '是否叶子节点:0=否,1=是不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '是否叶子节点:0=否,1=是不能多于10个字符', trigger: 'blur'}
          ],
          pid: [
            {required: true, message: '父ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '父ID不能多于10个字符', trigger: 'blur'}
          ],
          icon: [
            {required: true, message: '图标不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '图标不能多于10个字符', trigger: 'blur'}
          ],
          remark: [
            {required: true, message: '备注/描述不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '备注/描述不能多于10个字符', trigger: 'blur'}
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
        if (val) {
          // 对话框显示时执行
        }
      },
      addModal: function (val) {
        if (!val) {
          this.$emit('on-modal-change', val)
        }
      }
    },
    methods: {
      handleOk () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.addLoading = true
            this.$store.dispatch('addOrganization', {
              data: this.addForm
            }).then(() => {
              this.$Message.success(this.title + '成功！')
              this.handleCancel()
              this.$emit('on-refresh-page')
            }, (res) => {
              this.addLoading = false
              this.$Message.error(res.data.message)
            })
          }
        })
      },
      handleCancel () {
        this.resetFields()
        this.addModal = false
        this.addLoading = false
      },
      resetFields () {
        this.$refs.addForm.resetFields()
      }
    }
  }
</script>
