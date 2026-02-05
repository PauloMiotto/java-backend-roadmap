//Main para os dias 4 : Transferência de valores

package com.paulomiotto.banking;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AccountService service = new AccountService();

        Customer paulo = new Customer("CUST-001", "Paulo Miotto");
        Customer maria = new Customer("CUST-002", "Maria Miotto");

        Account a1 = service.createAccount("ACC-1", paulo);
        Account a2 = service.createAccount("ACC-2", maria);

        a1.deposit(new BigDecimal("20000"));

        service.transferBetweenAccounts("ACC-1", "ACC-2", new BigDecimal("40"));

        service.

        System.out.println(a1.balance()); // 60
        System.out.println(a2.balance()); // 40

        service.
    }
}

//Main para os dias 3 : Menu de depósito e retirada de dinheiro
/*
package com.paulomiotto.banking;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AccountService service = new AccountService();

        Customer paulo = new Customer("CUST-001", "Paulo Miotto");
        service.createAccount("ACC-1001", paulo);

        // Verificando valores!
        System.out.println("paulo.hashCode() :" + paulo.hashCode());
        System.out.println("paulo.toString() :" + paulo.toString());


        boolean running = true;

        while (running) {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1 - Deposit");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Check balance");
            System.out.println("0 - Exit");

            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> {
                        System.out.print("Amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());
                        service.deposit("ACC-1001", amount);
                        System.out.println("Deposit successful");
                    }
                    case "2" -> {
                        System.out.print("Amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());
                        service.withdraw("ACC-1001", amount);
                        System.out.println("Withdraw successful");
                    }
                    case "3" -> {
                        Account acc = service.findAccount("ACC-1001");
                        System.out.println("Balance: " + acc.balance());
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }



        scanner.close();
        System.out.println("Application terminated.");
    }
}
*/


//Main para os dias 1 e 2 : apenas para testar as classes Customer e Account
/*
package com.paulomiotto.banking;

import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
        Customer cliente = new Customer("CUST-001", "Paulo Miotto"); //CUST-001
        Account acc = new Account("ACC-1001", cliente);

        System.out.println("Created: " + acc);

        acc.deposit(new BigDecimal("150.00"));
        System.out.println("After deposit: " + acc.balance());

        acc.withdraw(new BigDecimal("40.50"));
        System.out.println("After withdraw: " + acc.balance());

        System.out.println("Final: " + acc);


    }
}
*/


// MINI-TESTE: == vs equals() vs Collections
/*
package com.paulomiotto.banking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Customer c1 = new Customer("CUST-001", "Paulo Miotto");
        Customer c2 = new Customer("CUST-001", "Paulo Miotto");

        System.out.println("== comparison:");
        System.out.println(c1 == c2);
        //
        //Interpretando o resultado (parte mais importante)
        //🔹 c1 == c2 → false
        //Porque:
        //== compara referência de memória
        //c1 e c2 são dois objetos diferentes criados com new
        //📌 == NUNCA usa equals.
        //

        System.out.println("\nEquals comparison:");
        System.out.println(c1.equals(c2));
        //
        //🔹 c1.equals(c2) → true
        //Porque:
        //você sobrescreveu equals
        //sua regra é: mesmo id = mesmo cliente
        //📌 Aqui está o ponto-chave:
        //você mudou o significado de “igual” para Customer.
        //

        System.out.println("\nList contains:");
        List<Customer> list = new ArrayList<>();
        list.add(c1);
        System.out.println(list.contains(c2));
        //
        //🔹 list.contains(c2) → true
        //Porque:
        //List.contains() chama equals() internamente
        //Ele percorre a lista e pergunta:
        //“algum elemento é igual a c2?”
        //📌 Se você não tivesse sobrescrito equals, isso seria false.
        //

        System.out.println("\nHashSet size:");
        Set<Customer> set = new HashSet<>();
        set.add(c1);
        set.add(c2);
        System.out.println(set.size());
        //
        //🔹 HashSet size → 1
        //Porque:
        //HashSet usa hashCode + equals
        //c1 e c2:
        //têm o mesmo hashCode (id igual)
        //são considerados iguais por equals
        //Resultado: só 1 elemento
        //📌 Se hashCode estivesse errado:
        //o set poderia ter tamanho 2
        //isso seria um bug clássico


        //A forma tecnicamente correta de dizer é:
        //    == compara se duas referências apontam para o MESMO objeto na memória.
        //    equals compara igualdade lógica, conforme a regra definida pela classe.
        //Ou seja:
        //    equals não é automaticamente “conteúdo”
        //    ele só compara “conteúdo” se você programar isso
        //

    }
}
*/


