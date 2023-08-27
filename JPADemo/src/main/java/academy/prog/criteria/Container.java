package academy.prog.criteria;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Container {
    @Id
    @GeneratedValue
    private Long id;

    private String owner;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Container() {}

    public Container(String owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
