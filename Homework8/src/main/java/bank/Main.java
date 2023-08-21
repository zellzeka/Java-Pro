package bank;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            emf = Persistence.createEntityManagerFactory("Bank");
            em = emf.createEntityManager();
            while (true) {
                System.out.println("1: add user");
                System.out.println("2: open an account");
                System.out.println("3: carry out a banking transaction");
                System.out.println("4: view balance");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addUser(sc);
                        break;
                    case "2":
                        addAccount(sc);
                        break;
                    case "3":
                        conductTransaction(sc);
                        break;
                    case "4":
                        viewBalance(sc);
                        break;
                    default:
                        return;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void conductTransaction(Scanner sc){
        System.out.println("1: replenish personal account");
        System.out.println("2: transfer money to another account");
        System.out.print("-> ");

        try {
            String s = sc.nextLine();
            switch (s) {
                case "1":
                    executeTransaction(BankOperation.REFILL, sc);
                    break;
                case "2":
                    executeTransaction(BankOperation.TRANSFER, sc);
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static User addUser(Scanner sc){
        System.out.println("Enter username");
        String name = sc.nextLine();
        User user = new User(name);
        performJPATransaction(()-> {
            em.persist(user);
            return null;
        });
        return user;
    }

    private static void addAccount(Scanner sc){
        User searchedUser = getUser(sc);
        System.out.println("Enter account currency(UAH/EUR/USD)");
        String currency = sc.nextLine();

        Account account = new Account(currency);
        searchedUser.addAccount(account);

        performJPATransaction(()-> {
            em.persist(searchedUser);
            return null;
        });
    }

    private static void executeTransaction(BankOperation operation, Scanner sc){
        User searchedUser = getUser(sc);
        Account selectedAccount = getAccount(sc, searchedUser);

        if(operation.equals(BankOperation.REFILL)){
            replenishAccount(sc, selectedAccount);

        } else if (operation.equals(BankOperation.TRANSFER)) {
            executeTransfer(sc, selectedAccount);
        }

    }

    private static void replenishAccount (Scanner sc, Account selAcc){
        System.out.println("Enter the amount of replenishment ");
        BigDecimal amount = new BigDecimal(sc.nextLine());
        selAcc.setBalance(amount);
        Transaction transaction = new Transaction(selAcc.getUser().getUserName());
        transaction.setAccount(selAcc);
        selAcc.setTransactions(transaction);

        performJPATransaction(()-> {
            em.merge(selAcc);

            return null;
        });
    }

    public static void executeTransfer (Scanner sc, Account selAcc) {
        System.out.println("Enter recipient name");
        String recipientName = sc.nextLine();
        TypedQuery<User> query = em.createQuery("SELECT c FROM User c WHERE c.userName = :name", User.class);
        query.setParameter("name", recipientName);
        User searchedUser = query.getSingleResult();

        Account recipientAccount = getAccount(sc, searchedUser);
        Rate transactionRate = new Rate(selAcc.getCurrency(), recipientAccount.getCurrency());
        Transaction transaction = new Transaction();
        transaction.setTransactionRate(transactionRate);
        transaction.setAccount(recipientAccount);

        System.out.println("Enter the amount of transaction");
        BigDecimal amount = new BigDecimal(sc.nextLine());
        BigDecimal userBalance = (selAcc.getBalance() != null) ? selAcc.getBalance() : BigDecimal.ZERO;
        if (userBalance.compareTo(amount) < 0){
            System.out.println("Not enough money in the account");
            return;
        }
        selAcc.setBalance(selAcc.getBalance().subtract(amount));
        BigDecimal recipientBalance = (recipientAccount.getBalance() != null) ? recipientAccount.getBalance() : BigDecimal.ZERO;
        BigDecimal convertedAmount  = amount.multiply(BigDecimal.valueOf(transaction.getTransactionRate().getExchangeRate()));
        recipientAccount.setBalance(recipientBalance.add(convertedAmount));


        performJPATransaction(()-> {
            em.merge(selAcc);
            em.merge(recipientAccount);
            em.persist(transaction);
            em.persist(transactionRate);
            return null;
        });

    }

    private static Account getAccount(Scanner sc, User searchedUser){
        Set<Account> accounts =  searchedUser.getUserAccounts();
        System.out.println("User accounts: ");
        for (Account account: accounts) {
            System.out.println(account);
        }
        System.out.println("Enter account ID");
        Long accountId = Long.parseLong(sc.nextLine());
        Account selectedAccount = null;
        for (Account account: accounts) {
            if(account.getId() == accountId) {
                selectedAccount = account;
            }
        }
        return selectedAccount;
    }

    private static User getUser(Scanner sc){
        System.out.println("Enter username");
        String name = sc.nextLine();
        TypedQuery<User> query = em.createQuery("SELECT c FROM User c WHERE c.userName = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    private  static void viewBalance(Scanner sc){
        User searchedUser = getUser(sc);
        Set<Account> usersAccounts = searchedUser.getUserAccounts();
        BigDecimal totalBalance = BigDecimal.ZERO;
        for (Account account: usersAccounts) {
            Rate transactionRate = new Rate(account.getCurrency(), "UAH");
            BigDecimal accountBalance = (account.getBalance() != null) ? account.getBalance() : BigDecimal.ZERO;
            BigDecimal convertedBalance = accountBalance.multiply(BigDecimal.valueOf(transactionRate.getExchangeRate()));
            totalBalance = totalBalance.add(convertedBalance);
        }
        System.out.println(totalBalance);

    }



    private static <T>T performJPATransaction(Callable<T> action){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            T result = action.call();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}
