package com.foundit.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foundit.service.JobApplyService;

@RestController
@RequestMapping("/jobs")
public class JobApplyController {
	
	private final JobApplyService jobApplyService;
	 
    public JobApplyController(JobApplyService jobApplyService) {
        this.jobApplyService = jobApplyService;
    }
 
    @PostMapping("/apply-all")
    public String applyToAllJobs() {
        jobApplyService.fetchAndApplyToAllJobs();
        return "Done - check server logs for per-job status";
    }

}
