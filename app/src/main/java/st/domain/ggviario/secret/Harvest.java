package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import st.domain.ggviario.secret.support.adapters.SectorAdapter;


/**
 * Created by xdata on 8/12/16.
 */

// TextStepper, ProgressStepper, DotStepper, TabStepper
public class Harvest extends AppCompatActivity
{
    Toolbar toolbar;
    TextView toolbarTitle;
    AppCompatSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.harvest);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.toolbarTitle = (TextView) this.findViewById(R.id.toolbar_title);

        this.toolbarTitle.setText("Recolha");
        this.spinner = (AppCompatSpinner) this.findViewById(R.id.sector_spnner);

        this.spinner.setAdapter(new SectorAdapter(this));

    }
}
