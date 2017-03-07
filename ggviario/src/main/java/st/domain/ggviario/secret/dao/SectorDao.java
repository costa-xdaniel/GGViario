package st.domain.ggviario.secret.dao;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import st.domain.ggviario.secret.model.Sector;
import st.domain.support.android.sql.OnQueryResult;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by xdata on 12/29/16.
 */

public class SectorDao extends Dao {

    public SectorDao(Context context) {
        super(context);
    }

    public List<Sector> loadSector () {

        final LinkedList<Sector> listSector = new LinkedList<Sector>();

        this.query(select(ALL)
                    .from($sector)
        );

        onQueryResult(new OnQueryResult() {
                    @Override
                    public boolean accept(SQLRow row) {
                        listSector.add(mountSector(row));
                        return true;
                    }
                }
        );

        return listSector;
    }


    static Sector mountSector(SQLRow row) {
        return new Sector(row.integer(sector.sector_id), row.string(sector.sector_name));
    }
}
