package st.domain.ggviario.secret.adapter;

import android.content.Context;
import android.view.View;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.items.CropChartSectorViewHolder;
import st.zudamue.support.android.adapter.ItemViewHolder;
import st.zudamue.support.android.adapter.RecyclerViewAdapter;

/**
 *
 * Created by dchost on 29/01/17.
 */

public class CropChartSectorAdapter extends RecyclerViewAdapter {

    public CropChartSectorAdapter(Context context) {
        super(context);
        this.registerFactory(R.layout._crop_report_chart_sector,
                new ViewHolderFactory() {
                    @Override
                    public ItemViewHolder factory(View view) {
                        return new CropChartSectorViewHolder(view);
                    }
                });
    }
}
