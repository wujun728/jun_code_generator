$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/generator/list',
        datatype: "json",
        colModel: [			
			{ label: '表名', name: 'tableName', width: 100, key: true },
			{ label: 'Engine', name: 'engine', width: 70},
			{ label: '表备注', name: 'tableComment', width: 100 },
			{ label: '创建时间', name: 'createTime', width: 100 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			tableName: null
		}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'tableName': vm.q.tableName},
                page:1 
            }).trigger("reloadGrid");
		},
		generator: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			location.href = "sys/generator/code?tables=" + JSON.stringify(tableNames);
		},
		generatorAll: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			confirm('是否覆盖后端接口代码', function(){
			 $.get( "sys/generator/allcode?tables=" + JSON.stringify(tableNames), function(r){
				  if(r.code == 0){
					  alert(r.msg, function(){
                         vm.reload();
                     });
                 }else{
                     alert(r.msg);
                 }
	            });
			});
 		},
 		generatorApi: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			confirm('是否覆盖移动端接口代码', function(){
			 $.get( "sys/generator/apicode?tables=" + JSON.stringify(tableNames), function(r){
				  if(r.code == 0){
					  alert(r.msg, function(){
                         vm.reload();
                     });
                 }else{
                     alert(r.msg);
                 }
	            });
			});
 		},
		update: function() {
			var tableNames = getSelectedRows();
			if(tableNames == null){
				return ;
			}
			  $.get( "sys/generator/update?tables=" + JSON.stringify(tableNames), function(r){
				  if(r.code == 0){
					  alert(r.msg, function(){
                          vm.reload();
                      });
                  }else{
                      alert(r.msg);
                  }
	            });
			 
		}
	}
});

