<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px" class="single-column-form">
    <!-- 模板设置 -->
    <div class="form-section">
      <h4 class="section-title">
        <i class="el-icon-grid"></i> 模板设置
      </h4>
      <el-form-item prop="tplCategory">
        <span slot="label">生成模板</span>
        <el-select v-model="info.tplCategory" @change="tplSelectChange" placeholder="请选择生成模板">
          <el-option label="单表（增删改查）" value="crud" />
          <el-option label="树表（增删改查）" value="tree" />
          <el-option label="主子表（增删改查）" value="sub" />
        </el-select>
      </el-form-item>
    </div>

    <!-- 命名与路径 -->
    <div class="form-section">
      <h4 class="section-title">
        <i class="el-icon-folder"></i> 命名与路径
      </h4>
      <el-form-item prop="packageName">
        <span slot="label">
          生成包路径
          <el-tooltip content="生成在哪个java包下，例如 com.ruoyi.system" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-input v-model="info.packageName" placeholder="例如：com.ruoyi.system" />
      </el-form-item>
      <el-form-item prop="moduleName">
        <span slot="label">
          生成模块名
          <el-tooltip content="可理解为子系统名，例如 system" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-input v-model="info.moduleName" placeholder="例如：system" />
      </el-form-item>
      <el-form-item prop="businessName">
        <span slot="label">
          生成业务名
          <el-tooltip content="可理解为功能英文名，例如 user" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-input v-model="info.businessName" placeholder="例如：user" />
      </el-form-item>
      <el-form-item prop="functionName">
        <span slot="label">
          生成功能名
          <el-tooltip content="用作类描述，例如 用户" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-input v-model="info.functionName" placeholder="例如：用户" />
      </el-form-item>
    </div>

    <!-- 技术栈配置 -->
    <div class="form-section">
      <h4 class="section-title">
        <i class="el-icon-setting"></i> 技术栈配置
      </h4>
      <el-form-item prop="backEnd">
        <span slot="label">后端框架</span>
        <el-select v-model="info.backEnd" placeholder="请选择后端框架">
          <el-option v-for="option in backendOptions" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
      </el-form-item>
      <el-form-item prop="frontEnd">
        <span slot="label">前端UI框架</span>
        <el-select v-model="info.frontEnd" placeholder="请选择前端UI框架">
          <el-option v-for="option in frontendOptions" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
      </el-form-item>
      <el-form-item prop="isRuoYi">
        <span slot="label">
          是否按若依框架
          <el-tooltip content="默认是按照若依框架生成，否 为通用框架" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-radio-group v-model="info.isRuoYi">
          <el-radio label="1">是</el-radio>
          <el-radio label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </div>

    <!-- 输出选项 -->
    <div class="form-section">
      <h4 class="section-title">
        <i class="el-icon-download"></i> 输出选项
      </h4>
      <el-form-item prop="genType">
        <span slot="label">
          生成代码方式
          <el-tooltip content="默认为zip压缩包下载，也可以自定义生成路径" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-radio-group v-model="info.genType">
          <el-radio label="0">zip压缩包</el-radio>
          <el-radio label="1">自定义路径</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="genPath" v-if="info.genType == '1'">
        <span slot="label">
          自定义路径
          <el-tooltip content="填写磁盘绝对路径，若不填写，则生成到当前Web项目下" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-input v-model="info.genPath" placeholder="请输入自定义生成路径">
          <el-dropdown slot="append">
            <el-button type="primary">
              最近路径快速选择
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="info.genPath = '/'">恢复默认的生成基础路径</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-input>
      </el-form-item>
    </div>

    <!-- 树表配置 -->
    <div class="form-section" v-show="info.tplCategory == 'tree'">
      <h4 class="section-title">
        <i class="el-icon-connection"></i> 树表配置
      </h4>
      <el-form-item>
        <span slot="label">
          树编码字段
          <el-tooltip content="树显示的编码字段名， 如：dept_id" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeCode" placeholder="请选择树编码字段">
          <el-option v-for="(column, index) in info.columns" :key="index"
            :label="column.columnName + '：' + column.columnComment" :value="column.columnName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          树父编码字段
          <el-tooltip content="树显示的父编码字段名， 如：parent_Id" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeParentCode" placeholder="请选择树父编码字段">
          <el-option v-for="(column, index) in info.columns" :key="index"
            :label="column.columnName + '：' + column.columnComment" :value="column.columnName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          树名称字段
          <el-tooltip content="树节点的显示名称字段名， 如：dept_name" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeName" placeholder="请选择树名称字段">
          <el-option v-for="(column, index) in info.columns" :key="index"
            :label="column.columnName + '：' + column.columnComment" :value="column.columnName" />
        </el-select>
      </el-form-item>
    </div>

    <!-- 主子表配置 -->
    <div class="form-section" v-show="info.tplCategory == 'sub'">
      <h4 class="section-title">
        <i class="el-icon-link"></i> 主子表配置
      </h4>
      <el-form-item>
        <span slot="label">
          关联子表的表名
          <el-tooltip content="关联子表的表名， 如：sys_user" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-select v-model="info.subTableName" placeholder="请选择关联子表" @change="subSelectChange">
          <el-option v-for="(table, index) in tables" :key="index"
            :label="table.tableName + '：' + table.tableComment" :value="table.tableName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          子表关联的外键名
          <el-tooltip content="子表关联的外键名， 如：user_id" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </span>
        <el-select v-model="info.subTableFkName" placeholder="请选择外键字段">
          <el-option v-for="(column, index) in subColumns" :key="index"
            :label="column.columnName + '：' + column.columnComment" :value="column.columnName" />
        </el-select>
      </el-form-item>
    </div>
  </el-form>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

