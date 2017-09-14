package com.example.gaurav_jaiswal.flickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolbar(true);


        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);


        if (photo != null) {

            TextView photoTitle = (TextView) findViewById(R.id.photo_title);

            Resources resources = getResources();
            String text = resources.getString(R.string.photo_tite_text, photo.getTitle());
            photoTitle.setText(text);


            // photoTitle.setText("Title: "+photo.getTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            //photoTags.setText("Tags : "+photo.getTags());

            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText(photo.getAuthor());


            Picasso.with(this).load(photo.getLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);


        }

//
//        BitmapDrawable drawable = (BitmapDrawable) photoImage.getDrawable();
//        Bitmap result = drawable.getBitmap();
//        Log.d("photoimage", "onClick: bitmap is " + result.getByteCount());
//
//
//        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
//
//        try {
//            wallpaperManager.setBitmap(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//



    }

}
