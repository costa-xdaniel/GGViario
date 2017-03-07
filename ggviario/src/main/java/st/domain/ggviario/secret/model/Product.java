package st.domain.ggviario.secret.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import st.domain.support.android.util.BaseCharSequence;


/**
 *
 * Created by dchost on 10/02/17.
 */

public class Product extends BaseCharSequence {

    private int id;
    private String name;
    private String detail;
    private Measure metreage;
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
        this.metreage = metreage;
        this.dateReg = dateReg;
        this.price = price;
        this.product = product;
        this.scaleSuper = scaleSuper;
        this.state = state;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Measure getMetreage() {
        return metreage;
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
}
