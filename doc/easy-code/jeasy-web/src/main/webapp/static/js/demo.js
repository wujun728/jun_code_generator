var editingId;
function edit(row){
    if (row){
        editingId = row.id;
        var nodes = $('#tg').treegrid('getChildren', editingId);
        if (nodes.length == 0) {
            $('#tg').treegrid('beginEdit', editingId);
        }
    }
}
function save(){
    if (editingId != undefined){
        var t = $('#tg');
        t.treegrid('endEdit', editingId);
        editingId = undefined;
        var persons = 0;
        var rows = t.treegrid('getChildren');
        for(var i=0; i<rows.length; i++){
            var p = parseInt(rows[i].persons);
            if (!isNaN(p)){
                persons += p;
            }
        }
        var frow = t.treegrid('getFooterRows')[0];
        frow.persons = persons;
        t.treegrid('reloadFooter');
    }
}
function cancel(){
    if (editingId != undefined){
        $('#tg').treegrid('cancelEdit', editingId);
        editingId = undefined;
    }
}

// 左侧点击触发添加Tab
var i = 0;
function addTab(title){
    i++;
    $('#tt').tabs('add',{
        title: title,
        closable: true,
        content:'<table id="dg'+i+'" class="easyui-datagrid" style="height:40%"' +
                    'data-options="' +
                        'url: \'/doc/listUrl\',' +
                        'method: \'get\',' +
                        'rownumbers:true,' +
                        'singleSelect:true,' +
                        'loadFilter: function(res) {' +
                            'return res.data.entity;' +
                        '},' +
                        'queryParams: {' +
                            'node:\'' + title + '\''+
                        '},' +
                        'onClickRow: function(index, row) {' +
                            'listParams(row);' +
                            '$(\'#btn-submit\').splitbutton(\'enable\');' +
                        '},' +
                        'rowStyler: function(index,row) {' +
                            'if (row.status == \'未开始1\') {' +
                                'return \'color:#FFFF7A;font-weight:bold;\';' +
                            '} else if (row.status == \'已完成1\') {' +
                                'return \'color:#7AFF7A;font-weight:bold;\';' +
                            '}' +
                        '}' +
                    '">' +
                    '<thead>' +
                    '<tr>' +
                        //'<th data-options="field:\'desc\'" width="30%">描述</th>' +
                        '<th data-options="field:\'url\'" width="40%">URL</th>' +
                        // '<th data-options="field:\'version\'" width="10%">Version</th>' +
                        // '<th data-options="field:\'platform\'" width="10%">Platform</th>' +
                        // '<th data-options="field:\'device\'" width="10%">Device</th>' +
                        '<th data-options="field:\'classDesc\',align:\'left\'">类.方法</th>' +
                        '<th data-options="field:\'method\',align:\'left\'">方式</th>' +
                        '<th data-options="field:\'status\',align:\'left\'">状态</th>' +
                        '<th data-options="field:\'author\',align:\'left\'">开发人员</th>' +
                        //'<th data-options="field:\'finish\',align:\'left\'">截止日期</th>' +
                    '</tr>' +
                    '</thead>' +
                '</table>' +
                '<table id="restg'+i+'" title="响应输出" class="easyui-treegrid" style="height:60%">' +
                    '<thead>' +
                        '<tr>' +
                        '<th data-options="field:\'name\'" width="40%">键</th>' +
                        '<th data-options="field:\'value\'" width="20%">值</th>' +
                        '<th data-options="field:\'desc\'" width="30%">描述</th>' +
                        '<th data-options="field:\'type\'" width="30%">类型</th>' +
                        '</tr>' +
                    '</thead>' +
                '</table>'
    });
}

var mycolumns = [[
    {field:'name',title:'键',width:60, resizable:true},
    {field:'value',title:'值',width:100,resizable:true},
    {field:'type',title:'类型',width:60,resizable:true},
    {field:'desc',title:'描述',width:120,resizable:true},
    {field:'rule',title:'规则',width:160,resizable:true}
]];

var headercolumns = [[
    {field:'name',title:'键',width:60, resizable:true},
    {field:'value',title:'值',width:100,resizable:true},
    {field:'desc',title:'描述',width:120,resizable:true},
]];

