layui.use(['layer', 'form', 'element'], function(){
    var layer = layui.layer;
    var form = layui.form;
    var element = layui.element;
    var $ = layui.jquery;

    var tableSqlIDE;
    var outputEditors = {};
    var outputJson = {};
    var currentGroup = '';
    var templates = [];

    function initTableSqlIDE() {
        tableSqlIDE = CodeMirror.fromTextArea(document.getElementById("tableSql"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-sql",
            lineWrapping: false,
            readOnly: false,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        tableSqlIDE.setSize('100%', '300px');
        var defaultSql = "CREATE TABLE `userinfo` (\n" +
            "  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',\n" +
            "  `username` varchar(255) NOT NULL COMMENT '用户名',\n" +
            "  `addtime` datetime NOT NULL COMMENT '创建时间',\n" +
            "  PRIMARY KEY (`user_id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息'";
        tableSqlIDE.setValue(defaultSql);
    }

    function initOutputEditors() {
        var defaultTabs = ['controller', 'service', 'service_impl', 'dao', 'model', 'mybatis'];
        for (var i = 0; i < defaultTabs.length; i++) {
            var tabName = defaultTabs[i];
            var mode = (tabName === 'mybatis') ? "text/html" : "text/x-java";
            outputEditors[tabName] = CodeMirror.fromTextArea(document.getElementById(tabName + "_ide"), {
                lineNumbers: true,
                matchBrackets: true,
                mode: mode,
                lineWrapping: true,
                readOnly: true,
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
            });
            outputEditors[tabName].setSize('100%', 'auto');
        }
    }

    function loadTemplates() {
        $.ajax({
            type: 'GET',
            url: '/template/all',
            dataType: 'json',
            success: function(res) {
                if (res.code === 200 || res.code === 0) {
                    var templateData = typeof res.data === 'string' ? JSON.parse(res.data) : res.data;
                    templates = templateData;
                    renderTemplateButtons(templateData);
                }
            },
            error: function() {
                $('#templateArea').html('<span style="color:red;">模板加载失败</span>');
            }
        });
    }

    function renderTemplateButtons(templateData) {
        var html = '';
        for (var i = 0; i < templateData.length; i++) {
            var group = templateData[i];
            html += '<span class="template-group-label">' + group.group + '</span>';
            for (var j = 0; j < group.templates.length; j++) {
                var tpl = group.templates[j];
                html += '<button type="button" class="layui-btn layui-btn-sm layui-btn-radius" data-group="' + group.group + '" data-name="' + tpl.name + '">' + tpl.description + '</button>';
            }
            html += ' ';
        }
        $('#templateArea').html(html);

        $('#templateArea .layui-btn').on('click', function(){
            var groupName = $(this).data('group');
            var tplName = $(this).data('name');
            currentGroup = groupName;
            if (outputJson[groupName + '/' + tplName]) {
                showOutputForGroup(groupName);
            }
        });
    }

    function showOutputForGroup(groupName) {
        var groupOutput = {};
        for (var key in outputJson) {
            if (key.startsWith(groupName + '/') || key === 'tableName') continue;
            if (!key.includes('/')) {
                groupOutput[key] = outputJson[key];
            }
        }
        for (var gKey in outputJson) {
            if (gKey.startsWith(groupName + '/')) {
                var shortName = gKey.substring(groupName.length + 1);
                groupOutput[shortName] = outputJson[gKey];
            }
        }
        updateOutputTabs(groupOutput);
    }

    function updateOutputTabs(resultData) {
        var tabTitle = $('#outputTabTitle');
        var tabContent = $('#outputTabContent');
        tabTitle.empty();
        tabContent.empty();

        var keys = Object.keys(resultData).filter(function(k){ return k !== 'tableName' && k !== 'lineNum'; });
        var firstKey = keys[0];

        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
            var isActive = (key === firstKey) ? ' layui-this' : '';
            var showClass = (key === firstKey) ? ' layui-show' : '';

            tabTitle.append('<li class="' + isActive + '">' + key + '</li>');

            var textareaId = 'output_' + key.replace(/[.$]/g, '_');
            tabContent.append('<div class="layui-tab-item' + showClass + '"><textarea id="' + textareaId + '"></textarea></div>');
        }

        element.render('tab', 'outputTab');

        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
            var textareaId = 'output_' + key.replace(/[.$]/g, '_');
            var mode = guessMode(key);
            var editor = CodeMirror.fromTextArea(document.getElementById(textareaId), {
                lineNumbers: true,
                matchBrackets: true,
                mode: mode,
                lineWrapping: true,
                readOnly: true
            });
            editor.setSize('100%', 'auto');
            if (resultData[key]) {
                editor.setValue(resultData[key].trim());
            }
        }
    }

    function guessMode(key) {
        if (key.indexOf('.xml') > -1 || key.indexOf('mybatis') > -1 || key === 'mybatis' || key.indexOf('daoxml') > -1) return "text/html";
        if (key.indexOf('.sql') > -1 || key === 'menu-sql' || key.indexOf('sql') > -1) return "text/x-sql";
        if (key.indexOf('.html') > -1) return "text/html";
        if (key.indexOf('.yml') > -1 || key.indexOf('.json') > -1) return "application/json";
        return "text/x-java";
    }

    function getFormData() {
        var formData = {};
        formData.tableSql = tableSqlIDE.getValue();
        formData.options = {};
        var inputs = $('#configForm [name]');
        inputs.each(function(){
            var name = $(this).attr('name');
            var type = $(this).attr('type');
            if (type === 'checkbox') {
                var checked = $(this).prop('checked');
                if ($(this).attr('lay-skin') === 'switch') {
                    formData.options[name] = checked;
                }
            } else if ($(this).is('select')) {
                formData.options[name] = $(this).val();
            } else {
                formData.options[name] = $(this).val();
            }
        });
        return formData;
    }

    $('#codeGenerateBtn').on('click', function(){
        var formData = getFormData();
        if (!formData.tableSql || formData.tableSql.trim().length === 0) {
            layer.msg('请输入表结构信息', {icon: 2});
            return;
        }
        layer.load(2);
        $.ajax({
            type: 'POST',
            url: '/code/generate',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function(res){
                layer.closeAll('loading');
                if (res.code === 200 || res.code === 0) {
                    outputJson = res.data || {};
                    if (!currentGroup) currentGroup = 'mybatis';
                    showOutputForGroup(currentGroup);
                    layer.msg('代码生成成功', {icon: 1});
                } else {
                    layer.msg(res.msg || '代码生成失败', {icon: 2});
                }
            },
            error: function(xhr){
                layer.closeAll('loading');
                layer.msg('请求失败: ' + xhr.status, {icon: 2});
            }
        });
    });

    $('#copyBtn').on('click', function(){
        var activeTab = $('#outputTabTitle li.layui-this').text();
        var textareaId = 'output_' + activeTab.replace(/[.$]/g, '_');
        var editorEl = document.getElementById(textareaId);
        if (editorEl && editorEl.CodeMirror) {
            var text = editorEl.CodeMirror.getValue();
            if (navigator.clipboard) {
                navigator.clipboard.writeText(text).then(function(){ layer.msg('已复制', {icon: 1}); });
            } else {
                layer.msg('浏览器不支持复制', {icon: 2});
            }
        }
    });

    initTableSqlIDE();
    initOutputEditors();
    loadTemplates();
    form.render();
});