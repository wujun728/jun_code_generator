/**
 * 管理初始化
 */
var TableBaseField = {
    id: "TableBaseFieldTable",	//表格id
        seItem: null,		//选中的条目
        table: null,
        layerIndex: -1
};

/**
 * 初始化表格的列
 */
TableBaseField.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', align: 'center', valign: 'middle'},
        {title: '别名', field: 'alias', align: 'center', valign: 'middle'},
        {title: '字段ID', field: 'fieldId', align: 'center', valign: 'middle'},
        {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
        {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
TableBaseField.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TableBaseField.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
TableBaseField.openAddTableBaseField = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tablebasefield/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
TableBaseField.openTableBaseFieldDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tablebasefield/goto_detail/' + TableBaseField.seItem.id
    });
        this.layerIndex = index;
    }
};

/**
 * 打开查看修改
 */
TableBaseField.openTableBaseFieldUpdate = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tablebasefield/goto_update/' + TableBaseField.seItem.id
    });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
TableBaseField.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tablebasefield/delete", function (data) {
            Feng.success("删除成功!");
            TableBaseField.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tablebasefieldId",this.seItem.id);
        ajax.start();
    }
};

TableBaseField.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询列表
 */
TableBaseField.search = function () {
    var queryData = {};
        queryData['id'] = $("#id").val();
        queryData['userId'] = $("#userId").val();
        queryData['alias'] = $("#alias").val();
        queryData['fieldId'] = $("#fieldId").val();
        queryData['crtUserId'] = $("#crtUserId").val();
        queryData['crtTime'] = $("#crtTime").val();
        queryData['mdfUserId'] = $("#mdfUserId").val();
        queryData['mdfTime'] = $("#mdfTime").val();

    TableBaseField.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TableBaseField.initColumn();
    var table = new BSTable(TableBaseField.id, "/tablebasefield/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TableBaseField.formParams());
    TableBaseField.table = table.init();
});
