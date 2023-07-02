package com.db.loanstorage.dao;

import com.db.loanstorage.model.Loan;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,String> {
	
	@Query("SELECT l.lenderId AS lenderId, l.interestPctPerDay AS interestPctPerDay, " +
	           "l.customerId AS customerId, SUM(l.remainingAmount) AS remainingAmount " +
	           "FROM Loan l GROUP BY l.lenderId, l.interestPctPerDay, l.customerId"
	           + " ORDER BY l.customerId,l.lenderId, l.interestPctPerDay")
	    List<Map<String, Object>> aggregateLoanDataByLender();
	
}
