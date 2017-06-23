package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import st.zudamue.support.android.view.SlidingTabLayout;

public abstract class AbstractActivityToolbarSlidingLayout extends AbstractActivityToolbar {

    private ViewPager pager;
    private SlidingTabLayout slidingTabLayout;

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        // INIT
        super.onCreate( savedInstanceState );


        //View Pager
        this.pager = getViewPager();
        this.slidingTabLayout = getSlidingLayout();
        this.slidingTabLayout.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
        this.slidingTabLayout.setSelectedIndicatorColors( getResources().getColor( R.color.white ) );

        this.pager.setAdapter( getAdapter( savedInstanceState ) );
        this.slidingTabLayout.setCustomTabView( getCustomViewLayout(), getCustomViewLayoutId() );
        this.onPreApplyPagerIntoSlidingTabLayout( slidingTabLayout, pager );
        this.slidingTabLayout.setViewPager( this.pager );
    }

    protected void onPreApplyPagerIntoSlidingTabLayout(SlidingTabLayout slidingTabLayout, ViewPager pager) {}

    protected abstract @NonNull ViewPager getViewPager();

    protected abstract @NonNull SlidingTabLayout getSlidingLayout();

    protected abstract @NonNull android.support.v4.view.PagerAdapter getAdapter( Bundle savedInstanceState );

    public @LayoutRes int getCustomViewLayout() {
        return R.layout.tab_whit;
    }

    public @IdRes int getCustomViewLayoutId() {
        return R.id.tab_title;
    }
}
