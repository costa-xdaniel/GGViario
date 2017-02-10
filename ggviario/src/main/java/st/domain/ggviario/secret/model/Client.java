package st.domain.ggviario.secret.model;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 04/02/17.
 */
public class Client extends BaseCharSequence {

    private Gender gender;
    private Integer id;
    private String name;
    private String surname;
    private ObjectItem residence;
    private ObjectItem typeDocument;
    private String contact;
    private String mail;
    private String document;

    public Client(Integer id, String name, String surname, ObjectItem residence, ObjectItem typeDocument, String contact, String mail, String document, Gender gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.residence = residence;
        this.typeDocument = typeDocument;
        this.contact = contact;
        this.mail = mail;
        this.document = document;
        this.gender = gender;
    }

    public Client(String name, String surname, ObjectItem residence, ObjectItem typeDocument, String contact, String mail, String document, Gender gender) {
        this.name = name;
        this.surname = surname;
        this.residence = residence;
        this.typeDocument = typeDocument;
        this.contact = contact;
        this.mail = mail;
        this.document = document;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public Client id(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Client surname(String surname) {
        this.surname = surname;
        return this;
    }

    public ObjectItem getResidence() {
        return residence;
    }

    public Client residence(ObjectItem residence) {
        this.residence = residence;
        return this;
    }

    public ObjectItem typeDocument() {
        return typeDocument;
    }

    public Client typeDocument(ObjectItem typeDocument) {
        this.typeDocument = typeDocument;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Client contact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public Client mail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public Client document(String document) {
        this.document = document;
        return this;
    }

    public Gender getGender() {
        return this.gender;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public CharSequence getAvatar() {
        if(this.name != null
                && this.name.trim().length() > 0 )
            return this.name.trim().subSequence(0, 1);
        return null;
    }

    public CharSequence getSubInformation() {
        String avatar =  this.contact != null ? this.contact
                : this.residence != null ? this.residence.getDesc()
                : this.typeDocument != null && this.document != null? "("+this.typeDocument.getDesc()+") "+this.document
                : this.gender != null ? this.gender.getDesc()
                : null;
        return avatar == null? null : avatar.toUpperCase();
    }
}
