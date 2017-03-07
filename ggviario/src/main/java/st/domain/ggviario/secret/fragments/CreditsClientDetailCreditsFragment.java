package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import st.domain.ggviario.secret.model.Credits;
import st.domain.support.android.adapter.ItemViewHolder;
import st.domain.support.android.adapter.RecyclerViewAdapter;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditsClientDetailCreditsFragment extends Fragment implements OnResultActivity {

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

        this.adapter.addItemFactory(R.layout._credit_item, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new ClientDetailCreditsViewHolder(view);
            }
        });

        this.recyclerView.setAdapter( this.adapter );


        this.populateRecyclerView( );
        return  this.rootView;
    }

    private void populateRecyclerView() {
        this.creditsDao.loadCreditClient( this.client, new Dao.OnLoadAllData<Credits>() {
            @Override
            protected void onLoadData(Credits credits, SQLRow row) {
                adapter.addItem( new ClientDetailCreditsViewHolder.CreditsClientDetailCreditsDataSet(credits));
            }
        });
    }

    @Override
    public boolean onResultActivity(int requestCode, int resultCode, Intent data, Context context) {
        return false;
    }
}
