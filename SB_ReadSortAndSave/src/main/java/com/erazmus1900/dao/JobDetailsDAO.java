package com.erazmus1900.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.erazmus1900.model.JobDetails;

@Repository
public class JobDetailsDAO {

	private static final Map<String, JobDetails> jobsMap = new HashMap<String, JobDetails>();

	static {
		initJobDetails();
	}

	private static void initJobDetails() {
		JobDetails job1 = new JobDetails("J01", new Date());
		JobDetails job2 = new JobDetails("J02", new Date());
		JobDetails job3 = new JobDetails("J03", new Date());

		jobsMap.put(job1.getJobNo(), job1);
		jobsMap.put(job2.getJobNo(), job2);
		jobsMap.put(job3.getJobNo(), job3);
	}

	public JobDetails getJobDetails(String jobNo) {
		return jobsMap.get(jobNo);
	}

	public JobDetails addEmployee(JobDetails job) {
		jobsMap.put(job.getJobNo(), job);
		return job;
	}

	public List<JobDetails> getJobsList() {
		Collection<JobDetails> c = jobsMap.values();
		List<JobDetails> list = new ArrayList<JobDetails>();
		list.addAll(c);
		return list;
	}

}
