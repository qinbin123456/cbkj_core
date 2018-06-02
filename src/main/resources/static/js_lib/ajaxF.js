var pathName_L = document.location.pathname;
var host_ = "http://"+ window.location.host;
var index_L = pathName_L.substr(1).indexOf("/");
var path_ = host_ + pathName_L.substr(0,index_L+1)+"/";
$.ajaxSetup({
    complete:function(XMLHttpRequest,status){
        if(XMLHttpRequest.status == 403){
            window.location.href = path_+"403";
        }else if(XMLHttpRequest.status != 200){
            window.location.href = path_+"400";
        }
    }
});
