package com.c5p1ng.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.c5p1ng.enums.SeckillStatEnum;
import com.c5p1ng.seckill.dao.SeckillDao;
import com.c5p1ng.seckill.dao.SuccessKilledDao;
import com.c5p1ng.seckill.dto.Exposer;
import com.c5p1ng.seckill.dto.SeckillExecution;
import com.c5p1ng.seckill.entity.Seckill;
import com.c5p1ng.seckill.entity.SuccessKilled;
import com.c5p1ng.seckill.exception.RepeatKillException;
import com.c5p1ng.seckill.exception.SeckillCloseException;
import com.c5p1ng.seckill.exception.SeckillException;
import com.c5p1ng.seckill.service.SeckillService;
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	private final String slat = "sadfkdjkdjf$%^#@*&!Wqqq";
	
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null){
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
			return new Exposer(false,seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" +slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Transactional
	/**
	 * 使用注解控制事务方法的优点：
	 * 1：开发团队达成一致约定，明确标注事务方法的变成风格
	 * 2：保证事务方法的执行时间尽可能短，不要穿插其他网络操作，rpc/http请求或者剥离到事务方法外部
	 * 3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		Date killTime = new Date();
		try {
			int updateCount = seckillDao.reduceNumber(seckillId, killTime);
			if(updateCount <= 0){
				throw new SeckillCloseException("秒杀已经关闭");
			}else{
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0){
					throw new RepeatKillException("重复秒杀");
				}else{
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		}catch(SeckillCloseException e1){
			throw e1;
		}catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

}
