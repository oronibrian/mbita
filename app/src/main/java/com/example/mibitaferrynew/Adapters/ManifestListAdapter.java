package com.example.mibitaferrynew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mibitaferrynew.Model.Manifests;
import com.example.mibitaferrynew.R;

import java.util.ArrayList;
import java.util.List;

public class ManifestListAdapter extends ArrayAdapter<Manifests> {
    private List<Manifests> manifestsList = new ArrayList<>();

    static class ViewHolder {
        TextView typeItem;
        TextView numberItem;
        TextView amountItem;
    }
    public ManifestListAdapter(Context context,  ArrayList<Manifests> packages) {
        super(context, 0,packages);
    }
    @Override
    public void add(Manifests object) {
        manifestsList.add(object);
        super.add(object);
    }
    @Override
    public int getCount() {
        return this.manifestsList.size();
    }

    @Override
    public Manifests getItem(int index) {
        return this.manifestsList.get(index);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ManifestListAdapter.ViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.manifest_list_item, parent, false);

            viewHolder = new ManifestListAdapter.ViewHolder();
            viewHolder.typeItem = row.findViewById(R.id.type_list_item);
            viewHolder.numberItem = row.findViewById(R.id.number_list_item);
            viewHolder.amountItem = row.findViewById(R.id.amount_list_item);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ManifestListAdapter.ViewHolder)row.getTag();
        }
        Manifests textview = getItem(position);
        viewHolder.typeItem.setText(textview.getTypes());
        viewHolder.numberItem.setText(textview.getNumber());
        viewHolder.amountItem.setText(textview.getAmount());
        return row;
    }
}