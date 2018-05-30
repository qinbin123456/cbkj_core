layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate;

    var id = $("input[name='id']").val();

    if(null != id && id.trim() != "" && id != undefined && id != "null"){
        $.getJSON("../../admin/edit/findObj",{id:id},function(result){
            var status = result.status;
            if(status){
                var data = result.data;

                form.val('example', {
                    "name": data.name // "name": "value"
                    ,"phone": data.phone
                    ,"roleID": data.roles[0].rid
                    ,"sex":data.sex  //复选框选中状态
                    ,"address": data.address //开关状态
                    ,"email": data.email
                })
            }else {
                parent.layer.msg(result.message);
            }

        });
    }
})