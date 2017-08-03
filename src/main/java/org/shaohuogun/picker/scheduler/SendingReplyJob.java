package org.shaohuogun.picker.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.shaohuogun.picker.plugin.quartz.QuartzConfig;
import org.shaohuogun.picker.reply.model.Reply;
import org.shaohuogun.picker.reply.service.ReplyService;
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
public class SendingReplyJob implements Job {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${quartz.job.repeat-interval.sending.reply}")
	private long repeatInterval;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			Scheduler scheduler = jobExecutionContext.getScheduler();
			SchedulerContext schedulerContext = scheduler.getContext();
			ApplicationContext applicationContext = (ApplicationContext) schedulerContext.get("applicationContext");
			ReplyService replyService = (ReplyService) applicationContext.getBean("replyService");
			Reply reply = replyService.getUnsentReply();
			if (reply == null) {
				return;
			}
			
			replyService.send(reply);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Bean(name = "sendingReplyBean")
	public JobDetailFactoryBean sendingReplyBean() {
		return QuartzConfig.createJobDetail(this.getClass());
	}

	@Bean(name = "sendingReplyTrigger")
	public SimpleTriggerFactoryBean sendingReplyTrigger(@Qualifier("sendingReplyBean") JobDetail jobDetail) {
		return QuartzConfig.createSimpleTrigger(jobDetail, repeatInterval);
	}

}
