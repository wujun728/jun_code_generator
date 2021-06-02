<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="layui-form layuimini-form" lay-filter="currentForm">
    <input name="id" type="hidden" />
    <div class="layui-form-item">
        <label class="layui-form-label required">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" lay-reqtext="用户名不能为空" placeholder="请输入用户名" value=""
                   class="layui-input">
            <tip>填写自己管理账号的名称。</tip>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">手机</label>
        <div class="layui-input-block">
            <input type="number" name="phone" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机" value=""
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">选择角色</label>
        <div class="layui-input-block">
            <input type="email" name="email" placeholder="请输入邮箱" value="" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
        </div>
    </div>
</div>
<script src="/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.$;

        var id = ${id}

        $.ajax({
            url: "/user/get/" + id,
            dataType: "json",
            success: function (res) {
                form.val("currentForm", res.data);
            }
        })

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            $.ajax({
                url: "/user/update",
                data: data.field,
                type: "post",
                success: function (res) {
                    if (res.code == 0) {
                        var parentTable = parent.layui.table;
                        parent.layer.msg(res.msg)
                        parentTable.reload("currentTableId")
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                    } else {
                        layer.msg(res.msg)
                    }
                }
            })
        })
    });
</script>
</body>
</html>