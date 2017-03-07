package st.domain.ggviario.secret.dao;

import android.content.Context;

import java.lang.reflect.Field;

import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.User;
import st.domain.support.android.sql.LiteDatabase;
import st.domain.support.android.sql.SQLRow;
import st.domain.support.android.sql.annnotation.Column;
import st.domain.support.android.sql.sqlite.AssetsDatabase;

/**
 *
 * Created by xdata on 12/24/16.
 */

public class Dao<E> extends LiteDatabase implements _database {

    public static int DATABASE_VERSION = 127;
    public static final String DATABASE_NAME = "ggviario.mobile.db";
    private UserDao userDao;


    String ALL = "*";

    public Dao(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }


    public static void inti(Context context) {
        new AssetsDatabase(context, Dao.DATABASE_NAME, Dao.DATABASE_VERSION)
                .close()
        ;
    }


    public Integer getCurrentUserId() {
        User user = this.getCurrentUser();
        if( user != null )
            return userDao.getCurrentUser().getId();
        return null;
    }

    public User getCurrentUser() {
        return userDao().getCurrentUser();
    }


    private UserDao userDao() {
        if( this.userDao == null )
            this.userDao = new UserDao( this.getContext() );
        return this.userDao;
    }


    protected interface OnLoadData<E> {
        boolean  onLoad (E e, SQLRow row);
    }

    public static  abstract class OnLoadAllData <E> implements OnLoadData <E> {

        @Override
        public boolean onLoad(E e, SQLRow row) {
            Class<Client> l = Client.class;
            onLoadData(e, row);
            return true;
        }

        protected abstract void onLoadData( E data, SQLRow row );
    }

    /**
     * Montar uma classe do SQLRow
     * @param row o row a ser montada
     * @param rowClass a classe com a capacidede de montar a row
     * @return the mouted object of class
     */
    public Object mount(SQLRow row, Class<?> rowClass ) {

        try{
            Object mount = rowClass.newInstance();
            for( Field field: rowClass.getFields() ) {
                if( field.isAnnotationPresent(Column.class) ) {
                    Column annotation = field.getAnnotation(Column.class);
                    if ( row.hasColumn( annotation.value() ) ) {
                        try {
                            field.set(mount, row.get(annotation.value(), field.getType()));
                        }catch (Exception ex) {

                        }
                    }
                }
            }
            return mount;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean support(Class<?> field) {
        return field.isAssignableFrom(Number.class);
    }

    private interface Action<E> {
        void execute( Field field, E vE, SQLRow row );
    }
}
