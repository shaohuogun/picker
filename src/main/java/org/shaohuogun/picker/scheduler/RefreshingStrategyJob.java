package org.shaohuogun.picker.scheduler;

import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.shaohuogun.picker.plugin.quartz.QuartzConfig;
import org.shaohuogun.picker.strategy.model.Strategy;
import org.shaohuogun.picker.strategy.service.StrategyPool;
import org.shaohuogun.picker.strategy.service.StrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class RefreshingStrategyJob implements Job {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${quartz.job.refreshing.strategy.repeat-interval}")
	private long repeatInterval;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			Scheduler scheduler = jobExecutionContext.getScheduler();
			SchedulerContext schedulerContext = scheduler.getContext();
			ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
			StrategyService strategyService = (StrategyService) applicationContext.getBean("strategyService");
			Date refreshTime = StrategyPool.getInstance().getRefreshTime();
			List<Strategy> strategies = strategyService.getFreshStrategies(refreshTime);
			StrategyPool.getInstance().refresh(strategies);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Bean(name = "refreshingStrategyBean")
	public JobDetailFactoryBean refreshingStrategyBean() {
		return QuartzConfig.createJobDetail(this.getClass());
	}

	@Bean(name = "refreshingStrategyTrigger")
	public SimpleTriggerFactoryBean refreshingStrategyTrigger(@Qualifier("refreshingStrategyBean") JobDetail jobDetail) {
		return QuartzConfig.createSimpleTrigger(jobDetail, repeatInterval);
	}

}
