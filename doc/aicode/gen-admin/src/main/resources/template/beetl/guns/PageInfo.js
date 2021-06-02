/**
 * 初始化${g.name}详情对话框
 */
var ${g.entityName}InfoDlg = {
    ${strutil.toUpperCase(strutil.subStringTo (g.entityName,0,1))}${strutil.subString  (g.entityName,1)}InfoData : {}
    // 校验条件
    validateFields: {
    }
};

/**
 * 清除数据
 */
${g.entityName}InfoDlg.clearData = function() {
    this.${strutil.toUpperCase(strutil.subStringTo (g.entityName,0,1))}${strutil.subString  (g.entityName,1)}InfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
${g.entityName}InfoDlg.set = function(key, val) {
    this.${strutil.toUpperCase(strutil.subStringTo (g.entityName,0,1))}${strutil.subString  (g.entityName,1)}InfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
${g.entityName}InfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
${g.entityName}InfoDlg.close = function() {
    parent.layer.close(window.parent.${g.entityName}.layerIndex);
}

/**
 * 收集数据
 */
${g.entityName}InfoDlg.collectData = function() {
    this.<%for(field in t.fields){%>.set('${field.name}')<%}%>;
}

/**
 * 提交添加
 */
${g.entityName}InfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/${strutil.toLowerCase(g.entityName)}/add", function(data){
        Feng.success("添加成功!");
        window.parent.${g.entityName}.table.refresh();
        ${g.entityName}InfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.${strutil.toUpperCase(strutil.subStringTo (g.entityName,0,1))}${strutil.subString  (g.entityName,1)}InfoData);
    ajax.start();
}

/**
 * 提交修改
 */
${g.entityName}InfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/${strutil.toLowerCase(g.entityName)}/update", function(data){
        Feng.success("修改成功!");
        window.parent.${g.entityName}.table.refresh();
        ${g.entityName}InfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.${strutil.toUpperCase(strutil.subStringTo (g.entityName,0,1))}${strutil.subString  (g.entityName,1)}InfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("${g.lowerEntityName}Form", ${g.entityName}InfoDlg.validateFields);
});
