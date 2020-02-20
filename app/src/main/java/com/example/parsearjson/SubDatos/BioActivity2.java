package com.example.parsearjson.SubDatos;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parsearjson.R;


public class BioActivity2 extends AppCompatActivity {

    TextView Title,Date,Pdf;
    ImageView Imagen;
    Button btnDescargar,btnBuscarPortada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio2);

        String title=(getIntent().getExtras().getString("curTitle"));
        String volum=(getIntent().getExtras().getString("curDate"));
        final String Pdf=(getIntent().getExtras().getString("curPdf"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Title= (TextView) findViewById(R.id.txtTitleBio);
        Date=(TextView) findViewById(R.id.txtDateBio);
        Imagen=(ImageView)findViewById(R.id.txtImagenBio);
        btnDescargar=findViewById(R.id.BtnDescargar);
        btnBuscarPortada=findViewById(R.id.BtnCargarPortada);



        Title.setText(title);
        Date.setText(volum);
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(Pdf));
                request.setDescription("PDF Paper");
                request.setTitle("Pdf Artcilee");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.pdf");
                DownloadManager manager = (DownloadManager) BioActivity2.this.getSystemService(Context.DOWNLOAD_SERVICE);
                try {
                    manager.enqueue(request);        }
                catch (Exception e) {
                    Toast.makeText(BioActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getPermission(String permission){

        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }
}
