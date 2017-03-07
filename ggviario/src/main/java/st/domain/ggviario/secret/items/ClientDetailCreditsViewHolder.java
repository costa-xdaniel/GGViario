package st.domain.ggviario.secret.items;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.CreditProduct;
import st.domain.ggviario.secret.model.Credits;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;
import st.domain.support.android.adapter.RecyclerViewAdapter;
import st.domain.support.android.util.DataUtil;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class ClientDetailCreditsViewHolder extends ItemViewHolder {

    private final TextView tvCreditDate;
    private final TextView tvCreditValueTotal;
    private final RecyclerViewAdapter adapter;
    private final TextView tvCreditCountPrimary;
    private final TextView tvCreditCountSecond;
    private final Button btPayNow;
    private final Button btPay;
    private final ImageButton ibtCreditViewMore;
    private final View areaViewMore;
    private CreditsClientDetailCreditsDataSet dataSet;
    private RecyclerView recyclerView;
    private DateFormat dateFormat;
    private NumberFormat formaterMoney;
    NumberFormat countFormatter;
    private Calendar calendar;

    private float scale;
    private int height;
    private int width;
    private boolean expand;

    public ClientDetailCreditsViewHolder(View itemView) {

        super(itemView);

        //formatter and calendar
        this.dateFormat = new SimpleDateFormat( "dd-MM-yyyy" );
        this.formaterMoney = NumberFormat.getNumberInstance( Locale.FRANCE );
        this.formaterMoney.setMinimumFractionDigits(2);
        this.formaterMoney.setMaximumFractionDigits(2);
        this.calendar = Calendar.getInstance();

        this.countFormatter = NumberFormat.getNumberInstance();
        this.countFormatter.setMinimumIntegerDigits(2);

        //load view components
        this.tvCreditDate = ( TextView ) this.itemView.findViewById( R.id.tv_credit_date );
        this.tvCreditValueTotal = ( TextView ) this.itemView.findViewById( R.id.tv_credit_value_final);
        this.tvCreditCountPrimary = ( TextView ) this.itemView.findViewById( R.id.tv_credit_count_primary );
        this.tvCreditCountSecond = ( TextView ) this.itemView.findViewById( R.id.tv_credit_count_second );

        //pay actions
        this.btPayNow = ( Button ) this.itemView.findViewById( R.id.bt_credit_pay_now );
        this.btPay = ( Button ) this.itemView.findViewById( R.id.bt_credit_pay );
        this.btPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payNow();
            }
        });
        this.btPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payFinally();
            }
        });

        //view more
        this.expand = false;
        this.ibtCreditViewMore = ( ImageButton ) this.itemView.findViewById( R.id.ibt_credit_more );
        this.areaViewMore = this.itemView.findViewById( R.id.area_credit_detail );
        this.areaViewMore.setVisibility( View.GONE );
        this.ibtCreditViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoreClicked();
            }
        });



        //dimension
        this.scale = getContext().getResources().getDisplayMetrics().density;
        View view =  this.itemView.findViewById( R.id.area_credit_count );
        this.width = this.getContext().getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f );
