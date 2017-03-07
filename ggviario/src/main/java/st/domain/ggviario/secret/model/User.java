package st.domain.ggviario.secret.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xdata on 7/26/16.
 */
public class User implements Parcelable
{
    private int id;
    private String name;
    private String surName;
    private String accesName;

    public User(int id, String name, String surName, String accesName) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.accesName = accesName;
    }


    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.surName = in.readString();
        this.accesName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.id );
        dest.writeString( this.name );
        dest.writeString( this.surName );
        dest.writeString( this.accesName );
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getAccesName() {
        return accesName;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
