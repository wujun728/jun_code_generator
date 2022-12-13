<template>
  <div>
    <slot></slot>
    <Table ref="selection" :loading="loading" :show-header="showHeader" :highlight-row="singleSelect"
           :columns="tableColumns" :data="tableData" :no-data-text="hasPermission ? (loading ? '加载中...' : (tableData.length == 0 ? '未查找到记录' : '')) : '未经授权, 请联系管理员'" class="margin-bottom" border
           @on-selection-change="handleSelectionChange" @on-current-change="handleCurrentChange"></Table>
    <Page v-if="page" :total="total" :current="pageNo" :page-size="pageSize" show-total show-elevator
          @on-change="handlePageChange" style="float: left; margin-top:5px;" size="small"></Page>
    <Button v-if="singleSelect" type="default" style="margin-left: 10px; margin-top:5px;" @click="handleClearCurrentRow" size="small">清除单选</Button>
  </div>
</template>

<script>
  import consts from '@/utils/consts'

  export default {
    name: 'list',
    props: {
      hasPermission: {
        type: Boolean,
        default: false
      },
      loading: {
        type: Boolean,
        default: false
      },
      page: {
        type: Boolean,
        default: true
      },
      pageNo: {
        type: Number,
        default: 1
      },
      pageSize: {
        type: Number,
        default: consts.PAGE_SIZE
      },
      columns: {
        type: Array,
        default () {
          return []
        }
      },
      data: {
        type: Array,
        default () {
          return []
        }
      },
      total: {
        type: Number,
        default: 0
      },
      showHeader: {
        type: Boolean,
        default: true
      },
      singleSelect: {
        type: Boolean,
        default: false
      },
      crossPageSelect: {
        type: Boolean,
        default: false
      },
      crossPageSelection: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        consts,
        tableData: []
      }
    },
    computed: {
      tableColumns () {
        if (this.singleSelect) {
          return this.columns.filter(function (item) {
            return item.type !== 'selection'
          })
        } else {
          return this.columns
        }
      }
    },
    watch: {
      data: function (data) {
        let currentPageKey = 'page' + this.pageNo + 'Selection'
        if (this.crossPageSelect && this.crossPageSelection.hasOwnProperty(currentPageKey)) {
          let currentPageSelection = this.crossPageSelection[currentPageKey]
          this.tableData = data.map(function (item, index, array) {
            let itemSelect = currentPageSelection.some(function (val, i, arr) {
              return val.id === item.id
            })
            if (itemSelect) {
              return Object.assign(item, {
                _checked: true
              })
            } else {
              return item
            }
          })
        } else {
          this.tableData = data
        }
      }
    },
    methods: {
      handleClearCurrentRow () {
        this.$refs.selection.clearCurrentRow()
      },
      handleSelectionChange (selection) {
        if (this.crossPageSelect) {
          Object.assign(this.crossPageSelection, {
            ['page' + this.pageNo + 'Selection']: selection
          })

          let crossPageSelection = []
          Object.keys(this.crossPageSelection).map((key) => {
            if (this.crossPageSelection[key] && this.crossPageSelection[key].length > 0) {
              crossPageSelection.push(...this.crossPageSelection[key])
            }
          })
          this.$emit('on-selection-change', crossPageSelection)
        } else {
          this.$emit('on-selection-change', selection)
        }
      },
      handlePageChange (pageNo) {
        this.$emit('on-page-change', pageNo)
      },
      handleCurrentChange (currentRow, oldCurrentRow) {
        this.$emit('on-current-change', currentRow, oldCurrentRow)
      }
    }
  }
</script>

<style lang="less">
  @import "styles/index.less";
</style>
