@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <select class="form-control" id="${id}" name="${id}">

        </select>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>
<script type="application/javascript">
    ${'$'}(function () {
        ${'$'}.getJSON('/dict/getKeys/${key}',function(data){
            var htm = '';
            ${'$'}.each(data,function (index,dict) {
                htm += '<option value="'+dict.num+'">'+dict.name+'</option>';
            })
            ${'$'}("#${id}").html(htm);
            @if(isNotEmpty(value)){
                ${'$'}("#${id}").val('${value}');
            @}
        });
    })
</script>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


