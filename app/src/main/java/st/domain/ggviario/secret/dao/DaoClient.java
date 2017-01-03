package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import st.domain.ggviario.secret.references.RData;
import st.domain.ggviario.secret.model.Client;
import st.domain.support.android.sql.SQLRow;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by xdata on 7/26/16.
 */
public class DaoClient extends Dao
{
    private Client defaultClient;

    public DaoClient(Context context)
    {
        super(context);
    }

    public static Client getAnonimeCLient()
    {
        return new Client(1, "Cliente", "Anonimo", "", "");
    }

    /**
     * Registar um novo client
     * @param client
     * @return
     */
    public synchronized long regClient(Client client)
    {
        DaoObject daoObject = new DaoObject(this.getContext());
        String idResidence = daoObject.getObjectId(client.getResidence(), DaoObject.TypeObject.RESIDENCIA);

        long id = -1;
        begin(Operaction.INSERT);
        insertInto(RData.T_CLIENT, RData.CLI_PREVIEWID)
                .columns(RData.CLI_NAME, RData.CLI_SURNAME, RData.CLI_CONTACT, RData.CLI_OBJ_RESID)
                .values(client.getName(), client.getSurname(), client.getContact(), idResidence);
        execute();
        id = (long) getResult();
        end();
        return id;
    }

    public ArrayList<Client> loadClientes()
    {
        ArrayList<Client> list = new ArrayList<>();
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.VER_CLIENTS);
//        execute();
//        List<SQLRow> result = getSelectResult();
//        end();
//        for(SQLRow row: result)
//        {
//            Client client = DaoClient.mountClient(row);
//            list.add(client);
//        }

        return list;
    }

    @NonNull
    protected static Client mountClient(SQLRow sqlRow) {

        Integer  id = sqlRow.integer(RData.CLI_ID);
        String name = sqlRow.string(RData.CLI_NAME);
        String surname = sqlRow.string(RData.CLI_SURNAME);
        String contact = sqlRow.string(RData.CLI_CONTACT);
        String morada = sqlRow.string(RData.OBJ_DESC);
        return new Client(id, name, surname, morada, contact);

    }
}
