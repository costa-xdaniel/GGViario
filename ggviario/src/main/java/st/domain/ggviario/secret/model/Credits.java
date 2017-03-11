package st.domain.ggviario.secret.model;


import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class Credits {

    private float valueTotal;
    private Date dateCredits;
    private List<CreditProduct>  productList;
    private boolean modifiable;

    public Credits () {
        this.modifiable = true;
        this.productList = new LinkedList<>();
        this.dateCredits = Calendar.getInstance().getTime();
        this.valueTotal = 0.0f;
    }

    public Credits ( float valueTotal, Date date, List<CreditProduct> productList ) {
        this.valueTotal = valueTotal;
        this.dateCredits = date;
        this.valueTotal = valueTotal;
        this.productList = new LinkedList<>( productList );
    }

    public boolean addProduct( CreditProduct creditsProduct ) {
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
}
