package io.github.ryanfin.facerekognitionapp.RecyclerViewConfig;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.github.ryanfin.facerekognitionapp.R;

/**
 * Created by RyanFin on 03/02/2018.
 */

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

    @Override
    public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.list_item, parent, false);
        ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

        ImageModel spacePhoto = mSpacePhotos[position];
        ImageView imageView = holder.mPhotoImageView;

        Glide.with(mContext)
                .load(spacePhoto.getmUrl())
                .placeholder(R.drawable.face_recognition_app_icon)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return (mSpacePhotos.length);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView mPhotoImageView;

        public MyViewHolder(View itemView) {

            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

//            int position = getAdapterPosition();
//            if(position != RecyclerView.NO_POSITION) {
//                SpacePhoto spacePhoto = mSpacePhotos[position];
//                Intent intent = new Intent(mContext, SpacePhotoActivity.class);
//                intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
//                startActivity(intent);
//            }
        }
    }

    private ImageModel[] mSpacePhotos;
    private Context mContext;

    public ImageGalleryAdapter(Context context,ImageModel[] spacePhotos) {
        mContext = context;
        mSpacePhotos = spacePhotos;
    }
}




