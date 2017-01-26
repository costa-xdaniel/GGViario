package st.domain.ggviario.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import st.domain.ggviario.secret.dao.DaoProvider;
import st.domain.ggviario.secret.support.CropAdapter;
import st.domain.ggviario.secret.support.ProviderAdapter;
import st.domain.ggviario.secret.support.events.BackHomeUpMenuObserver;
import st.domain.ggviario.secret.support.events.MenuMapper;
import st.domain.ggviario.secret.support.fragments.TextSeparator;

/**
 *
 * Created by xdata on 12/24/16.
 */

public class Provider extends AppCompatActivity implements ProviderAdapter.OpenProvider {

    private FloatingActionButton fabNewProvider;
    private RecyclerView rvListProvider;
    private ProviderAdapter addpter;
    private List<st.domain.ggviario.secret.model.Provider> list;
    Toolbar toolbar;
    private MenuMapper menuMapper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout._provider);
        this.fabNewProvider = (FloatingActionButton) this.findViewById(R.id.fab_new_provider);
        this.rvListProvider = (RecyclerView) findViewById(R.id.rv_list_provider);

        this.fabNewProvider.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCreateProvider();
                    }
                }
        );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){};

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);



        this.rvListProvider.setLayoutManager(layoutManager);
        this.addpter = new ProviderAdapter(this);
        this.addpter.setOpenProvider(this);
        this.rvListProvider.setAdapter(this.addpter);
        this.prepareToolbar();

        Thread action = new Thread(new Runnable() {
            @Override
            public void run() {
                populateList();
            }
        });
        this.runOnUiThread(action);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_provider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.menuMapper.menuAction(item);
    }




    private void prepareToolbar()
    {
        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.menuMapper = new MenuMapper(this);

        this.menuMapper.add(new BackHomeUpMenuObserver());
        this.toolbar.setTitle(R.string.prvider);
        this.toolbar.inflateMenu(R.menu.menu_provider_new);
        this.setSupportActionBar(toolbar);

        if(this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(R.string.crop);
            this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }


    private void populateList() {

        DaoProvider daoCrop = new DaoProvider(this);
        this.list = daoCrop.loadProviderData();
        this.addpter.clear();


        for(st.domain.ggviario.secret.model.Provider provider: list) {

            addpter.add(new ProviderAdapter.ProviderDataSet(provider, this));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.populateList();
    }

    private void openCreateProvider() {

        Intent intent = new Intent(this, CropNew.class);
        startActivityForResult(intent, 10);

    }

    @Override
    public void openProvider(st.domain.ggviario.secret.model.Provider dataSet) {

        /*
        Intent intent = new Intent(this, CropContent.class);
        Bundle b = new Bundle();
        b.putSerializable("date", dataSet.getDate());
        intent.putExtras(b);
        startActivity(intent);

        */
    }
}
