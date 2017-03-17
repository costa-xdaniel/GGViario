package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import st.domain.support.android.util.BaseCharSequence;


/**
 *
 * Created by dchost on 10/02/17.
 */

public class Product extends BaseCharSequence implements Parcelable{

    private int id;
    private String name;
    private String detail;
    private Measure measure;
    private Date dateReg;
    private Float price;
    private Product product;
    private double scaleSuper;
    private int state;
    private int stock;

    public Product(int id, String name, String detail, Measure metreage, Date dateReg, Float price, @Nullable Product product, double scaleSuper, int state, int stock) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.measure = metreage;
        this.dateReg = dateReg;
        this.price = price;
        this.product = product;
        this.scaleSuper = scaleSuper;
        this.state = state;
        this.stock = stock;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        detail = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
        scaleSuper = in.readDouble();
        state = in.readInt();
        stock = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeParcelable(product, flags);
        dest.writeDouble(scaleSuper);
        dest.writeInt(state);
        dest.writeInt(stock);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Measure getMeasure() {
        return measure;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public Float getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public double getScaleSuper() {
        return scaleSuper;
    }

    public int getState() {
        return state;
    }

    public int getStock() {
        return stock;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
