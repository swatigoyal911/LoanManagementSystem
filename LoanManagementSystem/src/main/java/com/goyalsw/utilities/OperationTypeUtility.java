package main.java.com.goyalsw.utilities;

import main.java.com.goyalsw.modals.enums.OperationType;

public class OperationTypeUtility {
    public OperationType getOperationTypeFromString(String str) {
        switch(str) {
            case "LOAN":
                return OperationType.LOAN;
            case "PAYMENT":
                return OperationType.PAYMENT;
            case "BALANCE":
                return OperationType.BALANCE;
            default:
                return OperationType.NONE;
        }
    }
}
