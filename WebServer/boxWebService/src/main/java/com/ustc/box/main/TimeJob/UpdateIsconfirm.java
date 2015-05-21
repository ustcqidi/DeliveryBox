package com.ustc.box.main.TimeJob;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ustc.box.main.service.DeliveryService;

public class UpdateIsconfirm implements TimerJobManager{

	@Autowired
	private DeliveryService deliveryService;
	
	private static final Logger LOGGER = Logger
			.getLogger(UpdateIsconfirm.class);
	@Override
	public void run() {
		LOGGER.info("定时刷新未确认的盒子开始>>>>>>");
		Long startTime = System.currentTimeMillis();
		deliveryService.deleteCancelBox();
		Long endTime = System.currentTimeMillis();
		LOGGER.info("定时刷新结束<<<<<<,耗时:"+(endTime - startTime)+"ms");
	}

}
