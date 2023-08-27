package academy.prog.inheritance;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Vip")
public class VipClient extends Client5 {
    private String discountCardNumber;

    public VipClient() {}

    public VipClient(String name, String discountCardNumber) {
        super(name);
        this.discountCardNumber = discountCardNumber;
    }

    public String getDiscountCardNumber() {
        return discountCardNumber;
    }

    public void setDiscountCardNumber(String discountCardNumber) {
        this.discountCardNumber = discountCardNumber;
    }
}
