package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.Gender;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 04/02/17.
 */

public class ClientDao extends Dao <Client>  {

    private ObjectDao objectDao;
    private Map<Integer, Gender> mapGender;

    public ClientDao(Context context) {
        super(context);
        this.mapGender = new LinkedHashMap<>();
        this.objectDao = new ObjectDao(context);
        this.loadGenders();
    }

    private void loadGenders() {
        query(select("*")
                .from(T_GENDER$)
        );

        onQueryResult(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                Gender gender = mountGender(row);
                mapGender.put(gender.id(), gender);
            }
        });
    }

    public List<Gender> getListGender() {
        return new LinkedList<>(this.mapGender.values());
    }

    public List<Client> loadAll () {

        final List<Client> list = new LinkedList<>();

        query( select("*")
                 .from(T_CLIENT$)
        );

        onQueryResult(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                Client client = mount(row);
                list.add(client);
            }
        });

        return list;
    }


    private @NonNull Gender mountGender(SQLRow row) {
        return new Gender(
                row.integer(T_GENDER.gender_id),
                row.string(T_GENDER.gender_desc)
        );
    }

    private  @NonNull Client mount (SQLRow row) {
        return new Client(
                row.integer(T_CLIENT.cli_id),
                row.string(T_CLIENT.cli_name),
                row.string(T_CLIENT.cli_surname),
                this.objectDao.get(row.integer(T_CLIENT.cli_obj_residence)),
                this.objectDao.get(row.integer(T_CLIENT.cli_obj_typedocument)),
                row.string(T_CLIENT.cli_contact),
                row.string(T_CLIENT.cli_mail),
                row.string(T_CLIENT.cli_document),
                this.mapGender.get(row.integer(T_CLIENT.cli_gender_id))
        );
    }

    public Client createNewClient(Client client) {
        execute(
                insertInto(T_CLIENT$) .columns(
                        T_CLIENT.cli_name,
                        T_CLIENT.cli_surname,
                        T_CLIENT.cli_gender_id,
                        T_CLIENT.cli_obj_residence,
                        T_CLIENT.cli_contact,
                        T_CLIENT.cli_mail,
                        T_CLIENT.cli_obj_typedocument,
                        T_CLIENT.cli_document
                ) .values(
                        client.name(),
                        client.surname(),
                        client.gender().id(),
                        ( client.residence() != null )? client.residence().getId() : null,
                        client.contact(),
                        client.mail(),
                        ( client.typeDocument() != null )? client.typeDocument().getId() : null,
                        client.document()
                )
        );

        Long id = getResultExecut();

        query(
                select("*")
                .from( T_CLIENT$ )
                .where( T_CLIENT.cli_id).equal( value( id ) )
                .limit(1)
        );

        List<SQLRow> list = catchAllResult();
        if(list != null && list.size() > 0  ) {
            return this.mount(list.get(0));
        }

        this.cloneDatabase();
        return null;
    }
}
