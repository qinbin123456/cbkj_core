$(function(){

    $(".layui-btn").click(function(){
       $("#sel").remove();
       $(this).append("<i class='iconfont_' id='sel'>&#xe63c;</i>");
    });

})