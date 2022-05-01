package main.java.com.goyalsw.modals.account;

import main.java.com.goyalsw.modals.Bank;
import main.java.com.goyalsw.modals.Emi;

import java.util.HashMap;
import java.util.Map;

public class LoanAccount extends Account{
    private static int cnt=1;
    private Double interestRate;
    private Double timePeriod;
    private Double totalPrincipalAmount;
    private Bank bank;
    private Double lumpSumAmount;
    private Map<String, Emi> emiMap;
    private Map<Integer, Double>lumpSumAmountMap;

    public LoanAccount(Bank bank, Double interestRate, Double timePeriod, Double totalPrincipalAmount) {
        super(String.valueOf(cnt));
        cnt++;
        this.interestRate = interestRate;
        this.timePeriod = timePeriod;
        this.totalPrincipalAmount = totalPrincipalAmount;
        this.bank = bank;
        this.lumpSumAmount = (double) 0;
        this.emiMap = new HashMap<>();
        this.lumpSumAmountMap = new HashMap<>();
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Double timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Double getTotalPrincipalAmount() {
        return totalPrincipalAmount;
    }

    public void setTotalPrincipalAmount(Double totalPrincipalAmount) {
        this.totalPrincipalAmount = totalPrincipalAmount;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Double getLumpSumAmount() {
        return this.lumpSumAmount;
    }

    public void setLumpSumAmount(Double amount, int emiNo) {
        this.lumpSumAmount += amount;
        double totalAmount = this.lumpSumAmountMap.getOrDefault(emiNo, (double) 0) + amount;

        this.lumpSumAmountMap.put(emiNo, totalAmount);
    }

    public Map<String, Emi> getEmiMap() {
        return emiMap;
    }

    public void setEmiMap(HashMap<String, Emi> emiMap) {
        this.emiMap = emiMap;
    }

    public Map<Integer, Double> getLumpSumAmountMap() {
        return lumpSumAmountMap;
    }

    public void setLumpSumAmountMap(Map<Integer, Double> lumpSumAmountMap) {
        this.lumpSumAmountMap = lumpSumAmountMap;
    }

    public double getSimpleInterest() {
        return (this.totalPrincipalAmount*this.interestRate*this.timePeriod)/100.0;
    }

    public double getTotalAmountToBePaid() {
        return this.totalPrincipalAmount + this.getSimpleInterest();
    }

    public int getEmiAmount() {
        double amount = this.getTotalAmountToBePaid()/(this.timePeriod * 12);
        return (int) Math.ceil(amount);
    }

    private Emi createEmi(String id, int amount) {
        return new Emi(id, amount);
    }

    public void addEmi(int emiNo) {
        if(!emiMap.containsKey(String.valueOf(emiNo)))
        {
            int start = emiMap.size()+1;
            int emiAmount = this.getEmiAmount();
            for(int i=start; i<=emiNo; i++)
            {
                double leftAmount = this.getTotalAmountToBePaid() - this.lumpSumAmount - emiAmount*(i-1);
                if(Math.ceil(leftAmount) <=0)
                    break;

                if(leftAmount < emiAmount) {
                    emiMap.put(String.valueOf(i), createEmi(String.valueOf(i), (int) Math.ceil(leftAmount)));
                    break;
                }
                else {
                    emiMap.put(String.valueOf(i), createEmi(String.valueOf(i), (int) Math.ceil(emiAmount)));
                }
            }
        }
    }

    public int getTotalEmiAmountPaidBeforeEmiNumber(int emiNo) {
        int emiAmount = 0;
        for(int i=1; i<=emiNo; i++) {
            emiAmount += emiMap.get(String.valueOf(i)).getAmount();
        }

        return emiAmount;
    }

    public int getTotalLumpSumAmountPaidBeforeEmiNumber(int emiNo) {
        int amount = 0;
        for(int i=1; i<=emiNo; i++) {
            amount += (int) Math.ceil(this.lumpSumAmountMap.getOrDefault(i, (double) 0));
        }

        return amount;
    }

    public int getTotalEmiLeft(int emiNo) {
        int emiAmount = this.getEmiAmount();
        double leftAmount = this.getTotalAmountToBePaid() - this.getTotalAmountPaid(emiNo);
        return (int) Math.ceil(leftAmount/emiAmount);
    }

    public int getTotalAmountPaid(int emiNo) {
        return this.getTotalEmiAmountPaidBeforeEmiNumber(emiNo) + this.getTotalLumpSumAmountPaidBeforeEmiNumber(emiNo);
    }
}
