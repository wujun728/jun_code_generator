/**
 * 模板管理管理初始化
 */
var Template = {
    id: "TemplateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Template.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'Id', field: 'id', align: 'center', valign: 'middle'},
        {title: '模板名称', field: 'templateName', align: 'center', valign: 'middle'},
        {title: '模板类型', field: 'templateType', align: 'center', valign: 'middle'},
        {title: '所属组', field: 'groupId', align: 'center', valign: 'middle'},
        {title: '模板描述', field: 'templateDesc', align: 'center', valign: 'middle'},
        {title: '模板路径', field: 'templatePath', align: 'center', valign: 'middle'},
        {title: '本地路径', field: 'localPath', align: 'center', valign: 'middle'},
        {title: '文件名称', field: 'fileName', align: 'center', valign: 'middle'},
        {title: '创建人', field: 'crtUserName', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'crtTime', align: 'center', valign: 'middle'},
        {title: '修改人', field: 'mdfUserName', align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'mdfTime', align: 'center', valign: 'middle'},
        {title: '详情', field: 'id', align: 'center', valign: 'middle',formatter: function (value, row, index) {
            return '<button type="button" class="btn btn-primary button-margin" onclick="Template.detail('+value+')"><i class="fa fa-binoculars"></i>&nbsp;详情 </button>';
        }}
    ];
};



Template.detail = function (id) {
    var index = layer.open({
        type: 2,
        title: '添加模板管理',
        area: ['100%', '100%'],
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/template/goto_detail/'+id
    });
    this.layerIndex = index;
};

Template.formParams = function () {
    var queryData = {};
    queryData['version'] = 2;
    queryData['originalId'] = $("#originalId").val();
    return queryData;
};

/**
 * 查询模板管理列表
 */
Template.search = function () {
    Template.table.refresh({query: Template.formParams()});
};

$(function () {
    var defaultColunms = Template.initColumn();
    var table = new BSTable(Template.id, "/template/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Template.formParams());
    Template.table = table.init();
});
