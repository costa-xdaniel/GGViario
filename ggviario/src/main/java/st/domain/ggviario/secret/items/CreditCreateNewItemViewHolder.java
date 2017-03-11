package st.domain.ggviario.secret.items;

import android.support.annotation.Px;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import st.domain.ggviario.secret.R;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;

/**
 *
 * Created by dchost on 15/02/17.
 */
public class CreditCreateNewItemViewHolder extends ItemViewHolder {

    private ItemCallback addProductCallback;

    public CreditCreateNewItemViewHolder(final View itemView) {
        super(itemView);
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback(addProductCallback);
            }
        });
    }

    @Override
    public void onBind(ItemDataSet dataSet, int currentAdapterPosition, int totalDataSet) {
    }

    @Override
    protected void onNewDataSetAddInCurrentPosition(int index, ItemDataSet newItemDataSet, ItemDataSet oldDataSet, int totalDataSet) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) this.itemView.getLayoutParams();

        int top = layoutParams.topMargin;
        int right= layoutParams.rightMargin;
        int bottom = layoutParams.bottomMargin;
        int left = layoutParams.leftMargin;

        float dp32 = this.getContext().getResources().getDimension( R.dimen.dp_32 );
        float dp24 = this.getContext().getResources().getDimension( R.dimen.dp_24 );
        float dp16 = this.getContext().getResources().getDimension( R.dimen.dp_16 );

        bottom = 32;
        if( index == 2 ){
            if( totalDataSet == 1 ){
                top = (int) dp32;
            }
            else {
                top = (int) dp16;
            }
        }

        layoutParams.setMargins( left, top, right, bottom );
    }

    public void addProductCallback(ItemCallback addProductCallback ) {
        this.addProductCallback = addProductCallback;
    }

    public static class AddCreditProductItemDataSet implements  ItemDataSet {
        @Override
        public int getLayoutId() {
            return R.layout.credits_operaction_add;
        }
    }
}
