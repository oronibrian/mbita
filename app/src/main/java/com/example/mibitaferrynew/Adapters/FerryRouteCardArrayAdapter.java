package com.example.mibitaferrynew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mibitaferrynew.Model.Routes;
import com.example.mibitaferrynew.R;

import java.util.ArrayList;
import java.util.List;

public class FerryRouteCardArrayAdapter  extends ArrayAdapter<Routes> {
    private static final String TAG = "CardArrayAdapter";
    private List<Routes> cardList = new ArrayList<Routes>();

    static class CardViewHolder {
        TextView line1;
    }

    public FerryRouteCardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    @Override
    public void add(Routes object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Routes getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Routes card = getItem(position);
        viewHolder.line1.setText(card.getName());
        return row;
    }


}

