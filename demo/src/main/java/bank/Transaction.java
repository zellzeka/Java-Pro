package bank;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    private String recipientName;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private  Rate transactionRate;

    public Transaction() {

    }

    public Transaction(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Rate getTransactionRate() {
        return transactionRate;
    }

    public void setTransactionRate(Rate transactionRate) {
        this.transactionRate = transactionRate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", recipientName='" + recipientName  +
                '}';
    }

}
