@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    key : 字典的名字
@*/
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">
            ${name}
        </button>
    </div>
    <select class="form-control" id="${id}">
    </select>
</div>
<script type="application/javascript">
    ${'$'}(function () {
        ${'$'}.getJSON('/dict/getKeys/${key}',function(data){
            var htm = '<option value="">-全部-</option>';
            ${'$'}.each(data,function (index,dict) {
                htm += '<option value="'+dict.num+'">'+dict.name+'</option>';
            })
            ${'$'}("#${id}").html(htm);
        });
    })
</script>
