package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.idescout.sql.SqlScoutServer;

import java.util.ArrayList;

import st.domain.ggviario.secret.dao.Dao;
import st.domain.support.android.beans.CallbackController;
import st.domain.support.android.fragment.GeralActivityPager;
import st.domain.support.android.model.CallbackClient;
import st.domain.support.android.model.Divice;
import st.domain.support.android.socket.ClientSocketListener;
import st.domain.support.android.socket.NetIntent;
import st.domain.support.android.text.XTextName;
import st.domain.support.android.view.SlidingTabLayout;
import st.domain.ggviario.secret.references.RData;
import st.domain.ggviario.secret.references.RMap;
import st.domain.ggviario.secret.support.fragments.BlanckFragment;
import st.domain.ggviario.secret.support.fragments.MainHome;

public class GGViario extends GeralActivityPager implements ClientSocketListener.OnTreatNetIntent, CallbackClient{
    public static final String SOCKET_SERVICE = "SOCKET_SERVICE";

    private ClientSocketListener service;
    private ViewPager pager;
    private SlidingTabLayout slidingLayout;
    private Toolbar tollbar;
    private String host;
    private Divice divice;

    public GGViario() {
        XTextName.apllyText(RData.class, XTextName.ApplyMode.ORIGINAL, XTextName.ApplyRange.CLASS_ONLY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // charSetExemple();
        oficialCode();


    }

    private void oficialCode() {
        setContentView(R.layout._main);
        Dao.inti(this);

        SqlScoutServer.create(this, getPackageName());

        this.pager = (ViewPager) this.findViewById(R.id.pagerInit);
        this.slidingLayout = (SlidingTabLayout) this.findViewById(R.id.tabs_init);
        this.tollbar = (Toolbar) this.findViewById(R.id.toolbar);


        this.tollbar.setTitle(R.string.app_name);
        this.setSupportActionBar(this.tollbar);
        tollbar.setTitleTextColor(getResources().getColor(R.color.white));

        MainHome home = new MainHome();
        BlanckFragment blank1 = new BlanckFragment();
        BlanckFragment blank2 = new BlanckFragment();

        this.divice = new Divice("Daniel");
        this.host = "192.168.43.28";
        this.service = new ClientSocketListener(this.host, RMap.SOCKET_PORT, this.divice.getMac(), this, this);

        this.slidingLayout.useColorizerOnTextColor(true);


        super.addFragment(home);
        super.addFragment(blank1);
        super.setViewPager(this.pager);
        super.setTabLayout(this.slidingLayout);
        super.setDistributeEvenly(false);
        super.setSelectedIndicatorColor(getResources().getColor(R.color.white));
        super.setUp();
        CallbackController.inOutingNet(this);
    }

    private void charSetExemple() {
        setContentView(R.layout.charts);

        LineChartView chartView = (LineChartView) this.findViewById(R.id.chart);


        ArrayList<ChartSet> dataset = new ArrayList<>();


        LineSet dataL;
        dataL = new LineSet();
        dataL.addPoint(new Point("", 1));
        dataL.addPoint(new Point("", 11));
        dataL.addPoint(new Point("", 12));
        dataL.addPoint(new Point("", 13));
        dataL.addPoint(new Point("", 14));
        dataset.add(dataL);

        dataL = new LineSet();
        dataL.setColor(getResources().getColor(R.color.mat_teal_primary));
        dataL.setThickness(3);
        dataL.addPoint(new Point("", 03f));
        dataL.addPoint(new Point("", 05f));
        dataL.addPoint(new Point("", 08f));
        dataL.addPoint(new Point("", 09f));
        dataL.addPoint(new Point("", 10f));
        dataset.add(dataL);


        chartView.addData(dataset);
        chartView.notifyDataUpdate();
        chartView.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean canTreat(NetIntent intent)
    {
        return false;
    }

    @Override
    public NetIntent treat(NetIntent intent)
    {
        return null;
    }

    @Override
    public void onConnectionResult(NetIntent.ConnectionStatus status)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> Result connection "+status);
    }

    /**
     *
     * @param sendType o tipo de envio para todos ao apenas para a activite
     * Recoperar o valor enviado de um outro fragmento
     * @param origem O fragmento que enviou o valor
     * @param summary O codigo do valor
     * @param values O valor enviado
     */
    @Override
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values)
    {
        if(summary.equals(RMap.SUMMARY_REGISTER_TREATER_LISTNER)) registerTreat(origem);
    }

    public void registerTreat(CallbackClient origem)
    {
        if(origem instanceof ClientSocketListener.OnTreatNetIntent)
            this.service.addTreater((ClientSocketListener.OnTreatNetIntent) origem);
        else Log.e("DBA:APP.TEST", "Can not registerTreat terater "+origem.getProtocolKey()+" this not support "+ClientSocketListener.OnTreatNetIntent.class.getName());
    }

    @Override
    public Bundle query(CallbackClient clientOrigen, String queryQuention, Object... inParams) {
        Bundle bundle = new Bundle();
        if(queryQuention.equals(RMap.QUERY_GET_SOCKET_SERVICE))
            bundle.putSerializable(GGViario.SOCKET_SERVICE, this.service);
        return bundle;
    }

    @Override
    public CharSequence getProtocolKey() {
        return RMap.IDENTIFIER_GGVIARIO;
    }
}
