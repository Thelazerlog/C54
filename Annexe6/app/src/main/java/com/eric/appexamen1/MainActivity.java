package com.eric.appexamen1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Ecouteur ec;
    Button bouton;
    ActivityResultLauncher lanceur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        bouton = findViewById(R.id.bouton);

        ec = new Ecouteur();
        bouton.setOnClickListener(ec);

        lanceur = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new CallBackImage()
        );
    }

    private class CallBackImage implements ActivityResultCallback<ActivityResult>{

        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                Uri uri= result.getData().getData();
                imageView.setImageURI(uri);
            }
        }
    }
    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*"); //seulment fichiers images
            lanceur.launch(i);
        }
    }

}