package com.foundit.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDto {
	
	private String id;
    private Long jobId;
    private String title;
    private CompanyDto company;
    private String companyName;
    private List<LocationDto> locations;
    private ExperienceDto minimumExperience;
    private ExperienceDto maximumExperience;
    private SalaryDto minimumSalary;
    private SalaryDto maximumSalary;
    private Long postedAt;
    private Integer totalApplicants;
    private Integer quickApplyJob;
    private Boolean isApplied;
    private Boolean activeJob;
    private String jdUrl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CompanyDto getCompany() {
		return company;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<LocationDto> getLocations() {
		return locations;
	}
	public void setLocations(List<LocationDto> locations) {
		this.locations = locations;
	}
	public ExperienceDto getMinimumExperience() {
		return minimumExperience;
	}
	public void setMinimumExperience(ExperienceDto minimumExperience) {
		this.minimumExperience = minimumExperience;
	}
	public ExperienceDto getMaximumExperience() {
		return maximumExperience;
	}
	public void setMaximumExperience(ExperienceDto maximumExperience) {
		this.maximumExperience = maximumExperience;
	}
	public SalaryDto getMinimumSalary() {
		return minimumSalary;
	}
	public void setMinimumSalary(SalaryDto minimumSalary) {
		this.minimumSalary = minimumSalary;
	}
	public SalaryDto getMaximumSalary() {
		return maximumSalary;
	}
	public void setMaximumSalary(SalaryDto maximumSalary) {
		this.maximumSalary = maximumSalary;
	}
	public Long getPostedAt() {
		return postedAt;
	}
	public void setPostedAt(Long postedAt) {
		this.postedAt = postedAt;
	}
	public Integer getTotalApplicants() {
		return totalApplicants;
	}
	public void setTotalApplicants(Integer totalApplicants) {
		this.totalApplicants = totalApplicants;
	}
	public Integer getQuickApplyJob() {
		return quickApplyJob;
	}
	public void setQuickApplyJob(Integer quickApplyJob) {
		this.quickApplyJob = quickApplyJob;
	}
	public Boolean getIsApplied() {
		return isApplied;
	}
	public void setIsApplied(Boolean isApplied) {
		this.isApplied = isApplied;
	}
	public Boolean getActiveJob() {
		return activeJob;
	}
	public void setActiveJob(Boolean activeJob) {
		this.activeJob = activeJob;
	}
	public String getJdUrl() {
		return jdUrl;
	}
	public void setJdUrl(String jdUrl) {
		this.jdUrl = jdUrl;
	}
    
    

}
