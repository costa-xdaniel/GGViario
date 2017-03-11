package st.domain.ggviario.secret.model;

import android.support.annotation.NonNull;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 28/02/17.
 */

public class ProductPrice extends BaseCharSequence {

    private Product product;
    private  Measure measure;
    private double quantity;
    private float price;

    public ProductPrice(Product product, Measure measure, double quantity, float price) {
        this.product = product;
        this.measure = measure;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Measure getMeasure() {
        return measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public float calcPrice ( double quantityCalc ) {

        /*
         quantity ------ price
         quantityCalc -- priceCalc

         priceCalc = (quantityCalc * price) / quantity
         transform price to base zero
         */

        return (float) (( quantityCalc * this.price ) / this.quantity);
    }

    @NonNull
    @Override
    public String toString() {
        return measure.getCod();
    }
}
