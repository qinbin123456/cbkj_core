layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form
    var id = $("input[name='mid']").val();

    if(null != id && id.trim() != "" && id != undefined && id != "null"){
        var url = path_+"menu/update/findObj";
        $.getJSON(url,{id:id},function(result){
            var status = result.status;
            if(status){
                var data = result.data;
                var enabledS = (""+data.enabled);
                var menuType = data.menuType;
                form.val('example', {
                    "mname": data.mname
                    ,"url": data.url
                    ,"path": data.path
                    ,"iconCls":data.iconcls
                    ,"parentId": data.parentMid
                    ,"menuType": menuType
                    ,"btnClass": data.btnClass
                    ,"btnType": data.btnType
                    ,"enabledS": enabledS
                })

            }else {
                parent.layer.msg(result.message);
            }
        });

    }
    $(".iconCls").click(function(){


        var ch_Index = parent.layer.open({
            type: 2,
            content:[path_+"menu/iconP"],
            title:"选择图标",
            area:["880px","600px"],
            id:"CHINDEX",
            shade:0.7,
            scrollbar: false,
            maxmin: false,
            fix: false,
            end:function(){

            },
            btn: ['确认','取消']
            ,yes: function(index, layero){
                var body = parent.layer.getChildFrame('body', index);
                var icon = $(body).find(".sel");
                if(icon.length>0){
                    var un = ("xe-"+$(icon).attr("title"));
                    $("input[name='iconCls']").val(un);
                    parent.layer.close(index);
                }else{
                    parent.layer.msg("请选择图标");
                }

            },btn2: function(index, layero){
                //按钮【按钮二】的回调
            }
        });
    });

    $(".btnClass").click(function(){
        var menuType = $("input[name='menuType']:checked").val();
        if(menuType == 2){
            var ch_Index = parent.layer.open({
                type: 2,
                content:[path_+"menu/btnP"],
                title:"选择按钮风格",
                area:["540px","500px"],
                id:"CHINDEX",
                shade:0.9,
                scrollbar: false,
                maxmin: false,
                fix: false,
                end:function(){

                },
                btn: ['确认','取消']
                ,yes: function(index, layero){
                    var body = parent.layer.getChildFrame('body', index);
                    var btn = $(body).find("#sel");

                    if(btn.length>0){
                        var un = ("layui-btn "+$(btn).parent().attr("value"));
                        $("input[name='btnClass']").val(un);
                        parent.layer.close(index);
                    }else{
                        parent.layer.msg("请选择按钮风格");
                    }

                },btn2: function(index, layero){
                    //按钮【按钮二】的回调
                }
            });
        }else{
            parent.layer.msg("按钮风格只针对菜单类型为按钮哦");
        }
    });
})
