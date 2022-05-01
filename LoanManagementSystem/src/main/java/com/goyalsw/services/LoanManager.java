package main.java.com.goyalsw.services;

import main.java.com.goyalsw.modals.Bank;
import main.java.com.goyalsw.modals.User;
import main.java.com.goyalsw.modals.account.LoanAccount;
import main.java.com.goyalsw.utilities.OperationTypeUtility;

import java.util.HashMap;
import java.util.Map;

public class LoanManager {
    private static LoanManager loanManager;
    private static final OperationTypeUtility operationTypeUtility = new OperationTypeUtility();
    Map<String, User> userMap;
    Map<String, Bank>bankMap;
    Map<String, LoanAccount>loanAccountMap;

    private LoanManager() {
        userMap = new HashMap<>();
        bankMap = new HashMap<>();
        loanAccountMap = new HashMap<>();
    }

    public static LoanManager getLoanManagerInstance() {
        if(loanManager == null) {
            loanManager = new LoanManager();
        }
        return loanManager;
    }

    public User getUser(String userName) {
        if(!userMap.containsKey(userName)) {
            User user = new User(userName);
            userMap.put(userName, user);
            return user;
        } else {
            return userMap.get(userName);
        }
    }

    public Bank getBank(String bankName) {
        if(!bankMap.containsKey(bankName)) {
            Bank bank = new Bank(bankName);
            bankMap.put(bankName, bank);
            return bank;
        } else {
            return bankMap.get(bankName);
        }
    }

    public LoanAccount getLoanAccount(String key, Bank bank, double principalAmount, double loanPeriod, double rateOfInterest) {
        if(!loanAccountMap.containsKey(key)) {
            LoanAccount loanAccount = new LoanAccount(bank, rateOfInterest, loanPeriod, principalAmount);
            loanAccountMap.put(key, loanAccount);
            return loanAccount;
        } else {
            return loanAccountMap.get(key);
        }
    }

    public void processLoan(String bankName, String userName, double principalAmount, double loanPeriod, double rateOfInterest) {
        User user = getUser(userName);
        Bank bank = getBank(bankName);

        String key = bankName + "_" + userName;
        try {
            if (!loanAccountMap.containsKey(key)) {
                user.setAccount(getLoanAccount(key, bank, principalAmount, loanPeriod, rateOfInterest));
            }
            else {
                throw new Exception("This Loan Account already exists");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void processPayment(String bankName, String userName, double lumpSumAmount, int emiNo) {
        LoanAccount loanAccount = loanAccountMap.get(bankName+"_"+userName);
        loanAccount.addEmi(emiNo);
        loanAccount.setLumpSumAmount(lumpSumAmount, emiNo);
    }

    public String getBalance(String bankName, String userName, int emiNo) {
        LoanAccount loanAccount = loanAccountMap.get(bankName+"_"+userName);
        loanAccount.addEmi(emiNo);
        String amountPaid = String.valueOf(loanAccount.getTotalAmountPaid(emiNo));
        String emiLeft = String.valueOf(loanAccount.getTotalEmiLeft(emiNo));
        return bankName + " " + userName + " " + amountPaid + " " + emiLeft;
    }

    public String getMessageFromOperation(String line) {
        String[] strArr = line.split(" ");

        switch(operationTypeUtility.getOperationTypeFromString(strArr[0])) {
            case LOAN:
                this.processLoan(strArr[1], strArr[2], Math.ceil(Float.parseFloat(strArr[3])), Math.ceil(Float.parseFloat(strArr[4])), Math.ceil(Float.parseFloat(strArr[5])));
                break;
            case PAYMENT:
                this.processPayment(strArr[1], strArr[2], Math.ceil(Float.parseFloat(strArr[3])), Integer.parseInt(strArr[4]));
                break;
            case BALANCE:
                return this.getBalance(strArr[1], strArr[2], Integer.parseInt(strArr[3]));
        }

        return "";
    }
}
