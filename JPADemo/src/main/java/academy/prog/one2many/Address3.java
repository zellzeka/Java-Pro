package academy.prog.one2many;

import javax.persistence.*;

@Entity
@Table(name = "address2")
public class Address3 {
    @Id
    @GeneratedValue
    private Long id;

    private String country;
    private String city;
    private String street;

    /*
        clients2:
        id name age
        2  dsf  14

        address2:
        id country city client_id
        1  dfgdfg  efds 2
     */

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client3 client;

    public Address3() {}

    public Address3(String country, String city, String street) {
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

    public Client3 getClient() {
        return client;
    }

    public void setClient(Client3 client) {
        this.client = client;
    }
}
