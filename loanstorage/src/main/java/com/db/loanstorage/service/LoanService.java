package com.db.loanstorage.service;

import com.db.loanstorage.dao.LoanDao;
import com.db.loanstorage.dao.LoanRepository;
import com.db.loanstorage.exception.InvalidLoanException;
import com.db.loanstorage.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanService {

	private static final Logger log = LoggerFactory.getLogger(LoanService.class);

	@Autowired
	LoanDao loanDao;

	@Autowired
	LoanRepository loanRepository;

	public boolean isValid(Loan loan) {
		if (validatePaymentDate(loan)) {
			Optional<Loan> exsitingLoan = loanRepository.findById(loan.getLoanId());
			if (exsitingLoan.isPresent()) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}

	/*
	 * private boolean validateVersion(Loan trade,Loan oldTrade) { //validation 1
	 * During transmission if the // lower version is being received by the store it
	 * will reject the trade and throw an exception. if(trade.getVersion() >=
	 * oldTrade.getVersion()){ return true; } return false; }
	 */

	// 2. Store should not allow the loan which has less due date than payment date
	private boolean validatePaymentDate(Loan loan) {
		return loan.getPaymentDate().isBefore(loan.getDueDate()) ? true : false;
	}

	public List<Loan> findAll() {
		return loanRepository.findAll();
	}
	
	public Loan addLoan(Loan loan) throws InvalidLoanException {
        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            throw new InvalidLoanException("Payment date can't be greater than the due date.");
        }
        Loan savedLoan = loanRepository.save(loan);

        if (LocalDate.now().isAfter(loan.getDueDate())) {
        	log.info("Loan with ID {0} has crossed the due date.", loan.getLoanId());
        }

        return savedLoan;
    }
	
	public List<Map<String, Object>> aggregateLoanDataByLender() {
        return loanRepository.aggregateLoanDataByLender();
    }
	
	public void checkForDueDate() {

		loanRepository.findAll().stream().forEach(t -> {
			if (!validatePaymentDate(t)) {
				//t.setCancel("Y");
				log.info("Loan where Due Date is gone beyond Today {}", t);
				//loanRepository.save(t);
			}
		});
	}

}
