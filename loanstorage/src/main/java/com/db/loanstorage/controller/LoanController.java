package com.db.loanstorage.controller;

import com.db.loanstorage.exception.InvalidLoanException;
import com.db.loanstorage.model.Loan;
import com.db.loanstorage.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanController {
    @Autowired
    LoanService loanService;
    

    @PostMapping("/loan")
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        try {
            Loan savedLoan = loanService.addLoan(loan);
            return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
        } catch (InvalidLoanException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    

    @GetMapping("/loan")
    public List<Loan> findAllLoans(){
        return loanService.findAll();
    }
}
