package se.tink.core.models.account;

import android.os.Parcel;
import android.os.Parcelable;

import se.tink.core.models.misc.ExactNumber;

public class Loan implements Parcelable {

    public Loan() {

    }

    protected Loan(Parcel in) {

        accountId = in.readString();

        if (in.readByte() == 0) {
            numMonthsBound = null;
        } else {
            numMonthsBound = in.readInt();
        }
        type = (Loan.Type) in.readSerializable();
        interest = in.readParcelable(ExactNumber.class.getClassLoader());
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Loan.Type type) {
        this.type = type;
    }

    public ExactNumber getInterest() {
        return interest;
    }

    public void setInterest(ExactNumber interest) {
        this.interest = interest;
    }

    public Integer getNumMonthsBound() {
        return numMonthsBound;
    }

    public void setNumMonthsBound(Integer numMonthsBound) {
        this.numMonthsBound = numMonthsBound;
    }

    public enum Type {
        TYPE_UNKNOWN,
        TYPE_MORTGAGE,
        TYPE_BLANCO,
        TYPE_MEMBERSHIP,
        TYPE_VEHICLE,
        TYPE_LAND,
        TYPE_STUDENT,
        TYPE_OTHER,
    }

    private String accountId;
    private Integer numMonthsBound;
    private Type type;
    private ExactNumber interest;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(accountId);

        if (numMonthsBound == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numMonthsBound);
        }

        dest.writeSerializable(type);
        dest.writeParcelable(interest, flags);
    }

    public static final Creator<Loan> CREATOR = new Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel in) {
            return new Loan(in);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };
}
