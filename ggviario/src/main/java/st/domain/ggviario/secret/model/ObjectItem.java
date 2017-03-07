package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;

import st.domain.support.android.util.BaseCharSequence;

/**
 *
 * Created by dchost on 03/02/17.
 */

public class ObjectItem  extends BaseCharSequence implements Parcelable {

    private int id;
    private String desc;
    private ObjectItemType itemType;
    private ObjectItem superObjectItem;

    public ObjectItem(int id, String desc, ObjectItemType itemType) {
        this(id, desc, itemType, null);
    }

    public ObjectItem(int id, String desc, ObjectItemType itemType, ObjectItem superObjectItem) {
        this.id = id;
        this.desc = desc;
        this.itemType = itemType;
        this.superObjectItem = superObjectItem;
    }

    public ObjectItem(String desc, ObjectItemType itemType, ObjectItem superObjectItem) {
        this.desc = desc;
        this.itemType = itemType;
        this.superObjectItem = superObjectItem;
    }


    protected ObjectItem(Parcel in) {
        this.id = in.readInt();
        this.desc = in.readString();
        this.itemType = in.readParcelable( ObjectItemType.class.getClassLoader() );
        this.superObjectItem = in.readParcelable(ObjectItem.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.desc );
        dest.writeParcelable( this.itemType, flags );
        dest.writeParcelable( this.superObjectItem, flags );
    }

    public static final Creator<ObjectItem> CREATOR = new Creator<ObjectItem>() {
        @Override
        public ObjectItem createFromParcel(Parcel in) {
            return new ObjectItem(in);
        }

        @Override
        public ObjectItem[] newArray(int size) {
            return new ObjectItem[size];
        }
    };

    @Override
    public String toString() {
        return this.desc;
    }

    public int getId() {
        return id;
    }

    public boolean hasSuper() {
        return this.superObjectItem != null;
    }


    public String getDesc() {
        return desc;
    }

    public ObjectItemType getItemType() {
        return itemType;
    }

    public ObjectItem getSuperObjectItem() {
        return superObjectItem;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj instanceof ObjectItem
                && ((ObjectItem) obj).id == this.id
                && ((ObjectItem) obj).desc.equals(this.desc)
                && ((ObjectItem) obj).itemType.equals(this.itemType)
                ;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
