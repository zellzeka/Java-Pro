package academy.prog.tests;

import org.junit.Test;
import academy.prog.simple.Client1;
import academy.prog.simple.CustomClient;

import javax.persistence.*;

import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class SimpleTest extends BaseTest {

    private Client1 saveTestClient(final String name, final Integer age) {
        Callable<Client1> c = new Callable<Client1>() {
            public Client1 call() throws Exception {
                Client1 client = new Client1(name, age);
                em.persist(client);
                return client;
            }
        };

        return performTransaction(c);
    }

    @Test
    public void testPersistAndFind() {
        Client1 client = saveTestClient("Nikolay", 20);

        long id = client.getId();
        assertTrue(id > 0);

        // find existing
        Client1 other = em.find(Client1.class, id);
        assertNotNull(other);
        assertEquals(client.getName(), other.getName());
        assertEquals(client.getAge(), other.getAge());

        // clear context
        em.clear();

        // entity was already loaded by find()
        other = em.getReference(Client1.class, id);
        assertNotNull(other);
        assertEquals(client.getName(), other.getName());
        assertEquals(client.getAge(), other.getAge());
    }

    @Test(expected = RuntimeException.class)
    public void testNullable() {
        saveTestClient("Nikolay", null);
    }

    @Test
    public void testMerge() {
        final Client1 client = saveTestClient("Ivan", 10);
        long id = client.getId();

        performTransaction(() -> {
            client.setAge(50);
            return null;
        });

        em.clear();

        Client1 other = em.find(Client1.class, id);
        assertEquals(50, other.getAge());
    }

    @Test
    public void testRemove() {
        final Client1 client = saveTestClient("Ivan", 10);
        final long id = client.getId();

        performTransaction(() -> {
            Client1 other = em.getReference(Client1.class, id);
            em.remove(other);
            return null;
        });

        Client1 another = em.find(Client1.class, id);
        assertNull(another);
    }

    @Test
    public void testSelect() {
        performTransaction(() -> {
            for (int i = 0; i < 20; i++) {
                Client1 client = new Client1("Name" + i, 100 + i);
                em.persist(client);
            }
            return null;
        });

        List<Client1> resultList;

        Query query = em.createQuery("SELECT c FROM Client1 c WHERE c.age >= :age");
        query.setParameter("age", 100);
        resultList = (List<Client1>) query.getResultList(); // type cast!!!
        assertEquals(20, resultList.size());

        TypedQuery<Client1> otherQuery = em.createQuery(
                "SELECT c FROM Client1 c WHERE c.age >= :age", Client1.class);
        otherQuery.setParameter("age", 100);
        resultList = otherQuery.getResultList(); // no type cast
        assertEquals(20, resultList.size());

        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(c) FROM Client1 c WHERE c.age >= :age", Long.class);
        countQuery.setParameter("age", 100);
        long count = countQuery.getSingleResult();
        assertEquals(20, count);

        // select properties
        TypedQuery<CustomClient> propQuery = em
                .createQuery("SELECT NEW academy.prog.simple.CustomClient(c.name, c.age) FROM Client1 c WHERE c.id = 1",
                        CustomClient.class);
        CustomClient res = propQuery.getSingleResult();
        assertNotNull(res);

        Query propQuery2 = em
                .createQuery("SELECT c.name, c.age FROM Client1 c WHERE c.id = 1");
        Object[] res2 = (Object[]) propQuery2.getSingleResult();

        assertEquals(2, res2.length);
        assertTrue(res2[0] instanceof String);
        assertTrue(res2[1] instanceof Integer);
    }

    // 0,1,2,....n-1
    // a,b,c,....z

    @Test
    public void testSelectWithLimit() {
        performTransaction(() -> {
            for (int i = 0; i < 100; i++) {
                Client1 client = new Client1("Anybody" + i, 100 + i);
                em.persist(client);
            }
            return null;
        });

        TypedQuery<Client1> query = em.createNamedQuery("selectByNameLike", Client1.class);
        query.setParameter("pattern", "%body%");
        query.setFirstResult(5);
        query.setMaxResults(20);

        List<Client1> result = query.getResultList();
        assertEquals(20, result.size());

        Client1 client = result.get(0);
        assertTrue(client.getName().startsWith("Anybody"));
        //assertEquals(105, client.getAge());
    }

    private void performWrongSelect(int age) {
        performTransaction(() -> {
            for (int i = 0; i < 5; i++) {
                Client1 client = new Client1("Hello" + i, 200 + i);
                em.persist(client);
            }
            return null;
        });

        TypedQuery<Client1> query = em.createQuery("SELECT c FROM Client1 c WHERE c.age > :age", Client1.class);
        query.setParameter("age", age);
        Client1 client = query.getSingleResult();
    }

    @Test(expected = NonUniqueResultException.class)
    public void testWrongCount1() {
        performWrongSelect(200);
    }

    @Test(expected = NoResultException.class)
    public void testWrongCount2() {
        performWrongSelect(1000);
    }
}
