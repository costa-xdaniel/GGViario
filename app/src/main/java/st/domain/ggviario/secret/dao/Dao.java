package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.support.android.sql.sqlite.AssetsDataBase;
import st.domain.support.android.old_sql.sqlite.LiteDataBase;

/**
 *
 * Created by xdata on 12/24/16.
 */

public class Dao extends LiteDataBase implements _database {
    public static int DATABASE_VERSION = 74;
    public static final String DATABASE_NAME = "ggviario.mobile.db";


    String ALL = "*";

    public Dao(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    public static void inti(Context context) {
        new AssetsDataBase(context, Dao.DATABASE_NAME, Dao.DATABASE_VERSION)
                .close();
    }

    public static void saveOutFile(Context context) {
        AssetsDataBase SQLiteServer = new AssetsDataBase(context, Dao.DATABASE_NAME, Dao.DATABASE_VERSION);
        SQLiteServer.outputDatabase();
    }
}
