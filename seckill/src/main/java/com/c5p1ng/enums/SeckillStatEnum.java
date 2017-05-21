package com.c5p1ng.enums;

import org.apache.ibatis.jdbc.Null;

/**
 * 枚举表述常量数据字段
 * 
 * @author 11984
 *
 */
public enum SeckillStatEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KELL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改");

	private int state;

	private String stateInfo;

	public int getState() {
		return state;
	}

	private SeckillStatEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStatEnum stateOf(int index){
		for(SeckillStatEnum state : values()){
			if(state.getState() == index){
				return state;
			}
		}
		return null;
	}

}
