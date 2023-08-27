package academy.prog.tests;

import org.junit.Test;
import academy.prog.nplusone.Address6;
import academy.prog.nplusone.Client6;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class NPlusOneText extends BaseTest {

    private void saveTestClientsWithAddresses(String name, int age) {
        final List<Client6> clients = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Client6 client = new Client6("Name" + i, i);

            for (int j = 0; j < 10; j++) {
                Address6 address = new Address6("Country" + j, "City" + j, "Street" + j);
                client.getAddresses().add(address);
            }

            clients.add(client);
        }

        performTransaction(new Callable<Void>() {
            public Void call() {
                for (Client6 client : clients) {
                    em.persist(client);
                }
                return null;
            }
        });
    }

    @Test
    public void nPlusOneProblem() {
        saveTestClientsWithAddresses("Problem", 10);

        em.clear();

        TypedQuery<Client6> query = em.createQuery("SELECT c FROM Client6 c", Client6.class);
        List<Client6> clients = query.getResultList();

        for (Client6 client : clients) {
            List<Address6> addresses = client.getAddresses();
            for (Address6 address : addresses) {
                System.out.println(address.getStreet());
            }
        }

        em.clear();

        query = em.createQuery("SELECT c FROM Client6 c JOIN FETCH c.addresses", Client6.class);
        clients = query.getResultList();

        for (Client6 client : clients) {
            List<Address6> addresses = client.getAddresses();
            for (Address6 address : addresses) {
                System.out.println(address.getStreet());
            }
        }

        // look at log... WTF? %)
    }
}
