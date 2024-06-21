package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountExistsException;
import com.example.exception.BadArgumentsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    /**
     * function to register account based on required requirements
     * 
     * username NOT blank
     * password ATLEAST 4 characters
     * account DOES NOT exist already with username 
     * 
     * @param account
     * @return
     */
    public Account registerAccount(Account account) throws AccountExistsException, BadArgumentsException{
        // retrieving account
        Account checkingAccount = accountRepository.findByUsername(account.getUsername());
        
        // throwing a AccountExistsExcpetion (custom exception) if account exists
        if (checkingAccount != null){
            throw new AccountExistsException("This account already exists");
        }
        
        // throwing a BadArgumentsException (custom) if other requirements are not met
        if (account.getPassword().length() < 4 && account.getUsername().isBlank()){
            throw new BadArgumentsException("Something went wrong");
        }
        
        // assuming everything is fine and we can save the account
        return accountRepository.save(account);
    }


    /**
     * function to verify is user can log in based on requirements
     * 
     * account EXISTS
     * passwords MATCH
     * 
     * @param account
     * @return Account object or null
     */
    public Account verifyAccount(Account account) {
        // querying user
        Account checkingAccount = accountRepository.findByUsername(account.getUsername());

        // checking if requirements are met, if so return object
        if (checkingAccount != null && checkingAccount.getPassword().equals(account.getPassword())){
            return checkingAccount;
        }

        return null;
    }


}
