package com.db.loanstorage.dao;

import com.db.loanstorage.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoanDaoImpl implements LoanDao{
    @Override
    public void save(Loan loan) {
        loanMap.put(loan.getLoanId(),loan);
    }

    @Override
    public List<Loan> findAll() {
         return loanMap.values().stream().
                 collect(Collectors.toList());
    }

    @Override
    public Loan findLoan(String loanId) {
        return loanMap.get(loanId);
    }
}
