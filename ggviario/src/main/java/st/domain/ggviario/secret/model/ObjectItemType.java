package st.domain.ggviario.secret.model;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 03/02/17.
 */

public class ObjectItemType extends BaseCharSequence {

    private int id;
    private String desc;
    private Integer state;

    public ObjectItemType(int id, String desc) {
        this(id, desc, 1);
    }

    public ObjectItemType(Integer id, String desc, Integer state) {
        this.id = id;
        this.desc = desc;
        this.state = state;
    }

    @Override
    public String toString() {
        return this.desc;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj instanceof ObjectItemType
                && ((ObjectItemType) obj).id == this.id
                && ((ObjectItemType) obj).desc.equals(this.desc);
    }
}
