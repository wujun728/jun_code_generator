<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px" class="single-column-form">
    <!-- 模板设置 -->
    <div class="form-section">
      <h4 class="section-title">
        <el-icon><Grid /></el-icon>
        模板设置
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
        <el-icon><Folder /></el-icon>
        命名与路径
      </h4>
      <el-form-item prop="packageName">
        <span slot="label">
          生成包路径
          <el-tooltip content="生成在哪个java包下，例如 com.javaxiaobear.gen" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-input v-model="info.packageName" placeholder="例如：com.javaxiaobear.gen" />
      </el-form-item>
      <el-form-item prop="moduleName">
        <span slot="label">
          生成模块名
          <el-tooltip content="可理解为子系统名，例如 gen" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-input v-model="info.moduleName" placeholder="例如：gen" />
      </el-form-item>
      <el-form-item prop="businessName">
        <span slot="label">
          生成业务名
          <el-tooltip content="可理解为功能英文名，例如 user" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-input v-model="info.businessName" placeholder="例如：user" />
      </el-form-item>
      <el-form-item prop="functionName">
        <span slot="label">
          生成功能名
          <el-tooltip content="用作类描述，例如 用户" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-input v-model="info.functionName" placeholder="例如：用户" />
      </el-form-item>
    </div>

    <!-- 技术栈配置 -->
    <div class="form-section">
      <h4 class="section-title">
        <el-icon><Setting /></el-icon>
        技术栈配置
      </h4>
      <el-form-item prop="backEnd">
        <span slot="label">后端框架</span>
        <el-select v-model="info.backEnd" placeholder="请选择后端框架">
          <el-option 
            v-for="option in backendOptions" 
            :key="option.value" 
            :label="option.label" 
            :value="option.value" 
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="frontEnd">
        <span slot="label">前端UI框架</span>
        <el-select v-model="info.frontEnd" placeholder="请选择前端UI框架">
          <el-option 
            v-for="option in frontendOptions" 
            :key="option.value" 
            :label="option.label" 
            :value="option.value" 
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="isRuoYi">
        <span slot="label">
          是否按若依框架
          <el-tooltip content="默认是按照若依框架生成，否 为通用框架" placement="top">
            <el-icon><QuestionFilled /></el-icon>
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
        <el-icon><Download /></el-icon>
        输出选项
      </h4>
      <el-form-item prop="genType">
        <span slot="label">
          生成代码方式
          <el-tooltip content="默认为zip压缩包下载，也可以自定义生成路径" placement="top">
            <el-icon><QuestionFilled /></el-icon>
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
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-input v-model="info.genPath" placeholder="请输入自定义生成路径">
          <template #append>
            <el-dropdown>
              <el-button type="primary">
                最近路径快速选择
                <el-icon class="el-icon--right"><ArrowDown /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="info.genPath = '/'">恢复默认的生成基础路径</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-input>
      </el-form-item>
    </div>

    <!-- 树表配置 -->
    <div class="form-section" v-show="info.tplCategory == 'tree'">
      <h4 class="section-title">
        <el-icon><Connection /></el-icon>
        树表配置
      </h4>
      <el-form-item>
        <span slot="label">
          树编码字段
          <el-tooltip content="树显示的编码字段名， 如：dept_id" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeCode" placeholder="请选择树编码字段">
          <el-option
            v-for="(column, index) in info.columns"
            :key="index"
            :label="column.columnName + '：' + column.columnComment"
            :value="column.columnName"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          树父编码字段
          <el-tooltip content="树显示的父编码字段名， 如：parent_Id" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeParentCode" placeholder="请选择树父编码字段">
          <el-option
            v-for="(column, index) in info.columns"
            :key="index"
            :label="column.columnName + '：' + column.columnComment"
            :value="column.columnName"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          树名称字段
          <el-tooltip content="树节点的显示名称字段名， 如：dept_name" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-select v-model="info.treeName" placeholder="请选择树名称字段">
          <el-option
            v-for="(column, index) in info.columns"
            :key="index"
            :label="column.columnName + '：' + column.columnComment"
            :value="column.columnName"
          ></el-option>
        </el-select>
      </el-form-item>
    </div>

    <!-- 主子表配置 -->
    <div class="form-section" v-show="info.tplCategory == 'sub'">
      <h4 class="section-title">
        <el-icon><Link /></el-icon>
        主子表配置
      </h4>
      <el-form-item>
        <span slot="label">
          关联子表的表名
          <el-tooltip content="关联子表的表名， 如：sys_user" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-select v-model="info.subTableName" placeholder="请选择关联子表" @change="subSelectChange">
          <el-option
            v-for="(table, index) in tables"
            :key="index"
            :label="table.tableName + '：' + table.tableComment"
            :value="table.tableName"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <span slot="label">
          子表关联的外键名
          <el-tooltip content="子表关联的外键名， 如：user_id" placement="top">
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
        </span>
        <el-select v-model="info.subTableFkName" placeholder="请选择外键字段">
          <el-option
            v-for="(column, index) in subColumns"
            :key="index"
            :label="column.columnName + '：' + column.columnComment"
            :value="column.columnName"
          ></el-option>
        </el-select>
      </el-form-item>
    </div>
  </el-form>
