package st.domain.ggviario.secret.dao;

import android.content.Context;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.CreditProduct;
import st.domain.ggviario.secret.model.Credits;
import st.domain.ggviario.secret.model.Measure;
import st.domain.ggviario.secret.model.Product;
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


    public void loadCreditClient( Client client, final OnLoadData<Credits> onLoadData ) {
        query(
                select("*")
                    .from($credit)
                    .where( credit.credi_cli_id ).equal( value( client.getId() ) )
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

        final List<CreditProduct> list = new LinkedList<>();
        query(
                select("*")
                .from($creditproduct)
                .where(creditproduct.crediprod_credi_id).equal( value( row.integer( credit.credi_id ) ) )

        ).onQueryResult(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                CreditProduct creditProduct = mountCreditProduct( row );
                list.add(creditProduct);
            }
        });

        return new Credits(
                row.real( credit.credi_valuetotal ),
                row.date( credit.credi_dtreg ),
                list
        );
    }

    private CreditProduct mountCreditProduct(SQLRow row) {

        ProductDao productDao = new ProductDao( this.getContext() );
        Product product = productDao.getProduct(row.integer( creditproduct.crediprod_prod_id ) );
        Measure measure = productDao.getMeasureById( row.integer( creditproduct.crediprod_meas_id ) );

        return new CreditProduct(
                row.integer( creditproduct.crediprod_id ),
                product,
                row.realDouble( creditproduct.crediprod_quantity ),
                row.real( creditproduct.crediprod_price),
                measure,
                row.real( creditproduct.crediprod_pricequantity )
        );
    }

    public boolean registerCredit(Client client, Date dateFinisher, double valueTotal, List< CreditProduct > creditProducts) {



        execute(
                insertInto($credit)
                .columns(
                        credit.credi_cli_id,
                        credit.credi_dtfinalizar,
                        credit.credi_user_id,
                        credit.credi_valuetotal
                ) . values (
                        client.getId(),
                        date( dateFinisher ),
                        super.getCurrentUserId(),
                        valueTotal
                )
        );

        long idCredit = getResultExecut();

        for( CreditProduct item: creditProducts ) {



            execute(
                    insertInto($creditproduct).columns(
                            creditproduct.crediprod_credi_id,
                            creditproduct.crediprod_prod_id,
                            creditproduct.crediprod_quantity,
                            creditproduct.crediprod_user_id,
                            creditproduct.crediprod_price,
                            creditproduct.crediprod_pricequantity,
                            creditproduct.crediprod_meas_id
                    ) . values(
                            idCredit,
                            item.getProduct().getId(),
                            item.getQuantity(),
                            getCurrentUserId(),
                            item.getPrice(),
                            item.getPriceQuantity(),
                            item.getMeasure().getId()
                    )
            );
        }

        return true;
    }
}
