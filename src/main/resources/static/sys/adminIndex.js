var table,lodingIndex,tableIns;

layui.use('table', function(){
    table = layui.table;
    //加载图标
    lodingIndex = layer.load(2, {time: 10*1000});
    tableIns = table.render({
        elem: '#admin'
        ,url:'admin/getPages'
        ,page: { //
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            ,curr: 1
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
        ,cols: [[//破除
            {checkbox: true}
            ,{field:'name',width:150, title: '管理员'}
            ,{field:'phone',width:180,align:'center', title: '手机号码'}
            ,{field:'sex',width:100, align:'center',title: '性别', sort: true,templet:
                    function(d){
                        if(d.sex == "M")
                            return "男";
                        else if(d.sex == "F")
                            return "女";
                        else
                            return "未知";
                    }
            }
            ,{field:'status',width:100, align:'center',title: '状态',templet:
                    function(d){
                        if(d.status == "1")
                            return "正常";
                        else if(d.status == "2")
                            return "禁用";
                        else
                            return "未知";
                    }
            }
            ,{field:'rnamess', width:200, title: '角色' }
            ,{field:'createName', width:150, title: '创建者', sort: true}
            ,{field:'create_date', title: '创建时间',align:'center', templet:
                    function(d){
                        return dateFormatter(d.create_date);
                    }
            }

        ]]
        ,id:"adminTable"
        ,done: function(res, curr, count){

            layer.close(lodingIndex);
        }
        ,height: 'full'

    });

    table.on('tool(adminTable)', function(obj){
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
                    name:name.val()
                }
            });
        },
        //新增
        add_:function(){
            updateRow(null,"新增管理员","new");
        },
        update_:function(){

            var checkStatus = table.checkStatus('adminTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要修改的管理员");

            }else if(data.length == 1){
                updateRow(data[0].id,"修改管理员","update");
            }else{
                layer.msg("只能单个进行修改哦");
            }
        },
        updatePwd_:function(){
            var checkStatus = table.checkStatus('adminTable'),data=checkStatus.data;
            if(data.length<=0){
                layer.msg("请选择需要重置密码的用户");
            }else{
                var params = {};
                var ids = "";
                data.forEach(function(obj,index){
                    ids += obj.id+",";
                });
                ids = ids.substring(0,ids.length-1);

                parent.layer.prompt({title: '输入新的密码', formType: 1}, function(pass, index){
                    params.ids = ids;
                    params.newPwd = pass;

                    $.post("../admin/changePwd",params,function(result){
                        if(result.status){
                            $(".layui-laypage-btn").click();
                            parent.layer.close(index);
                        }else{
                            parent.layer.msg(result.message);
                        }
                    },"JSON");

                });
            }
        },
        updateStatus_:function(){
            var checkStatus = table.checkStatus('adminTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要操作的管理员");

            }else if(data.length == 1){
                var obj = data[0];
                var params = {};
                params.id = obj.id;
                params.status = (obj.status==1?2:1);
                var text = "";
                if(obj.status == 1){
                    text = "禁用";
                }else{
                    text = "启用";
                }

                parent.layer.confirm('确定需要 '+text+' 管理员[ <span style="color:red">'+obj.name+'</span> ]吗？', {
                    btn: ['确定','取消'] //按钮
                }, function(){

                    $.post("../admin/changeStatus",params,function(result){
                        if(result.status){
                            $(".layui-laypage-btn").click();
                            parent.layer.closeAll();
                        }else{
                            parent.layer.msg(result.message);
                        }
                    },"JSON");

                }, function(){

                });

            }else{
                layer.msg("目前只能单个操作哦");
            }

        },
        delete_:function(){
            var checkStatus = table.checkStatus('adminTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要删除的管理员");
            }else{
                var names = "";
                var params = {};
                var ids = "";
                data.forEach(function(obj,index){
                   names += obj.name+"、";
                   ids += obj.id+",";
                });
                names = names.substring(0,names.length-1);
                ids = ids.substring(0,ids.length-1);
                parent.layer.confirm('确定需要删除管理员[ <span style="color:red">'+names+'</span> ]吗？', {
                    btn: ['删除','取消'] //按钮
                }, function(){

                    params.ids = ids;
                    $.post("../admin/deleteLis",params,function(result){
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

    var url = type === "new"?"admin/insert/toPage":"admin/update/toPage?ID="+ids;

    var qb_Index = parent.layer.open({
        type: 2,
        content:[url, 'no'],
        title:title,
        area:["480px","450px"],
        id:"QB_UPDATE",
        shade:0.7,
        scrollbar: false,
        maxmin: false,
        fix: false,
        end:function(){//窗口关闭执行回调
            if(type === "new"){
                lodingIndex = layer.load(2, {time: 10*1000});
                tableIns.reload({page: {curr: 1 }});
            }else{
                $(".layui-laypage-btn").click();
            }
        },
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            var body = parent.layer.getChildFrame('body', index);
            var params = {};
            var formBody = $(body).find(".layui-form");
            if(formBody.length > 0){
                params.token = $(formBody).find("input[name='token']").val();
                params.name = $(formBody).find("input[name='name']").val();
                params.phone = $(formBody).find("input[name='phone']").val();
                params.rid =  $(formBody).find("select[name='roleID']").val();
                params.sex = $(formBody).find("input[name='sex']:checked").val();
                params.address = $(formBody).find("input[name='address']").val();
                params.email = $(formBody).find("input[name='email']").val();
                params.id = $(formBody).find("input[name='id']").val();

                if(null == params.name || params.name.trim() == ""){
                    parent.layer.msg("管理员必填哦");
                    return ;
                }
                if(null == params.phone || params.phone.trim() == ""){
                    parent.layer.msg("手机号码必填哦");
                    return ;
                }
                var url = type === "new"?"../admin/insert":"../admin/update";
                $.post(url,params,function(result){
                    if(result.status){
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