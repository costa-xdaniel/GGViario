package st.domain.ggviario.secret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.adapter.CreditClientAdapter;
import st.domain.ggviario.secret.dao.ClientDao;
import st.domain.ggviario.secret.items.CreditsClientItem;
import st.domain.ggviario.secret.model.Client;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;
import st.domain.support.android.adapter.RecyclerViewAdapter;
import st.domain.support.android.sql.SQLRow;

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
        this.rootView = inflater.inflate(R.layout._credits_clients, container, false);

        // Recycler view bloc
        this.recyclerView = ( RecyclerView ) this.rootView.findViewById( R.id.rv_credits_clients );
        this.adapter = new CreditClientAdapter( this.context );
        this.adapter.addItemFactory(R.layout._credits_clients_items, new RecyclerViewAdapter.ViewHolderFactory() {
            @Override
            public ItemViewHolder factory(View view) {
                return new CreditsClientItem( view );
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
                Integer totalCredits = row.integer( ClientDao.VER_STATUS_CLIENT.totalcredits );
                Integer totalCreditsPay = row.integer( ClientDao.VER_STATUS_CLIENT.totalcreditspay );
                ItemDataSet clientDataSet = CreditsClientItem.newInstance( client, totalCredits, totalCreditsPay );
                adapter.add( clientDataSet );
            }
        });
    }
}