var url="";
var method="GET";
var restg = "";
var status = "";
function listParams(row) {
    var tabObj = $('#tt');
    var tab = tabObj.tabs('getSelected');
    restg = "restg" + tabObj.tabs('getTabIndex',tab);

    url = row['url'];
    method = row['method'];
    status = row['status'];

    var methodType = 'get'
    if (row['method'].indexOf("POST") != -1) {
        methodType = 'post';
    }

    var headerMap = {};
    if (row['version'] != '') {
        headerMap['version'] = row['version'];
    }

    if (row['platform'] != '') {
        headerMap['platform'] = row['platform'];
    }

    if (row['device'] != '') {
        headerMap['device'] = row['device'];
    }

    $.ajax({
        url: row['url'] + '?display=1',
        type: methodType,
        headers: headerMap,
        dataType: 'json',
        success: function (res) {
            $('#tg').treegrid({
                idField: 'id',
                treeField: 'name',
                animate: true,
                collapsible: true,
                fitColumns: true,
                data: res,
                loadFilter: function (res) {
                    $('#pg').propertygrid({
                        scrollbarSize: 0,
                        columns: mycolumns,
                        data: res.data.entity.kv
                    });

                    $('#hg').propertygrid({
                        scrollbarSize: 0,
                        columns: headercolumns,
                        data: res.data.entity.header
                    });

                    return res.data.entity.body;
                },
                onClickRow: function (row) {
                    edit(row);
                }
            });
        },
        error: function () {
        }
    });

    $('#'+restg).treegrid({
        idField:'id',
        treeField:'name',
        animate: true,
        collapsible: true,
        fitColumns: true,
        traditional: true,
        data:{"code":200,"data":{"message":"success","list":[]}},
        loadFilter:function() {
            return [];
        }
    });
}

function getSelected(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $.messager.alert('Info', row.itemid+":"+row.productid+":"+row.attr1);
    }
}

function getSelections(){
    var ss = [];
    var rows = $('#dg').datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        ss.push('<span>'+row.itemid+":"+row.productid+":"+row.attr1+'</span>');
    }
    $.messager.alert('Info', ss.join('<br/>'));
}

