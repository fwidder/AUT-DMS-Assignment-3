package com.hotservice.sauron.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.utils.UserHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private int REQUEST_PICTURE = 1;
    private int TAKE_PICTURE = 2;
    private Button newPic;
    private Button selectPic;
    private Bitmap mdpi;
    private Bitmap hdpi;
    private Bitmap xhdpi;
    private Bitmap xxhdpi;
    private Bitmap xxxhdpi;

    public static Bitmap scaleBitmap(Bitmap bm, int side) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) side) / width;
        float scaleHeight = ((float) side) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profilePic = findViewById(R.id.profilePic);
        newPic = findViewById(R.id.newPic);
        selectPic = findViewById(R.id.selectPic);
        Button btSave = findViewById(R.id.SaveProfile);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Group.getInstance().getMe().setMobileNumber(((EditText) findViewById((R.id.phoneNum))).getText().toString());
                    Group.getInstance().getMe().setName(((EditText) findViewById((R.id.editName))).getText().toString());
                    Group.getInstance().getMe().setSmsState(((Switch) findViewById(R.id.UnlimitedSwitch)).isChecked());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String filename = "sauron.config";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(new UserHelper().toBytes(Group.getInstance().getMe()));
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                filename = "sauron.picture";
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    xxxhdpi.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    outputStream.write(byteArray);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(ProfileActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });


        try {
            ((EditText) findViewById((R.id.phoneNum))).setText(Group.getInstance().getMe().getMobileNumber());
            ((EditText) findViewById((R.id.editName))).setText(Group.getInstance().getMe().getName());
            ((Switch) findViewById((R.id.UnlimitedSwitch))).setChecked(Group.getInstance().getMe().getSmsState());
            String filename = "sauron.picture";
            FileInputStream inputStream;
            byte[] targetArray = null;
            try {
                inputStream = openFileInput(filename);
                targetArray = new byte[inputStream.available()];
                inputStream.read(targetArray);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            if (targetArray != null && targetArray.length > 0)
                bitmap = BitmapFactory.decodeByteArray(targetArray, 0, targetArray.length);
            if (bitmap != null)
                ((ImageView) findViewById(R.id.profilePic)).setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        newPic.setVisibility(View.VISIBLE);
        selectPic.setVisibility(View.VISIBLE);
    }

    public void openGallery(View v) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = f.getPath();
        Uri data = Uri.parse(path);
        photoPicker.setDataAndType(data, "image/png");
        startActivityForResult(photoPicker, REQUEST_PICTURE);
    }

    public void takePicture(View v) {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeIntent, TAKE_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE) {
            Uri imageUri = data.getData();
            InputStream in;
            try {
                in = getContentResolver().openInputStream(Objects.requireNonNull(imageUri));
                Bitmap photo = BitmapFactory.decodeStream(in);
                getSizes(photo);
                profilePic.setImageBitmap(xxxhdpi);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Unable to load picture", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == TAKE_PICTURE) {
            Bundle extras = data.getExtras();
            Bitmap photoBitmap = (Bitmap) Objects.requireNonNull(extras).get("data");
            getSizes(photoBitmap);
            profilePic.setImageBitmap(xxhdpi);
        }
        newPic.setVisibility(View.INVISIBLE);
        selectPic.setVisibility(View.INVISIBLE);
    }

    public void getSizes(Bitmap bitmap) {
        mdpi = scaleBitmap(squareBitmap(bitmap), 48);
        hdpi = scaleBitmap(squareBitmap(bitmap), 72);
        xhdpi = scaleBitmap(squareBitmap(bitmap), 96);
        xxhdpi = scaleBitmap(squareBitmap(bitmap), 144);
        xxxhdpi = scaleBitmap(squareBitmap(bitmap), 192);
    }

    public Bitmap squareBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        return Bitmap.createBitmap(
                bm, 0, (height - width) / 2, width, width);
    }


}
