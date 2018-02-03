package io.github.ryanfin.facerekognitionapp.RecyclerViewConfig;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RyanFin on 03/02/2018.
 */

public class ImageModel implements Parcelable {

    private String mUrl;
    private String mTitle;

    public ImageModel(String mUrl, String mTitle) {
        this.mUrl = mUrl;
        this.mTitle = mTitle;
    }

    protected ImageModel(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public static  ImageModel[] getSpacePhotos() {

        return new ImageModel[]{
                new ImageModel("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"),
                new ImageModel("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"),
                new ImageModel("http://i.imgur.com/n6RfJX2.jpg", "Galaxy Orion"),
                new ImageModel("http://i.imgur.com/qpr5LR2.jpg", "Earth"),
                new ImageModel("http://i.imgur.com/pSHXfu5.jpg", "Astronaut"),
                new ImageModel("http://i.imgur.com/3wQcZeY.jpg", "Satellite"),
        };
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
