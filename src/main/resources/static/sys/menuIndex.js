var table,lodingIndex,tableIns;

layui.use('table', function(){
    table = layui.table;
    //加载图标
    lodingIndex = layer.load(2, {time: 10*1000});

    var form = layui.form
    form.on('switch(sexStatus)', function(data){
        var status = (this.checked?2:1);
        var mid = data.value;
        var params = {};
        params.mid = mid;
        params.enabled = status;
        $.post("../menu/changeEnabled",params,function(result){
            if(!result.status){
                layer.msg(result.message);
            }else{
                $(".layui-laypage-btn").click();
            }
        },"JSON");

    });
    tableIns = table.render({
        elem: '#qb'
        ,url:'menu/getPages'
        ,page: page_
        ,cols: [[//破除
            {checkbox: true}
            ,{field:'mname',width:150, title: '菜单名称'}
            ,{field:'url',width:150, title: '过滤路径'}
            ,{field:'path',width:150, title: '请求路径', sort: true}
            ,{field:'enabled',width:90, templet: '#switchTpl',align:'center',title: '状态',width:100}
            ,{field:'menu_type', width:90,align:'center', title: '类型' ,templet:
                    function(d){
                        if(d.menu_type == "1")
                            return "菜单";
                        else if(d.menu_type == "2")
                            return "按钮";
                        else
                            return "未知";
                    }
            }
            ,{field:'parentName', width:150,align:'center', title: '上级菜单', sort: true}
            ,{field:'createName', width:100,align:'center', title: '创建者', sort: true}
            ,{field:'create_date', title: '创建时间',align:'center', templet:
                    function(d){
                        return dateFormatter(d.create_date);
                    }
            }

        ]]
        ,id:"qbTable"
        ,done: function(res, curr, count){
            //判断行是否有权限操作
            var statusWitch = $("div[data-type='updateStatus_']");
            if(statusWitch.length<=0){
                $("input[name='enabled']").attr("disabled","disabled");
            }

            layer.close(lodingIndex);
        }
        ,height: 'full'

    });

    table.on('tool(qbTable)', function(obj){
        var data = obj.data;
        // 单行数据操作
        if(obj.event === 'detail'){

        }
    });

    var $ = layui.$, active = {
        reload: function(){
            lodingIndex = layer.load(2, {time: 10*1000});
            var name = $('#name');
            //执行重载
            tableIns.reload({
                page: {curr: 1 }
                ,where: {
                    mname:name.val()
                }
            });
        },
        //新增
        add_:function(){
            updateRow(null,"新增菜单","new");
        },
        update_:function(){

            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要修改的菜单");

            }else if(data.length == 1){
                updateRow(data[0].mid,"修改菜单","update");
            }else{
                layer.msg("只能单个进行修改哦");
            }
        },
        updateStatus_:function(){
            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要操作的菜单");

            }else if(data.length == 1){
                var obj = data[0];
                var params = {};
                params.mid = obj.mid;
                params.enabled = (obj.enabled==1?2:1);

                $.post("../menu/changeEnabled",params,function(result){
                    if(result.status){
                        $(".layui-laypage-btn").click();
                        parent.layer.closeAll();
                    }else{
                        parent.layer.msg(result.message);
                    }
                },"JSON");

            }else{
                layer.msg("目前只能单个操作哦");
            }

        },
        delete_:function(){
            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要删除的菜单");
            }else{
                var names = "";
                var params = {};
                var ids = "";
                data.forEach(function(obj,index){
                    names += obj.mname+"、";
                    ids += obj.mid+",";
                });
                names = names.substring(0,names.length-1);
                ids = ids.substring(0,ids.length-1);
                parent.layer.confirm('确定需要删除菜单[ <span style="color:red">'+names+'</span> ]吗？', {
                    btn: ['删除','取消'] //按钮
                }, function(){

                    params.ids = ids;
                    $.post("../menu/deleteLis",params,function(result){
                        if(result.status){
                            $(".layui-laypage-btn").click();
                            parent.layer.closeAll();
                        }else{
                            parent.layer.msg(result.message);
                        }
                    },"JSON");

                }, function(){

                });
            }
        }
    };

    $('.btns .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});

/**
 * 新增或者修改执行
 * @param ids
 * @param title
 * @param type
 */
function updateRow(ids,title,type){

    var url = type === "new"?"menu/insert/toPage":"menu/update/toPage?ID="+ids;

    var qb_Index = parent.layer.open({
        type: 2,
        content:[url,'no'],
        title:title,
        area:["560px","600px"],
        id:"QB_UPDATE",
        shade:0.7,
        scrollbar: false,
        maxmin: false,
        fix: false,
        end:function(){

        },
        btn: ['确定', '取消']
        ,yes: function(index, layero){

            var body = parent.layer.getChildFrame('body', index);
            var params = {};
            var formBody = $(body).find(".layui-form");

            if(formBody.length > 0){
                params.mid = $(body).find("input[name='mid']").val();
                params.token = $(body).find("input[name='token']").val();
                params.mname = $(body).find("input[name='mname']").val();
                params.url = $(body).find("input[name='url']").val();
                params.path = $(body).find("input[name='path']").val();
                params.iconcls = $(body).find("input[name='iconCls']").val();
                params.enabled = $(body).find("input[name='enabledS']:checked").val();
                params.parentMid = $(body).find("select[name='parentId']").val();
                params.menuType = $(body).find("input[name='menuType']:checked").val();
                params.btnClass = $(body).find("input[name='btnClass']").val();
                params.btnType = $(body).find("input[name='btnType']").val();

                if(null == params.mname || params.mname.trim() == ""){
                    parent.layer.msg("菜单名称必填哦");
                    return ;
                }
                if(null == params.url || params.url.trim() == ""){
                    parent.layer.msg("过滤路径必填哦");
                    return ;
                }
                if(null == params.path || params.path.trim() == ""){
                    parent.layer.msg("请求路径必填哦");
                    return ;
                }
                if(null == params.iconcls || params.iconcls.trim() == ""){
                    parent.layer.msg("菜单图标必选哦");
                    return ;
                }
                if(params.menuType == 2){
                    if(null == params.btnClass || params.btnClass.trim() == ""){
                        parent.layer.msg("菜单风格必选哦");
                        return ;
                    }
                    if(null == params.btnType || params.btnType.trim() == ""){
                        parent.layer.msg("按钮函数必填哦");
                        return ;
                    }
                }

                var url = type === "new"?"../menu/insert":"../menu/update";
                $.post(url,params,function(result){
                    if(result.status){
                        if(type === "new"){
                            lodingIndex = layer.load(2, {time: 10*1000});
                            tableIns.reload({page: {curr: 1 }});
                        }else{
                            $(".layui-laypage-btn").click();
                        }
                        parent.layer.close(qb_Index);
                    }else{
                        parent.layer.msg(result.message);
                    }
                },"json");
            }else{
                parent.layer.close(qb_Index);
            }
        },btn2: function(index, layero){
            //按钮【按钮二】的回调
        }
    });
}