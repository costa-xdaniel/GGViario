package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.dao.CreditsDao;
import st.domain.ggviario.secret.model.Credit;
import st.zudamue.support.android.fragment.BaseBottomSheetDialogFragment;

/**
 *
 * Created by daniel on 3/13/17.
 */

public class CreditPayBottomSheetDialogFragment extends BaseBottomSheetDialogFragment {

    private Credit credit;
    private NumberFormat numberFormat;
    private EditText edNewValue;
    private TextView tvCreditRemanecent;
    private float lastValidValue;
    private CreditsDao creditsDao;
    private Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout._credit_pay, container, false );

        //Prepare tools
        this.numberFormat = NumberFormat.getNumberInstance( Locale.FRANCE );
        this.numberFormat.setMinimumFractionDigits( 2 );
        this.numberFormat.setMaximumFractionDigits( 2 );
        this.numberFormat.setMinimumIntegerDigits( 1 );

        //Load data
        Bundle arguments = savedInstanceState;
        if( arguments == null ) arguments = getArguments();
        prepareData( arguments );


        //Prepare buttom pay
        Button btPay = (Button) rootView.findViewById( R.id.bt_credit_pay );
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        //Print credits values
        TextView tvCreditTotal = (TextView) rootView.findViewById( R.id.tv_credit_value_final );
        tvCreditTotal.setText( this.numberFormat.format( this.credit.getValueTotal() ) );

        TextView tvCreditValuePay = (TextView) rootView.findViewById( R.id.tv_credit_value_pay);
        tvCreditValuePay.setText( this.numberFormat.format( this.credit.getValuePay() ) );

        //value remanecente
        this.edNewValue = (EditText) rootView.findViewById( R.id.ed_value_pay );
        this.edNewValue.setText( String.valueOf( 0.0f ) );
        this.edNewValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                keyUp( keyCode, event );
                return false;
            }
        });

        this.tvCreditRemanecent = (TextView) rootView.findViewById( R.id.tv_credit_remanecent );
        this.tvCreditRemanecent.setText( this.numberFormat.format( this.credit.getValueRemanecente() ) );
        this.tvCreditRemanecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRestante();
            }
        });

        return  rootView;
    }

    /**
     * value remanecente
     */
    private void clickRestante() {
        this.edNewValue.setText( String.valueOf( this.credit.getValueRemanecente() ) );
        this.tvCreditRemanecent.setText( this.numberFormat.format( 0.0f ) );
    }

    /**
     * key up
     * @param keyCode
     * @param event
     */
    private void keyUp(int keyCode, KeyEvent event) {
        float value = this.getValue();
        float recalceRemanecente = this.credit.getValueRemanecente() - value;
        if( recalceRemanecente < 0 ) {
            this.edNewValue.setText( String.valueOf( this.lastValidValue ) );
            this.tvCreditRemanecent.setText( this.numberFormat.format( value ) );
        } else {
            this.tvCreditRemanecent.setText( this.numberFormat.format( recalceRemanecente ) );
            this.lastValidValue = value;
        }
    }

    private void prepareData( Bundle arguments ) {
        this.credit = arguments.getParcelable( "credit" );
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        out.putParcelable( "credit", this.credit );
    }

    /**
     * Pay the credit
     */
    private void pay() {
        this.creditsDao = new CreditsDao( this.context );
        this.creditsDao.payCredit( this.credit, this.getValue(), null );
    }

    public float getValue() {
        try {
            return Float.parseFloat( this.edNewValue.getText().toString() );
        }catch ( Exception ignore ){
            return 0;
        }
    }
}
