package app.example.com.mydemo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/14.
 */

public class Stu implements Parcelable {
    private String name;
    private int age;
    private String sex;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.sex);
        dest.writeInt(this.id);
    }

    public Stu() {
    }

    protected Stu(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.sex = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Stu> CREATOR = new Parcelable.Creator<Stu>() {
        @Override
        public Stu createFromParcel(Parcel source) {
            return new Stu(source);
        }

        @Override
        public Stu[] newArray(int size) {
            return new Stu[size];
        }
    };

    @Override
    public String toString() {
        return "Stu{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", id=" + id +
                '}';
    }
}
