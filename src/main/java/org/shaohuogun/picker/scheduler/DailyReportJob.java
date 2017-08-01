package org.shaohuogun.picker.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.shaohuogun.picker.plugin.quartz.QuartzConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class DailyReportJob implements Job {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${quartz.job.cron-expression.daily.report}")
	private String cronExpression;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Hello world from Quartz! Generating the daily report...");

	}

	@Bean(name = "dailyReportBean")
	public JobDetailFactoryBean dailyReportBean() {
		return QuartzConfig.createJobDetail(this.getClass());
	}

	@Bean(name = "dailyReportTrigger")
	public CronTriggerFactoryBean dailyReportTrigger(@Qualifier("dailyReportBean") JobDetail jobDetail) {
		return QuartzConfig.createCronTrigger(jobDetail, cronExpression);
	}

}
