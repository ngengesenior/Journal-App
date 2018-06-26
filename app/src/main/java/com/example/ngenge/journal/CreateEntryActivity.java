package com.example.ngenge.journal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEntryActivity extends AppCompatActivity {
    @BindView(R.id.editTextEntry)
    EditText editTextEntry;

    @BindView(R.id.editTextEntryTitle)
    EditText editTextEntryTitle;

    @BindView(R.id.imageViewEntryPhoto)
    ImageView entryPhoto;

    @BindView(R.id.editTextTags)
    EditText editTextTags;

    @BindView(R.id.root)
    ConstraintLayout root;
    private int IMAGE_PERMISSION_REQUEST_CODE = 233;

    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        ButterKnife.bind(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_select_image:
                selectImage();
                return true;
            case R.id.action_done:
                submitEntry();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                openImage();
            } else {
                //permission denied
                String[] permissionRequest = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissionRequest, IMAGE_PERMISSION_REQUEST_CODE);
            }
        } else {
            //we are on device apis lower than version 23
            openImage();
        }
    }

    private void openImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, getString(R.string.str_select_image)),
                IMAGE_PERMISSION_REQUEST_CODE);

    }


    private void submitEntry() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IMAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImage();
            } else {
                showSnackbar(getString(R.string.str_could_not_select));

            }
        }
    }


    private void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PERMISSION_REQUEST_CODE
                && resultCode == RESULT_OK && data.getData() != null) {
            imageUri = data.getData();
            entryPhoto.setImageURI(imageUri);
        }
    }
}
