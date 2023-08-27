package academy.prog.tests;

import org.junit.Test;
import academy.prog.criteria.Container;
import academy.prog.criteria.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CriteriaTest extends BaseTest {
    private void saveFewProducts() {
        List<Product> products = Arrays.asList(
                new Product("iPhone", 1000, 200),
                new Product("Samsung", 900, 150),
                new Product("Meizu", 500, 120));

        performTransaction(() -> {
            products.forEach((c) -> em.persist(c));
            return null;
        });
    }

    private void saveContainersProducts() {
        Container c1 = new Container("Alex");
        Container c2 = new Container("Oleg");

        c1.getProducts().add(new Product("BMW", 10000, 300));
        c1.getProducts().add(new Product("Lexus", 8000, 200));

        c2.getProducts().add(new Product("Orange", 1, 2));
        c2.getProducts().add(new Product("Banana", 2, 3));

        performTransaction(() -> {
            em.persist(c1);
            em.persist(c2);
            return null;
        });
    }

    @Test
    public void testCriteriaSimple() {
        saveFewProducts();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.select(root)
                .where(builder.greaterThan(root.get("price"), 900));

        List<Product> res1 = em.createQuery(criteria).getResultList();
        assertEquals(1, res1.size());
    }

    @Test
    public void testCriteriaSimple2() {
        saveFewProducts();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.select(root.get("name"))
                .where(builder.le(root.get("price"), 500));

        List<String> res2 = em.createQuery(criteria).getResultList();
        assertEquals(1, res2.size());
    }

    @Test
    public void testCriteriaTwoTables() {
        saveContainersProducts();

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        Root<Product> fromProd = criteria.from(Product.class);
        Root<Container> fromCont = criteria.from(Container.class);

        criteria.select(fromProd)
                .where(builder.and(
                        builder.equal(fromCont.get("owner"), "Alex"),
                        builder.gt(fromProd.get("weight"), 10)
                ))
                .orderBy(builder.asc(fromProd.get("id")));

        List<Product> res2 = em.createQuery(criteria).getResultList();
        assertEquals(2, res2.size());
    }
}
