package com.codereviewtool.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bulkJob")
public class JobController {
  @Autowired JobLauncher jobLauncher;
  @Autowired Job job;

  @PostMapping(value = "/run")
  public void runJob() throws Exception {
    Map<String, JobParameter> map = new HashMap<>();
    map.put("time", new JobParameter(System.currentTimeMillis()));
    JobParameters jobParameters = new JobParameters(map);
    jobLauncher.run(job, jobParameters);
  }
}
