package academy.prog.nplusone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients7")
public class Client6 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address6> addresses = new ArrayList<>();

    public Client6() {}

    public Client6(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Address6> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address6> addresses) {
        this.addresses = addresses;
    }
}
