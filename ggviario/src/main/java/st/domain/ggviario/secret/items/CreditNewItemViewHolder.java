package st.domain.ggviario.secret.items;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Measure;
import st.domain.ggviario.secret.model.Product;
import st.domain.ggviario.secret.model.ProductPrice;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;

/**
 *
 * Created by dchost on 05/03/17.
 */

public class CreditNewItemViewHolder extends ItemViewHolder {

    private TextView tvCreditProduct;
    private TextView tvCreditQuantity;
    private TextView tvCreditPriceQuantity;
    private ItemCallback removeCallback;
    private ItemCallback editCallback;
    private CreditProductItemDataSet dataSet;
    private NumberFormat numberFormat;

    public CreditNewItemViewHolder removeCallback (ItemCallback removeCallback ){
        this.removeCallback = removeCallback;
        return this;
    }

    public CreditNewItemViewHolder editCallback (ItemCallback editCallback ){
        this.editCallback = editCallback;
        return this;
    }

    public CreditNewItemViewHolder(View itemView) {
        super(itemView);

        this.numberFormat = NumberFormat.getNumberInstance( Locale.FRANCE );
        this.numberFormat.setMaximumFractionDigits( 2 );
        this.numberFormat.setMinimumFractionDigits( 2 );
        this.numberFormat.setMinimumIntegerDigits( 1 );

        this.tvCreditProduct = ( TextView ) this.itemView.findViewById( R.id.tv_credit_product );
        this.tvCreditQuantity = ( TextView ) this.itemView.findViewById( R.id.tv_credit_quantity );
        this.tvCreditPriceQuantity = ( TextView ) this.itemView.findViewById( R.id.tv_credit_price_quantity );
        this.itemView.findViewById( R.id.bt_credit_item_remove ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

    }

    private void edit() {
        this.callback( this.editCallback );
    }

    private void remove() {
        callback( this.removeCallback );
    }

    @Override
    public void onBind(ItemDataSet dataSet, int currentAdapterPosition, int totalDataSet) {
        this.dataSet = (CreditProductItemDataSet) dataSet;
        this.tvCreditProduct.setText( this.dataSet.getSelectedProduct() );
        this.tvCreditPriceQuantity.setText( this.numberFormat.format( this.dataSet.getPriceQuantity() ) );

        // ex: 20 kg (1/93 000,00)
        String textQuantity = this.dataSet.getQuantity() + " " + this.dataSet.getSelectedMeasure().getCod() + " (1/" + this.dataSet.getProductPrice().calcPrice( 1 ) + ")";
        this.tvCreditQuantity.setText( textQuantity );

        StaggeredGridLayoutManager.LayoutParams layoutParam = (StaggeredGridLayoutManager.LayoutParams) this.itemView.getLayoutParams();
        float dp8 = this.getContext().getResources().getDimension( R.dimen.dp_8 );
        float dp1 = this.getContext().getResources().getDimension( R.dimen.dp_1 );

        int left = layoutParam.leftMargin;
        int top = layoutParam.topMargin;
        int right = layoutParam.rightMargin;
        int bottom = layoutParam.bottomMargin;

        if( currentAdapterPosition == 0 )
            top = (int) dp8;
        else if( top != dp8 )
            top = (int) dp1;

        if( currentAdapterPosition + 1 == totalDataSet)
            bottom = (int) dp8;
        else if( bottom != dp8 )
            bottom = 0;

        layoutParam.setMargins( left, top, right, bottom );

    }

    public static class CreditProductItemDataSet implements ItemDataSet{

        private Product selectedProduct;
        private ProductPrice productPrice;
        private double quantity;
        private float priceQuantity;
        private int selectedProductIndex;
        private int selectedMeasureIndex;
        private float oldPriceQuantity;

        public CreditProductItemDataSet ( float oldPriceQuantity ) {
            this.oldPriceQuantity = oldPriceQuantity;
        }

        @Override
        public int getLayoutId() {
            return R.layout._credit_newcredit_item;
        }

        public Product getSelectedProduct() {
            return selectedProduct;
        }

        public Measure getSelectedMeasure() {
            return this.productPrice.getMeasure();
        }

        public double getQuantity() {
            return quantity;
        }

        public float getPriceQuantity() {
            return priceQuantity;
        }

        public int getSelectedProductIndex() {
            return selectedProductIndex;
        }

        public int getSelectedMeasureIndex() {
            return selectedMeasureIndex;
        }

        public ProductPrice getProductPrice() {
            return productPrice;
        }

        public CreditProductItemDataSet selectedProduct(Product selectedProduct) {
            this.selectedProduct = selectedProduct;
            return this;
        }

        public CreditProductItemDataSet productPrice(ProductPrice productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public CreditProductItemDataSet quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public CreditProductItemDataSet priceQuantity(float priceQuantity) {
            this.priceQuantity = priceQuantity;
            return this;
        }

        public CreditProductItemDataSet selectedProductIndex(int selectedProductIndex) {
            this.selectedProductIndex = selectedProductIndex;
            return this;
        }

        public CreditProductItemDataSet selectedMeasureIndex(int selectedMeasureIndex) {
            this.selectedMeasureIndex = selectedMeasureIndex;
            return this;
        }

        public float getOldPriceQuantity() {
            return oldPriceQuantity;
        }
    }
}
