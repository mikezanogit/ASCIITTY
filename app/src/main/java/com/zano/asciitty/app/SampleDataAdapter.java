package com.zano.asciitty.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mamanzan on 5/21/2014.
 * http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
 */
public class SampleDataAdapter extends ArrayAdapter<Item> {
    private Context context;

    public SampleDataAdapter(Context context, int layoutId, ArrayList<Item> items){
        super(context,layoutId,items);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ( LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_ascii_items, null);
        }

        Item item  = this.getItem(position);
        if(item != null){
            TextView itemView = (TextView) view.findViewById(R.id.textViewAsciiName);
            itemView.setText(item.name);
        }

        return view;
    }

}
