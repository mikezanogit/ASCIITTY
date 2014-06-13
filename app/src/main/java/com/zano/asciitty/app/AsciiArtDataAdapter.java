package com.zano.asciitty.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamanzan on 5/21/2014.
 * http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
 */


public class AsciiArtDataAdapter extends ArrayAdapter<AsciiArtItem> {

    private List<AsciiArtItem> values;
    private Context context;
    private IAsciiArtDataAdapterActions mDataAdapterActions;

    public IAsciiArtDataAdapterActions getmDataAdapterActions() {
        return mDataAdapterActions;
    }

    public void setmDataAdapterActions(IAsciiArtDataAdapterActions mListener) {
        this.mDataAdapterActions = mListener;
    }



    @Override
    public void add(AsciiArtItem object) {
        //this.values.add(object);
        super.add(object);
    }

    @Override
    public void remove(AsciiArtItem object) {
       //this.values.remove(object);
        super.remove(object);
    }

    public AsciiArtDataAdapter(Context context, int layoutId, ArrayList<AsciiArtItem> items){


        super(context,layoutId,items);
        //values now references items, its not a copy, thats why they both are updated.
        this.values = items;
        this.context = context;
    }

    public int indexOf(AsciiArtItem item) {
        return this.values.indexOf(item);
    }

    public void update(AsciiArtItem updated, int index) {

        this.values.set(index, updated);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            ///LayoutInflater inflater = ( LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.fragment_ascii_item, parent, false);
        }

        //AsciiArtItem item  = this.getItem(position);
        AsciiArtItem item = this.values.get(position);
        if(item != null){
            TextView itemView = (TextView) view.findViewById(R.id.textViewAsciiName);
            itemView.setText(item.getName());
        }

        mDataAdapterActions.OnItemClick(view, item);



        return view;
    }

}
