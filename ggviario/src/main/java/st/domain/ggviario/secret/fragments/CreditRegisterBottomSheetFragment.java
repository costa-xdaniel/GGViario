package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import st.domain.ggviario.GGViario;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.adapter.SimpleSpinnerAdapter;
import st.domain.ggviario.secret.dao.ProductDao;
import st.domain.ggviario.secret.items.CreditRegisterItemViewHolder;
import st.domain.ggviario.secret.model.Product;
import st.domain.ggviario.secret.model.ProductPrice;
import st.domain.support.android.fragment.BaseBottomSheetDialogFragment;
import st.domain.support.android.fragment.CallbackFragmentManager;

/**
 *
 * Created by dchost on 05/03/17.
 */

public class CreditRegisterBottomSheetFragment extends BaseBottomSheetDialogFragment {

    private Context context;
    private SimpleSpinnerAdapter productAdapter;
    private Spinner productSpinner;
    private SimpleSpinnerAdapter measureAdapter;
    private Spinner measureSpinner;
    private TextView tvProductPrice;
    private NumberFormat numberFormat;
    private TextView tvPriceQuantity;
    private ProductDao productDao;
    private EditText edCreditQuantity;
    private CallbackFragmentManager.FragmentCallback actionCallback;
    private float oldPriceQuantity;

    @Override
    public void onAttach(Context context) {
        super.onAttach( this.context );
        this.context = context;
    }

    public CreditRegisterBottomSheetFragment setActionCallback(CallbackFragmentManager.FragmentCallback actionCallback ){
        this.actionCallback = actionCallback;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout._credit_newcredit_additembottomsheet, container, false);
        
        if( savedInstanceState == null )
            savedInstanceState = getArguments();

        //load arguments
        CreditRegisterItemViewHolder.CreditProductItemDataSet item = savedInstanceState.getParcelable("item");

        // load util
        this.numberFormat = NumberFormat.getNumberInstance( Locale.FRANCE );
        this.numberFormat.setMinimumFractionDigits( 2 );
        this.numberFormat.setMaximumFractionDigits( 2 );
        this.numberFormat.setMinimumIntegerDigits( 1 );
        this.productDao = new ProductDao( this.context );


        // load view
        this.tvProductPrice = ( TextView ) rootView.findViewById( R.id.tv_credit_product_price);
        this.tvPriceQuantity = ( TextView ) rootView.findViewById( R.id.tv_credit_price_quantity );



