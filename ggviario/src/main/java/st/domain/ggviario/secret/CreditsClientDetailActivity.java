package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import st.domain.ggviario.secret.fragments.CreditsClientDetailCreditsFragment;
import st.domain.ggviario.secret.fragments.SampleFragment;
import st.domain.support.android.view.SlidingTabLayout;

/**
 *
 * Created by dchost on 07/02/17.
 */

public class   CreditsClientDetailActivity extends AbstractActivityToolbarSlidingLayout {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;

    @Override
    protected int getContentView() {
        return R.layout._credits_clients_detais;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        //TRASITION
        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition enterTransition = new Explode()
                    .setDuration(3000);

            Transition returnTransition = new Slide()
                    .setDuration(3000);

            getWindow().setEnterTransition( enterTransition );
            getWindow().setReturnTransition( returnTransition );
        }
*/

        super.onCreate(savedInstanceState);

        // CollapsingToolbar
        this.toolbar = this.getPrincipalToolbar();
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById( R.id.collapsing_toolbar );
        this.collapsingToolbarLayout.setTitle( " " );
        this.collapsingToolbarLayout.setExpandedTitleColor( this.getResources().getColor( android.R.color.white ) );
        this.collapsingToolbarLayout.setCollapsedTitleTextColor( getResources().getColor( android.R.color.white ));


    }

    @Override
    protected void onPreApplyPagerIntoSlidingTabLayout(SlidingTabLayout slidingTabLayout, ViewPager pager) {
        slidingTabLayout.setCustomTabView( R.layout.tab_primary, R.id.tab_title );
        slidingTabLayout.setBackgroundColor( getResources().getColor( android.R.color.transparent ) );
        slidingTabLayout.setSelectedIndicatorColors( getResources().getColor( R.color.colorAccent ) );
        slidingTabLayout.setDistributeEvenly( true );
    }

    @NonNull
    @Override
    protected ViewPager getViewPager() {
        return (ViewPager) this.findViewById( R.id.view_pager );
    }

    @NonNull
    @Override
    protected SlidingTabLayout getSlidingLayout() {
        return (SlidingTabLayout) this.findViewById( R.id.sliding_pager_tabs );
    }

    @NonNull
    @Override
    protected PagerAdapter getAdapter() {

        st.domain.ggviario.secret.adapter.PagerAdapter adapter = new st.domain.ggviario.secret.adapter.PagerAdapter(this.getSupportFragmentManager(), this);

        adapter.addFragment(getString(R.string.geral_view), new CreditsClientDetailCreditsFragment());
        adapter.addFragment(getString(R.string.credits_pendents), new SampleFragment() );
        adapter.addFragment(getString(R.string.credits_pay), new SampleFragment() );

        return adapter;
    }

    @NonNull
    @Override
    public Toolbar getPrincipalToolbar() {
        if(this.toolbar == null )
            this.toolbar = (Toolbar) this.findViewById( R.id.toolbar );
        return this.toolbar;
    }
}
