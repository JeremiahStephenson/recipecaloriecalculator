package com.jerry.recipe.calorie.calculator.util;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterParameter implements Parcelable {
	private String mKey;
	private String mValue;

	public FilterParameter(String key, String value) {
		mKey = key;
		mValue = value;
	}
	
	public FilterParameter(Parcel parcel) {
		mKey = parcel.readString();
		mValue = parcel.readString();
	}
	
	@Override
    public int describeContents() {
	    return 0;
    }

	@Override
    public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mKey);
		parcel.writeString(mValue);
    }

	public static final Parcelable.Creator<FilterParameter> CREATOR = new Parcelable.Creator<FilterParameter>() {
		public FilterParameter createFromParcel(Parcel in) {
			return new FilterParameter(in);
		}

		public FilterParameter[] newArray(int size) {
			return new FilterParameter[size];
		}
	};

	public String getKey() {
		return mKey;
	}

	public String getValue() {
		return mValue;
	}
}
