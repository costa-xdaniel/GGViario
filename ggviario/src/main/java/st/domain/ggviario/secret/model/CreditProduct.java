package st.domain.ggviario.secret.model;


/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditProduct {

    private final float priceQuantity;
    private final Measure measure;
    private Integer id;
    private Product product;
    private double quantity;
    private  float price;


    public CreditProduct(Integer id, Product product, double quantity, float price, Measure measure, float priceQuantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
        this.measure = measure;
        this.priceQuantity = priceQuantity;

    }

    public Product getProduct() {
        return this.product;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public float getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    public float getPriceQuantity() {
        return this.priceQuantity;
    }

    public Measure getMeasure() {
        return measure;
    }

}
