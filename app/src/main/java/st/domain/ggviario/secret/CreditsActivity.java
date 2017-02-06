package st.domain.ggviario.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import st.domain.ggviario.secret.adapter.PagerAdapter;
import st.domain.ggviario.secret.fragments.CreditsClientFragment;
import st.domain.support.android.view.SlidingTabLayout;

/**
 *
 * Created by dchost on 01/02/17.
 */

public class CreditsActivity extends AbstractActivityToolbarSlidingLayout {


    private FloatingActionButton fab;

    @Override
    protected int getContentView() {
        return R.layout._credits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Floating Action Menu
        this.fab = (FloatingActionButton) this.findViewById(R.id.fab_new_client);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewClient();
            }
        });

    }

    @NonNull
    @Override
    protected ViewPager getViewPager() {
        return (ViewPager) this.findViewById(R.id.view_pager);
    }

    @NonNull
    @Override
    protected SlidingTabLayout getSlidingLayout() {
        return (SlidingTabLayout) findViewById(R.id.sliding_pager_tabs);
    }

    @NonNull
    @Override
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected int getActivityName() {
        return R.string.credits;
    }

    @Override
    protected android.support.v4.view.PagerAdapter getAdapter() {
        PagerAdapter adapter = new PagerAdapter(this.getSupportFragmentManager(), this);

        CreditsClientFragment creditsClientFragment = new CreditsClientFragment();
        Bundle bundle = new Bundle();
        creditsClientFragment.setArguments(bundle);
        adapter.addFragment("Clientes", creditsClientFragment);

        return adapter;
    }

    private void startNewClient() {

        Intent intent = new Intent(this, CreditsClientNewActivity.class);
        startActivity(intent);

    }
}
