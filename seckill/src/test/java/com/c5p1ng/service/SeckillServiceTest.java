package com.c5p1ng.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.c5p1ng.seckill.dto.Exposer;
import com.c5p1ng.seckill.dto.SeckillExecution;
import com.c5p1ng.seckill.entity.Seckill;
import com.c5p1ng.seckill.exception.RepeatKillException;
import com.c5p1ng.seckill.exception.SeckillCloseException;
import com.c5p1ng.seckill.exception.SeckillException;
import com.c5p1ng.seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml" })
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() throws Exception {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetById() throws Exception {
		long id = 1000;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
	}

	// 集成测试代码完整逻辑，注意代码的可重复性
	@Test
	public void testSeckillLogic() {
		long id = 1001;
		long userPhone = 15764210010L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if (exposer.isExposed()) {
			logger.info("exposer={}", exposer);
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id,
						userPhone, md5);
				logger.info("execution={}", execution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.warn("exposer={}", exposer);
		}
	}

	// @Test
	// public void testExecuteSeckillByProcedure(){
	// long seckillId = 1001;
	// long userPhone = 13556233400L;
	// Exposer exposer = seckillService.exportSeckillUrl(seckillId);
	// if(exposer.isExposed()){
	// String md5 = exposer.getMd5();
	// SeckillExecution execution =
	// seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
	// logger.info(execution.getStateInfo());
	// }
	//
	//
	// }

	@Test
	public void testExportSeckillUrl() throws Exception {
		long id = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}", exposer);
	}

	@Test
	public void testExecuteSeckill() throws Exception {
		long id = 1000;
		long phone = 1350217128L;
		String md5 = "039682714a004d08a4lb1324d17c6260";
		try {
			SeckillExecution execution = seckillService.executeSeckill(id,
					phone, md5);
			logger.info("result={}", execution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e2) {
			logger.error(e2.getMessage());
		}

	}
}
