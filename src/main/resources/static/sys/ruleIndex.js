var table,lodingIndex,tableIns;

layui.use('table', function(){
    table = layui.table;
    //加载图标
    lodingIndex = layer.load(2, {time: 10*1000});
    tableIns = table.render({
        elem: '#qb'
        ,url:'rule/getPages'
        ,page: page_
        ,cols: [[//破除
            {checkbox: true}
            ,{field:'rname_zh',width:180, title: '中文名'}
            ,{field:'rname',width:180, title: '英文名'}
            ,{field:'index_url',width:200,title: '首页地址'}
            ,{field:'rdescr',width:200,title: '描述'}
            ,{field:'createName', width:150,align:'center', title: '创建者', sort: true}
            ,{field:'create_date', title: '创建时间',align:'center'}
        ]]
        ,id:"qbTable"
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
                    rname:name.val()
                }
            });
        },
        //新增
        add_:function(){
            updateRow(null,"新增角色","new");
        },
        update_:function(){

            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要修改的角色");

            }else if(data.length == 1){
                updateRow(data[0].rid,"修改角色","update");
            }else{
                layer.msg("只能单个进行修改哦");
            }
        },
        updateQue_:function(){
            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length<=0){
                layer.msg("请选择需要设置权限的角色");
            }else if(data.length == 1){
                authority(data[0].rid);
            }else{
                layer.msg("只能单个进行操作哦");
            }
        },
        delete_:function(){
            var checkStatus = table.checkStatus('qbTable'),data=checkStatus.data;
            if(data.length <= 0){
                layer.msg("请选择需要删除的角色");
            }else if(data.length == 1){
                var names = "";
                var params = {};
                var ids = "";
                data.forEach(function(obj,index){
                    names += obj.rname_zh+"、";
                    ids += obj.rid+",";
                });
                names = names.substring(0,names.length-1);
                ids = ids.substring(0,ids.length-1);
                parent.layer.confirm('确定需要删除管理员[ <span style="color:red">'+names+'</span> ]吗？', {
                    btn: ['删除','取消'] //按钮
                }, function(){

                    params.ids = ids;
                    $.post("../rule/deleteLis",params,function(result){
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
                layer.msg("只能单个进行删除哦");
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

    var url = type === "new"?"rule/insert/toPage":"rule/update/toPage?ID="+ids;
    var qb_Index = parent.layer.open({
        type: 2,
        content:[url, 'no'],
        title:title,
        area:["480px","400px"],
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
            if(formBody.length > 0) {
                params.token = $(formBody).find("input[name='token']").val();
                params.rid = $(formBody).find("input[name='rid']").val();
                params.rname = $(formBody).find("input[name='rname']").val();
                params.rnameZh = $(formBody).find("input[name='rname_zh']").val();
                params.indexUrl = $(formBody).find("input[name='index_url']").val();
                params.rdescr = $(formBody).find("textarea[name='rdescr']").val();
                if (null == params.rnameZh || params.rnameZh.trim() == "") {
                    parent.layer.msg("角色名称必填哦");
                    return;
                }
                if (null == params.rname || params.rname.trim() == "") {
                    parent.layer.msg("英文角色名称必填哦");
                    return;
                }
                var url = type === "new" ? "../rule/insert" : "../rule/update";
                $.post(url, params, function (result) {
                    if (result.status) {
                        if (type === "new") {
                            lodingIndex = layer.load(2, {time: 10 * 1000});
                            tableIns.reload({page: {curr: 1}});
                        } else {
                            $(".layui-laypage-btn").click();
                        }
                        parent.layer.close(qb_Index);
                    } else {
                        parent.layer.msg(result.message);
                    }
                }, "json");
            }

        },btn2: function(index, layero){
            //按钮【按钮二】的回调
        }

    });
}

function authority(ids){

    var url = "rule/authority/toPage?ID="+ids;
    var qb_Index = parent.layer.open({
        type: 2,
        content:[url],
        title:"权限设置",
        area:["580px","600px"],
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
            if(formBody.length > 0) {

                var ids="";
                $(formBody).find("input[type='checkbox']:checked").each(function(){
                    var val = $(this).val();
                    if(null != val && val != undefined && val != "no" && val!="on"){
                        ids += (val+",");
                    }
                })
                if(ids != ""){
                    ids = ids.substring(0,ids.length-1);
                }
                params.token = $(formBody).find("input[name='token']").val();
                params.rid = $(formBody).find("input[name='rid']").val();
                params.mids = ids;
                var url = "../rule/authority";
                $.post(url, params, function (result) {
                    if (result.status) {

                        // lodingIndex = layer.load(2, {time: 10 * 1000});
                        // tableIns.reload({page: {curr: 1}});
                        parent.layer.close(qb_Index);
                    } else {
                        parent.layer.msg(result.message);
                    }
                }, "json");
            }
        },btn2: function(index, layero){
            //取消需要做些什么呢
        }

    });
}
