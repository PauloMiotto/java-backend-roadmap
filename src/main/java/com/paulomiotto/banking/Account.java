package com.paulomiotto.banking;

import com.paulomiotto.banking.exception.InvalidAmountException;
import com.paulomiotto.banking.exception.InsufficientFundsException;

import java.math.BigDecimal;    //usado para dinheiro (precisão exata, sem erro de ponto flutuante)
import java.math.RoundingMode;  //define como arredondar valores monetários.
import java.util.Objects;       //usado para validação de null (requireNonNull).

public class Account { //Representa a conta bancária, diferente de Customer, essa classe tem estado mutável.
    private final String accountNumber; //final porque não deve mudar depois de criada.
    private final Customer owner;       //Dono da conta. Também final porque conta não troca de dono.
    private BigDecimal balance;         //Saldo da conta. Não é final porque muda ao longo do tempo.
                                        //Estado interno controlado pela própria classe.
                                        //Campos mutáveis não ganham setters genéricos.

    public Account(String accountNumber, Customer owner) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null/blank");
        }
        this.accountNumber = accountNumber;
        this.owner = Objects.requireNonNull(owner, "Owner cannot be null"); //Garante que a conta sempre tem um dono
                                                                                    //requireNonNull lança NullPointerException com mensagem clara.
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);  //inicializa saldo em 0.00.
                                                                                    //setScale(2) → duas casas decimais (padrão monetário).
                                                                                    //HALF_UP → arredondamento comercial clássico.
    }

    public String accountNumber() { //Getter moderno do número da conta. Não há Setter porque não deve mudar.
        return accountNumber;
    }

    public Customer owner() { //Getter moderno, retorna o dono da conta
        return owner;
    }

    public BigDecimal balance() { //Getter moderno do saldo; permite consultar, mas não alterar diretamente.
        return balance;
    }

    public void deposit(BigDecimal amount) {
        amount = normalizeAmount(amount); //Depois dessa linha, o metodo pode confiar que amount:
                                          //não é nulo;
                                          //tem 2 casas decimais;
                                          //segue a regra de arredondamento definida
        if (amount.compareTo(BigDecimal.ZERO) <= 0) { //compareTo é o jeito correto com BigDecimal
            //throw new IllegalArgumentException("Deposit amount must be > 0");
            throw new InvalidAmountException("Deposit amount must be > 0");
        }
        balance = balance.add(amount);

    }

    public void withdraw(BigDecimal amount) {
        amount = normalizeAmount(amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be > 0");
        }
        if (balance.compareTo(amount) < 0) {
            //throw new IllegalStateException("Insufficient funds")
            throw new InsufficientFundsException();
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
