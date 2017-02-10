package st.domain.ggviario.secret.items;

import android.content.res.Resources;
import android.support.annotation.Px;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Credits;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditsClinetDetaisCreditsViewHolder extends ItemViewHolder {

    private final TextView tvCreditDate;
    private final TextView tvCreditValueTotal;
    private CreditsClientDetailCreditsDataSet dataSet;

    private DateFormat dateFormat;
    private NumberFormat numberFormat;

    public CreditsClinetDetaisCreditsViewHolder(View itemView) {

        super(itemView);

        this.tvCreditDate = ( TextView ) this.itemView.findViewById( R.id.tv_credit_date );
        this.tvCreditValueTotal = ( TextView ) this.itemView.findViewById( R.id.tv_credit_value_total );
        this.dateFormat = new SimpleDateFormat( "dd-MM-yyyy" );
        this.numberFormat = NumberFormat.getNumberInstance( Locale.FRANCE );
    }

    @Override
    public void bind( ItemDataSet dataSet, int currentAdapterPosition, int totalAdapterItem ) {
        this.dataSet = (CreditsClientDetailCreditsDataSet) dataSet;
        this.tvCreditDate.setText( this.dateFormat.format( this.dataSet.credits.getDateCredits() ) );
        this.tvCreditValueTotal.setText( this.numberFormat.format(this.dataSet.credits.getValueTotal()).concat( " STD" ) );

        ViewGroup.LayoutParams layoutParam = this.itemView.getLayoutParams();
        if( layoutParam instanceof  StaggeredGridLayoutManager.LayoutParams ) {

            Resources resoursce = this.getContext().getResources();
            int left = 0;
            int right = 0;

            int top = (int) ((currentAdapterPosition == 0 )? resoursce.getDimension( R.dimen.px_16 )
                    : resoursce.getDimension( R.dimen.px_4 ));
            int bottom = (int) ((currentAdapterPosition == totalAdapterItem )? resoursce.getDimension( R.dimen.px_16 )
                                            : resoursce.getDimension( R.dimen.px_4 ));

            ((StaggeredGridLayoutManager.LayoutParams) layoutParam).setMargins(left, top, right, bottom);
        }



    }

    public static  class CreditsClientDetailCreditsDataSet implements ItemDataSet{

        private Credits credits;

        public CreditsClientDetailCreditsDataSet(Credits credits) {
            this.credits = credits;
        }

        @Override
        public int getLayoutId() {
            return R.layout._credits_clients_detais_credits_item;
        }
    }
}
