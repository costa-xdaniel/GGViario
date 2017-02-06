package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import st.domain.ggviario.secret.adapter.PagerAdapter;
import st.domain.ggviario.secret.dao.Dao;
import st.domain.ggviario.secret.fragments.MainHomeFragment;
import st.domain.support.android.view.SlidingTabLayout;

public class MainActivity extends AbstractActivityToolbarSlidingLayout {

    @Override
    protected ViewPager getViewPager() {
        return (ViewPager) this.findViewById(R.id.view_pager);
    }

    @Override
    protected SlidingTabLayout getSlidingLayout() {
        return (SlidingTabLayout) this.findViewById(R.id.sliding_pager_tabs);
    }

    @Override
    protected int getContentView() {
        return R.layout._main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // INIT
        super.onCreate(savedInstanceState);
        Dao.inti(this);
    }

    @Override
    protected android.support.v4.view.PagerAdapter getAdapter() {

        MainHomeFragment home = new MainHomeFragment();
        PagerAdapter viewPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), this);
        viewPagerAdapter.addFragment("MAIN", home);
        return viewPagerAdapter;

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Toolbar getToolbar() {
        return (Toolbar) this.findViewById(R.id.toolbar);
    }

    @Override
    protected boolean isViewBackAsHome() {
        return false;
    }
}
