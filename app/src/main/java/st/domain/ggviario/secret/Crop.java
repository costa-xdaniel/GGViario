package st.domain.ggviario.secret;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import st.domain.ggviario.secret.dao.DaoCrop;
import st.domain.ggviario.secret.support.CropAdapter;
import st.domain.ggviario.secret.support.events.BackHomeUpMenuObserver;
import st.domain.ggviario.secret.support.events.MenuMapper;
import st.domain.ggviario.secret.support.fragments.TextSeparator;

/**
 * Created by xdata on 12/24/16.
 */

public class Crop extends AppCompatActivity implements CropAdapter.OpenCrop {

    private FloatingActionButton fadNewClient;
    private RecyclerView rvListCrop;
    private CropAdapter addpter;
    private List<st.domain.ggviario.secret.model.Crop> list;
    Toolbar toolbar;
    private MenuMapper menuMapper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout._crop);
        this.fadNewClient = (FloatingActionButton) this.findViewById(R.id.fab_new_crop);
        this.rvListCrop = (RecyclerView) findViewById(R.id.rv_list_crop);

        this.fadNewClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCreateCrop();
                    }
                }
        );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
        };

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        findViewById(1);

        this.rvListCrop.setLayoutManager(layoutManager);
        this.addpter = new CropAdapter(this);
        this.addpter.setOpenCrop(this);
        this.rvListCrop.setAdapter(this.addpter);
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
        getMenuInflater().inflate(R.menu.menu_crop, menu);
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
        this.toolbar.setTitle(R.string.crop);
        this.toolbar.inflateMenu(R.menu.menu_crop_new);

        this.setSupportActionBar(toolbar);
        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(R.string.crop);
            this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }


    private void populateList() {

        DaoCrop daoCrop = new DaoCrop(this);
        this.list = daoCrop.loadCropData();
        this.addpter.clear();
        Calendar lastDate = null;
        Calendar auxDate;
        Calendar mesPassdo = Calendar.getInstance();
        mesPassdo.add(Calendar.MONTH, -1);


        DateFormat monthFormater = new SimpleDateFormat(getString(R.string.month_of));
        DateFormat yearFormater = new SimpleDateFormat(getString(R.string.year_of_month_of));
        int iCountMesPassdo = 0;
        for(st.domain.ggviario.secret.model.Crop crop: list)
        {
            auxDate = Calendar.getInstance();
            auxDate.setTime(crop.getDate());

            if(lastDate == null)
                lastDate = auxDate;

            if(Calendar.getInstance().get(Calendar.YEAR) == auxDate.get(Calendar.YEAR)
                    && Calendar.getInstance().get(Calendar.MONTH) == auxDate.get(Calendar.MONTH)
                    && this.addpter.getItemCount() == 0 ) {
                addpter.add(new TextSeparator.TextSeparatorDataSet(getString(R.string.this_month)));
            }
            else if (mesPassdo.get(Calendar.YEAR) == auxDate.get(Calendar.YEAR)
                    && mesPassdo.get(Calendar.MONTH) ==  auxDate.get(Calendar.MONTH)
                    && iCountMesPassdo == 0){

                iCountMesPassdo ++;
                addpter.add(new TextSeparator.TextSeparatorDataSet(getString(R.string.past_month)));
            }
            else if(lastDate.get(Calendar.YEAR) == auxDate.get(Calendar.YEAR)
                    && lastDate.get(Calendar.MONTH) != auxDate.get(Calendar.MONTH))
            {
                String text = monthFormater.format(auxDate.getTime());
                addpter.add(new TextSeparator.TextSeparatorDataSet(text));
            }
            else if(lastDate.get(Calendar.YEAR) != auxDate.get(Calendar.YEAR))
            {
                String text = yearFormater.format(auxDate.getTime());
                addpter.add(new TextSeparator.TextSeparatorDataSet(text));
            }

            addpter.add(new CropAdapter.CropDataSet(crop, this));
            lastDate = auxDate;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.populateList();
    }

    private void openCreateCrop() {

        Intent intent = new Intent(this, CropNew.class);
        startActivityForResult(intent, 10);

    }

    @Override
    public void openCrop(st.domain.ggviario.secret.model.Crop dataSet) {

        Intent intent = new Intent(this, CropContent.class);
        Bundle b = new Bundle();
        b.putSerializable("date", dataSet.getDate());
        intent.putExtras(b);
        startActivity(intent);

    }
}
