package academy.prog.one2one;

import javax.persistence.*;

@Entity
@Table(name = "address1")
public class Address2 {
    @Id
    @GeneratedValue
    private Long id;

    private String country;
    private String city;
    private String street;

    @OneToOne(mappedBy = "address")
    private Client2 client;

    public Address2() {}

    public Address2(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Client2 getClient() {
        return client;
    }

    public void setClient(Client2 client) {
        this.client = client;
    }
}
