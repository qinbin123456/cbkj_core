layui.use(['form', 'layedit', 'laydate'], function() {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate;

    var id = $("input[name='rid']").val();

    if(null != id && id.trim() != "" && id != undefined && id != "null"){
        $.getJSON("../../rule/edit/findObj",{id:id},function(result){
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