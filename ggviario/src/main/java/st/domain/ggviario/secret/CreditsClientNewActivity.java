package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import st.domain.ggviario.secret.adapter.SimpleSpinnerAdapter;
import st.domain.ggviario.secret.dao.ClientDao;
import st.domain.ggviario.secret.dao.ObjectDao;
import st.domain.ggviario.secret.model.Client;
import st.domain.ggviario.secret.model.Gender;
import st.domain.ggviario.secret.model.ObjectItem;

/**
 *
 * Created by dchost on 03/02/17.
 */

public class CreditsClientNewActivity extends AbstractActivityToolbarNew {

    private EditText edName;
    private EditText edSurnname;
    private Spinner spinnerGender;
    private EditText edResidence;
    private EditText edContact;
    private EditText edMail;
    private Spinner spinnerDocuments;
    private EditText edDocument;
    private ClientDao dao;
    private ObjectDao objectDao;

    @Override
    protected int getContentView() {
        return R.layout._credits_clients_new;
    }

    @Override
    protected int getActivityName() {
        return R.string.new_client;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //Create
        super.onCreate(savedInstanceState);


        //Init
        this.edName = (EditText) this.findViewById(R.id.ed_cliente_name);
        this.edSurnname = (EditText) this.findViewById(R.id.ed_cliente_surname);
        this.edResidence = (EditText) this.findViewById(R.id.ed_cliente_residence);
        this.edContact = (EditText) this.findViewById(R.id.ed_cliente_contact);
        this.edMail = (EditText) this.findViewById(R.id.ed_client_mail);
        this.spinnerDocuments = (Spinner) this.findViewById(R.id.spinner_client_documents);
        this.edDocument = (EditText) this.findViewById(R.id.ed_client_document_num);


        //Mount gender spinner
        this.spinnerGender = (Spinner) this.findViewById(R.id.spinner_client_gender);
        this.dao = new ClientDao(this);
        SimpleSpinnerAdapter genderSpinnerAdapter = new SimpleSpinnerAdapter(this);
        genderSpinnerAdapter.add("Sexo");
        genderSpinnerAdapter.addAll(this.dao.getListGender());
        genderSpinnerAdapter.setOnItemCreated(new SimpleSpinnerAdapter.OnItemCreated() {
            @Override
            public void accept(View viewLayout, TextView view, int index, CharSequence item) {
                if(index == 0) {
                    view.setText(null);
                    view.setHint(item);
                }

            }
        });
        this.spinnerGender.setAdapter(genderSpinnerAdapter);

        //Mount spinner document
        this.objectDao = new ObjectDao(this);
        SimpleSpinnerAdapter documentsSpinnerAdapter = new SimpleSpinnerAdapter(this);
        documentsSpinnerAdapter.add(getString(R.string.type_documents_optional));
        documentsSpinnerAdapter.addAll(objectDao.loadTypeDocuments());
        documentsSpinnerAdapter.setOnItemCreated(genderSpinnerAdapter.getOnItemCreated());
        this.spinnerDocuments.setAdapter(documentsSpinnerAdapter);

    }


    @Override
    public int doneId() {
        return R.id.opt_done;
    }

    @Override
    protected boolean onDoneAction() {
        String name = this.edName.getText().toString();
        String surname = this.edName.getText().toString();
        CharSequence gender = emptyIfString((CharSequence) this.spinnerGender.getSelectedItem());
        String residence = this.edResidence.getText().toString();
        String contact = this.edContact.getText().toString();
        String mail = this.edMail.getText().toString();
        CharSequence typeDocument = emptyIfString((CharSequence) this.spinnerDocuments.getSelectedItem());
        String numDocument = this.edDocument.getText().toString();


        //Validar values
        if(isEmpty(name, getString(R.string.name_is_required))) return false;
        if(isEmpty(gender.toString(), getString(R.string.gender_is_required))) return false;
        if(isEmpty(residence, getString(R.string.residence_is_required))) return false;

        //Register into database
        Client client = new Client(
                name,
                surname,
                this.objectDao.findResidence( residence ),
                ( typeDocument instanceof ObjectItem )? ( ObjectItem ) typeDocument: null,
                contact,
                mail,
                numDocument,
                ( Gender ) gender
        );

        client = this.dao.createNewClient(client);
        if(client == null ){
            Toast.makeText( this, "Não pode registrar o cliente", Toast.LENGTH_LONG ).show();
        }

        return true;
    }

    private boolean isEmpty(String value, String message) {
        if( value == null || value.isEmpty() ) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private CharSequence emptyIfString (CharSequence charSequence) {
        return (charSequence  == null || charSequence instanceof  String )? "": charSequence;
    }

    @Override
    public Toolbar getPrincipalToolbar() {
        return (Toolbar) this.findViewById(R.id.toolbar);
    }
}

