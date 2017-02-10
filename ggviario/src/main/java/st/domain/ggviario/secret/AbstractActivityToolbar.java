package st.domain.ggviario.secret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import st.domain.ggviario.secret.callbaks.MenuMapper;
import st.domain.ggviario.secret.callbaks.MenuObserver;

/**
 *
 * Created by dchost on 04/02/17.
 */

public abstract class AbstractActivityToolbar extends AppCompatActivity {

    private MenuMapper mMenuMapper;


    /**
     * Should be called instead of {@link AbstractActivityToolbar#getContentView()}}
     */
    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getContentView());


        //Toolbar

        if(prepareToolbar()) {
            Toolbar toolbar = getPrincipalToolbar();
            toolbar.setTitle(getActivityName());
            toolbar.inflateMenu(getMenu());
            //this.toolbar.setLogo();
            //this.toolbar.setSubtitle();

            this.setSupportActionBar(toolbar);
            if (this.getSupportActionBar() != null ) {

                if (isViewBackAsHome())
                    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                this.getSupportActionBar().setTitle(getActivityName());
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            }
        }

        this.mMenuMapper = new MenuMapper(this);
        this.getMenuMapper().add(new MenuObserver() {
            @Override
            public boolean accept(MenuItem menuItem, Activity activity) {
                if( onHomeAsUpPressed() )
                    finish();
                return true;
            }

            @Override
            public int getKey() {
                return android.R.id.home;
            }
        });

    }

    public abstract @NonNull Toolbar getPrincipalToolbar();

    protected boolean prepareToolbar() {
        return true;
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.getMenuMapper().menuAction(item);
    }

    private int getMenu() {
        return R.menu.menu_crop_new;
    }

    protected int getActivityName() {
        return R.string.ggviario;
    }


    protected boolean isViewBackAsHome() {
        return true;
    }

    public MenuMapper getMenuMapper() {
        return this.mMenuMapper;
    }

    protected  boolean onHomeAsUpPressed() {
        return true;
    }
}
