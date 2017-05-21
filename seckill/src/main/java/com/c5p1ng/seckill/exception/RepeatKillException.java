package com.c5p1ng.seckill.exception;
/**
 * 重复秒杀异常（运行期异常）
 * @author 11984
 *
 */
public class RepeatKillException extends SeckillException {

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public RepeatKillException(String message) {
		super(message);
	}

}
