package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import com.db.chart.model.Point;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import st.domain.ggviario.secret.model.Crop;
import st.domain.ggviario.secret.model.CropSector;
import st.domain.ggviario.secret.model.Sector;
import st.domain.ggviario.secret.model.User;
import st.zudamue.support.android.sql.OnQueryResult;
import st.zudamue.support.android.sql.SQLRow;
import st.zudamue.support.android.sql.builder.Select;


/**
 *
 * Created by xdata on 12/24/16.
 */
public class CropDao extends Dao {
    public CropDao(Context context) {
        super(context);
    }

    private int count;
    public void register(int quantity, Sector sector, int quantityPercasOvos, int quantityPercasGalinha) {

        User user = getCurrentUser();

        execute(
                insertInto($crop)
                .columns(
                    crop.crop_ovos,
                    crop.crop_sector_id,
                    crop.crop_user_id,
                    crop.crop_ovosdefeituosos,
                    crop.crop_percasgalinhas
                ).values(
                    quantity,
                    sector.getId(),
                    user.getId(),
                    quantityPercasOvos,
                    quantityPercasGalinha
                )
        );

        super.cloneDatabase();
    }

    public List<Crop> loadCropData(){

        final List<Crop> cropList = new Stack<>();

        query(select(ALL)
                .from($ver_cropgroup)
        );

        onQueryResult(
                new OnQueryResult() {
                    @Override
                    public boolean accept(SQLRow row) {
                        cropList.add(mountCropDate(row));
                        return true;
                    }
                }
        );

        return cropList;
    }

    public  List<CropSector> loadCropContents(final Date date){

        final List<CropSector> contentsList = new LinkedList<>();

        query(select(ALL)
                    .from($ver_cropsectordate)
                    .where(ver_cropsectordate.date).equal(date(date))
        );

        onQueryResult(new OnQueryResult() {
            @Override
            public boolean accept(SQLRow row) {
                contentsList.add(mountDateSector(row));
                return true;
            }
        });

        return contentsList;
    }

    static CropSector mountDateSector(SQLRow row) {
        return new CropSector(
                row.date(ver_cropgroup.date),
                row.integer(ver_cropgroup.quantity),
                row.integer(ver_cropgroup.quantitypercas),
                row.integer(ver_cropgroup.quantitypercasgalinha),
                SectorDao.mountSector(row)
        );
    }

    @NonNull
    public static Crop mountCropDate(SQLRow row) {
        return new Crop(

                row.date(ver_cropgroup.date),
                row.integer(ver_cropgroup.quantity),
                row.integer(ver_cropgroup.quantitypercas),
                row.integer(ver_cropgroup.quantitypercasgalinha)

        );
    }

    public List<Point> reportCropSectorDaily(Integer id, final ReportType type, Date dateInicio, Date dateFim) {
        final LinkedList<Point> points = new LinkedList<>();

        //strftime('%Y-%m-%d'

        Select sumSector = (Select) new Select(sum(crop.crop_ovos))
                .from($crop)
                .where(strftime("%Y-%m-%d", column(crop.crop_dtreg))).equal(column(ver_crop_date.date));

        if(id != null)
            sumSector.and(crop.crop_sector_id).equal(value(id));


        query( select(ver_crop_date.date,
                        sumSector.as("sum")
                        )
                .from($ver_crop_date)
                .where(ver_crop_date.date).between(date(dateInicio), date(dateFim))
        );

        final Map<Integer, String> dayOfWekMap = new LinkedHashMap<>();
        dayOfWekMap.put(Calendar.SUNDAY, "Dom."); //Domingo
        dayOfWekMap.put(Calendar.MONDAY, "Seg."); //Segunda
        dayOfWekMap.put(Calendar.TUESDAY, "Ter."); //Terca
        dayOfWekMap.put(Calendar.WEDNESDAY, "Qua."); //Quarta
        dayOfWekMap.put(Calendar.THURSDAY, "Qui."); //Qunita
        dayOfWekMap.put(Calendar.FRIDAY, "Sex."); //Sexta
        dayOfWekMap.put(Calendar.SATURDAY, "Sab."); //Dabado


        final Map<Integer, String> monthOfYear = new LinkedHashMap<>();
        monthOfYear.put(0, "Jan");
        monthOfYear.put(1, "Jan");
        monthOfYear.put(2, "Jan");
        monthOfYear.put(3, "Jan");
        monthOfYear.put(4, "Jan");
        monthOfYear.put(5, "Jan");
        monthOfYear.put(6, "Jan");
        monthOfYear.put(7, "Jan");
        monthOfYear.put(8, "Jan");
        monthOfYear.put(9, "Jan");
        monthOfYear.put(10, "Jan");
        monthOfYear.put(11, "Jan");
        monthOfYear.put(12, "Jan");


        final Calendar calendar = Calendar.getInstance();

        this.count = 0;
        onQueryResult(new OnQueryResult() {
            @Override
            public boolean accept(SQLRow row) {

                Date date = row.date(ver_crop_date.date);
                calendar.setTime(date);
                boolean accept = false;

                String label = "";
                switch (type) {
                    case DATE:

                        break;

                    case WEEK:
                        label = dayOfWekMap.get(calendar.get(Calendar.DAY_OF_WEEK));
                        accept = true;
                        break;

                    case MONTH:
                        label = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                        accept = true;
                        break;

                    case YEAR:
                        break;

                    case  ALL_YEAR:
                        break;

                    default:break;
                }

                if(accept) {
                    Integer quantity = row.integer("sum");
                    if(quantity == null) quantity = 0;
                    points.add(new Point(label,  quantity));
                }

                count ++;
                return true;
            }
        });

        return points;
    }


    public enum  ReportType {
        DATE,
        MONTH,
        WEEK,
        YEAR,
        ALL_YEAR
    }
}
