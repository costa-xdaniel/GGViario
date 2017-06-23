package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;

import st.zudamue.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 03/02/17.
 */

public class ObjectItemType extends BaseCharSequence implements Parcelable {

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

    protected ObjectItemType(Parcel in) {
        this.id = in.readInt();
        this.desc = in.readString();
        this.state = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.desc) ;
        dest.writeInt( this.state );
    }

    public static final Creator<ObjectItemType> CREATOR = new Creator<ObjectItemType>() {
        @Override
        public ObjectItemType createFromParcel(Parcel in) {
            return new ObjectItemType(in);
        }

        @Override
        public ObjectItemType[] newArray(int size) {
            return new ObjectItemType[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

}
