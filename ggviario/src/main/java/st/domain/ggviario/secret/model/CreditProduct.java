package st.domain.ggviario.secret.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditProduct implements Parcelable {

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

    protected CreditProduct(Parcel in) {
        priceQuantity = in.readFloat();
        measure = in.readParcelable(Measure.class.getClassLoader());
        readId( in );
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readDouble();
        price = in.readFloat();
    }

    private void readId(Parcel in) {
        id = in.readInt();
        if( id == -1 ) id = null;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(priceQuantity);
        dest.writeParcelable(measure, flags);
        writeId( dest );
        dest.writeParcelable(product, flags);
        dest.writeDouble(quantity);
        dest.writeFloat(price);
    }

    private void writeId(Parcel dest) {
        if( this.id != null )
            dest.writeInt( id );
        else dest.writeInt( -1 );
    }

    public static final Creator<CreditProduct> CREATOR = new Creator<CreditProduct>() {
        @Override
        public CreditProduct createFromParcel(Parcel in) {
            return new CreditProduct(in);
        }

        @Override
        public CreditProduct[] newArray(int size) {
            return new CreditProduct[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

}
