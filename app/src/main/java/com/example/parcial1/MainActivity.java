package com.example.parcial1;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mEdtNombre, mEdtApellido, mEdtNumero;
    Button mBtnAdd, mBtnList;
    ImageView mImageView;

   // final int REQUEST_CODE_GALLERY = 999;
    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("new record");
        mEdtNombre = findViewById(R.id.edtNombre);
        mEdtApellido = findViewById(R.id.edtApellido);
        mEdtNumero = findViewById(R.id.edtNumero);
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnList = findViewById(R.id.btnList);
       // mImageView = findViewById(R.id.cropImageView);

        mSQLiteHelper = new SQLiteHelper(this,"RECORDDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR, apellido VARCHAR, numero VARCHAR)");


        //Seleccioana imagen
 /*       mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });*/

        //Agrega el recorrido de sqlite
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mSQLiteHelper.insertData(
                            mEdtNombre.getText().toString().trim(),
                            mEdtApellido.getText().toString().trim(),
                            mEdtNumero.getText().toString().trim()
                    );
                    Toast.makeText(MainActivity.this,"Agregado correctamente", Toast.LENGTH_SHORT).show();
                    mEdtNombre.setText("");
                    mEdtApellido.setText("");
                    mEdtNumero.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //mira la lista de contactos
        mBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RecordListActivity.class));
            }
        });
    }
    /*
    @Override
    public void onRequestPermissionsResult (int requiestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requiestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //galeria
            }
            else{
                Toast.makeText(this, "No tienes permiso pata acceder a los archivos locales", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requiestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
