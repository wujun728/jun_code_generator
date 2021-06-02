<template>
  <Modal v-model="isShow" width="65%" :closable="false" :mask-closable="false">
    <div slot="header" class="info-header">
      <div class="ivu-modal-header-inner">${g.name}</div>
      <a class="ivu-modal-close" @click="closeModel"><i class="ivu-icon ivu-icon-ios-close"></i></a>
    </div>
    <div>
      <Form ref="${strutil.toLowerCase(g.entityName)}Form" :model="form" :label-width="80" :rules="ruleValidate">
        <Row>
          <%for(field in t.fields){%>
          <Col span="8">
            <FormItem label="${field.chinaName}" prop="account">
              <Input v-model="form.${field.name}" :disabled="disable"></Input>
            </FormItem>
          </Col>
          <%}%>
        </Row>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="closeModel">取消</Button>
      <Button type="primary" @click="submit"  v-show="!disable">提交</Button>
    </div>
  </Modal>
</template>

<script>

import {C, U} from '@/libs/api.request'


export default {
  name: '${strutil.toLowerCase(g.entityName)}-info',
  data () {
    return {
      form: {},
      isShow: false,
      type: 'add',
      disable: false,
      ruleValidate: {
          <%for(field in t.fields){%>
          <%if(field.notNull == 1){%>
              ${field.name}: [
                  {required: true, message: '${field.chinaName}不允许为空', trigger: 'blur'}
              ],
          <%}}%>
      }
    }
  },
  methods: {
    openModel (type, data) {
      this.isShow = true
      this.type = type
      if (data) {
        this.form = data
      } else {
        this.form = {}
      }
      this.disable = type === 'detail'
    },
    closeModel () {
      this.isShow = false
    },
    submit () {
      this.$refs.${strutil.toLowerCase(g.entityName)}Form.validate((valid) => {
        if (valid) {
          switch (this.type) {
            case 'create':
              C('${strutil.toLowerCase(g.entityName)}', this.form).then(data => {
                this.isShow = false
                this.$emit('handleSearch')
              })
              break
            case 'update':
              U('${strutil.toLowerCase(g.entityName)}', this.form).then(data => {
                this.isShow = false
                this.$emit('handleSearch')
              })
              break
          }
        } else {
          this.$Message.error('请检查填写的数据')
        }
      })
    }
  },
  mounted: function () {
  }
}
</script>

<style scoped>
  .info-header {
    height: 40px;
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1;
  }

  .ivu-modal-header-inner {
    margin: 10px 15px 0px 0px;
    padding-left: 15px;
    height: 40px;
  }

  .ivu-modal-close {
    margin: 10px 15px 0px 0px;
  }

  .ivu-form-item {
    width: 260px;
  }
</style>
