package com.foundit.dto;

public class CanJobApplyResponseDto {
	
	private Integer status;
    private String statusText;
    private ApiResponseDto apiResponse;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public ApiResponseDto getApiResponse() {
		return apiResponse;
	}
	public void setApiResponse(ApiResponseDto apiResponse) {
		this.apiResponse = apiResponse;
	}
    
    

}
