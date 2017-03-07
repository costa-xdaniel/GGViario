package st.domain.ggviario.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import st.domain.ggviario.secret.adapter.PagerAdapter;
import st.domain.ggviario.secret.fragments.CropDateFragment;
import st.domain.ggviario.secret.fragments.CropGeralReport;
import st.domain.ggviario.secret.fragments.OnResultActivity;
import st.domain.ggviario.secret.references.RMap;
import st.domain.support.android.view.SlidingTabLayout;

/**
 *
 * Created by dchost on 30/01/17.
 */

public class CropActivity extends AbstractActivityToolbarSlidingLayout {

    private FloatingActionButton floatingActionButton;


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
        return R.layout._crop;
    }

    @NonNull
    @Override
    public Toolbar getPrincipalToolbar() {
        return (Toolbar) this.findViewById(R.id.toolbar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("APP.GGVIARIO", "-> CropActivity.onCreate");
        super.onCreate(savedInstanceState);

        // FloatingActionButton
        this.floatingActionButton = (FloatingActionButton) this.findViewById(R.id.fab);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCrop();
            }
        });

    }

    @Override
    protected int getActivityName() {
        return R.string.crop;
    }

    @Override
    protected android.support.v4.view.PagerAdapter getAdapter(Bundle savedInstanceState) {

        PagerAdapter pagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), this);

        Bundle bundle = new Bundle();
        CropGeralReport geralReport = new CropGeralReport();
        geralReport.setArguments(bundle);
        pagerAdapter.addFragment("Report", geralReport);


        bundle = new Bundle();
        CropDateFragment dayFragment = new CropDateFragment();
        dayFragment.setArguments(bundle);
        pagerAdapter.addFragment("Diario", dayFragment);

        return pagerAdapter;

    }

    private void newCrop() {
        Log.i("APP.GGVIARIO", "-> CropActivity.newCrop");

        Intent intent = new Intent(this, CropNewActivity.class);
        this.startActivityForResult(intent, RMap.REQUEST_NEW_CROP);
    }

    @Override
    protected void onStart() {
        Log.i("APP.GGVIARIO", "-> CropActivity.onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("APP.GGVIARIO", "-> CropActivity.onResume");
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("APP.GGVIARIO", "-> CropActivity.onActivityResult");

        onResult(requestCode, resultCode, data);
    }

    private void onResult(int requestCode, int resultCode, Intent data) {
        Log.i("APP.GGVIARIO", "-> CropActivity.onResult");
        for(Fragment fragment: this.getSupportFragmentManager().getFragments()) {
            if((fragment instanceof OnResultActivity)) {
                    if(((OnResultActivity) fragment).onResultActivity(requestCode, resultCode, data, this))
                        break;
            }
        }
    }
}
