package st.domain.ggviario.secret.model;

import android.support.annotation.NonNull;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class Measure extends BaseCharSequence {

    private int id;
    private String cod;
    private String name;

    public Measure(int id, String cod, String name) {
        this.id = id;
        this.cod = cod;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getCod() {
        return cod;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
