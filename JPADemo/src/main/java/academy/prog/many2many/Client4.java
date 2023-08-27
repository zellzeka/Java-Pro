package academy.prog.many2many;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*

Cli   Co
id    id
1     5
2     7

client_course
client_id   course_id
1           5
1           7
2           7
 */

@Entity
@Table(name = "clients3")
public class Client4 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer age;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "client_course",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    public Client4() {}

    public Client4(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.getClients().add(this);
    }

    public void clearAddresses() {
        courses.clear();
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

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client4 client = (Client4) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(name, client.name) &&
                Objects.equals(age, client.age) &&
                courses.equals(client.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, courses);
    }
}
