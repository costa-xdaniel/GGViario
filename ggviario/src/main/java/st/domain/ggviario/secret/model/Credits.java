package st.domain.ggviario.secret.model;


import java.util.Date;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class Credits {

    private float valueTotal;
    private Date dateCredits;

    public Credits ( float valueTotal, Date date ) {
        this.valueTotal = valueTotal;
        this.dateCredits = date;
    }


    public float getValueTotal() {
        return valueTotal;
    }

    public Date getDateCredits() {
        return dateCredits;
    }
}
