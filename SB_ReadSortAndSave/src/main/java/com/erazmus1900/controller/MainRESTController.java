package com.erazmus1900.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.erazmus1900.dao.JobDetailsDAO;
import com.erazmus1900.job.ExecuteJob;
import com.erazmus1900.model.JobDetails;

@RestController

public class MainRESTController {

	@Autowired
	private JobDetailsDAO jobDetailsDAO;

	@RequestMapping("/")
	@ResponseBody
	public String welcome() {
		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<JobDetails> getEmployees() {
		List<JobDetails> list = jobDetailsDAO.getJobsList();

		ExecuteJob ej = new ExecuteJob();
		ej.readAndSort();

		return list;
	}

}
