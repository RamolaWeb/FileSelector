package com.ramola.fileselector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;
    public static final int PERMISSION_READ = 2;
    private TextView filePathView;
    private Button fileChooserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePathView = findViewById(R.id.filePathTextView);
        fileChooserBtn = findViewById(R.id.fileChooseButton);
        fileChooserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        showFilePicker();
                    }
                    else  {
                        String permission [] = {
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        };
                        ActivityCompat.requestPermissions(MainActivity.this, permission,  PERMISSION_READ);
                    }
                }
                else {
                    showFilePicker();
                }
            }
        });
    }

    public void showFilePicker() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    File file = new File(data.getData().getPath());
                    // Path of File you have selected
                    String filePath = file.getPath();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_READ) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showFilePicker();
            }
        }
    }
}
