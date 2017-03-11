package st.domain.ggviario.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import st.domain.ggviario.secret.fragments.CreditsClientDetailCreditsFragment;
import st.domain.ggviario.secret.fragments.SampleFragment;
import st.domain.ggviario.secret.model.Client;
import st.domain.support.android.view.SlidingTabLayout;

/**
 *
 * Created by dchost on 07/02/17.
 */

public class CreditsClientDetailActivity extends AbstractActivityToolbarSlidingLayout {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private Client client;

    @Override
    protected int getContentView() {
        return R.layout._credit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        // CollapsingToolbar
        this.toolbar = this.getPrincipalToolbar();
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById( R.id.collapsing_toolbar );
        this.collapsingToolbarLayout.setTitle( " " );
        this.collapsingToolbarLayout.setExpandedTitleColor( this.getResources().getColor( android.R.color.white ) );
        this.collapsingToolbarLayout.setCollapsedTitleTextColor( getResources().getColor( android.R.color.white ));

        this.fab = (FloatingActionButton) this.findViewById( R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewCredits();
            }
        });

    }

    private void openNewCredits() {

        Intent intent = new Intent( this, CreditsNew.class );
        Bundle extras = new Bundle();
        extras.putParcelable( "client", this.client );
        intent.putExtras( extras );
        startActivity( intent );

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
        return ( SlidingTabLayout ) this.findViewById( R.id.sliding_pager_tabs );
    }

    @NonNull
    @Override
    protected PagerAdapter getAdapter( Bundle savedInstanceState ) {
        if( savedInstanceState == null )
            savedInstanceState = this.getIntent().getExtras();
        if( this.client == null )
            this.client = this.loadClient( savedInstanceState );

        st.domain.ggviario.secret.adapter.PagerAdapter adapter = new st.domain.ggviario.secret.adapter.PagerAdapter( this.getSupportFragmentManager(), this );
        Fragment creditClient =  new CreditsClientDetailCreditsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable( "client", this.client );
        creditClient.setArguments( bundle );

        adapter.addFragment( getString( R.string.geral_view ), creditClient );

        adapter.addFragment( getString( R.string.credits_pendents ), new SampleFragment() );
        adapter.addFragment( getString( R.string.credits_pay ), new SampleFragment() );

        return adapter;
    }

    private Client loadClient( Bundle savedExtrasInstance ) {
        return  ( Client ) savedExtrasInstance.getParcelable( "client" );
    }

    @NonNull
    @Override
    public Toolbar getPrincipalToolbar() {
        if( this.toolbar == null )
            this.toolbar = ( Toolbar ) this.findViewById( R.id.toolbar );
        return this.toolbar;
    }
}
