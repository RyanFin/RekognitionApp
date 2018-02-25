package io.github.ryanfin.facerekognitionapp.RecyclerViewConfig;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import io.github.ryanfin.facerekognitionapp.R;

/**
 * Created by RyanFin on 03/02/2018.
 */

public class ImageModel implements Parcelable {

    private String mUrl;
    private String mTitle;
    private int mDrawable;

    public ImageModel(String mUrl, String mTitle, int mDrawable) {
        this.mUrl = mUrl;
        this.mTitle = mTitle;
        this.mDrawable = mDrawable;
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

    public int getmDrawable() {
        return mDrawable;
    }

    public void setmDrawable(int mDrawable) {
        this.mDrawable = mDrawable;
    }

    public static  ImageModel[] getSpacePhotos() {

        return new ImageModel[]{
                new ImageModel("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/A._Schwarzenegger.jpg/220px-A._Schwarzenegger.jpg", "Arnold Schwarzenegger",  R.drawable.arnold_schwarzenegger),
                new ImageModel("http://static.tvgcdn.net/mediabin/showcards/celebs/k-l/thumbs/leonardo-dicaprio_sc_768x1024.png", "Leornardo Dicaprio",R.drawable.leonardo_dicaprio),
                new ImageModel("https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg", "Barack Obama",R.drawable.barack_obama),
                new ImageModel("http://www.observerbd.com/2017/11/27/1511791440.jpg", "Brad Pitt",R.drawable.brad_pitt),
                new ImageModel("https://www.unilad.co.uk/wp-content/uploads/2016/03/celeb1.jpg", "Beyonce",R.drawable.beyonce),
                new ImageModel("https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Will_Smith_by_Gage_Skidmore.jpg/1200px-Will_Smith_by_Gage_Skidmore.jpg", "Will Smith",R.drawable.will_smith),
                new ImageModel("https://secure.i.telegraph.co.uk/multimedia/archive/01418/sugar_1418175c.jpg", "Alan Sugar",R.drawable.alan_sugar),
                new ImageModel("https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/Elon_Musk_2015.jpg/220px-Elon_Musk_2015.jpg", "Elon Musk",R.drawable.elon_musk),
                new ImageModel("https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_80%2Cw_300/MTE4MDAzNDEwNDYyNDEwMjU0/sir-richard-branson-9224520-1-402.jpg", "Richard Branson",R.drawable.richard_branson),
                new ImageModel("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Steve_Jobs_Headshot_2010-CROP.jpg/1200px-Steve_Jobs_Headshot_2010-CROP.jpg", "Steve Jobs",R.drawable.steve_jobs),
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
