var xthree;
layui.use(['form'], function () {
    var form = layui.form;
    var id = $("input[name='rid']").val();
    if(null != id && id.trim() != "" && id != undefined && id != "null"){
       xthree = new layuiXtree({
            elem: 'xtree3'                  //必填三兄弟之老大
            , form: form                    //必填三兄弟之这才是真老大
            , data: path_+'/rule/authority/findMenu?id='+id //必填三兄弟之这也算是老大
            , isopen: true  //加载完毕后的展开状态，默认值：true
            , ckall: true    //启用全选功能，默认值：false
            , ckallback: function () {

            } //全选框状态改变后执行的回调函数
            , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
                open: "&#xe623;"       //节点打开的图标
                , close: "&#xe625;"    //节点关闭的图标
                , end: "&#xe655;"      //末尾节点的图标
            }
            , color: {       //三种图标颜色，独立配色，更改几个都可以
                open: "#c2c2c2"        //节点图标打开的颜色
                , close: "#393D49"     //节点图标关闭的颜色
                , end: "#828282"       //末级节点图标的颜色
            }
            , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
                // console.log(data.elem); //得到checkbox原始DOM对象
                // console.log(data.elem.checked); //开关是否开启，true或者false
                // console.log(data.value); //开关value值，也可以通过data.elem.value得到
                // console.log(data.othis); //得到美化后的DOM对象
            }
        });
    }

})
