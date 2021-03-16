package com.example.cs492finalproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {
    private static final int pic_id = 111;

    Button camera_open_id;
    ImageView click_image_id;
    private Toast errorToast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_pic_share:
                sharePic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sharePic(){
        String baseUrl = "http://mobile.twitter.com";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");

        try{
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            if(this.errorToast != null){
                this.errorToast.cancel();
            }
            this.errorToast = Toast.makeText(
                    this,
                    getString(R.string.search_action_error),
                    Toast.LENGTH_LONG
            );
            this.errorToast.show();
        }
    }

}
