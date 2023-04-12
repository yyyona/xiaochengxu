// 此变量在引用页面的js中声明
// var defaults = {
//     s1: 'province',
//     s2: 'city',
//     s3: 'area',
//     v1: null,
//     v2: null,
//     v3: null
// };

//城市三级联动插件 treeSelect
var $form;
var form;
var $;
layui.define(['jquery', 'form'], function () {
    $ = layui.jquery;
    form = layui.form;
    $form = $('form');
    treeSelect(defaults);
});

function treeSelect(config) {
    config.v1 = config.v1 ? config.v1 : 110000;//如果参数 config 中包含 v1 属性，则使用该属性值，否则将 v1 值设置为 110000
    config.v2 = config.v2 ? config.v2 : 110100;
    config.v3 = config.v3 ? config.v3 : 110101;
    $.each(threeSelectData, function (k, v) {
        appendOptionTo($form.find('select[name=' + config.s1 + ']'), k, v.val, config.v1);//遍历操作，其中 k 表示当前遍历到的元素的键名，v 表示当前遍历到的元素的键值，即一个地区对象。
    });//在 $form 表单中查找名称为 config.s1 的下拉框元素，并进行 jQuery 封装
    form.render();
    cityEvent(config);
    areaEvent(config);
    form.on('select(' + config.s1 + ')', function (data) {
        cityEvent(data);
        form.on('select(' + config.s2 + ')', function (data) {
            areaEvent(data);
        });
    });

    //城市级下拉框选项的更新
    function cityEvent(data) {
        $form.find('select[name=' + config.s2 + ']').html("");
        config.v1 = data.value ? data.value : config.v1;
        $.each(threeSelectData, function (k, v) {
            if (v.val == config.v1) {
                if (v.items) {
                    $.each(v.items, function (kt, vt) {
                        appendOptionTo($form.find('select[name=' + config.s2 + ']'), kt, vt.val, config.v2);
                    });
                }
            }
        });
        form.render();
        config.v2 = $('select[name=' + config.s2 + ']').val();
        areaEvent(config);
    }

    //县（区）级下拉框选项的更新
    function areaEvent(data) {
        $form.find('select[name=' + config.s3 + ']').html("");
        config.v2 = data.value ? data.value : config.v2;
        $.each(threeSelectData, function (k, v) {
            if (v.val == config.v1) {
                if (v.items) {
                    $.each(v.items, function (kt, vt) {
                        if (vt.val == config.v2) {
                            $.each(vt.items, function (ka, va) {
                                appendOptionTo($form.find('select[name=' + config.s3 + ']'), ka, va, config.v3);
                            });
                        }
                    });
                }
            }
        });
        form.render();
        form.on('select(' + config.s3 + ')', function (data) { });
    }

    function appendOptionTo($o, k, v, d) {
        var $opt = $("<option>").text(k).val(v);//创建一个新的选项元素，并设置该选项的显示名称和值，然后将其赋值给变量 $opt
        if (v == d) { $opt.attr("selected", "selected") }//如果当前选项的值等于默认选中项的值，则将该选项设置为选中状态（即默认选中
        $opt.appendTo($o);
    }
}