        Button btAddOk = (Button) rootView.findViewById( R.id.bt_credit_add_ok );
        btAddOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAction();
            }
        });


        //Prepare editTextQuantity
        this.edCreditQuantity = (EditText) rootView.findViewById( R.id.ed_credit_quantity );
        this.edCreditQuantity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                editQuantity( keyCode, event );
                return false;
            }
        });




        // prepare product spinner
        this.productSpinner = ( Spinner ) rootView.findViewById( R.id.spinner_chose_product );
        this.productAdapter = new SimpleSpinnerAdapter(getContext());
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                onSelectedProduct( index );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        SimpleSpinnerAdapter.OnItemCreated onItemCreated;
        productAdapter.setOnItemCreated(onItemCreated = new SimpleSpinnerAdapter.OnItemCreated() {
            @Override
            public void accept(View viewLayout, TextView view, final int index, CharSequence item) {
                if( index == 0 ){
                    view.setHint( item );
                    view.setText( null );
                } else {
                }
            }
        });

        //populate
        productAdapter.add( "Produto" );
        this.productAdapter.addAll( this.productDao.loadProducts() );
        this.productSpinner.setAdapter( this.productAdapter );


        // prepare measure dataset
        this.measureSpinner = ( Spinner ) rootView.findViewById( R.id.spinner_chose_measure );
        this.measureAdapter = new SimpleSpinnerAdapter( getContext() );
        this.measureSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id) {
                onMeasureSelected( position );
            }
            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
            }
        });
        this.measureAdapter.setOnItemCreated( onItemCreated );
        this.measureSpinner.setAdapter( measureAdapter );
        this.onSelectedProduct( 0 );



        //Set the transfered data if exist
        this.oldPriceQuantity = 0.0f;
        if ( item != null ) {

            this.oldPriceQuantity =  item.getOldPriceQuantity();
            this.edCreditQuantity.setText( String.valueOf( item.getQuantity() ) );

            this.productSpinner.setSelection( item.getSelectedProductIndex() );
            this.onSelectedProduct( item.getSelectedProductIndex() );

            this.measureSpinner.setSelection( item.getSelectedMeasureIndex() );
            this.onMeasureSelected( item.getSelectedMeasureIndex() );

        }

        return rootView;
    }


    private void editQuantity( int keyCode, KeyEvent event ) {
        int measureIndex = this.measureSpinner.getSelectedItemPosition();
        if( measureIndex > 0 && this.hasQuantity() ){
            ProductPrice productPrice = (ProductPrice) this.measureAdapter.get( measureIndex );
            float priceQuantity = productPrice.calcPrice(getEnterQuantity());
            this.tvPriceQuantity.setText( this.numberFormat.format( priceQuantity ) );
        }
        else {
            this.tvPriceQuantity.setText( this.numberFormat.format( 0.0 ) );
        }
    }

    private void onSelectedProduct( int index ) {
        this.measureAdapter.clear();
        this.measureAdapter.add( "Medida" );
        if( index == 0 ){
            this.measureAdapter.notifyDataSetChanged();
            this.tvProductPrice.setText( this.numberFormat.format( 0.0 ));
        } else {
            Product product = (Product) this.productAdapter.get( index );
            List< ProductPrice > listMeasure = this.productDao.loadProductPriceOf( product );
            this.measureAdapter.addAll( listMeasure );
            this.measureAdapter.notifyDataSetChanged();
            this.tvPriceQuantity.setText( this.numberFormat.format( 0 ) );
        }
    }

    private void onMeasureSelected( int index ) {

        if( index != 0 ) {
            ProductPrice productPrice = (ProductPrice) this.measureAdapter.get( index );
            this.tvProductPrice.setText( this.numberFormat.format( productPrice.getPrice() ) );

            if( hasQuantity() ) {
                double quantity = this.getEnterQuantity();
                float priceQuantity = productPrice.calcPrice(quantity);
                this.tvPriceQuantity.setText( this.numberFormat.format( priceQuantity ) );
            }
        }
    }

    private boolean hasQuantity() {
        String text = this.edCreditQuantity.getText().toString();
        if( text != null && text.length() > 0 ){
            try {
                return Double.parseDouble( text ) > 0.0;
            }catch (Exception ignore){}
        }
        return false;
    }

    private double getEnterQuantity (){
        if( hasQuantity() ){
            return Double.parseDouble( this.edCreditQuantity.getText().toString() );
        }
        return 0.0;
    }



    private void onAction() {

        if( !isValid() ) {
            Toast.makeText( this.context, "Preencha a folha completamente", Toast.LENGTH_LONG ).show();
            return;
        }


        Map<String, Object> params = new LinkedHashMap<>();
        CreditRegisterItemViewHolder.CreditProductItemDataSet item = new CreditRegisterItemViewHolder.CreditProductItemDataSet( this.oldPriceQuantity);
        int measureIndex = this.measureSpinner.getSelectedItemPosition();
        int productIndex = this.productSpinner.getSelectedItemPosition();
        Product product = ( Product ) this.productAdapter.get( productIndex );
        ProductPrice productPrice = (ProductPrice) this.measureAdapter.get( measureIndex );
        double quantity = this.getEnterQuantity();
        float priceQuantity = productPrice.calcPrice( quantity );

        item.selectedProduct( product )
            .priceQuantity( priceQuantity )
            .quantity( quantity )
            .productPrice( productPrice )
            .selectedMeasureIndex( measureIndex )
            .selectedProductIndex( productIndex )
        ;
        params.put( "item", item );

        getCallback().callbackObject(actionCallback, params);
        this.dismiss();
    }

    public boolean isValid() {
        return this.hasQuantity()
                && this.measureSpinner.getSelectedItemPosition() != 0 ;
    }
}
