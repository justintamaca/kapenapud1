package com.example.kapenapud1;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedProductItem implements Parcelable {
    private String name;
    private String price;
    private int quantity;

    public SelectedProductItem(String name, String price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    protected SelectedProductItem(Parcel in) {
        name = in.readString();
        price = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<SelectedProductItem> CREATOR = new Creator<SelectedProductItem>() {
        @Override
        public SelectedProductItem createFromParcel(Parcel in) {
            return new SelectedProductItem(in);
        }

        @Override
        public SelectedProductItem[] newArray(int size) {
            return new SelectedProductItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeInt(quantity);
    }
}
