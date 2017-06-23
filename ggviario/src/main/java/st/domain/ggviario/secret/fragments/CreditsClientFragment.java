package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.ggviario.secret.CreditsClientDetailActivity;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.adapter.CreditClientAdapter;
import st.domain.ggviario.secret.dao.ClientDao;
import st.domain.ggviario.secret.items.ClientViewHolder;
import st.domain.ggviario.secret.model.Client;
import st.zudamue.support.android.adapter.ItemDataSet;
import st.zudamue.support.android.adapter.ItemViewHolder;
import st.zudamue.support.android.adapter.RecyclerViewAdapter;
import st.zudamue.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 01/02/17.
 */

public class CreditsClientFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Context context;
    private ClientDao dao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.recycler_view, container, false);

        final ItemViewHolder.ItemCallback onClickItemCallback = new ItemViewHolder.ItemCallback() {
            @Override
            public void onCallback(ItemViewHolder itemViewHolder, View view, ItemDataSet itemDataSet, int adapterPosition) {
                ClientViewHolder.CreditsClientDataSet clientDataSet = (ClientViewHolder.CreditsClientDataSet) itemDataSet;

                Intent intent = new Intent( context, CreditsClientDetailActivity.class );
                Bundle bundle = new Bundle();
                bundle.putParcelable( "client", clientDataSet.getClient());
                intent.putExtras( bundle );
                context.startActivity(intent);
            }
        };

        // Recycler view bloc
        this.recyclerView = ( RecyclerView ) this.rootView.findViewById( R.id.rv);
        this.adapter = new CreditClientAdapter( this.context );
        this.adapter.registerFactory(R.layout._client_item, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new ClientViewHolder( view )
                        .setOnClickCallback( onClickItemCallback );
            }
        });

        RecyclerView.LayoutManager lln = new LinearLayoutManager( this.context, LinearLayoutManager.VERTICAL, false );
        this.recyclerView.setLayoutManager( lln );
        this.recyclerView.setAdapter( this.adapter );
        this.dao = new ClientDao( this.context );
        this.populateRecyclerView();

        return this.rootView;
    }

    private void populateRecyclerView() {
        this.dao.onLoad(new ClientDao.OnLoadAllData<Client>() {
            @Override
            protected void onLoadData( Client client, SQLRow row ) {
                Integer totalCredits = row.integer( ClientDao.ver_client_status.totalcredits );
                Integer totalCreditsPay = row.integer( ClientDao.ver_client_status.totalcreditspay );
                ItemDataSet clientDataSet = ClientViewHolder.newInstance( client, totalCredits, totalCreditsPay );
                adapter.addItem( clientDataSet );
            }
        });
    }
}
