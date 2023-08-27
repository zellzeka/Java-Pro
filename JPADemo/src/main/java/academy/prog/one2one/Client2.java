package academy.prog.one2one;

import javax.persistence.*;

@Entity
@Table(name = "clients2")
public class Client2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    /*
        clients2:
        id name age address_id
        2  dsf  14  1

        address1:
        id country city ...
        1  dfgdfg  efds

        cli + addr -> p(cli)
     */

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address2 address;

    @Transient
    private String comment = "default comment";

    public Client2() {}

    public Client2(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Address2 getAddress() {
        return address;
    }

    public void setAddress(Address2 address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
