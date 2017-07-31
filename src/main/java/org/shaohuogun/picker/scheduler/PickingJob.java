package org.shaohuogun.picker.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.shaohuogun.picker.plugin.quartz.QuartzConfig;
import org.shaohuogun.picker.processor.service.ProcessorService;
import org.shaohuogun.picker.request.model.AsyncRequest;
import org.shaohuogun.picker.request.service.RequestService;
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
public class PickingJob implements Job {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${quartz.job.picking.repeat-interval}")
	private long repeatInterval;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			Scheduler scheduler = jobExecutionContext.getScheduler();
			SchedulerContext schedulerContext = scheduler.getContext();
			ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
			RequestService requestService = (RequestService) applicationContext.getBean("requestService");
			AsyncRequest request = requestService.getRequestByStatus(AsyncRequest.STATUS_INITIAL);
			if (request == null) {
				return;
			}

			ProcessorService processorService = (ProcessorService) applicationContext.getBean("processorService");
			processorService.process(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Bean(name = "pickingBean")
	public JobDetailFactoryBean pickingBean() {
		return QuartzConfig.createJobDetail(this.getClass());
	}

	@Bean(name = "pickingTrigger")
	public SimpleTriggerFactoryBean pickingTrigger(@Qualifier("pickingBean") JobDetail jobDetail) {
		return QuartzConfig.createSimpleTrigger(jobDetail, repeatInterval);
	}

}
