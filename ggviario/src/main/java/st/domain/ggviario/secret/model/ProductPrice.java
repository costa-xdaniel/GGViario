package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 28/02/17.
 */

public class ProductPrice extends BaseCharSequence implements Parcelable {

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

    //Parcelable

    protected ProductPrice(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readDouble();
        price = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeDouble(quantity);
        dest.writeFloat(price);
    }

    public static final Creator<ProductPrice> CREATOR = new Creator<ProductPrice>() {
        @Override
        public ProductPrice createFromParcel(Parcel in) {
            return new ProductPrice(in);
        }

        @Override
        public ProductPrice[] newArray(int size) {
            return new ProductPrice[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }
}
