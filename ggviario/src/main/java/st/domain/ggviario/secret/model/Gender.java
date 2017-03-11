package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;

import st.domain.ggviario.secret.dao._database;
import st.domain.support.android.sql.annnotation.Column;
import st.domain.support.android.util.BaseCharSequence;


/**
 *
 * Created by dchost on 04/02/17.
 */

public class Gender extends BaseCharSequence  implements Parcelable {


    private int id;
    private String desc;

    public Gender(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    protected Gender(Parcel in) {
        this.id = in.readInt();
        this.desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.desc );
    }

    public static final Creator<Gender> CREATOR = new Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel in) {
            return new Gender(in);
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
