$.ajaxSetup({
    complete:function(XMLHttpRequest,status){
        if(XMLHttpRequest.status == 403){
            window.location.href='403'
        }
        // var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
        // if(sessionstatus=="timeout"){
        //     //如果超时就处理 ，指定要跳转的页面(比如登陆页)
        //     window.location.replace("/login/index.php");
        // }
    }
});