package st.domain.ggviario.secret.items;

import android.view.View;
import android.widget.TextView;

import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Client;
import st.zudamue.support.android.adapter.ItemDataSet;
import st.zudamue.support.android.adapter.ItemViewHolder;

/**
 *
 * Created by dchost on 06/02/17.
 */

public class ClientViewHolder extends ItemViewHolder {

    private CreditsClientDataSet dataSet;
    private TextView tvClientAvatar;
    private TextView tvClientName;
    private TextView tvClientSubInformation;
    private TextView tvClientAvatarSecond;
    private ItemCallback onClickcallback;

    public ClientViewHolder setOnClickCallback(ItemCallback onClickCallback ){
        this.onClickcallback = onClickCallback;
        return this;
    }

    public ClientViewHolder(View itemView) {
        super(itemView);
        this.tvClientAvatar = ( TextView ) this.itemView.findViewById( R.id.tv_client_avatar );
        this.tvClientName = ( TextView ) this.itemView.findViewById( R.id.tv_client_name );
        this.tvClientSubInformation = ( TextView ) this.itemView.findViewById( R.id.tv_client_info );
        this.tvClientAvatarSecond = ( TextView ) this.itemView.findViewById( R.id.tv_client_avatar_second );
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback( onClickcallback );
            }
        });
    }


    @Override
    public void onBind(ItemDataSet dataSet, int currentAdapterPosition, int totalDataSet) {
        this.dataSet = ( CreditsClientDataSet ) dataSet;
        this.tvClientAvatar.setText( this.dataSet.client.getAvatar() );
        this.tvClientName.setText( this.dataSet.client );
        this.tvClientSubInformation.setText( this.dataSet.client.getSubInformation() );
        this.tvClientAvatarSecond.setText( this.dataSet.getAvatarSecond() );
    }


    public static CreditsClientDataSet newInstance ( Client client, int totalCredits, int totalCreditsPay ) {
        return new CreditsClientDataSet(client, totalCredits, totalCreditsPay);
    }


    public static class CreditsClientDataSet implements ItemDataSet {

        private final Client client;
        private final int totalCreditsPay;
        private final int totalCredits;

        private CreditsClientDataSet ( Client client, int totalCredits, int totalCreditsPay ) {
            this.client = client;
            this.totalCredits = totalCredits;
            this.totalCreditsPay = totalCreditsPay;
        }

        @Override
        public int getLayoutId() {
            return R.layout._client_item;
        }

        public CharSequence getAvatarSecond() {
            return String.valueOf( this.totalCredits - this.totalCreditsPay );
        }

        public Client getClient() {
            return this.client;
        }
    }
}
