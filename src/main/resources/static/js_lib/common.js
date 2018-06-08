/**
 * 公共基础分页插件
 * @type {{layout: string[], curr: number, groups: number, first: boolean, last: boolean}}
 * @private
 */
var page_ = {
     layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
    ,curr: 1
    ,groups: 1 //只显示 1 个连续页码
    ,first: false //不显示首页
    ,last: false //不显示尾页
};
/**
 * 公共基础表格 扩展可以参考layui 表格扩展
 * @type {{elem: string, page: {layout: string[], curr: number, groups: number, first: boolean, last: boolean}, id: string, done: table_.done, height: string}}
 * @private
 */
var commonTable = {
     elem: '#qb'
    ,page: page_
    ,id:"qbTable"
    ,done: function(res, curr, count){
        layer.close(lodingIndex);
    }
    ,height: 'full'
};