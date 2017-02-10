package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

/**
 *
 * Created by dchost on 07/02/17.
 */

public class ZColapsingTest extends AbstractActivityToolbar {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;

    @Override
    protected int getContentView() {
        return R.layout.zcolapsing_test;
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
        this.collapsingToolbarLayout.setTitle( "Cliente Name" );
        this.collapsingToolbarLayout.setExpandedTitleColor( this.getResources().getColor( android.R.color.white ) );
        this.collapsingToolbarLayout.setCollapsedTitleTextColor( getResources().getColor( android.R.color.white ));


    }

    @NonNull
    @Override
    public Toolbar getPrincipalToolbar() {
        if(this.toolbar == null )
            this.toolbar = (Toolbar) this.findViewById( R.id.toolbar );
        return this.toolbar;
    }
}
