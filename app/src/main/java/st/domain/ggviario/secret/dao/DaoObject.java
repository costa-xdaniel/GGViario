package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.ggviario.secret.model.User;

import java.util.List;

import st.domain.ggviario.secret.references.RData;
import st.domain.support.android.sql.SQLRow;

/**
 * Created by xdata on 7/26/16.
 */
public class DaoObject extends Dao
{


    public DaoObject(Context context) {
        super(context);
    }

    public String getObjectId(final String name, final TypeObject typeObject)
    {

        DaoUser daoUser = new DaoUser(this.getContext());
//        User user = daoUser.getLogedUser();
//        if(user == null)
//            throw new Error("Nehum utilizador na seção");
//
//        if(name==null || typeObject == null)
//            return null;
//
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.T_OBJECT)
//                .where(new Condicion() {
//                    @Override
//                    public boolean accept(int wherePosition, SQLRow row)
//                    {
//                        return (row.integer(RData.OBJ_TOBJ_ID))== typeObject.getDataBaseType()
//                                && row.string(RData.OBJ_DESC).equalsIgnoreCase(name);
//                    }
//                })
//                .limit(1);
//        execute();
//        List<SQLRow> result = getSelectResult();
//        end();
//
//
//        if(result.size()>0)
//            return result.get(0).string(RData.OBJ_ID);
//        else
//        {
//
//            begin(Operaction.INSERT);
//
//            insertInto(RData.T_OBJECT, RData.OBJ_PREVIEWID)
//                    .columns(RData.OBJ_DESC, RData.OBJ_TOBJ_ID, RData.OBJ_USER_ID)
//                    .values(name, typeObject.dataBaseType, user.getId())
//                    .returning(null);
//            execute();
//            SQLRow insertResult = getInsertResult();
//            String id =  "~~"+insertResult.string(RData.OBJ_PREVIEWID);
//            end();
//            return id;
//        }
        return null;
    }

    public enum TypeObject
    {
        RESIDENCIA(1),
        TYPE_DOCUMENT(3);

        private final int dataBaseType;

        TypeObject(int dataBaseType) {
            this.dataBaseType = dataBaseType;
        }

        public int getDataBaseType() {
            return dataBaseType;
        }
    }
}