function buildObjMap(tg, nodes) {
    var objMap = {};
    for (var i=0; i<nodes.length; i++) {
        var editingId = nodes[i]['id'];
        tg.treegrid('endEdit', editingId);
        var childrens = nodes[i].children;
        if (typeof(childrens) != 'undefined' && childrens.length > 0) {
            if (nodes[i]['type'] == 'List' || nodes[i]['type'] == 'Set' || nodes[i]['type'].indexOf('[]') != -1) {
                var childrenList = [];
                for (var m=0; m<childrens.length; m++) {
                    var subEditingId1 = childrens[m]['id'];
                    tg.treegrid('endEdit', subEditingId1);

                    if (childrens[m]['type'] == 'String' && childrens[m]['value'] != null && childrens[m]['value'] != '') {
                        childrenList[m] = childrens[m]['value'];
                    } else if (childrens[m]['type'] == 'Long' && childrens[m]['value'] != null && childrens[m]['value'] != '') {
                        childrenList[m] = Number(childrens[m]['value']);
                    } else if (childrens[m]['type'] == 'Integer' && childrens[m]['value'] != null && childrens[m]['value'] != '') {
                        childrenList[m] = Number(childrens[m]['value']);
                    } else if (childrens[m]['type'] == 'Double' && childrens[m]['value'] != null && childrens[m]['value'] != '') {
                        childrenList[m] = parseFloat(childrens[m]['value']);
                    } else if (childrens[m]['type'] == 'Float' && childrens[m]['value'] != null && childrens[m]['value'] != '') {
                        childrenList[m] = parseFloat(childrens[m]['value']);
                    }  else if (childrens[m]['type'] == 'List' || childrens[m]['type'] == 'Set' || childrens[m]['type'].indexOf('[]') != -1) {
                        var childrenList2 = [];
                        var childrens2 = childrens[m].children;
                        for (var k=0; k<childrens2.length; k++) {
                            var subEditingId3 = childrens2[k]['id'];
                            tg.treegrid('endEdit', subEditingId3);
                            if (childrens2[k]['type'] == 'String' && childrens2[k]['value'] != null && childrens2[k]['value'] != '') {
                                childrenList2[k] = childrens2[k]['value'];
                            } else if (childrens2[k]['type'] == 'Long' && childrens2[k]['value'] != null && childrens2[k]['value'] != '') {
                                childrenList2[k] = Number(childrens2[k]['value']);
                            } else if (childrens2[k]['type'] == 'Integer' && childrens2[k]['value'] != null && childrens2[k]['value'] != '') {
                                childrenList2[k] = Number(childrens2[k]['value']);
                            } else if (childrens2[k]['type'] == 'Double' && childrens2[k]['value'] != null && childrens2[k]['value'] != '') {
                                childrenList2[k] = parseFloat(childrens2[k]['value']);
                            } else if (childrens2[k]['type'] == 'Float' && childrens2[k]['value'] != null && childrens2[k]['value'] != '') {
                                childrenList2[k] = parseFloat(childrens2[k]['value']);
                            } else {
                                var subChildrens3 = childrens2[k].children;
                                childrenList2[k] = buildObjMap(tg, subChildrens3);
                            }
                        }
                        childrenList[m] = childrenList2;
                    } else {
                        var subChildrens0 = childrens[m].children;
                        childrenList[m] = buildObjMap(tg, subChildrens0);
                    }
                }
                objMap[nodes[i]['name']] = childrenList;
            } else {
                var childrenObj = {};
                for (var j=0; j<childrens.length; j++) {
                    var subEditingId0 = childrens[j]['id'];
                    tg.treegrid('endEdit', subEditingId0);
                    if (childrens[j]['type'] == 'String' && childrens[j]['value'] != null && childrens[j]['value'] != '') {
                        childrenObj[childrens[j]['name']] = childrens[j]['value'];
                    } else if (childrens[j]['type'] == 'Long' && childrens[j]['value'] != null && childrens[j]['value'] != '') {
                        childrenObj[childrens[j]['name']] = Number(childrens[j]['value']);
                    } else if (childrens[j]['type'] == 'Integer' && childrens[j]['value'] != null && childrens[j]['value'] != '') {
                        childrenObj[childrens[j]['name']] = Number(childrens[j]['value']);
                    } else if (childrens[j]['type'] == 'Double' && childrens[j]['value'] != null && childrens[j]['value'] != '') {
                        childrenObj[childrens[j]['name']] = parseFloat(childrens[j]['value']);
                    } else if (childrens[j]['type'] == 'Float' && childrens[j]['value'] != null && childrens[j]['value'] != '') {
                        childrenObj[childrens[j]['name']] = parseFloat(childrens[j]['value']);
                    } else if (childrens[j]['type'] == 'List' || childrens[j]['type'] == 'Set' || childrens[j]['type'].indexOf('[]') != -1) {
                        var childrenList1 = [];
                        var childrens1 = childrens[j].children;
                        for (var n=0; n<childrens1.length; n++) {
                            var subEditingId2 = childrens1[n]['id'];
                            tg.treegrid('endEdit', subEditingId2);
                            if (childrens1[n]['type'] == 'String' && childrens1[n]['value'] != null && childrens1[n]['value'] != '') {
                                childrenList1[n] = childrens1[n]['value'];
                            } else if (childrens1[n]['type'] == 'Long' && childrens1[n]['value'] != null && childrens1[n]['value'] != '') {
                                childrenList1[n] = Number(childrens1[n]['value']);
                            } else if (childrens1[n]['type'] == 'Integer' && childrens1[n]['value'] != null && childrens1[n]['value'] != '') {
                                childrenList1[n] = Number(childrens1[n]['value']);
                            } else if (childrens1[n]['type'] == 'Double' && childrens1[n]['value'] != null && childrens1[n]['value'] != '') {
                                childrenList1[n] = parseFloat(childrens1[n]['value']);
                            } else if (childrens1[n]['type'] == 'Float' && childrens1[n]['value'] != null && childrens1[n]['value'] != '') {
                                childrenList1[n] = parseFloat(childrens1[n]['value']);
                            } else {
                                var subChildrens2 = childrens1[n].children;
                                childrenList1[n] = buildObjMap(tg, subChildrens2);
                            }
                        }
                        childrenObj[childrens[j]['name']] = childrenList1;
                    } else {
                        var subChildrens1 = childrens[j].children;
                        childrenObj[childrens[j]['name']] = buildObjMap(tg, subChildrens1);
                    }
                }
                objMap[nodes[i]['name']] = childrenObj;
            }
        } else if (nodes[i]['type'] == 'String' && nodes[i]['value'] != '') {
            objMap[nodes[i]['name']] = nodes[i]['value'];
        } else if (nodes[i]['type'] == 'Long' && nodes[i]['value'] != '') {
            objMap[nodes[i]['name']] = Number(nodes[i]['value']);
        } else if (nodes[i]['type'] == 'Integer' && nodes[i]['value'] != '') {
            objMap[nodes[i]['name']] = Number(nodes[i]['value']);
        } else if (nodes[i]['type'] == 'Double' && nodes[i]['value'] != '') {
            objMap[nodes[i]['name']] = parseFloat(nodes[i]['value']);
        } else if (nodes[i]['type'] == 'Float' && nodes[i]['value'] != '') {
            objMap[nodes[i]['name']] = parseFloat(nodes[i]['value']);
        }
    }
    return objMap;
}

