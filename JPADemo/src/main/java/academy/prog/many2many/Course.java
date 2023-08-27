package academy.prog.many2many;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<Client4> clients = new HashSet<>();

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public void addClient(Client4 client) {
        clients.add(client);
        client.getCourses().add(this);
    }

    public Long getId() {
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

    public Set<Client4> getClients() {
        return clients;
    }

    public void setClients(Set<Client4> clients) {
        this.clients = clients;
    }
}
