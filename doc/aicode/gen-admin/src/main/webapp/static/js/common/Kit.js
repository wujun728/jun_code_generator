var Kit = {
    dictMap: new Object(),
    getDictName: function (dict, num) {
        if (!Kit.dictMap[dict + '_' + num]) {
            $.ajax({
                url: '/dict/getDataByKey/' + dict + '/' + num,
                async: false,
                dataType: 'JSON',
                success: function (data) {
                    Kit.dictMap[dict + '_' + num] = data.name;
                }

            })
        }
        return Kit.dictMap[dict + '_' + num];
    },
    optionAdd: function (element, arr, defultVal, flag) {
        if (flag) {
            element.empty();
        } else {
            element.empty().append("<option value=''>请选择</option>");
        }
        if (arr) {
            for (var i = 0; i < arr.length; i++) {
                var select = "";
                if (defultVal) {
                    if (arr[i].areaCode == defultVal)
                        select = "selected='selected'";
                }

                var option = "<option value='" + arr[i].id + "' " + select + ">"
                    + arr[i].name + "</option>";
                element.append(option);
            }
        } else {
            element.empty().append("<option value=''>请选择</option>");
        }
    },
    getAreaData: function (id, type) {
        $.getJSON('/sysareainfo/list/' + id, function (data) {
            Kit.optionAdd($('#' + type), data);
        })
    },
    areaMap: new Object(),
    getAreaName: function (id) {
        if (!Kit.areaMap[id]) {
            $.ajax({
                url: '/sysareainfo/query/' + id,
                async: false,
                dataType: 'JSON',
                success: function (data) {
                    Kit.areaMap[id] = data.name;
                }

            })
        }
        return Kit.areaMap[id];
    }
}
//省市县初始化
$(function () {
    Kit.getAreaData('100000', 'province');
    $("#province").on('change', function () {
        Kit.getAreaData($("#province").val(), 'city');
    });
    $("#city").on('change', function () {
        Kit.getAreaData($("#city").val(), 'area');
    });
});