//        this.width = this.itemView.getWidth();
        this.height = ( this.width /16 )* 9;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = this.height;

        //recycler view area
        this.recyclerView = (RecyclerView) this.itemView.findViewById( R.id.rv );
        this.adapter = new RecyclerViewAdapter( this.getContext() );
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        this.recyclerView.setLayoutManager( layoutManager );
        this.adapter.addItemFactory(R.layout._credit_item_product,
                new RecyclerViewAdapter.ViewHolderFactory() {
                    @Override
                    public ItemViewHolder factory(View view) {
                        return new CreditProductViewHolder(view);
                    }
                });
        this.recyclerView.setAdapter( this.adapter );
    }

    private void onMoreClicked() {
        this.expand = !this.expand;
        expand( this.expand);
    }

    public void expand(boolean expand) {
        if( expand ){
            this.ibtCreditViewMore.setImageResource( R.drawable.ic_expand_less_black_24dp );
            this.areaViewMore.setVisibility( View.VISIBLE );
        }
        else {
            this.ibtCreditViewMore.setImageResource( R.drawable.ic_expand_more_black_24dp );
            this.areaViewMore.setVisibility( View.GONE );
        }
    }

    private void payNow() {

    }

    private void payFinally() {

    }

    @Override
    public void onBind(ItemDataSet dataSet, int currentAdapterPosition, int totalDataSet) {

        //setItem values
        this.dataSet = (CreditsClientDetailCreditsDataSet) dataSet;
        this.tvCreditDate.setText( this.dateFormat.format( this.dataSet.credits.getDateCredits() ) );
        this.tvCreditValueTotal.setText( this.formaterMoney.format(this.dataSet.credits.getValueTotal()).concat( " STD" ) );

        //count days
        DataUtil dataUtil = new DataUtil();
        DataUtil.Interval interval = dataUtil.difference(this.dataSet.credits.getDateCredits());

        if( interval.getYear() == 0 && interval.getMonth() == 0) {
            this.tvCreditCountPrimary.setText( countFormatter.format( interval.getDay() ) );
            this.setCountSecond(interval.getDay(), R.string.days, R.string.day );
        }
        else if( interval.getYear() == 0 ){
            this.tvCreditCountPrimary.setText( countFormatter.format( interval.getMonth() ) );
            setCountSecond(interval.getMonth(), R.string.months, R.string.month);
        }
        else{
            this.tvCreditCountPrimary.setText( countFormatter.format( interval.getYear() ) );
            setCountSecond(interval.getYear(), R.string.years, R.string.year);
        }


        ViewGroup.LayoutParams layoutParam = this.itemView.getLayoutParams();
        if( layoutParam instanceof  StaggeredGridLayoutManager.LayoutParams ) {

            Resources resoursce = this.getContext().getResources();
            int left = 0;
            int right = 0;

            int top = (int) ((currentAdapterPosition == 0 )? resoursce.getDimension( R.dimen.dp_16 )
                    : resoursce.getDimension( R.dimen.dp_4 ));
            int bottom = (int) ((currentAdapterPosition == totalDataSet)? resoursce.getDimension( R.dimen.dp_16 )
                                            : resoursce.getDimension( R.dimen.dp_4 ));

//            ((StaggeredGridLayoutManager.LayoutParams) layoutParam).setMargins(left, top, right, bottom);
        }


        //Calendar
        this.calendar.setTime( this.dataSet.credits.getDateCredits() );

        //Repopulate Recycler View
        this.adapter.clear();
        for (CreditProduct creditProduct: this.dataSet.credits.getProductList()) {
            CreditProductDataSet creditProductDataSet = new CreditProductDataSet( creditProduct );
            this.adapter.addItem( creditProductDataSet );
        }
    }

    public void setCountSecond(int interval, int strings, int string) {
        if( interval > 1 ) this.tvCreditCountSecond.setText( getContext().getString( strings ));
        else this.tvCreditCountSecond.setText( getContext().getString( string ) );
    }

    public static  class CreditsClientDetailCreditsDataSet implements ItemDataSet{

        private Credits credits;

        public CreditsClientDetailCreditsDataSet(Credits credits) {
            this.credits = credits;
        }

        @Override
        public int getLayoutId() {
            return R.layout._credit_item;
        }
    }

    private class CreditProductViewHolder extends  ItemViewHolder {

        private  TextView tvProductName;
        private  TextView tvProductQuantity;
        private  TextView tvProductValue;
        private CreditProductDataSet dataSet;
        private NumberFormat numberFormat;

        public CreditProductViewHolder(View itemView) {
            super(itemView);
            this.tvProductName = (TextView) this.itemView.findViewById( R.id.tv_credit_product);
            this.tvProductQuantity = (TextView) this.itemView.findViewById( R.id.tv_credit_quantity);
            this.tvProductValue = (TextView) this.itemView.findViewById( R.id.tv_credit_product_price);

            this.numberFormat = NumberFormat.getNumberInstance( Locale.FRANCE );
            this.numberFormat.setMaximumFractionDigits( 2 );
            this.numberFormat.setMinimumFractionDigits( 2 );
        }

        @Override
        public void onBind(ItemDataSet dataSet, int currentAdapterPosition, int totalDataSet) {
            //Set values
            this.dataSet = (CreditProductDataSet) dataSet;
            this.tvProductName.setText( this.dataSet.creditProduct.getProduct() );
            this.tvProductQuantity.setText( this.numberFormat.format( this.dataSet.creditProduct.getQuantity() ));
            String price = this.numberFormat.format( this.dataSet.creditProduct.getPrice() );
            this.tvProductValue.setText( price.concat( " STD" ) );

            ViewGroup.LayoutParams layoutParam = this.itemView.getLayoutParams();
            if( layoutParam instanceof StaggeredGridLayoutManager.LayoutParams) {

                Resources resources = this.getContext().getResources();
                int left = (int) resources.getDimension( R.dimen.dp_1 );
                int top = (int) resources.getDimension( R.dimen.dp_1 );
                int right = (int) resources.getDimension( R.dimen.dp_1 );
                int bottom = (int) resources.getDimension( R.dimen.dp_1 );
//                ( ( StaggeredGridLayoutManager.LayoutParams ) layoutParam ).setMargins( left, top, right, bottom );
            }
        }
    }

    private class CreditProductDataSet implements  ItemDataSet {

        private CreditProduct creditProduct;

        public CreditProductDataSet ( CreditProduct creditProduct ) {
            this.creditProduct = creditProduct;
        }

        @Override
        public int getLayoutId() {
            return R.layout._credit_item_product;
        }
    }
}
