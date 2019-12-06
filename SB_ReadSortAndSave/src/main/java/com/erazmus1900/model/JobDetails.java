package com.erazmus1900.model;

import java.util.Date;

public class JobDetails {

	private String jobNo;
	private Date jobDateTime;

	public JobDetails() {

	}

	public JobDetails(String jobNo, Date jobDateTime) {
		this.jobNo = jobNo;
		this.jobDateTime = jobDateTime;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Date getJobDateTime() {
		return jobDateTime;
	}

	public void setJobDateTime(Date jobDateTime) {
		this.jobDateTime = jobDateTime;
	}

}
