package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.ggviario.secret.model.Credits;
import st.domain.support.android.sql.OnQueryResult;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 10/02/17.
 */

public class CreditsDao extends Dao<Credits> {

    public CreditsDao(Context context) {
        super(context);
    }


    @Override
    public void onLoad(final OnLoadData<Credits> onLoadData) {
        query(
                select("*")
                .from($CREDIT)
        );

        onQueryResult(new OnQueryResult() {
            @Override
            public boolean accept(SQLRow row) {
                Credits credits = mount(row);
                return onLoadData.onLoad( credits, row );
            }
        });
    }

    private Credits mount( SQLRow row ) {

        return new Credits(
                row.real( T_CREDIT.credi_valuetotal ),
                row.date( T_CREDIT.credi_dtreg )
        );
    }
}
