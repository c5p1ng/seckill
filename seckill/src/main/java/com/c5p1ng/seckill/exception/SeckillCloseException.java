package com.c5p1ng.seckill.exception;
/**
 * 秒杀关闭异常
 * @author 11984
 *
 */
public class SeckillCloseException extends SeckillException {

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
