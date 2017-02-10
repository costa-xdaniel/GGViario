package st.domain.ggviario.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import st.domain.ggviario.secret.dao.CropDao;
import st.domain.ggviario.secret.dao.SectorDao;
import st.domain.ggviario.secret.model.Sector;
import st.domain.ggviario.secret.adapter.SimpleSpinnerAdapter;
import st.domain.ggviario.secret.references.RMap;


/**
 *
 * Created by xdata on 8/12/16.
 */

// TextStepper, ProgressStepper, DotStepper, TabStepper
public class CropNewActivity extends AbstractActivityToolbarNew {

    AppCompatSpinner spinner;
    private EditText edQuantity;
    private EditText edQuantityPercas;
    private EditText edQuantityPercasGalinha;
    private SimpleSpinnerAdapter sectorAdapter;

    @Override
    protected int getContentView() {
        return R.layout._crop_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.spinner = (AppCompatSpinner) this.findViewById(R.id.sector_spnner);
        this.edQuantity = (EditText) this.findViewById(R.id.ed_crop_quantity);
        this.edQuantityPercas = (EditText) this.findViewById(R.id.ed_crop_quantity_perca);
        this.edQuantityPercasGalinha = (EditText) this.findViewById(R.id.ed_crop_quantity_percas_galinha);

        this.sectorAdapter = new SimpleSpinnerAdapter(this);
        this.sectorAdapter.add(getString(R.string.select_sector));
        SectorDao sectorDao = new SectorDao(this);
        this.sectorAdapter.addAll(sectorDao.loadSector());
        this.spinner.setAdapter(this.sectorAdapter);
    }

    @Override
    protected int getActivityName() {
        return R.string.new_client;
    }

    @Override
    public int doneId() {
        return R.id.opt_done;
    }

    @Override
    protected boolean onDoneAction() {
        String tQuantity = this.edQuantity.getText().toString();
        String tQuantityPercas = this.edQuantityPercas.getText().toString();
        String tQuantityPercasGalinha = this.edQuantityPercasGalinha.getText().toString();
        int index = this.spinner.getSelectedItemPosition();

        if(tQuantity.isEmpty()) tQuantity = "0";
        if(tQuantityPercas.isEmpty()) tQuantityPercas = "0";
        if(tQuantityPercasGalinha.isEmpty()) tQuantityPercasGalinha = "0";

        CharSequence sector = this.sectorAdapter.getItem(index);
        if( !(sector instanceof  Sector) ) {
            Toast.makeText(this, R.string.select_sector, Toast.LENGTH_LONG).show();
            return false;
        }


        int quantity = Integer.parseInt(tQuantity);
        int quantityPercas = Integer.parseInt(tQuantityPercas);
        int quantityPercasGalinha = Integer.parseInt(tQuantityPercasGalinha);

        if(quantity == 0
                && quantityPercas == 0
                && quantityPercasGalinha == 0){
            Toast.makeText(this, R.string.comple_the_camp, Toast.LENGTH_LONG).show();
            return false;
        }


        CropDao daoCrop = new CropDao(this.getApplicationContext());
        daoCrop.register(quantity, (Sector) sector, quantityPercas, quantityPercasGalinha);
        //daoCrop.close();

        Intent intent = new Intent();
        intent.putExtra("quantity", quantity);
        intent.putExtra("quantityPerca", quantityPercas);
        intent.putExtra("quantityPercasGalinha", quantityPercasGalinha);
        intent.putExtra("sector", ((Sector) sector).getId());

        this.setResult(RMap.REQUEST_NEW_CROP, intent);
        this.finish();
        return true;
    }

    @Override
    public Toolbar getPrincipalToolbar() {
        return (Toolbar) this.findViewById(R.id.toolbar);
    }
}
