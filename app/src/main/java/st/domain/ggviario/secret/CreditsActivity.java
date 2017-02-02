package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import st.domain.ggviario.secret.adapter.PagerAdapter;
import st.domain.ggviario.secret.fragments.CreditsClientFragment;
import st.domain.support.android.view.SlidingTabLayout;

/**
 * Created by dchost on 01/02/17.
 */

public class CreditsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SlidingTabLayout slidingLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //INIT
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout._credits);



        //TOOLBAR
        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.toolbar.setTitle(R.string.credits);
        this.toolbar.setTitleTextColor(this.getResources().getColor(android.R.color.white));
        this.setSupportActionBar(this.toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        //VIEW PAGER
        this.slidingLayout = (SlidingTabLayout) this.findViewById(R.id.sliding_pager_tabs);
        this.viewPager = (ViewPager) this.findViewById(R.id.view_pager);
        this.slidingLayout.setSelectedIndicatorColors(this.getResources().getColor(android.R.color.white));
        this.slidingLayout.setCustomTabView(R.layout.tab, R.id.tab_title);

        PagerAdapter adapter = new PagerAdapter(this.getSupportFragmentManager(), this);
        adapter.addFragment("Clientes", new CreditsClientFragment());

        this.viewPager.setAdapter(adapter);
        this.slidingLayout.setViewPager(this.viewPager);


    }
}
