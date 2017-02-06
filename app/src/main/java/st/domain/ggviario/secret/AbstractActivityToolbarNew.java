package st.domain.ggviario.secret;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Menu;
import android.view.MenuItem;

import st.domain.ggviario.secret.callbaks.MenuObserver;


/**
 *
 * Created by xdata on 8/12/16.
 */

// TextStepper, ProgressStepper, DotStepper, TabStepper
public abstract class AbstractActivityToolbarNew extends AbstractActivityToolbar
{

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMenuMapper().add(new MenuObserver() {
            @Override
            public boolean accept(MenuItem menuItem, Activity activity) {
                return onDoneAction();
            }

            @Override
            public int getKey() {
                return doneId();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crop_new, menu);
        return true;
    }


    public abstract int doneId();

    protected abstract boolean onDoneAction();

}
