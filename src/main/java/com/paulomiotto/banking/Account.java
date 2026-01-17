package com.paulomiotto.banking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Account {
    private final String accountNumber;
    private final Customer owner;
    private BigDecimal balance;

    public Account(String accountNumber, Customer owner) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null/blank");
        }
        this.accountNumber = accountNumber;
        this.owner = Objects.requireNonNull(owner, "Owner cannot be null");
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public String accountNumber() {
        return accountNumber;
    }

    public Customer owner() {
        return owner;
    }

    public BigDecimal balance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        amount = normalizeAmount(amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be > 0");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        amount = normalizeAmount(amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be > 0");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance = balance.subtract(amount);
    }

    private BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Account{accountNumber='%s', owner=%s, balance=%s}"
                .formatted(accountNumber, owner, balance);
    }
}
