layui.use(['form', 'layedit', 'laydate'], function() {

    var form = layui.form;
    var id = $("input[name='rid']").val();
    form.render();
    if(null != id && id.trim() != "" && id != undefined && id != "null"){
        $.getJSON(path_+"rule/update/findObj",{id:id},function(result){
            var status = result.status;
            if(status){
                var data = result.data;

                form.val('example', {
                    "rname": data.rname
                    ,"rname_zh": data.rnameZh
                    ,"index_url": data.indexUrl
                    ,"rdescr":data.rdescr

                })
            }else {
                parent.layer.msg(result.message);
            }
        });
    }
})