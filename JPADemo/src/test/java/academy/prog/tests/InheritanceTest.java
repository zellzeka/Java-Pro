package academy.prog.tests;

import org.junit.Test;
import academy.prog.inheritance.Client5;
import academy.prog.inheritance.VipClient;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InheritanceTest extends BaseTest {

    private void saveFewClients() {
        List<Client5> clients = Arrays.asList(
                new Client5("Vsevolod"),
                new VipClient("Dmitriy", "77777"),
                new VipClient("Andrey", "55555"));

        performTransaction(() -> {
            clients.forEach((c) -> em.persist(c));
            return null;
        });
    }

    @Test
    public void testPersist() {
        saveFewClients();

        TypedQuery<VipClient> query1 = em.createQuery(
                "SELECT v FROM VipClient v", VipClient.class);
        List<VipClient> res1 = query1.getResultList();
        assertEquals(2, res1.size());

        TypedQuery<Client5> query2 = em.createQuery(
                "SELECT c FROM Client5 c", Client5.class);
        List<Client5> res2 = query2.getResultList();
        assertEquals(3, res2.size());

        TypedQuery<Client5> query3 = em.createQuery(
                "SELECT c FROM Client5 c WHERE TYPE(c) = Client5", Client5.class);
        List<Client5> res3 = query3.getResultList();
        assertEquals(1, res3.size());
    }
}
