package com.db.loanstorage;

import com.db.loanstorage.controller.LoanController;
import com.db.loanstorage.dao.LoanRepository;
import com.db.loanstorage.exception.InvalidLoanException;
import com.db.loanstorage.model.Loan;
import com.db.loanstorage.service.LoanService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanStorageApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private LoanController loanController;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private LoanService loanService;
	

	@Test
	void testLoanValidateAndStore_successful() {
		LocalDate paymentDate = getLocalDate(2023,6,5);
		ResponseEntity responseEntity = loanController.addLoan(createLoan("L1","C1","LEN1",new BigDecimal(10000),new BigDecimal(10000),paymentDate,new BigDecimal(1),paymentDate.plusMonths(1),new BigDecimal(0.01d),""));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity.getStatusCode());
		List<Loan> loanList =loanController.findAllLoans();
		Assertions.assertEquals("L1",loanList.get(0).getLoanId());
		
	}

	@Test
	void testLoanValidateAndStoreWhenDueDatePast() {
		try {
			ResponseEntity responseEntity = loanController.addLoan(createLoan("L1","C1","LEND2",new BigDecimal(2500),new BigDecimal(1200),LocalDate.now(),new BigDecimal(1),LocalDate.now().plusMonths(-1),new BigDecimal(1.5),""));
		}catch (InvalidLoanException ie) {
			Assertions.assertEquals("Payment date can't be greater than the due date.", ie.getMessage());
		}
	}
	
	
	@Test
    public void testAggregateLoanDataByLender() {
		LocalDate paymentDate = getLocalDate(2023,6,5);
		ResponseEntity responseEntity = loanController.addLoan(createLoan("L1","C1","LEN1",new BigDecimal(10000),new BigDecimal(10000),paymentDate,new BigDecimal(1),paymentDate.plusMonths(1),new BigDecimal(0.01d),""));

		paymentDate = getLocalDate(2023,6,1);
		ResponseEntity responseEntity2 = loanController.addLoan(createLoan("L2","C1","LEN1",new BigDecimal(20000),new BigDecimal(5000),paymentDate,new BigDecimal(1),paymentDate.plusMonths(2).plusDays(5),new BigDecimal(0.01d),""));
		paymentDate = getLocalDate(2023,4,4);
		ResponseEntity responseEntity3 = loanController.addLoan(createLoan("L3","C2","LEN2",new BigDecimal(50000),new BigDecimal(30000),paymentDate,new BigDecimal(2),paymentDate.plusMonths(1),new BigDecimal(0.02d),""));
		ResponseEntity responseEntity4 = loanController.addLoan(createLoan("L4","C3","LEN2",new BigDecimal(50000),new BigDecimal(30000),paymentDate,new BigDecimal(2),paymentDate.plusMonths(1),new BigDecimal(0.02d),""));

        // Mock the result of the aggregate query
        List<Map<String, Object>> expectedData = Arrays.asList(
                Map.of("lenderId", "LEN1", "interestPctPerDay", 1, "customerId", "C1", "remainingAmount", 15000),
                Map.of("lenderId", "LEN2", "interestPctPerDay", 2, "customerId", "C2", "remainingAmount", 30000),
                Map.of("lenderId", "LEN2", "interestPctPerDay", 2, "customerId", "C3", "remainingAmount", 30000)
        );
        //when(loanRepository.aggregateLoanDataByLender()).thenReturn(expectedData);

        // Perform the aggregation
        List<Map<String, Object>> aggregatedData = loanService.aggregateLoanDataByLender();

        // Assert the result
        Assertions.assertEquals(expectedData.size(), aggregatedData.size());
        for (int i = 0; i < expectedData.size(); i++) {
            Map<String, Object> expectedEntry = expectedData.get(i);
            Map<String, Object> actualEntry = aggregatedData.get(i);

            Assertions.assertEquals(expectedEntry.get("lenderId").toString(), actualEntry.get("lenderId").toString());
            Assertions.assertEquals(expectedEntry.get("interestPctPerDay").toString(), actualEntry.get("interestPctPerDay").toString());
            Assertions.assertEquals(expectedEntry.get("customerId").toString(), actualEntry.get("customerId").toString());
            Assertions.assertEquals(expectedEntry.get("remainingAmount").toString(), actualEntry.get("remainingAmount").toString());
        }
    }

	private Loan createLoan(String loanId, String customerId, String lenderId, BigDecimal amount, BigDecimal remainingAmount, LocalDate paymentDate, BigDecimal interestPctPerDay, LocalDate dueDate, BigDecimal penaltyPctPerDay, String cancel){
		Loan loan = new Loan();
		loan.setLoanId(loanId);
		loan.setCustomerId(customerId);
		loan.setLenderId(lenderId);
		loan.setAmount(amount);
		loan.setRemainingAmount(remainingAmount);
		loan.setPaymentDate(paymentDate);
		loan.setInterestPctPerDay(interestPctPerDay);
		loan.setDueDate(dueDate);
		loan.setPenaltyPctPerDay(penaltyPctPerDay);
		loan.setCancel(cancel);
		return loan;
	}

	public static LocalDate getLocalDate(int year,int month, int day){
		LocalDate localDate = LocalDate.of(year,month,day);
		return localDate;
	}




}
