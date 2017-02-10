package st.domain.ggviario.secret.items;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import st.domain.ggviario.secret.CreditsClientDetailActivity;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Client;
import st.domain.support.android.adapter.ItemDataSet;
import st.domain.support.android.adapter.ItemViewHolder;

/**
 *
 * Created by dchost on 06/02/17.
 */

public class    CreditsClientItem extends ItemViewHolder {

    private CreditsClientDataSet dataSet;
    private TextView tvClientAvatar;
    private TextView tvClientName;
    private TextView tvClientSubInformation;
    private TextView tvClientAvatarSecond;

    public CreditsClientItem(View itemView) {
        super(itemView);
        this.tvClientAvatar = ( TextView ) this.itemView.findViewById( R.id.tv_client_avatar );
        this.tvClientName = ( TextView ) this.itemView.findViewById( R.id.tv_client_name );
        this.tvClientSubInformation = ( TextView ) this.itemView.findViewById( R.id.tv_client_info );
        this.tvClientAvatarSecond = ( TextView ) this.itemView.findViewById( R.id.tv_client_avatar_second );
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick ( view );
            }
        });

    }

    private void itemClick(View view) {
        Intent intent = new Intent( this.getContext(), CreditsClientDetailActivity.class );

        /*
        TRANSITION

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), null );
            (getContext()).startActivity( intent, options.toBundle() );
        }
        else this.getContext().startActivity(intent);
        */

        this.getContext().startActivity(intent);
    }

    @Override
    public void bind( ItemDataSet dataSet, int currentAdapterPosition, int totalAdapterItem ) {
        this.dataSet = ( CreditsClientDataSet ) dataSet;
        this.tvClientAvatar.setText( this.dataSet.client.getAvatar() );
        this.tvClientName.setText( this.dataSet.client );
        this.tvClientSubInformation.setText( this.dataSet.client.getSubInformation() );
        this.tvClientAvatarSecond.setText( this.dataSet.getAvatarSecond() );
    }


    public static CreditsClientDataSet newInstance ( Client client, int totalCredits, int totalCreditsPay ) {
        return new CreditsClientDataSet(client, totalCredits, totalCreditsPay);
    }


    private static class CreditsClientDataSet implements ItemDataSet {

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
            return R.layout._credits_clients_items;
        }

        public CharSequence getAvatarSecond() {
            return String.valueOf( this.totalCredits - this.totalCreditsPay );
        }
    }
}
