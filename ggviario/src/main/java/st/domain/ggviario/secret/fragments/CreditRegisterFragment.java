package st.domain.ggviario.secret.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.dao.CreditsDao;
import st.domain.ggviario.secret.items.CreditCreateNewItemViewHolder;
import st.domain.ggviario.secret.items.CreditRegisterItemViewHolder;
import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.CreditProduct;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;
import st.domain.support.android.adapter.RecyclerViewAdapter;
import st.domain.support.android.fragment.BaseFragment;
import st.domain.support.android.fragment.CallbackFragmentManager;
import st.domain.support.android.fragment.DatePickerDialogSupport;
import st.domain.support.android.util.DateUtil;


/**
 *
 * Created by dchost on 15/02/17.
 */
public class CreditRegisterFragment extends BaseFragment {

    private Context context;
    private RecyclerViewAdapter adapter;
    private float finalValueTotal;
    private TextView tvCreditValueFinal;
    private NumberFormat numberFormatter;
    private DateFormat dateFormatter;
    private Client client;
    private Date dateFinisher;
    private boolean dateChanged;
    private CallbackFragmentManager.FragmentCallback done;
    private TextView tvCreditDateFinisher;
    private Calendar creditCalendar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public CreditRegisterFragment setDone(CallbackFragmentManager.FragmentCallback done){
        this.done = done;
        return this;
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putLong( "credit-date", this.dateFinisher.getTime() );
        out.putBoolean( "credit-date-change", this.dateChanged );
        out.putParcelable( "client", this.client );
        out.putFloat( "credit-value-total", this.finalValueTotal );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout._credit_newcredit, container, false);

        this.dateFormatter = new SimpleDateFormat( "dd 'de' MMMMM 'de' yyyy");
        this.numberFormatter = NumberFormat.getCurrencyInstance( Locale.FRANCE );
        this.numberFormatter.setMaximumFractionDigits( 2 );
        this.numberFormatter.setMinimumFractionDigits( 2 );
        this.numberFormatter.setMinimumIntegerDigits( 1 );
        this.creditCalendar = Calendar.getInstance();

        //date of credit

        Bundle arguments = savedInstanceState;

        //on init
        if( arguments == null ) {

            arguments = getArguments();
            // Add more one month in the date finhisher
            creditCalendar.add( Calendar.MONTH, 1 );
            this.dateFinisher =  creditCalendar.getTime();
            this.dateChanged = false;
            this.client = arguments.getParcelable( "client" );
            this.finalValueTotal = 0.0f;
        } else {
            this.dateFinisher = new Date( arguments.getLong( "credit-date" ) );
            this.dateChanged = arguments.getBoolean( "credit-date-change" );
            this.client = arguments.getParcelable( "client" );
            this.finalValueTotal = arguments.getFloat( "credit-value-total" );
            this.creditCalendar.setTime( this.dateFinisher );
        }


        //credit value
        this.tvCreditValueFinal = ( TextView) rootView.findViewById( R.id.tv_credit_value_final);
        this.tvCreditValueFinal.setText( this.numberFormatter.format( this.finalValueTotal ) );

        //credit date
        this.tvCreditDateFinisher = ( TextView ) rootView.findViewById( R.id.tv_credit_date );
        if( ! this.dateChanged )
            this.tvCreditDateFinisher.setText( this.dateFormatter.format( dateFinisher ).concat( "(1 mês)" ) );
        else
            this.tvCreditDateFinisher.setText( this.dateFormatter.format( dateFinisher ) );

        //change date
        rootView.findViewById( R.id.bt_credit_change_date ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openModalChangeDate();
            }
        });



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

        return rootView;
    }

    private void openModalChangeDate() {

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        creditCalendar.set( year, monthOfYear, dayOfMonth );
                        dateFinisher = creditCalendar.getTime();
                        dateChanged = true;
                        tvCreditDateFinisher.setText( dateFormatter.format( dateFinisher ) );
                    }
                },
                creditCalendar.get( Calendar.YEAR ),
                creditCalendar.get( Calendar.MONTH ),
                creditCalendar.get( Calendar.DAY_OF_MONTH )
        );

        final Date currentDate = Calendar.getInstance().getTime();
        final Calendar calendar = Calendar.getInstance();

        datePickerDialog.setMinDate( Calendar.getInstance() );
