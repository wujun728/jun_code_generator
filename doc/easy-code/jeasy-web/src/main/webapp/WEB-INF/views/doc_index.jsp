<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>接口文档</title>
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
    <script type="text/javascript" src="/static/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/demo.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true" title="功能模块" style="width:15%;">
        <ul id="menu" class="easyui-tree"></ul>
    </div>

    <div data-options="region:'east',split:true" title="接口测试" style="width:40%;">
        <div class="easyui-panel" style="padding:5px;">
            <a id="btn-submit" href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'" onclick="openWindow()" disable>提交测试</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">Edit</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'">Help</a>
            <a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">About</a>
        </div>
        <div id="mm1" style="width:150px;">
            <div data-options="iconCls:'icon-undo'">Undo</div>
            <div data-options="iconCls:'icon-redo'">Redo</div>
            <div class="menu-sep"></div>
            <div>Cut</div>
            <div>Copy</div>
            <div>Paste</div>
            <div class="menu-sep"></div>
            <div>
                <span>Toolbar</span>
                <div>
                    <div>Address</div>
                    <div>Link</div>
                    <div>Navigation Toolbar</div>
                    <div>Bookmark Toolbar</div>
                    <div class="menu-sep"></div>
                    <div>New Toolbar...</div>
                </div>
            </div>
            <div data-options="iconCls:'icon-remove'">Delete</div>
            <div>Select All</div>
        </div>
        <div id="mm2" style="width:100px;">
            <div>Help</div>
            <div>Update</div>
            <div>About</div>
        </div>
        <div id="mm3">
            <div>History</div>
            <div>Faq</div>
            <div>Our Team</div>
        </div>
        <div id="w" class="easyui-window" title="提交测试" data-options="modal:true,closed:true,iconCls:'icon-save',onBeforeClose:function(){$('#ff').form('clear');}" style="width:60%;height:55%;padding:10px;">
            <div class="easyui-panel">
                <div style="padding:10px 60px 20px 60px;" >
                    <form id="ff" class="easyui-form" method="post">
                        <table cellpadding="5">
                            <tr>
                                <td>请求URL:</td>
                                <td><input class="easyui-textbox" type="text" name="url" style="width:500px;" readonly/></td>
                            </tr>
                            <tr>
                                <td>请求方式:</td>
                                <td><input class="easyui-combobox" id="method" name="method" data-options="valueField:'id',textField:'text'" style="width:500px;" /></td>
                            </tr>
                            <tr>
                                <td>接口类型:</td>
                                <td><input class="easyui-combobox" id="urlType" name="urlType" data-options="valueField:'id',textField:'text'" style="width:500px;" /></td>
                            </tr>
                            <tr>
                                <td>body参数:</td>
                                <td><input class="easyui-textbox" type="text" name="bodyParam" data-options="multiline:true" style="width:500px;height:60px" readonly/></td>
                            </tr>
                            <tr>
                                <td>K-V参数:</td>
                                <td><input class="easyui-textbox" id="kvParam" name="kvParam" data-options="multiline:true" style="width:500px;height:60px" readonly/></td>
                            </tr>
                        </table>
                    </form>
                    <div style="text-align:center;padding:5px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitForm()" style="width:80px">Submit</a>
                    </div>
                </div>
            </div>
        </div>
        <table id="hg" class="easyui-propertygrid" title="Header设置">
        </table>
        <table id="tg" class="easyui-treegrid" title="body参数" style="height: 60%;">
            <thead>
            <tr>
                <th data-options="field:'name',width:100,align:'left'">键</th>
                <th data-options="field:'value',width:80,align:'left',editor:'text'">值</th>
                <th data-options="field:'type',width:60,align:'left'">类型</th>
                <th data-options="field:'desc',width:80,align:'left'">描述</th>
                <th data-options="field:'rule',width:120,align:'left'">规则</th>
            </tr>
            </thead>
        </table>
        <table id="pg" class="easyui-propertygrid" title="K-V参数">
        </table>
        <%--<div style="margin:20px 0;">--%>
        <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()">Edit</a>--%>
        <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">Save</a>--%>
        <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">Cancel</a>--%>
        <%--</div>--%>
    </div>
    <div data-options="region:'center',title:'接口列表'">
        <div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
            <div title="使用说明文档" data-options="href:'/static/html/about.html'" style="padding:10px"></div>
        </div>
    </div>
</div>
</body>
</html>
