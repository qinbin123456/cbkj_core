layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form
    var id = $("input[name='id']").val();

    if(null != id && id.trim() != "" && id != undefined && id != "null"){
        var url = path_+"admin/update/findObj";
        $.getJSON(url,{id:id},function(result){
            var status = result.status;
            if(status){
                var data = result.data;
                form.val('example', {
                    "name": data.name // "name": "value"
                    ,"phone": data.phone
                    ,"roleID": data.roles[0].rid
                    ,"sex":data.sex
                    ,"address": data.address
                    ,"email": data.email
                })
            }else {
                parent.layer.msg(result.message);
            }
        });

    }
    form.render();
})