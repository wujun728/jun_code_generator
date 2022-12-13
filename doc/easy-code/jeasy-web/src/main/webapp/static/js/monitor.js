function refreshMonitor() {
    $('#controllerMonitor').datagrid({
        url:'/monitor/report?type=controller',
        singleSelect:true,
        collapsible:true,
        method:'get',
        loadFilter:function(res) {
            return res.data.entity;
        }
    });

    $('#serviceMonitor').datagrid({
        url:'/monitor/report?type=service',
        singleSelect:true,
        collapsible:true,
        method:'get',
        loadFilter:function(res) {
            return res.data.entity;
        }
    });

    $('#daoMonitor').datagrid({
        url:'/monitor/report?type=dao',
        singleSelect:true,
        collapsible:true,
        method:'get',
        loadFilter:function(res) {
            return res.data.entity;
        }
    });
}

$(function(){
    refreshMonitor();
    var int = self.setInterval("refreshMonitor()",10000); // 开启定时
    // int=window.clearInterval(int); 关闭定时
});
