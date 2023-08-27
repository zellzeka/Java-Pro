package academy.prog.criteria;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer weight;

    @ManyToOne
    private Container container;

    public Product() {}

    public Product(String name, Integer price, Integer weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
