package com.foundit.dto;

public class SalaryDto {

	private String currency;
    private Long absoluteValue;
    private Long absoluteMonthlyValue;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getAbsoluteValue() {
		return absoluteValue;
	}
	public void setAbsoluteValue(Long absoluteValue) {
		this.absoluteValue = absoluteValue;
	}
	public Long getAbsoluteMonthlyValue() {
		return absoluteMonthlyValue;
	}
	public void setAbsoluteMonthlyValue(Long absoluteMonthlyValue) {
		this.absoluteMonthlyValue = absoluteMonthlyValue;
	}
    
    
}
