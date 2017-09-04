package org.shaohuogun.picker.plugin.quartz;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	List<Trigger> listOfTrigger;

	@Bean
	public JobFactory jobFactory() {
		AutoWiringJobFactory jobFactory = new AutoWiringJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();

		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setDataSource(dataSource);

		factory.setJobFactory(jobFactory());
		// 将Spring的ApplicationContext添加到SchedulerContext中
		// 让Quartz Job可以通过ApplicationContext获取Spring管理的Bean
		factory.setApplicationContext(applicationContext);
		factory.setApplicationContextSchedulerContextKey("applicationContext");
		factory.setQuartzProperties(quartzProperties());

		// Set all the trigger beans we have defined.
		if ((listOfTrigger != null) && !listOfTrigger.isEmpty()) {
			factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
		}

		return factory;
	}

	public static JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		// Set job's durability to be stored in DB.
		factoryBean.setDurability(true);
		return factoryBean;
	}

	public static SimpleTriggerFactoryBean createSimpleTrigger(JobDetail jobDetail, long repeatInterval) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(repeatInterval);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
		return factoryBean;
	}

	public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
		return factoryBean;
	}

}
