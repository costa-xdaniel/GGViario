package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.dao.CreditsDao;
import st.domain.ggviario.secret.dao.Dao;
import st.domain.ggviario.secret.items.ClientDetailCreditsViewHolder;
import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.Credit;
import st.zudamue.support.android.adapter.ItemDataSet;
import st.zudamue.support.android.adapter.ItemViewHolder;
import st.zudamue.support.android.adapter.RecyclerViewAdapter;
import st.zudamue.support.android.fragment.BaseFragment;
import st.zudamue.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditsClientDetailCreditsFragment extends BaseFragment implements OnResultActivity {

    private View rootView;
    private RecyclerView recyclerView;
    private Context context;
    private RecyclerViewAdapter adapter;
    private CreditsDao creditsDao;
    private Client client;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if( savedInstanceState == null )
            savedInstanceState = getArguments();

        this.client = savedInstanceState.getParcelable( "client" );

        this.rootView = inflater.inflate( R.layout.recycler_view, container, false );
        this.recyclerView = (RecyclerView) this.rootView.findViewById( R.id.rv );

        //RECYCLER VIEW AREA
        this.creditsDao = new CreditsDao( this.context );
        RecyclerView.LayoutManager layoutParams = new StaggeredGridLayoutManager( 1, StaggeredGridLayoutManager.VERTICAL );
        this.adapter = new RecyclerViewAdapter( this.context );
        this.recyclerView.setLayoutManager( layoutParams );
        final ItemViewHolder.ItemCallback payCredits = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition) {
                openPaySheetBottom( itemDataSet, adapterPosition );
            }
        };

        final ItemViewHolder.ItemCallback payCreditsNow = new ItemViewHolder.DataSetCallback() {
            @Override
            protected void onDataSetCallback(ItemDataSet itemDataSet, int adapterPosition) {
                opemPayNowSheetBottom( itemDataSet, adapterPosition );
            }
        };


        this.adapter.registerFactory(R.layout._credit_item, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new ClientDetailCreditsViewHolder(view)
                        .payCallback( payCredits )
                        .payNowCallback( payCreditsNow );
            }
        });

        this.recyclerView.setAdapter( this.adapter );


        this.populateRecyclerView( );
        return  this.rootView;
    }

    private void openPaySheetBottom(ItemDataSet itemDataSet, int adapterPosition) {
        BottomSheetDialogFragment dialogFragment = new CreditPayBottomSheetDialogFragment();
        Bundle arguments = new Bundle();
        ClientDetailCreditsViewHolder.CreditsClientDetailCreditsDataSet creditDataSet = (ClientDetailCreditsViewHolder.CreditsClientDetailCreditsDataSet) itemDataSet;
        arguments.putParcelable( "credit", creditDataSet.getCredit());
        dialogFragment.setArguments( arguments );
        dialogFragment.show( this.getFragmentManager(), "" );
    }

    private void opemPayNowSheetBottom(ItemDataSet itemDataSet, int adapterPosition) {
        //TODO openPaySheetBottom credit here
    }

    private void populateRecyclerView() {
        this.creditsDao.loadCreditClient( this.client, new Dao.OnLoadAllData<Credit>() {
            @Override
            protected void onLoadData(Credit credit, SQLRow row) {
                adapter.addItem( new ClientDetailCreditsViewHolder.CreditsClientDetailCreditsDataSet(credit));
            }
        });
    }

    @Override
    public boolean onResultActivity(int requestCode, int resultCode, Intent data, Context context) {
        return false;
    }
}
