package com.c5p1ng.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.c5p1ng.seckill.dao.SeckillDao;
import com.c5p1ng.seckill.entity.Seckill;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * @author 11984
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入dao实现类依赖
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() throws Exception{
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testReduceNumber() throws Exception{
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000L, killTime);
		System.out.println(updateCount);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		List<Seckill> list = seckillDao.queryAll(0, 100);
		for (Seckill seckill : list) {
			System.out.println(seckill);
		}
	}
	
	
}
