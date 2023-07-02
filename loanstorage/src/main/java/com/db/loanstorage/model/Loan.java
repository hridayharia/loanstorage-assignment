package com.db.loanstorage.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "LOANSTORE")
public class Loan {

    @Id
    private String loanId;

    private String customerId;

    private String lenderId;

    private BigDecimal amount;

    private BigDecimal remainingAmount;

    private LocalDate paymentDate;
    
    private BigDecimal interestPctPerDay;
    
    private LocalDate dueDate;

    private BigDecimal penaltyPctPerDay;

    private String cancel;

    
    public String getLoanId() {
		return loanId;
	}


	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getLenderId() {
		return lenderId;
	}


	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}


	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public BigDecimal getInterestPctPerDay() {
		return interestPctPerDay;
	}


	public void setInterestPctPerDay(BigDecimal interestPctPerDay) {
		this.interestPctPerDay = interestPctPerDay;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public BigDecimal getPenaltyPctPerDay() {
		return penaltyPctPerDay;
	}


	public void setPenaltyPctPerDay(BigDecimal penaltyPctPerDay) {
		this.penaltyPctPerDay = penaltyPctPerDay;
	}


	public String getCancel() {
		return cancel;
	}


	public void setCancel(String cancel) {
		this.cancel = cancel;
	}


	@Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", customerId=" + customerId +
                ", lenderId=" + lenderId +
                ", amount=" + amount +
                ", remainingAmount=" + remainingAmount +
                ", paymentDate='" + paymentDate + '\'' +
                ", interestPctPerDay='" + interestPctPerDay + '\'' +
                ", dueDate=" + dueDate +
                ", penaltyPctPerDay=" + penaltyPctPerDay +
                ", cancel='" + cancel + '\'' +
                '}';
    }
}
