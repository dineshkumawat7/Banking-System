package com.bank.account.service.enums;

public enum AccountType {
    SAVINGS("Savings Account", "A savings account that earns interest."),
    Salary_Account("Salary Account","Special zero-balance account used by employers to deposit salaries"),
    Current_Account("Current Account", "A current account account for daily transactions."),
    FIXED_DEPOSIT("Fixed Deposit Account", "A fixed deposit account with a higher interest rate for locked-in deposits."),
    BUSINESS("Business Account", "An account designed for business transactions."),
    JOINT("Joint Account", "An account shared between two or more individuals.");

    private final String displayName;
    private final String description;

    AccountType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

}
