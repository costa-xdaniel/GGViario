package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.dao.CreditsDao;
import st.domain.ggviario.secret.dao.ProductDao;
import st.domain.ggviario.secret.items.CreditCreateNewItemViewHolder;
import st.domain.ggviario.secret.items.CreditNewItemViewHolder;
import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.CreditProduct;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;
import st.domain.support.android.adapter.RecyclerViewAdapter;
import st.domain.support.android.fragment.BaseFragment;
import st.domain.support.android.fragment.CallbackFragmentManager;


/**
 *
 * Created by dchost on 15/02/17.
 */
public class CreditsNewFragment  extends BaseFragment {

    private Context context;
    private RecyclerViewAdapter adapter;
    private double finalValueTotal;
    private TextView tvCreditValueFinal;
    private NumberFormat numberFormatter;
    private Client client;
    private Date dateFinisher;
    private CallbackFragmentManager.FragmentCallback done;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public CreditsNewFragment setDone(CallbackFragmentManager.FragmentCallback done){
        this.done = done;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout._credit_newcredit, container, false);
        this.tvCreditValueFinal = ( TextView) rootView.findViewById( R.id.tv_credit_value_final);
        this.numberFormatter = NumberFormat.getCurrencyInstance( Locale.FRANCE );
        this.numberFormatter.setMaximumFractionDigits( 2 );
        this.numberFormatter.setMinimumFractionDigits( 2 );
        this.numberFormatter.setMinimumIntegerDigits( 1 );

        //Prepare botom sheet
//        View bottomSheet = rootView.findViewById(R.id.ll_bottom_sheet);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {}
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
//        });




        //Prepare recycler vew item
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        this.adapter = new RecyclerViewAdapter( this.context );
        StaggeredGridLayoutManager sgl = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( sgl );
        recyclerView.setAdapter( this.adapter );
        this.populateData( );

        if( savedInstanceState == null ) {

            // Add more one month in the date finhisher
            Calendar calendar = Calendar.getInstance();
            calendar.add( Calendar.MONTH, 1);
            this.dateFinisher =  calendar.getTime();

            if( dateFinisher == null )
                throw  new RuntimeException( " dateFinisher is null " );

            Bundle arguments = this.getArguments();
            this.client = arguments.getParcelable( "client" );
        }

        return rootView;
    }

    /**
     * Populate the data setItem using the addItem  product date setItem
     * this is dynamic populate
     */
    private void populateData() {

        final CallbackFragmentManager.FragmentCallback addCreditItemCallback = new CallbackFragmentManager.FragmentCallback() {
            @Override
            public void onCallback(Fragment fragment, Map<String, Object> objectsParam, Bundle extraParam, Map<String, Object> pushMap) {
                CreditNewItemViewHolder.CreditProductItemDataSet item = ( CreditNewItemViewHolder.CreditProductItemDataSet ) objectsParam.get( "item" );
                adapter.addItem( adapter.getItemCount() - 1, item );
                finalValueTotal = finalValueTotal + item.getPriceQuantity();
                tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
            }
        };


        final ItemViewHolder.ItemCallback onClickOperationAddItem = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback( ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition ) {
                CreditsAddItemFragment dialogTestFragment = new CreditsAddItemFragment();
                Bundle params = new Bundle();
                params.putFloat( "old_price_quantity", 0.0f );
                dialogTestFragment.setArguments( params );
                dialogTestFragment.setActionCallback( addCreditItemCallback );
                dialogTestFragment.show( getFragmentManager(), CreditsAddItemFragment.class.getName() );
             }
        };


        final ItemViewHolder.ItemCallback removeCallback = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition) {
                CreditNewItemViewHolder.CreditProductItemDataSet creditItemDataSet = (CreditNewItemViewHolder.CreditProductItemDataSet) itemDataSet;
                adapter.removeItem( adapterPosition );
                finalValueTotal = finalValueTotal - creditItemDataSet.getOldPriceQuantity();
                tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
            }
        };


        final ItemViewHolder.ItemCallback editCallback = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, final int adapterPosition ) {

                final CreditNewItemViewHolder.CreditProductItemDataSet item = (CreditNewItemViewHolder.CreditProductItemDataSet) itemDataSet;
                CreditsAddItemFragment dialogTestFragment = new CreditsAddItemFragment();
                Bundle params = new Bundle();
                params.putFloat( "old_price_quantity", item.getPriceQuantity() );
                dialogTestFragment.setArguments( params );
                dialogTestFragment.setActionCallback(new CallbackFragmentManager.FragmentCallback() {
                    @Override
                    public void onCallback(Fragment fragment, Map<String, Object> objectsParam, Bundle extraParam, Map<String, Object> pushMap) {
                        adapter.setItem( adapterPosition, (ItemDataSet) objectsParam.get( "item" ));
                        finalValueTotal = finalValueTotal + item.getPriceQuantity() - item.getOldPriceQuantity();
                        tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
                    }
                });
                dialogTestFragment.show( getFragmentManager(), CreditsAddItemFragment.class.getName() );
            }
        };


        this.adapter.addItemFactory(R.layout.credits_operaction_add, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory( View view ) {
                CreditCreateNewItemViewHolder addCreditsProduct = new CreditCreateNewItemViewHolder( view );
                addCreditsProduct.addProductCallback( onClickOperationAddItem );
                return addCreditsProduct;
            }
        });

        /**
         * Add the credit credit item factory
         */
        this.adapter.addItemFactory(R.layout._credit_newcredit_item, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new CreditNewItemViewHolder( view )
                        .editCallback( editCallback )
                        .removeCallback( removeCallback )
                        ;
            }
        });

        ItemDataSet add = new CreditCreateNewItemViewHolder.AddCreditProductItemDataSet();
        this.adapter.addItem( add );
    }

    public boolean done() {

        if ( this.adapter.getItemCount() < 2 ) {
            Toast.makeText( this.context, "Nenhum item adicionado (Adicionar pelo menos um item)", Toast.LENGTH_LONG).show();
            return false;
        }
        this.regCredits();
        return true;
    }

    private void regCredits() {
        CreditsDao creditsDao = new CreditsDao( this.context );
        creditsDao.registerCredit( this.client, this.dateFinisher, this.finalValueTotal, this.createListCreditsProduct());
    }

    private List<CreditProduct> createListCreditsProduct() {
        List<CreditProduct> list = new LinkedList<>();
        for( ItemDataSet itemDataSet: this.adapter ) {

            if( itemDataSet instanceof CreditNewItemViewHolder.CreditProductItemDataSet ){
                CreditNewItemViewHolder.CreditProductItemDataSet dataSet = ( CreditNewItemViewHolder.CreditProductItemDataSet ) itemDataSet;
                CreditProduct creditProduct = new CreditProduct(
                        null,
                        dataSet.getSelectedProduct(),
                        dataSet.getQuantity(),
                        dataSet.getProductPrice().getPrice(),
                        dataSet.getSelectedMeasure(),
                        dataSet.getProductPrice().calcPrice( dataSet.getQuantity() )
                );
                list.add( creditProduct );
            }
        }

        this.callback( this.done );
        return list;
    }
}
