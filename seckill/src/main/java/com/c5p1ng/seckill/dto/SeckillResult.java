package com.c5p1ng.seckill.dto;

/**
 * 封装json结果
 * @author 11984
 *
 * @param <T>
 */
public class SeckillResult<T> {
	private boolean success;
	private T data;
	private String error;
	
	public SeckillResult(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	
	public SeckillResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public T getData() {
		return data;
	}

	public String getError() {
		return error;
	}
	
	
}
