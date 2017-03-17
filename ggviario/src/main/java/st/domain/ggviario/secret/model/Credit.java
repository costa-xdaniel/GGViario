package st.domain.ggviario.secret.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class Credit implements Parcelable {

    private Integer id;
    private float valueTotal;
    private Date dateCredits;
    private List<CreditProduct>  productList;
    private boolean modifiable;
    private float valuePay;
    private int state;

    public Credit() {
        this.modifiable = true;
        this.productList = new LinkedList<>();
        this.dateCredits = Calendar.getInstance().getTime();
        this.valueTotal = 0.0f;
        this.valuePay = 0.0F;
    }

    public Credit(int id, float valueTotal, Date date, List<CreditProduct> productList, float valuePay, int state ) {
        this.id = id;
        this.valueTotal = valueTotal;
        this.dateCredits = date;
        this.valueTotal = valueTotal;
        this.productList = new LinkedList<>( productList );
        this.valuePay = valuePay;
        this.state = state;
    }

    protected Credit(Parcel in) {
        this.id = in.readInt();
        if( this.id == -1 ) this.id = null;
        valueTotal = in.readFloat();
        dateCredits = new Date( in.readLong() );
        productList = in.createTypedArrayList(CreditProduct.CREATOR);
        modifiable = in.readByte() != 0;
        this.valuePay = in.readFloat();
        this.state = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id == null? -1 : this.id );
        dest.writeFloat( valueTotal );
        dest.writeLong( dateCredits.getTime() );
        dest.writeTypedList(productList);
        dest.writeByte( (byte) (modifiable ? 1 : 0));
        dest.writeFloat( this.valuePay );
        dest.writeInt( this.state );
    }

    public static final Creator<Credit> CREATOR = new Creator<Credit>() {
        @Override
        public Credit createFromParcel(Parcel in) {
            return new Credit(in);
        }

        @Override
        public Credit[] newArray(int size) {
            return new Credit[size];
        }
    };

    public boolean addProduct(CreditProduct creditsProduct ) {
        if(!modifiable) return false;
        this.productList.add( creditsProduct );
        this.valueTotal  += creditsProduct.getPrice();
        return true;
    }

    public boolean deleteProduct ( CreditProduct creditsProduct ) {
        int index = this.productList.indexOf( creditsProduct );
        return deleteProduct(index);
    }

    public boolean deleteProduct(int index) {
        if( !modifiable ) return  false;
        if ( index != -1 ) {
            CreditProduct delProduct = this.productList.get( index );
            this.valueTotal  -= delProduct.getPrice();
            return true;
        }
        return false;
    }

    public float getValueTotal() {
        return valueTotal;
    }

    public Date getDateCredits() {
        return dateCredits;
    }

    public List<CreditProduct> getProductList() {
        return productList;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public float getValuePay() {
        return valuePay;
    }

    public float getValueRemanecente() {
        return this.getValueTotal() - this.getValuePay();
    }

    public int getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }
}
