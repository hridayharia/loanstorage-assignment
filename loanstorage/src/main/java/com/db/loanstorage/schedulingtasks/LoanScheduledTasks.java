
package com.db.loanstorage.schedulingtasks;

import com.db.loanstorage.dao.LoanRepository;
import com.db.loanstorage.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class LoanScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(LoanScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	LoanService loanService;
	
	@Autowired
	LoanRepository loanRepository;

	
	@Scheduled(cron = "${loan.day.schedule}")//currently setup 1440 min (in a day)
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		loanRepository.findAll().stream().forEach(t -> {
			if (t.getDueDate().isBefore(LocalDate.now())) {
				//t.setCancel("Y");
				log.info("Loan where Due Date is gone beyond Today {}", t);
				//loanRepository.save(t);
			}
		});
	}
}