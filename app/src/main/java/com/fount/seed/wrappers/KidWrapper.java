package com.fount.seed.wrappers;

import android.os.Parcel;
import android.os.Parcelable;

import com.fount.seed.utils.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * KidWrapper
 */
@SuppressWarnings("WeakerAccess")
@IgnoreExtraProperties
public class KidWrapper implements Parcelable {

    @Exclude
    public static final int BOY = 1;

    @Exclude
    public static final int GIRL = 2;

    public String uid;
    public String kidName;
    public String sponsorName;
    public String sponsorEmail;
    public String cityName;
    public String kidAddress;
    public String classRoom;
    public String churchName;
    public String cellPhone;
    public String allergy;
    public Long gender;
    public Boolean canLeave;
    public Boolean church;
    public Boolean willReturn;
    public String birthDate;

    @SuppressWarnings("unused")
    public KidWrapper() {
    }

    public KidWrapper(String kidName, String sponsorName,
                      String sponsorEmail, String cityName,
                      String kidAddress, String classRoom,
                      String churchName, String cellPhone,
                      Long gender, Boolean canLeave,
                      Boolean church, Boolean willReturn,
                      String birthDate, String allergy) {
        this.kidName = kidName;
        this.sponsorName = sponsorName;
        this.sponsorEmail = sponsorEmail;
        this.cityName = cityName;
        this.kidAddress = kidAddress;
        this.classRoom = classRoom;
        this.churchName = churchName;
        this.cellPhone = cellPhone;
        this.gender = gender;
        this.canLeave = canLeave;
        this.allergy = allergy;
        this.church = church;
        this.willReturn = willReturn;
        this.birthDate = birthDate;
    }

    protected KidWrapper(Parcel in) {
        uid = in.readString();
        kidName = in.readString();
        sponsorName = in.readString();
        sponsorEmail = in.readString();
        cityName = in.readString();
        kidAddress = in.readString();
        classRoom = in.readString();
        churchName = in.readString();
        cellPhone = in.readString();
        allergy = in.readString();
        gender = in.readLong();
        canLeave = in.readByte() != 0;
        church = in.readByte() != 0;
        willReturn = in.readByte() != 0;
        birthDate = in.readString();
    }

    public static final Creator<KidWrapper> CREATOR = new Creator<KidWrapper>() {
        @Override
        public KidWrapper createFromParcel(Parcel in) {
            return new KidWrapper(in);
        }

        @Override
        public KidWrapper[] newArray(int size) {
            return new KidWrapper[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKidName() {
        return kidName;
    }

    @PropertyName(Constants.CLASS_ROOM)
    public String getClassRoom() {
        return classRoom;
    }

    public Long getGender() {
        return gender;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public String getSponsorEmail() {
        return sponsorEmail;
    }

    public String getCityName() {
        return cityName;
    }

    public String getKidAddress() {
        return kidAddress;
    }

    public String getChurchName() {
        return churchName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getAllergy() {
        return allergy;
    }

    public Boolean isCanLeave() {
        return canLeave;
    }

    public Boolean isChurch() {
        return church;
    }

    public Boolean isWillReturn() {
        return willReturn;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return kidName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(kidName);
        dest.writeString(sponsorName);
        dest.writeString(sponsorEmail);
        dest.writeString(cityName);
        dest.writeString(kidAddress);
        dest.writeString(classRoom);
        dest.writeString(churchName);
        dest.writeString(cellPhone);
        dest.writeString(allergy);
        dest.writeLong(gender);
        dest.writeByte((byte) (canLeave ? 1 : 0));
        dest.writeByte((byte) (church ? 1 : 0));
        dest.writeByte((byte) (willReturn ? 1 : 0));
        dest.writeString(birthDate);
    }

}
