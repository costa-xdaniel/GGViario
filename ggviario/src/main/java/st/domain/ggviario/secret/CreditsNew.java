package st.domain.ggviario.secret;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Map;

import st.domain.ggviario.secret.callbaks.MenuObserver;
import st.domain.ggviario.secret.fragments.CreditsNewFragment;
import st.domain.support.android.fragment.BaseFragment;
import st.domain.support.android.fragment.CallbackFragmentManager;

/**
 *
 * Created by dchost on 15/02/17.
 */

public class CreditsNew extends AbstractActivityToolbarDone  {
    @Override
    protected int getContentView() {
        return R.layout.activit_toolbar;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getMenuMapper().add(new MenuObserver() {
            @Override
            public boolean accept(MenuItem menuItem, Activity activity) {
                return false;
            }

            @Override
            public int getKey() {
                return 0;
            }
        });


        if ( savedInstanceState == null )
            savedInstanceState = getIntent().getExtras();

        FragmentManager support = getSupportFragmentManager();
        Fragment fragment = new CreditsNewFragment()
                .setDone(new CallbackFragmentManager.FragmentCallback() {

                    @Override
                    public void onCallback(Fragment fragment, Map<String, Object> objectsParam, Bundle extraParam, Map<String, Object> pushMap) {
                        finish();
                    }
                })
                ;
        fragment.setArguments( savedInstanceState);
        support.beginTransaction().add( R.id.content_area, fragment, "credit-new" )
                .commit()
                ;
    }


    @Override
    public void onBackPressed() {}

    @Override
    protected boolean onDoneAction() {
        Fragment fragment = this.getSupportFragmentManager().findFragmentByTag("credit-new");
        return (fragment instanceof CreditsNewFragment) && ((CreditsNewFragment) fragment).done();
    }

    @NonNull
    @Override
    public Toolbar getPrincipalToolbar() {
        return ( Toolbar ) this.findViewById( R.id.toolbar );
    }

}
