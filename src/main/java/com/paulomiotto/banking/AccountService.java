package com.paulomiotto.banking;

import com.paulomiotto.banking.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class AccountService { //ela representa a camada de serviço (service layer): coordena operações do domínio.
                              //Ideia: tirar lógica do Main e concentrar aqui.
    private final Map<String, Account> accounts = new HashMap<>(); //Cria um “banco de dados em memória”.
                                                                   //Por que private? Só o AccountService deve gerenciar esse mapa (encapsulamento).
                                                                   //Por que final? A referência do mapa não muda (você não troca o mapa), mas o conteúdo pode mudar (put/get).
                                                                   //Por que new HashMap<>()? Você precisa de uma implementação concreta para armazenar dados.
                                                                   //Map<String, Account> significa:
                                                                   //chave (String) = número da conta (accountNumber)
                                                                   //valor (Account) = o objeto da conta

    //Metodo: criar conta usando conceito cláusula de guarda (guard clause):
    /* Uma guard clause:
       1. verifica uma condição inválida logo no início;
       2. interrompe o metodo imediatamente;
       3. evita blocos else desnecessários.
       P.S. Se o "if" termina o metodo (return, throw, break), você NÃO precisa de "else".
     */
    //“createAccount é um metodo público que retorna um objeto do tipo Account.”
    public Account createAccount(String accountNumber, Customer owner) { //Regra de negócio: não pode existir duas contas com o mesmo número.
        if (accounts.containsKey(accountNumber)) {                       //containsKey verifica rapidamente se a chave já existe no Map.
            throw new IllegalArgumentException("Account already exists");//Lança IllegalArgumentException porque o “pedido” para criar é inválido (duplicado).
        }

        //Cria o objeto Account
        Account account = new Account(accountNumber, owner); //Cria um objeto Account.
        accounts.put(accountNumber, account);                //Armazena a conta no mapa. put(chave, valor) salva o valor e permite encontrar depois por chave
        return account;                                      //Retorna a conta criada, útil para imprimir, consultar saldo etc.
    }

    //Metodo: buscar conta
    public Account findAccount(String accountNumber) { //Metodo público para encontrar uma conta pelo número
        Account account = accounts.get(accountNumber); //Busca a conta no mapa
        if (account == null) {                         //Se existir, retorna o objeto Account.
            throw new IllegalArgumentException("Account not found"); //Se não existir, get retorna null.
        }
        return account;
    }

    //Metodo: depósito
    public void deposit(String accountNumber, BigDecimal amount) { //Metodo público para depositar em uma conta específica. Não retorna nada (void) porque o efeito está no estado da conta.
        Account account = findAccount(accountNumber); //Reusa a lógica de busca e validação. Se a conta não existir, findAccount lança exceção e o depósito não continua.
        account.deposit(amount); //Delegação: o AccountService não mexe no saldo diretamente. Quem decide como depositar e validar valores é a classe Account. Isso é bom design: cada classe com sua responsabilidade.
    }

    //Metodo: saque
    public void withdraw(String accountNumber, BigDecimal amount) { //Metodo público para sacar.
        Account account = findAccount(accountNumber); //Busca a conta e garante que ela existe
        account.withdraw(amount); //Delegação: Account faz validações (valor > 0, saldo suficiente etc.). Se não tiver saldo, Account.withdraw lança IllegalStateException("Insufficient funds").
    }

    //Metodo: transfer
    public void transfer(String fromAccountNumber,
                         String toAccountNumber,
                         BigDecimal amount) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        /* //Bloco substituido pelo metodo getAccountOrThrow para validar se as contas existem.
        Account from = accounts.get(fromAccountNumber);
        Account to = accounts.get(toAccountNumber);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Account not found");
        }*/

        //O metodo getAccountOrThrow subsituiu o bloco comentado acima.
        Account from = getAccountOrThrow(fromAccountNumber);
        Account to = getAccountOrThrow(toAccountNumber);

        from.withdraw(amount);
        to.deposit(amount);

    }

    //Pequena melhoria que eu faria (opcional): metodo auxiliar “getOrThrow”
    private Account getAccountOrThrow(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

}

/*O que é mais importante entender dessa classe
1) Ela é um “backend em miniatura”
   - Map = banco em memória
   - Service = lógica de aplicação
   - Main = interface do usuário
   - Isso espelha arquitetura real:
   - Controller → Service → Repository/DB

2) Por que Map e não List?
Porque no backend você quase sempre busca por chave (id).
   - Map.get(chave) é rápido e direto.
   - List exigiria loop procurando.

3) Por que AccountService não tem setters?
Porque não faz sentido expor o mapa.
Ele controla tudo por métodos de intenção:
   - createAccount
   - deposit
   - withdraw
   - findAccount


 */
