<template>
  <el-form ref="basicInfoForm" :model="info" :rules="rules" label-width="150px" class="single-column-form">
    <!-- 表基本信息 -->
    <div class="form-section">
      <h4 class="section-title">
        <el-icon><Document /></el-icon>
        表基本信息
      </h4>
      <el-form-item label="表名称" prop="tableName">
        <el-input placeholder="请输入表名称" v-model="info.tableName" />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input placeholder="请输入表描述" v-model="info.tableComment" />
      </el-form-item>
    </div>

    <!-- 代码生成信息 -->
    <div class="form-section">
      <h4 class="section-title">
        <el-icon><Edit /></el-icon>
        代码生成信息
      </h4>
      <el-form-item label="实体类名称" prop="className">
        <el-input placeholder="请输入实体类名称" v-model="info.className" />
      </el-form-item>
      <el-form-item label="作者" prop="functionAuthor">
        <el-input placeholder="请输入作者姓名" v-model="info.functionAuthor" />
      </el-form-item>
    </div>

    <!-- 其他信息 -->
    <div class="form-section">
      <h4 class="section-title">
        <el-icon><Memo /></el-icon>
        其他信息
      </h4>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" :rows="3" placeholder="请输入备注信息" v-model="info.remark"></el-input>
      </el-form-item>
    </div>
  </el-form>
</template>
<script>
import { Document, Edit, Memo } from '@element-plus/icons-vue'

export default {
  name: "BasicInfoForm",
  components: {
    Document,
    Edit,
    Memo
  },
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        tableName: [
          { required: true, message: "请输入表名称", trigger: "blur" }
        ],
        tableComment: [
          { required: true, message: "请输入表描述", trigger: "blur" }
        ],
        className: [
          { required: true, message: "请输入实体类名称", trigger: "blur" }
        ],
        functionAuthor: [
          { required: true, message: "请输入作者", trigger: "blur" }
        ]
      }
    };
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
    
    :deep(.el-textarea__inner) {
      border-radius: 6px;
      transition: all 0.2s ease;
      
      &:hover {
        border-color: #9ca3af;
      }
      
      &:focus {
        border-color: #3b82f6;
        box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
      }
    }
  }
}
</style>
