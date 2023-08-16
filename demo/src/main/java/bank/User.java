package bank;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> userAccounts = new HashSet<>();

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Account> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public void addAccount(Account account){
        if ( ! userAccounts.contains(account)) {
            userAccounts.add(account);
            account.setUser(this);
        }
    }
}
