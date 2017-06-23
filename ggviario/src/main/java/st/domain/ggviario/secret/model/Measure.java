package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import st.zudamue.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class Measure extends BaseCharSequence implements Parcelable {

    private int id;
    private String cod;
    private String name;

    public Measure(int id, String cod, String name) {
        this.id = id;
        this.cod = cod;
        this.name = name;
    }

    protected Measure(Parcel in) {
        id = in.readInt();
        cod = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cod);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        @Override
        public Measure createFromParcel(Parcel in) {
            return new Measure(in);
        }

        @Override
        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };

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
