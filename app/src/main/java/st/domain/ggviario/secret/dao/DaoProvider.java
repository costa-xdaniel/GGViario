package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import st.domain.ggviario.secret.model.Crop;
import st.domain.ggviario.secret.model.CropSector;
import st.domain.ggviario.secret.model.Provider;
import st.domain.ggviario.secret.model.Sector;
import st.domain.ggviario.secret.model.User;
import st.domain.support.android.sql.OnCatchSQLRow;
import st.domain.support.android.sql.SQLRow;
import st.domain.support.android.sql.builder.Select;
import st.domain.support.android.sql.sqlite.Convert;

/**
 *
 * Created by xdata on 12/24/16.
 */
public class DaoProvider extends Dao {
    public DaoProvider(Context context) {
        super(context);
    }


    public List<Provider> loadProviderData(){

        final List<Provider> cropList = new Stack<>();

        query().execute(new Select(ALL)
                .from(VER_SIMPLEPROVIDER$)

        ).forLoopCursor(new OnCatchSQLRow() {
            @Override
            public void accept(SQLRow row) {
                cropList.add(moutProvider(row));
            }
        });

        return cropList;
    }

    @NonNull
    public static Provider moutProvider(SQLRow row) {
        return new Provider(
                row.integer(VER_SIMPLEPROVIDER.id),
                row.string(VER_SIMPLEPROVIDER.name),
                row.string(VER_SIMPLEPROVIDER.contact),
                row.string(VER_SIMPLEPROVIDER.location),
                row.string(VER_SIMPLEPROVIDER.mail),
                row.string(VER_SIMPLEPROVIDER.site)
        );
    }
}
