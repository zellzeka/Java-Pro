package academy.prog.tests;

import org.junit.Test;
import academy.prog.one2one.Address2;
import academy.prog.one2one.Client2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OneToOneTest extends BaseTest {

    private TwoIDs saveTestClientWithAddress(
            String name, int age, String country, String city, String street) {
        final Client2 client = new Client2(name, age);
        final Address2 address = new Address2(country, city, street);

        client.setAddress(address);
        address.setClient(client); // !! not necessary in this case
        //...
        
        return performTransaction(() -> {
            em.persist(client);
            return new TwoIDs(client.getId(), address.getId());
        });
    }

    @Test
    public void testPersist() {
        TwoIDs ids = saveTestClientWithAddress("Vsevolod", 31,
                "Ukraine", "Kyiv", "Some street");

        em.clear();

        Client2 otherClient = em.find(Client2.class, ids.getClientId());
        assertNotNull(otherClient);

        Address2 otherAddress = otherClient.getAddress();
        assertEquals("Some street", otherAddress.getStreet());

        em.clear();

        otherAddress = em.find(Address2.class, ids.getAddressId());
        assertNotNull(otherAddress);

        otherClient = otherAddress.getClient();
        assertNotNull(otherClient);
        assertEquals("Vsevolod", otherClient.getName());
    }

    @Test
    public void testRemoveClient() {
        TwoIDs ids = saveTestClientWithAddress("Aleksandr", 35,
                "Ukraine", "Lviv", "Any street");

        em.clear();

        final Client2 client = em.getReference(Client2.class, ids.getClientId());
        assertNotNull(client);

        performTransaction(() -> {
            em.remove(client);
            return null;
        });

        Address2 address = em.find(Address2.class, ids.getAddressId());
        assertNull(address);
    }

    @Test
    public void testRemoveAddressWrong() {
        TwoIDs ids = saveTestClientWithAddress("Ivan", 25,
                "Ukraine", "Kharkiv", "Street 1");

        em.clear();

        final Address2 address = em.getReference(Address2.class, ids.getAddressId());
        assertNotNull(address);

        performTransaction(() -> {
            em.remove(address);
            return null;
        });

        em.clear();

        Client2 client = em.find(Client2.class, ids.getClientId());
        assertNotNull(client.getAddress());
    }

    @Test
    public void testRemoveAddressCorrect() {
        TwoIDs ids = saveTestClientWithAddress("Vitaliy", 23,
                "Ukraine", "Dnipro", "Street 3");

        em.clear();

        final Address2 address = em.getReference(Address2.class, ids.getAddressId());
        assertNotNull(address);

        performTransaction(() -> {
            Client2 client = address.getClient();
            client.setAddress(null);

            em.merge(client);
            em.remove(address);

            return null;
        });

        em.clear();

        Client2 client = em.find(Client2.class, ids.getClientId());
        assertNull(client.getAddress());
    }
}
