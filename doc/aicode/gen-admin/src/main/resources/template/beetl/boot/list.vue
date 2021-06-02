<template>
    <div>
        <Card>
            <div class="search-con search-con-top">
                <Form :model="form" :label-width="80" inline>
                    <%for(field in t.fields){%>
                    <%if(field.isQuery == 1){%>
                    <FormItem label="${field.chinaName}">
                        <Input v-model="form.${field.name}"></Input>
                    </FormItem>
                    <%}}%>
                    <Button @click="handleSearch" class="search-btn" type="primary">
                        <Icon type="search"/>
                        搜索
                    </Button>
                </Form>
                <div>
                    <Button type="primary" icon="plus" @click="handleCreate">新增</Button> &nbsp;&nbsp;
                    <Button type="primary" icon="android-delete" @click="handleDelete">删除</Button>
                </div>
            </div>
            <tables ref="tables" v-model="tableData" :columns="columns" @on-search="handleSearch"
                    @on-update="handleUpdate" @on-detail="handleDetail" @on-selection-change="selectionChange"/>
        </Card>
        <${strutil.toLowerCase(g.entityName)}Info ref="${strutil.toLowerCase(g.entityName)}InfoRef" ></${strutil.toLowerCase(g.entityName)}Info>
    </div>
</template>

<script>
    import Tables from '_c/tables'
    import {L, D} from '@/libs/api.request'
    import ${strutil.toLowerCase(g.entityName)}Info from './${strutil.toLowerCase(g.entityName)}-info'
    import {getIds} from '@/libs/util'

    export default {
        name: '${strutil.toLowerCase(g.entityName)}-list',
        components: {
            Tables,
            ${strutil.toLowerCase(g.entityName)}Info
        },
        data () {
            return {
                columns: [
                    <%for(field in t.fields){%>
                    <%if(field.isShowList == 1){%>
                    {title: '${field.chinaName}', key: '${field.name}'},
                    <%}}%>
                    {
                        title: '操作',
                        key: 'handle',
                        minWidth: 200,
                        options: ['update', 'detail']
                    }
                ],
                tableData: {
                    rows: [],
                    total: 0
                },
                form: {
                    map: {}
                },
                selectedData: [],
                infoIsShow: false
            }
        },
        methods: {
            handleUpdate (param) {
                this.$refs.${strutil.toLowerCase(g.entityName)}InfoRef.openModel('update', param.row)
            },
            handleDetail (param) {
                this.$refs.${strutil.toLowerCase(g.entityName)}InfoRef.openModel('detail', param.row)
            },
            handleCreate () {
                this.$refs.${strutil.toLowerCase(g.entityName)}InfoRef.openModel('create')
            },
            handleDelete () {
                D('${strutil.toLowerCase(g.entityName)}', getIds(this.selectedData)).then(data => {
                    this.$Message.success(data)
                    this.handleSearch()
                })
            },
            selectionChange (selection) {
                this.selectedData = selection
            },
            handleSearch (page, pageSize) {
                if (isNaN(page)) {
                    page = 1
                }
                var param = {
                    page: page,
                    pageSize: pageSize,
                    model: this.form,
                    map: this.form.map
                }
                L('${strutil.toLowerCase(g.entityName)}', param).then(data => {
                    this.tableData = data
                })
            }
        },
        mounted () {
            this.handleSearch()
        }
    }
</script>

<style>

</style>
