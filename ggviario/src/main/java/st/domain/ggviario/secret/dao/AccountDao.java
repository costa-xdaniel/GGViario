package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.ggviario.secret.model.Account;

/**
 *
 * Created by daniel on 3/14/17.
 */

public class AccountDao extends Dao<Account> {

    public AccountDao(Context context) {
        super(context);
    }

    public boolean regMovementAccount(int idAccount, float creditValue, float debitValue, String deseginicao ){
        return this.regMovementAccount( idAccount, creditValue, debitValue, deseginicao, null, null, null );
    }

    public boolean regMovementAccount(int idAccount, float creditValue, float debitValue, String deseginicao, String documnet, String table, Integer rowKey ) {

        execute( insertInto( $movimentaccount ).columns(
                    movimentaccount.movaccount_account_id,
                    movimentaccount.movaccount_user_id,
                    movimentaccount.movaccount_debit,
                    movimentaccount.movaccount_credit,
                    movimentaccount.movaccount_desg,
                    movimentaccount.movaccount_document,
                    movimentaccount.movaccount_table,
                    movimentaccount.movaccount_rowkey
                ).values(
                    idAccount,
                    getCurrentUserId(),
                    debitValue,
                    creditValue,
                    deseginicao,
                    documnet,
                    table,
                    rowKey
                )
        );
        return true;
    }





}
