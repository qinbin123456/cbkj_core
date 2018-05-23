package com.example.cbkj_core.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Page implements Serializable{

	private static final long serialVersionUID = 2843053623355369576L;
	
	private Integer limit=10;
	private Integer page=1;
	
	public Page() {};

	public Page(int rows, int page) {
		this.page = page;
		this.limit = rows;
	}
	
	public int getCurrentRow() {
		if (this.page <= 0) return 0;
		return this.limit * (this.page - 1);
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * layui 数据格式
	 * @param code  状态码 0成功 1失败
	 * @param msg   消息
	 * @param count 数据总数量
	 * @param data  数据
	 * @return
	 */
	public static Object getLayuiData(int code,String msg,long count,Object data){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("code",code);
		result.put("msg",msg);
		result.put("count",count);
		result.put("data",data);
		return result;
	}
	

}