</template>
<script>
import { QuestionFilled, Grid, Folder, Setting, Download, Connection, Link, ArrowDown } from '@element-plus/icons-vue'
import { BACKEND_OPTIONS, FRONTEND_OPTIONS } from '@/utils/tech-stack'

export default {
  name: "GenInfoForm",
  components: { 
    QuestionFilled, 
    Grid, 
    Folder, 
    Setting, 
    Download, 
    Connection, 
    Link, 
    ArrowDown 
  },
  props: {
    info: {
      type: Object,
      default: null
    },
    tables: {
      type: Array,
      default: null
    },
    menus: {
      type: Array,
      default: []
    },
  },
  data() {
    return {
      subColumns: [],
      rules: {
        tplCategory: [
          { required: true, message: "请选择生成模板", trigger: "blur" }
        ],
        packageName: [
          { required: true, message: "请输入生成包路径", trigger: "blur" }
        ],
        moduleName: [
          { required: true, message: "请输入生成模块名", trigger: "blur" }
        ],
        businessName: [
          { required: true, message: "请输入生成业务名", trigger: "blur" }
        ],
        functionName: [
          { required: true, message: "请输入生成功能名", trigger: "blur" }
        ],
      }
    };
  },
  computed: {
    backendOptions() {
      return BACKEND_OPTIONS
    },
    frontendOptions() {
      return FRONTEND_OPTIONS
    }
  },
  created() {},
  watch: {
    'info.subTableName': function(val) {
      this.setSubTableColumns(val);
    }
  },
  methods: {
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      };
    },
    /** 选择子表名触发 */
    subSelectChange(value) {
      this.info.subTableFkName = '';
    },
    /** 选择生成模板触发 */
    tplSelectChange(value) {
      if(value !== 'sub') {
        this.info.subTableName = '';
        this.info.subTableFkName = '';
      }
    },
    /** 设置关联外键 */
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

<style lang="scss" scoped>
.single-column-form {
  .form-section {
    margin-bottom: 32px;
    
    &:last-child {
      margin-bottom: 0;
    }
  }
  
  .section-title {
    display: flex;
    align-items: center;
    gap: 8px;
    margin: 0 0 20px 0;
    padding: 0 0 12px 0;
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
    border-bottom: 2px solid #e5e7eb;
    
    .el-icon {
      font-size: 18px;
      color: #3b82f6;
    }
  }
  
  .el-form-item {
    margin-bottom: 20px;
    
    :deep(.el-form-item__label) {
      font-weight: 500;
      color: #374151;
    }
    
    :deep(.el-input__wrapper) {
      border-radius: 6px;
      transition: all 0.2s ease;
      
      &:hover {
        border-color: #9ca3af;
      }
      
      &.is-focus {
        border-color: #3b82f6;
        box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
      }
    }
    
    :deep(.el-select) {
      width: 100%;
      
      .el-input__wrapper {
        border-radius: 6px;
        transition: all 0.2s ease;
        
        &:hover {
          border-color: #9ca3af;
        }
        
        &.is-focus {
          border-color: #3b82f6;
          box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
        }
      }
    }
    
    :deep(.el-radio-group) {
      .el-radio {
        margin-right: 24px;
        
        .el-radio__label {
          font-weight: 500;
          color: #374151;
        }
        
        &.is-checked {
          .el-radio__label {
            color: #3b82f6;
          }
        }
      }
    }
  }
}
</style>
