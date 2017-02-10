package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.support.android.sql.LiteDatabase;
import st.domain.support.android.sql.SQLRow;
import st.domain.support.android.sql.sqlite.AssetsDatabase;

/**
 *
 * Created by xdata on 12/24/16.
 */

public class Dao<E> extends LiteDatabase implements _database {

    public static int DATABASE_VERSION = 118;
    public static final String DATABASE_NAME = "ggviario.mobile.db";


    String ALL = "*";

    public Dao(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }


    public static void inti(Context context) {
        new AssetsDatabase(context, Dao.DATABASE_NAME, Dao.DATABASE_VERSION)
                .close()
        ;
    }

    public void onLoad (OnLoadData <E> onLoadData) {
    }


    protected interface OnLoadData<E> {
        boolean  onLoad (E e, SQLRow row);
    }

    public static  abstract class OnLoadAllData <E> implements OnLoadData <E> {

        @Override
        public boolean onLoad(E e, SQLRow row) {
            onLoadData(e, row);
            return true;
        }

        protected abstract void onLoadData( E data, SQLRow row );
    }
}
