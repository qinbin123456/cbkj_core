var isPwd = true;
layui.use(['layer', 'form'], function() {

    var layer = layui.layer,form = layui.form;
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.key == "Enter"){ // enter 键
            loginF();
        }
    };
    $(".layui-btn").click(function() {
        loginF();
    });
    form.render();
});

$(function() {
    var name = $.cookie("name_");
    var pwd = $.cookie("pwd_");
    //cookie 获取登录信息填入
    if (name != null && typeof(name) != "undefined" && name.trim() != "" && name != "null") {
        $("input[name='userName']").val(name);
        $("input[name='password']").val(pwd);
        $("input[name='rememberMe']").attr("checked","true");
        isPwd = false;
    }
    if (window != top){
        top.location.href = location.href;
    }

});

function loginF(){
    var param = {};
    param.name = $("input[name='userName']").val();
    param.pwd = $("input[name='password']").val();
    $.cookie("name_", null);
    $.cookie("pwd_", null);
    if (param.name == null || typeof(param.name ) == "undefined" || param.name.trim() == "") {
        layer.msg("请填写管理员名称!!");
        return;
    }
    if (param.pwd != null && typeof(param.pwd ) != "undefined" && param.pwd.trim() != "") {
        if(isPwd){//缓存获取不需要再次加密
            param.pwd = md5(param.pwd);
        }
        $("input[name='password']").val(param.pwd);
    }else{
        layer.msg("请填写管理员密码!!");
        return;
    }
    if ($("input[name='rememberMe']").is(":checked")) {

        $.cookie("name_", param.name, {expires: 7});
        $.cookie("pwd_", param.pwd, {expires: 7});
    }
    $(".msg").html("");
    $.post("login", param, function (result) {
        if(result.status != "success"){
            $("input[name='password']").val("");
            $(".msg").html(result.msg);
        }else{
            window.location.href="toMain";
        }
    }, "json");
}

