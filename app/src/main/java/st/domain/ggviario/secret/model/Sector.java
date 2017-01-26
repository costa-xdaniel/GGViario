package st.domain.ggviario.secret.model;

import st.domain.ggviario.secret.dao.Dao;

/**
 * Created by xdata on 12/24/16.
 */
public class Sector implements Dao.T_SECTOR {
    private int id;
    private String name;

    public Sector(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
