package com.example.cs492finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {
    private static final int pic_id = 111;

    Button camera_open_id;
    ImageView click_image_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        this.camera_open_id = (Button)findViewById(R.id.camera_button);
        this.click_image_id = (ImageView)findViewById(R.id.click_photo);

        camera_open_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap)data.getExtras().get("data");

            click_image_id.setImageBitmap(photo);
            MediaStore.Images.Media.insertImage(getContentResolver(), photo, "photo" , "photo");
        }
    }
}
