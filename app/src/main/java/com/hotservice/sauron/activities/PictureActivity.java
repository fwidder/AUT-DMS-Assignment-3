package com.hotservice.sauron.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hotservice.sauron.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PictureActivity extends AppCompatActivity {
    private static final int IMAGE_GALLERY_REQUEST = 10;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profilePic = findViewById(R.id.profilePic);

        //FloatingActionButton fab = findViewById(R.id.fab);
        // fab.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {
        //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //               .setAction("Action", null).show();
        //    }
        // });
        // });
    }

    public void openGallery(View v) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = f.getPath();
        Uri data = Uri.parse(path);
        photoPicker.setDataAndType(data, "image/png");
        startActivityForResult(photoPicker, IMAGE_GALLERY_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            Uri imageUri = data.getData();
            InputStream in;
            try (InputStream inputStream = in = getContentResolver().openInputStream(Objects.requireNonNull(imageUri))) {
                Bitmap photo = BitmapFactory.decodeStream(in);
                profilePic.setImageBitmap(photo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load picture", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "An error occured", Toast.LENGTH_LONG).show();
            }
        }
    }

}
