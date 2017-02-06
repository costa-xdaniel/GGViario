package st.domain.ggviario.secret.model;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 04/02/17.
 */

public class Gender extends BaseCharSequence {

    private int id;
    private  String desc;

    public Gender(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int id() {
        return id;
    }

    public String desc() {
        return desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
