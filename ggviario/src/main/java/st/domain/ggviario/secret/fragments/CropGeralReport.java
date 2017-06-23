package st.domain.ggviario.secret.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.Point;

import java.util.Calendar;
import java.util.List;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.adapter.MainReportAdapter;
import st.domain.ggviario.secret.dao.CropDao;
import st.domain.ggviario.secret.dao.SectorDao;
import st.domain.ggviario.secret.items.CropChartLineViewHolder;
import st.domain.ggviario.secret.model.Sector;
import st.domain.ggviario.secret.references.RColors;
import st.zudamue.support.android.adapter.RecyclerViewAdapter;
import st.zudamue.support.android.util.DateUtil;

/**
 *
 * Created by xdata on 8/11/16.
 */
public class CropGeralReport extends Fragment
{
    private  String title;
    private View rootview;
    private RecyclerView reportsRecyclerView;
    private RecyclerViewAdapter adapter;
    private AppCompatSeekBar seekBar;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout._crop_report, container, false);
        this.reportsRecyclerView = (RecyclerView) this.rootview.findViewById(R.id.rv_reports);
        this.seekBar = (AppCompatSeekBar) this.rootview.findViewById(R.id.sb_chose_chart);

        RecyclerView.LayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        this.adapter = new MainReportAdapter(this.getContext());
        this.reportsRecyclerView.setLayoutManager(llm);
        this.reportsRecyclerView.setAdapter(this.adapter);

        Activity c = (Activity) this.getContext();
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populate();
            }
        });

        return  rootview;
    }

    private void populate() {

        Log.i( "APP.GGVIARIO", "-> CropGeralReport.populate" );

        CropChartLineViewHolder.ChartDataSet chartDatSet;
        this.adapter.addItem(chartDatSet = new CropChartLineViewHolder.ChartDataSet());

        SectorDao daoSector = new SectorDao(this.getContext());
        CropDao daoCrop = new CropDao(this.getContext());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);

        DateUtil dateUtil = new DateUtil(calendar.getTime());

        Log.i( "APP.GGVIARIO", "first-value: "+dateUtil );
        Log.i( "APP.GGVIARIO", "first-day-of-week: "+dateUtil.firtDayOfWeek() );
        Log.i( "APP.GGVIARIO", "last-day-of-week: "+dateUtil.lastDayOfWeek());
        Log.i( "APP.GGVIARIO", "first-day-of-month: "+dateUtil.firstDayOfMonth() );
        Log.i( "APP.GGVIARIO", "last-day-of-mont: "+dateUtil.lastDayOfMonth() );

        List<Point> list = daoCrop.reportCropSectorDaily(null, CropDao.ReportType.WEEK, dateUtil.firtDayOfWeek(), dateUtil.lastDayOfWeek());
        chartDatSet.addLine(new CropChartLineViewHolder.ChartDataLine(R.color.colorAccent, new Sector(null, "Total"), list));

        for(Sector sector: daoSector.loadSector()){
            list = daoCrop.reportCropSectorDaily(sector.getId(), CropDao.ReportType.WEEK, dateUtil.firtDayOfWeek(), dateUtil.lastDayOfWeek());
            int color = RColors.switchColor(RColors.SECTOR_COLORS, sector.getId());
            chartDatSet.addLine(new CropChartLineViewHolder.ChartDataLine(color, sector, list));
        }

        //daoCrop.close();
        //daoSector.close();
    }



    public void setTitle(String title) {
        this.title = title;
    }

}
