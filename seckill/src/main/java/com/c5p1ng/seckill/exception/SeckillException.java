package com.c5p1ng.seckill.exception;
/**
 * 秒杀相关业务异常
 * @author 11984
 *
 */
public class SeckillException extends RuntimeException {

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

}
