package com.erazmus1900.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ExecuteJob {

	public void readAndSort() {

		try {

			JobDetail jobReadAndSort = JobBuilder.newJob(JobReadAndSort.class)
					.withIdentity("jobReadAndSort", "groupReadAndSort").build();

			Trigger triggerReadAndSort = TriggerBuilder.newTrigger()
					.withIdentity("cronTriggerReadAndSort", "groupReadAndSort")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?")).build();

			Scheduler schedulerReadAndSort = new StdSchedulerFactory().getScheduler();
			schedulerReadAndSort.start();
			schedulerReadAndSort.scheduleJob(jobReadAndSort, triggerReadAndSort);

			Thread.sleep(100000);

			schedulerReadAndSort.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
