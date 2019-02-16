package com.example.barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    TextView barcodeResult;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeResult = (TextView)findViewById(R.id.barcodeResult);
    }

    public void scanBarcode(View v) {
        Intent intent = new Intent(this, scanBarcodeActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult (int request, int result, Intent data) {

        if (request == 0) {
            if (result == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeResult.setText(barcode.displayValue);
                    barcodeResult.setTextColor(Color.BLACK);


                } else {
                    barcodeResult.setText("No Barcode Detected!");
                    barcodeResult.setTextColor(Color.RED);

                }
            }

        } else {
            super.onActivityResult(request, result, data);
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();


            } else {

                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}
}
