package st.domain.ggviario.secret.support;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Provider;
import st.domain.ggviario.secret.support.fragments.TextSeparator;

/**
 *
 * Created by xdata on 12/24/16.
 */

public class ProviderAdapter extends RecyclerViewAdapter{

    private OpenProvider openProvider;

    public ProviderAdapter(Context context) {
        super(context);

        this.addItemFactory(R.layout._provider_item,
                new ViewHolderFactory() {
                    @Override
                    public ItemViewHolder factory(View view) {
                        return new ProviderViewHolder(view);
                    }
                });

        this.addItemFactory(R.layout.item_textseparator, TextSeparator.factoryInstance());
    }

    public void setOpenProvider(OpenProvider openProvider){
        this.openProvider = openProvider;
    }

    private void openCrop(ProviderDataSet dataSet){
        if(this.openProvider != null)
            this.openProvider.openProvider(dataSet.provider);
    }

    private class ProviderViewHolder extends ItemViewHolder {

        private ProviderDataSet dataSet;
        private TextView tvProviderName;
        private TextView tvProviderLocation;
        private TextView tvProviderContact;



        public ProviderViewHolder(View itemView) {

            super(itemView);
            this.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openCrop(dataSet);
                        }
                    }
            );

            this.tvProviderName  = (TextView) itemView.findViewById(R.id.tv_provider_name);
            this.tvProviderContact  = (TextView) itemView.findViewById(R.id.tv_provider_contact);
            this.tvProviderLocation  = (TextView) itemView.findViewById(R.id.tv_provider_location);
        }

        /**
         *
         * @param dataSet
         */
        @Override
        public void bind(ItemDataSet dataSet) {

            this.dataSet = (ProviderDataSet) dataSet;
            this.tvProviderName.setText(this.dataSet.provider.getName());
            this.tvProviderLocation.setText(this.dataSet.provider.getLocation());
            this.tvProviderContact.setText(this.dataSet.provider.getContact());

        }

    }

    public static class ProviderDataSet implements ItemDataSet {

        private OpenProvider openProvider;
        public Provider provider;

        public ProviderDataSet(Provider provider, OpenProvider openProvider) {
            this.provider = provider;
            this.openProvider = openProvider;
        }

        @Override
        public int getLayoutId() {
            return R.layout._provider_item;
        }
    }

    public interface OpenProvider {
        void openProvider(Provider dataSet);
    }
}
