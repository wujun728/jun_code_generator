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
          <Form-item label="表名称" prop="tableName">
            <Input v-model="addForm.tableName" size="small" placeholder="请输入表名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="记录ID" prop="recordId">
            <Input v-model="addForm.recordId" size="small" placeholder="请输入记录ID"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="字段名称" prop="fieldName">
            <Input v-model="addForm.fieldName" size="small" placeholder="请输入字段名称"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="日志类型值" prop="logTypeVal">
            <Input v-model="addForm.logTypeVal" size="small" placeholder="请输入日志类型值"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="日志类型编码:字典" prop="logTypeCode">
            <Input v-model="addForm.logTypeCode" size="small" placeholder="请输入日志类型编码:字典"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="操作类型值" prop="optTypeVal">
            <Input v-model="addForm.optTypeVal" size="small" placeholder="请输入操作类型值"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="操作类型编码:字典" prop="optTypeCode">
            <Input v-model="addForm.optTypeCode" size="small" placeholder="请输入操作类型编码:字典"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="操作类型描述" prop="optDesc">
            <Input v-model="addForm.optDesc" size="small" placeholder="请输入操作类型描述"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="操作前值" prop="beforeValue">
            <Input v-model="addForm.beforeValue" size="small" placeholder="请输入操作前值"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="操作后值" prop="afterValue">
            <Input v-model="addForm.afterValue" size="small" placeholder="请输入操作后值"/>
          </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="24">
          <Form-item label="备注" prop="remark">
            <Input v-model="addForm.remark" size="small" placeholder="请输入备注"/>
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
      title: {type: String, default: '日志新增'},
      okText: {type: String, default: '确定'},
      cancelText: {type: String, default: '取消'}
    },
    computed: mapState([
      'logStore'
    ]),
    data () {
      return {
        addModal: false,
        addLoading: false,
        addForm: {
          id: 0,
          tableName: '',
          recordId: 0,
          fieldName: '',
          logTypeVal: 0,
          logTypeCode: '',
          optTypeVal: 0,
          optTypeCode: '',
          optDesc: '',
          beforeValue: '',
          afterValue: '',
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
          tableName: [
            {required: true, message: '表名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '表名称不能多于10个字符', trigger: 'blur'}
          ],
          recordId: [
            {required: true, message: '记录ID不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '记录ID不能多于10个字符', trigger: 'blur'}
          ],
          fieldName: [
            {required: true, message: '字段名称不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '字段名称不能多于10个字符', trigger: 'blur'}
          ],
          logTypeVal: [
            {required: true, message: '日志类型值不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '日志类型值不能多于10个字符', trigger: 'blur'}
          ],
          logTypeCode: [
            {required: true, message: '日志类型编码:字典不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '日志类型编码:字典不能多于10个字符', trigger: 'blur'}
          ],
          optTypeVal: [
            {required: true, message: '操作类型值不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '操作类型值不能多于10个字符', trigger: 'blur'}
          ],
          optTypeCode: [
            {required: true, message: '操作类型编码:字典不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '操作类型编码:字典不能多于10个字符', trigger: 'blur'}
          ],
          optDesc: [
            {required: true, message: '操作类型描述不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '操作类型描述不能多于10个字符', trigger: 'blur'}
          ],
          beforeValue: [
            {required: true, message: '操作前值不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '操作前值不能多于10个字符', trigger: 'blur'}
          ],
          afterValue: [
            {required: true, message: '操作后值不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '操作后值不能多于10个字符', trigger: 'blur'}
          ],
          remark: [
            {required: true, message: '备注不能为空', trigger: 'blur'},
            {type: 'string', max: 10, message: '备注不能多于10个字符', trigger: 'blur'}
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
            this.$store.dispatch('addLog', {
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
