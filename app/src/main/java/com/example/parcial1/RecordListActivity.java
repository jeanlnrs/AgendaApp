package com.example.parcial1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        mListView = findViewById(R.id.listView);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);

        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String numero = cursor.getString(3);
            mList.add(new Model(id,nombre,apellido,numero));
        }
        mAdapter.notifyDataSetChanged();
        if(mList.size()==0){
            Toast.makeText(this, "No se encontraron contactos...",Toast.LENGTH_SHORT).show();
        }
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                CharSequence[] items = {"Actualizar", "Eliminar"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordListActivity.this);
                dialog.setTitle("Elige una accion");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==0){
                            Cursor c = MainActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            //update
                            showDialogUpdate(RecordListActivity.this, arrID.get(i));
                        }
                        if (i==1){
                            //eliminar
                            Cursor c = MainActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(i));
                        }
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    private void showDialogUpdate(Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_data);
        dialog.setTitle("Actualizar");

        final EditText edtNombre = dialog.findViewById(R.id.edtNombre);
        final EditText edtApellido = dialog.findViewById(R.id.edtApellido);
        final EditText edtNumero = dialog.findViewById(R.id.edtNumero);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT * FROM RECORD WHERE id="+position);
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            edtNombre.setText(nombre);
            String apellido = cursor.getString(2);
            edtApellido.setText(apellido);
            String numero = cursor.getString(3);
            edtNumero.setText(numero);

            mList.add(new Model(id,nombre,apellido,numero));
        }
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    MainActivity.mSQLiteHelper.updateData(
                            edtNombre.getText().toString().trim(),
                            edtApellido.getText().toString().trim(),
                            edtNumero.getText().toString().trim(),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Actualizacion exitosa",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Error en actualizacion",error.getMessage());
                }
                updateRecordList();{

                }
            }

        });
    }
    private void updateRecordList() {
        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String numero = cursor.getString(3);

            mList.add(new Model(id,nombre,apellido,numero));
        }
        mAdapter.notifyDataSetChanged();
    }
    private void showDialogDelete(final Integer idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordListActivity.this);
        dialogDelete.setTitle("Precaucion");
        dialogDelete.setMessage("Estas seguro que desas eliminar un contacto");
        dialogDelete.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                try{
                    MainActivity.mSQLiteHelper.delateData(idRecord);
                    Toast.makeText(RecordListActivity.this,"Eliminacion correcta",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }
}
