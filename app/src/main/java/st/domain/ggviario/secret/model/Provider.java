package st.domain.ggviario.secret.model;

import java.util.Date;

/**
 *
 * Created by xdata on 1/14/17.
 */
public class Provider {

    private final Integer id;
    private String site;
    private String mail;
    private String name;
    private String location;
    private String contact;


    public Provider(Integer id, String name, String contact, String location, String mail, String site) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.mail = mail;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }
}
