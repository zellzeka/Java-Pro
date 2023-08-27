package academy.prog.tests;

import org.junit.Test;
import academy.prog.one2many.Address3;
import academy.prog.one2many.Client3;

import java.util.List;

import static org.junit.Assert.*;

public class OneToManyTest extends BaseTest {
    private Long saveTestClientWithAddresses(
            String name, int age, String country, String city, String street) {

        final Client3 client = new Client3(name, age);
        final Address3 address1 = new Address3(country, city, street);
        final Address3 address2 = new Address3(country + "1", city + "1", street + "1");

        client.addAddress(address1);
        client.addAddress(address2);

        return performTransaction(() -> {
            em.persist(client);
            return client.getId();
        });
    }

    @Test
    public void testPersist() {
        long id = saveTestClientWithAddresses("Vsevolod", 31,
                "Ukraine", "Kyiv", "Some street");

        em.clear();

        Client3 otherClient = em.find(Client3.class, id);
        assertNotNull(otherClient);

        List<Address3> addrs = otherClient.getAddresses();
        assertEquals(2, addrs.size());
    }

    @Test
    public void testRemoveClient() {
        long id = saveTestClientWithAddresses("Aleksandr", 35,
                "Ukraine", "Lviv", "Any street");

        em.clear();

        final Client3 client = em.getReference(Client3.class, id);
        assertNotNull(client);

        performTransaction(() -> {
            em.remove(client);
            return null;
        });
    }

    @Test
    public void testRemoveAddress() {
        long id = saveTestClientWithAddresses("Anybody", 50,
                "Ukraine", "Kyiv", "Some street 45");

        em.clear();

        final Client3 otherClient = em.find(Client3.class, id);
        assertNotNull(otherClient);

        final List<Address3> addrs = otherClient.getAddresses();
        assertEquals(2, addrs.size());

        performTransaction(() -> {
            Address3 a1 = otherClient.getAddress(0);
            Address3 a2 = otherClient.getAddress(1);

            otherClient.clearAddresses();
            em.merge(otherClient);

            em.remove(a1);
            em.remove(a2);
            return null;
        });

        Client3 otherClient2 = em.find(Client3.class, id);
        assertNotNull(otherClient2);

        List<Address3> addrs2 = otherClient2.getAddresses();
        assertEquals(0, addrs2.size());
    }
}
