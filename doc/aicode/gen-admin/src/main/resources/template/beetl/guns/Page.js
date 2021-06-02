/**
 * ${g.name}管理初始化
 */
var ${g.entityName} = {
    id: "${g.entityName}Table",	//表格id
        seItem: null,		//选中的条目
        table: null,
        layerIndex: -1
};

/**
 * 初始化表格的列
 */
${g.entityName}.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
        <%for(field in t.fields){%>
        <%if(field.isShowList == 1){%>
        {title: '${field.chinaName}', field: '${field.name}', align: 'center', valign: 'middle'},
        <%}}%>
    ];
};

/**
 * 检查是否选中
 */
${g.entityName}.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ${g.entityName}.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加${g.name}
 */
${g.entityName}.openAdd${g.entityName} = function () {
    var index = layer.open({
        type: 2,
        title: '添加${g.name}',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/${strutil.toLowerCase(g.entityName)}/goto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看${g.name}详情
 */
${g.entityName}.open${g.entityName}Detail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '${g.name}详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/${strutil.toLowerCase(g.entityName)}/goto_update/' + ${g.entityName}.seItem.id
    });
        this.layerIndex = index;
    }
};

/**
 * 删除${g.name}
 */
${g.entityName}.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/${strutil.toLowerCase(g.entityName)}/delete", function (data) {
            Feng.success("删除成功!");
            ${g.entityName}.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("${strutil.toLowerCase(g.entityName)}Id",this.seItem.id);
        ajax.start();
    }
};

${g.entityName}.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 查询${g.name}列表
 */
${g.entityName}.search = function () {
    var queryData = {};
    <%for(field in t.fields){%>
    <%if(field.isQuery == 1){%>
        queryData['${field.chinaName}'] = \$("#${field.chinaName}").val();
    <%}}%>

    ${g.entityName}.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ${g.entityName}.initColumn();
    var table = new BSTable(${g.entityName}.id, "/${strutil.toLowerCase(g.entityName)}/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(${g.entityName}.formParams());
    ${g.entityName}.table = table.init();
});
