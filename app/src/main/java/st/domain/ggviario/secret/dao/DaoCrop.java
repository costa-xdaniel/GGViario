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
import st.domain.ggviario.secret.model.Sector;
import st.domain.ggviario.secret.model.User;
import st.domain.support.android.sql.OnCatchSQLRow;
import st.domain.support.android.sql.SQLRow;
import st.domain.support.android.sql.sqlite.Convert;
import st.domain.support.android.sql.builder.Select;

/**
 * Created by xdata on 12/24/16.
 */
public class DaoCrop  extends Dao {
    public DaoCrop(Context context) {
        super(context);
    }

    public void register(int quantity, Sector sector, int quantityPercasOvos, int quantityPercasGalinha) {

        User s = DaoUser.geUser(this.getContext());

        begin(Operaction.INSERT);

        insertInto(T_CROP$, T_CROP.crop_id)

                .columns(

                        T_CROP.crop_totalovos,
                        T_CROP.crop_sector_id,
                        T_CROP.crop_user_id,
                        T_CROP.crop_percasovos,
                        T_CROP.crop_percasgalinhas

                ).values(

                        quantity,
                        sector.getId(),
                        s.getId(),
                        quantityPercasOvos,
                        quantityPercasGalinha
        );
        execute();
        end();

        Log.i("GGVIARIO@INFO", "last row id is" + getResources().last_insert_rowid()+"");

        end();

        Dao.saveOutFile(this.getContext());
    }

    public List<Crop> loadCropData(){

        final List<Crop> cropList = new Stack<>();

        query().execute(new Select(ALL)
                .from(VER_CROPGROUP$)
        );

        query().forLoopCursor(new OnCatchSQLRow() {
            @Override
            public void accept(SQLRow row) {
                cropList.add(mountCropDate(row));
            }
        });

        return cropList;
    }

    public  List<CropSector> loadCropContents(final Date date){

        final List<CropSector> contentsList = new LinkedList<>();

        query().execute(
                new Select(ALL)
                    .from(VER_CROPSECTORDATE$)
                    .where(VER_CROPSECTORDATE.date).equal(Convert.date(date))
        );

        query().forLoopCursor(new OnCatchSQLRow() {
            @Override
            public void accept(SQLRow row) {
                contentsList.add(mountDateSector(row));
            }
        });


        query().execute();

        return contentsList;
    }

    static CropSector mountDateSector(SQLRow row) {
        return new CropSector(
                row.date(VER_CROPGROUP.date),
                row.integer(VER_CROPGROUP.quantity),
                row.integer(VER_CROPGROUP.quantitypercas),
                row.integer(VER_CROPGROUP.quantitypercasgalinha),
                DaoSector.mountSector(row)
        );
    }

    @NonNull
    public static Crop mountCropDate(SQLRow row) {
        return new Crop(

                row.date(VER_CROPGROUP.date),
                row.integer(VER_CROPGROUP.quantity),
                row.integer(VER_CROPGROUP.quantitypercas),
                row.integer(VER_CROPGROUP.quantitypercasgalinha)

        );
    }
}
