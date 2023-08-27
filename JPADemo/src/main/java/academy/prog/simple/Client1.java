package academy.prog.simple;

import javax.persistence.*;

@Entity
@Table(name = "clients1")
@NamedQuery(name = "selectByNameLike",
        query = "SELECT c FROM Client1 c WHERE c.name LIKE :pattern")
public class Client1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // or @GeneratedValue
    //@Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(nullable = false)
    private Integer age;

    public Client1() {}

    public Client1(String name, Integer age) {
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
}