//        datePickerDialog.setOnCheckSelection(new DatePickerDialogSupport.OnCheckSelection() {
//            @Override
//            public boolean isSelectable(int year, int month, int day) {
////                calendar.set( year, month, day );
////                Date date = calendar.getTime();
////                DateUtil dateUtil = new DateUtil( date );
////                return currentDate.before( date )
////                        || dateUtil.isToday();
//                return true;
//            }
//        });

//        datePickerDialog.setSelectableDays(selectableDays);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });


        FragmentManager fragmanager = this.getActivity().getFragmentManager();
        datePickerDialog.show( fragmanager, "credit-date-picker" );

    }

    /**
     * Populate the data setItem using the addItem  product date setItem
     * this is dynamic populate
     */
    private void populateData() {

        final CallbackFragmentManager.FragmentCallback addCreditItemCallback = new CallbackFragmentManager.FragmentCallback() {
            @Override
            public void onCallback(Fragment fragment, Map<String, Object> objectsParam, Bundle extraParam, Map<String, Object> pushMap) {
                CreditRegisterItemViewHolder.CreditProductItemDataSet item = ( CreditRegisterItemViewHolder.CreditProductItemDataSet ) objectsParam.get( "item" );
                adapter.addItem( adapter.getItemCount() - 1, item );
                finalValueTotal = finalValueTotal + item.getPriceQuantity();
                tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
            }
        };


        final ItemViewHolder.ItemCallback onClickOperationAddItem = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback( ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition ) {
                CreditRegisterBottomSheetFragment dialogTestFragment = new CreditRegisterBottomSheetFragment();
                Bundle params = new Bundle();
                params.putFloat( "old_price_quantity", 0.0f );
                dialogTestFragment.setArguments( params );
                dialogTestFragment.setActionCallback( addCreditItemCallback );
                dialogTestFragment.show( getFragmentManager(), CreditRegisterBottomSheetFragment.class.getName() );
             }
        };


        final ItemViewHolder.ItemCallback removeCallback = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition) {
                CreditRegisterItemViewHolder.CreditProductItemDataSet creditItemDataSet = (CreditRegisterItemViewHolder.CreditProductItemDataSet) itemDataSet;
                adapter.removeItem( adapterPosition );
                finalValueTotal = finalValueTotal - creditItemDataSet.getOldPriceQuantity();
                tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
            }
        };


        final ItemViewHolder.ItemCallback editCallback = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, final int adapterPosition ) {

                final CreditRegisterItemViewHolder.CreditProductItemDataSet item = (CreditRegisterItemViewHolder.CreditProductItemDataSet) itemDataSet;
                CreditRegisterBottomSheetFragment dialogTestFragment = new CreditRegisterBottomSheetFragment();
                Bundle params = new Bundle();
                params.putParcelable( "item", item );
                dialogTestFragment.setArguments( params );

                dialogTestFragment.setActionCallback( new CallbackFragmentManager.FragmentCallback() {
                    @Override
                    public void onCallback(Fragment fragment, Map<String, Object> objectsParam, Bundle extraParam, Map<String, Object> pushMap) {
                        adapter.setItem( adapterPosition, (ItemDataSet) objectsParam.get( "item" ));
                        finalValueTotal = finalValueTotal + item.getPriceQuantity() - item.getOldPriceQuantity();
                        tvCreditValueFinal.setText( numberFormatter.format( finalValueTotal ) );
                    }
                });
                dialogTestFragment.show( getFragmentManager(), CreditRegisterBottomSheetFragment.class.getName() );
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
                return new CreditRegisterItemViewHolder( view )
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

            if( itemDataSet instanceof CreditRegisterItemViewHolder.CreditProductItemDataSet ){
                CreditRegisterItemViewHolder.CreditProductItemDataSet dataSet = ( CreditRegisterItemViewHolder.CreditProductItemDataSet ) itemDataSet;
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
