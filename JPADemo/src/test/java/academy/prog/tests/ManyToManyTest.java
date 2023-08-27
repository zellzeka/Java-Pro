package academy.prog.tests;

import org.junit.Test;
import academy.prog.many2many.Client4;
import academy.prog.many2many.Course;

import java.util.Arrays;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManyToManyTest extends BaseTest {

    private List<Long> saveClientsWithCourses() {
        Client4 client1 = new Client4("Vsevolod", 34);
        Client4 client2 = new Client4("Julia", 34);

        Course course1 = new Course("Java Pro");
        Course course2 = new Course("Front-End");

        client1.addCourse(course1);
        client1.addCourse(course2);

        course1.addClient(client2);

        return performTransaction(() -> {
            em.persist(client1);
            em.persist(client2);

            return Arrays.asList(client1.getId(), client2.getId());
        });
    }

    @Test
    public void testPersist() {
        List<Long> ids = saveClientsWithCourses();

        Client4 client = em.find(Client4.class, ids.get(0));
        assertEquals(2, client.getCourses().size());
    }
}