const BACKEND_OPTIONS = [
  { value: '0', label: 'MyBatis' },
  { value: '1', label: 'MyBatis-Plus' }
];
const FRONTEND_OPTIONS = [
  { value: '0', label: 'Element UI (Vue2)' },
  { value: '1', label: 'Ant Design Vue' },
  { value: '2', label: 'HTML' },
  { value: '3', label: 'Element UI (Vue3)' }
];

export default {
  components: { Treeselect },
  props: {
    info: { type: Object, default: null },
    tables: { type: Array, default: null },
    menus: { type: Array, default: () => [] }
  },
  data() {
    return {
      subColumns: [],
      backendOptions: BACKEND_OPTIONS,
      frontendOptions: FRONTEND_OPTIONS,
      rules: {
        tplCategory: [{ required: true, message: "请选择生成模板", trigger: "blur" }],
        packageName: [{ required: true, message: "请输入生成包路径", trigger: "blur" }],
        moduleName: [{ required: true, message: "请输入生成模块名", trigger: "blur" }],
        businessName: [{ required: true, message: "请输入生成业务名", trigger: "blur" }],
        functionName: [{ required: true, message: "请输入生成功能名", trigger: "blur" }]
      }
    };
  },
  watch: {
    'info.subTableName': function (val) {
      this.setSubTableColumns(val);
    }
  },
  methods: {
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return { id: node.menuId, label: node.menuName, children: node.children };
    },
    subSelectChange(value) {
      this.info.subTableFkName = '';
    },
    tplSelectChange(value) {
      if (value !== 'sub') {
        this.info.subTableName = '';
        this.info.subTableFkName = '';
      }
    },
    setSubTableColumns(value) {
      for (var item in this.tables) {
        const name = this.tables[item].tableName;
        if (value === name) {
          this.subColumns = this.tables[item].columns;
          break;
        }
      }
    }
  }
};
</script>

<style scoped>
.single-column-form .form-section {
  margin-bottom: 32px;
}
.single-column-form .form-section:last-child {
  margin-bottom: 0;
}
.section-title {
  display: flex; align-items: center; gap: 8px;
  margin: 0 0 20px 0; padding: 0 0 12px 0;
  font-size: 16px; font-weight: 600; color: #1f2937;
  border-bottom: 2px solid #e5e7eb;
}
.section-title i { font-size: 18px; color: #3b82f6; }
</style>