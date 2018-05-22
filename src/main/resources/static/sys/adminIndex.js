layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#admin'
        ,url:'/demo/table/user/'
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            ,curr: 1
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页

        }
        ,cols: [[
            ,{field:'name', width:100, title: '用户名'}
            ,{field:'sex', width:80, title: '性别', sort: true}
            ,{field:'status', width:80, title: '状态'}
            ,{field:'roleName',  title: '角色', }
            ,{field:'createName', width:130, title: '创建者', sort: true}
            ,{field:'create_date', title: '创建时间', minWidth: 190}
        ]]

    });
});