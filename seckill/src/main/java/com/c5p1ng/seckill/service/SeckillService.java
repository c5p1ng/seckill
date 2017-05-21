package com.c5p1ng.seckill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.c5p1ng.seckill.dto.Exposer;
import com.c5p1ng.seckill.dto.SeckillExecution;
import com.c5p1ng.seckill.entity.Seckill;
import com.c5p1ng.seckill.exception.RepeatKillException;
import com.c5p1ng.seckill.exception.SeckillCloseException;
import com.c5p1ng.seckill.exception.SeckillException;
import com.mysql.jdbc.log.Log;

/**
 * 业务接口
 * @author 11984
 *
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开始输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