var bodyParam = "";
var kvParam = "";
var queryParams = {};
var headerParams = {};
function openWindow() {
    queryParams = {};
    headerParams = {};
    kvParam = "";
    bodyParam = "";

    var tg = $('#tg');
    var tgData = tg.treegrid('getData');
    var bodyObj = buildObjMap(tg, tgData);
    var bodyJson = JSON.stringify(bodyObj);

    bodyParam = "body=" + bodyJson;
    queryParams['body'] = bodyJson;

    var headerData = $('#hg').propertygrid('getData');
    for (var i = 0; i < 8; i++) {
        if (headerData['rows'].length > i && headerData['rows'][i]['value'] != '') {
            headerParams[headerData['rows'][i]['name']] = headerData['rows'][i]['value'];
        }
    }

    var pg = $('#pg');
    var pgData = pg.propertygrid('getRows');
    var tempArr = [];
    for (var k = 0; k < pgData.length; k++) {
        if (pgData[k]['name'].indexOf("[") != -1) {
            var key = pgData[k]['name'].substring(0,pgData[k]['name'].indexOf("["));
            var pgIndex = pgData[k]['name'].charAt(pgData[k]['name'].indexOf("[") + 1);
            if (!(key in queryParams)) {
                tempArr = [];
                queryParams[key] = tempArr;
            }

            if (pgData[k]['value'] != '') {
                tempArr[parseInt(pgIndex)] = pgData[k]['value'];
                queryParams[key] = tempArr;

                if (k == 0) {
                    kvParam = key + "=" + pgData[k]['value'];
                } else {
                    kvParam = kvParam + "&" + key + "=" + pgData[k]['value'];
                }
            }
        } else {
            if (pgData[k]['value'] != '') {
                queryParams[pgData[k]['name']] = pgData[k]['value'];

                if (k == 0) {
                    kvParam = pgData[k]['name'] + "=" + pgData[k]['value'];
                } else {
                    kvParam = kvParam + "&" + pgData[k]['name'] + "=" + pgData[k]['value'];
                }
            }
        }
    }

    $('#w').window('open');
    $('#ff').form('load',{
        url: url,
        method: method,
        bodyParam: bodyParam,
        kvParam: kvParam
    });

    var methodData = [];

    if (method == 'GET') {
        methodData = [
            {id:'1',text:'GET','selected':true}
        ];
    } else if (method == 'POST') {
        methodData = [
            {id:'2',text:'POST','selected':true}
        ];
    } else {
        methodData = [
            {id:'1',text:'GET','selected':true},
            {id:'2',text:'POST'}
        ];
    }

    var urlTypeData = [];
    if (status == '已完成') {
        urlTypeData = [
            {id:'1',text:'DOC接口','selected':true},
            {id:'2',text:'真实接口'}
        ];
    } else {
        urlTypeData = [
            {id:'1',text:'DOC接口','selected':true}
        ];
    }

    $('#method').combobox({
        data:methodData
    });

    $('#urlType').combobox({
        onSelect: function (record) {
            if (record['id'] == '1') {
                if (kvParam == '') {
                    $('#ff').form('load', {
                        kvParam: "doc=" + record['id']
                    });
                } else {
                    $('#ff').form('load', {
                        kvParam: "doc=" + record['id'] + "&" + kvParam
                    });
                }
            } else {
                $('#ff').form('load', {
                    kvParam: kvParam
                });
            }
        },
        onLoadSuccess: function () {
            if (kvParam == '') {
                $('#ff').form('load', {
                    kvParam: "doc=" + $('#urlType').combobox('getValue')
                });
            } else {
                $('#ff').form('load', {
                    kvParam: "doc=" + $('#urlType').combobox('getValue') + "&" + kvParam
                });
            }
        },
        data:urlTypeData
    });
}

function submitForm() {
    var methodType = 'GET';
    var methodVal = $('#method').combobox('getValue');

    if (methodVal == '2') {
        methodType = 'POST';
    }

    queryParams['doc']= $('#urlType').combobox('getValue');

    $.ajax({
        url:url,
        type:methodType,
        data:queryParams,
        headers: headerParams,
        dataType:'json',
        success:function(res){
            $('#'+restg).treegrid({
                idField:'id',
                treeField:'name',
                animate: true,
                collapsible: true,
                fitColumns: true,
                traditional: true,
                data:res,
                loadFilter:function(res) {
                    return res.data.list;
                }
            });
        },
        error:function(){}
    });

    $('#ff').form('clear');
    $('#w').window('close');
}

$.ajaxSettings.traditional = true;

$(function(){
    // 初始化左侧菜单
    $('#menu').tree({
        url:'/doc/menu',
        method:'get',
        animate:true,
        dnd:true,
        loadFilter: function(res){
            return res.data.list;
        },
        onClick: function(node){
            addTab(node.text);
        }
    });
    $('#btn-submit').splitbutton('disable');
});
