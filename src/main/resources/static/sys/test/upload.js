layui.use('upload', function() {
    var $ = layui.jquery
        , upload = layui.upload;

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        , url: '../upload/post2'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            // obj.preview(function (index, file, result) {
            //     $('#demo1').attr('src', result); //图片链接（base64）
            // });
        }
        , done: function (res) {
            if(res.status){
                console.log(res);
                parent.layer.msg("上传成功");
            }else{
                parent.layer.msg("上传失败")
            }
        }
        , error: function () {
           parent.layer.msg("上传失败");
        }
    });
    /**
     * 动态添加一个tab 案例
     */
    $("#addTabTest").click(function(){
          var data = {};
          data.title = "测试添加";
          data.href = "http://www.baidu.com";
          data.icon = "xe-642";
          parent.navtab.tabAdd(data);
    });
})