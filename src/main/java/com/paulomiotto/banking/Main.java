package com.paulomiotto.banking;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Customer paulo = new Customer("CUST-001", "Paulo Miotto");
        Account acc = new Account("ACC-1001", paulo);

        System.out.println("Created: " + acc);

        acc.deposit(new BigDecimal("150.00"));
        System.out.println("After deposit: " + acc.balance());

        acc.withdraw(new BigDecimal("40.50"));
        System.out.println("After withdraw: " + acc.balance());

        System.out.println("Final: " + acc);
    }
}
