package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.Gender;
import st.domain.support.android.sql.OnQueryResult;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 04/02/17.
 */

public class ClientDao extends Dao<Client> {

    private ObjectDao objectDao;
    private Map< Integer, Gender > mapGender;
    private Map< Integer, Client > mapClient;

    public ClientDao(Context context) {
        super(context);

        this.mapGender = new LinkedHashMap<>();
        this.objectDao = new ObjectDao(context);
        this.mapClient = new LinkedHashMap<>();
        this.loadGenders();
    }

    private void loadGenders() {
        query(select("*")
                .from($gender)
        );

        onQueryResult(new OnAllQueryResults() {
            @Override
            protected void onRow( SQLRow row ) {
                Gender gender = mountGender( row );

                Log.i( "APP.GGVIARIO", "onRow: (gender) "+gender );
                mapGender.put(gender.getId(), gender);
            }
        });
    }

    public List<Gender> getListGender() {
        return new LinkedList<>(this.mapGender.values());
    }

    public List<Client> loadAll () {

        final List<Client> list = new LinkedList<>();

        query( select("*")
                 .from($client)
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
                row.integer(gender.gender_id),
                row.string(gender.gender_desc)
        );
    }

    private  @NonNull Client mount (SQLRow row) {
        Client vClient = new Client(

                row.integer(client.cli_id),
                row.string(client.cli_name),
                row.string(client.cli_surname),
                this.objectDao.get(row.integer(client.cli_obj_residence)),
                this.objectDao.get(row.integer(client.cli_obj_typedocument)),
                row.string(client.cli_contact),
                row.string(client.cli_mail),
                row.string(client.cli_document),
                this.mapGender.get(row.integer(client.cli_gender_id))

        );

        this.mapClient.put( vClient.getId(), vClient );
        return vClient;
    }

    public Client createNewClient(Client client) {
        execute(
                insertInto($client) .columns(
                        _database.client.cli_name,
                        _database.client.cli_surname,
                        _database.client.cli_gender_id,
                        _database.client.cli_obj_residence,
                        _database.client.cli_contact,
                        _database.client.cli_mail,
                        _database.client.cli_obj_typedocument,
                        _database.client.cli_document
                ) .values(
                        client.getName(),
                        client.getSurname(),
                        client.getGender().getId(),
                        ( client.getResidence() != null )? client.getResidence().getId() : null,
                        client.getContact(),
                        client.getMail(),
                        ( client.typeDocument() != null )? client.typeDocument().getId() : null,
                        client.getDocument()
                )
        );

        Long id = getResultExecut();

        query(
                select("*")
                .from($client)
                .where( _database.client.cli_id).equal( value( id ) )
                .limit(1)
        );

        List<SQLRow> list = catchAllResult();
        if(list != null && list.size() > 0  ) {
            return this.mount(list.get(0));
        }

        this.cloneDatabase();
        return null;
    }

    public void onLoad(final OnLoadData<Client> onLoadData) {
        query( select("*")
                .from($ver_client_status)
        );

        onQueryResult(new OnQueryResult() {
            @Override
            public boolean accept(SQLRow row) {
                Client client = mount( row );
                return onLoadData.onLoad(client, row);
            }
        });
    }
}
