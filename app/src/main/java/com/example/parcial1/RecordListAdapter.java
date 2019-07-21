package com.example.parcial1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> recordList;


    public RecordListAdapter(Context context, int layout, ArrayList<Model> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }


    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtNombre, txtApellido, txtNumero;
    }

    @Override
    public View getView(int i, View view , ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtNombre = row.findViewById(R.id.txtNombre);
            holder.txtApellido = row.findViewById(R.id.txtApellido);
            holder.txtNumero = row.findViewById(R.id.txtNumero);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder)row.getTag();
        }
        Model model = recordList.get(i);

        holder.txtNombre.setText(model.getNombre());
        holder.txtApellido.setText(model.getApellido());
        holder.txtNumero.setText(model.getNumero());


        return row;
    }
}
