package com.cristi.result;

public class ResultResp {

	private Integer code;//1:成功;

	private Object data;// 数据对象
	
	private String desc;//
	
	public ResultResp() {

    }
	
	public ResultResp(Integer code , Object data , String desc) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
