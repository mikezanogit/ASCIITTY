package com.zano.asciitty.app;

import android.app.Dialog;
import android.app.Fragment;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamanzan on 6/9/2014.
 */
public class AsciiItemsFragment extends Fragment implements IAsciiArtDataAdapterActions {

    private AsciiArtDataAdapter mAsciiArtDataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ascii_items, container, false);
    }
    /**
     * Remove an item from the list.  Called after user taps the "Delete" buttom.
     * @param item AsciiArtItem to be removed.
     */
    public void remove(AsciiArtItem item) {
        mAsciiArtDataAdapter.remove(item);
    }
    /**
     * Update the collection by either adding a new item to the displayed list
     * or by actually updating an existing item
     * @param item  AsciiArtItem to be updated.
     */
    public void update(AsciiArtItem item) {

        int index = mAsciiArtDataAdapter.indexOf(item);

        if(index < 0) { //Item doesn't exist.
            mAsciiArtDataAdapter.add(item);
        }
        else { //Item exists.
            mAsciiArtDataAdapter.update(item, index);
        }
    }
    /**
     * Starts up the fragment by accessing the SQLite database to pull the list
     * of ascii art items to display.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //Create Delete Dialog



        List<AsciiArtItem> values = null;

        //Grab the ascii art items from the database.
        try {
            AsciiArtDataRepository dataSource;
            dataSource = new AsciiArtDataRepository(this.getActivity());
            dataSource.open();
            values = dataSource.getAllAsciiArtItems();
            dataSource.close();
        }
        catch(SQLException e) {
            Log.e("SQL error", e.getMessage());
        }

        //Start up the adapter with the list of art items.
        mAsciiArtDataAdapter = new AsciiArtDataAdapter(getActivity(), R.layout.fragment_ascii_item, (ArrayList<AsciiArtItem>) values);
        mAsciiArtDataAdapter.setmDataAdapterActions(this);

        //Connect the view with data
        ListView listView = (ListView) this.getActivity().findViewById(R.id.listView);
        listView.setAdapter(mAsciiArtDataAdapter);


        Button add = (Button) this.getActivity().findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IAsciiItemActions actions = (IAsciiItemActions) view.getContext();
                actions.createAsciiItem();
            }
        });
    }



    @Override
    public void OnItemClick(View view, final AsciiArtItem item) {

        Button edit = (Button) view.findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IAsciiItemActions actions = (IAsciiItemActions) view.getContext();
                actions.editAsciiItem(item);

            }
        });

        Button delete = (Button) view.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IAsciiItemActions actions = (IAsciiItemActions) view.getContext();
                actions.deleteAsciiItem(item);
            }
        });
    }

    private ArrayList<AsciiArtItem> parseXML(XmlResourceParser parser) throws XmlPullParserException, IOException
    {
        ArrayList<AsciiArtItem> products = null;
        int eventType = parser.getEventType();
        AsciiArtItem currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String nodeName = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    products = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    nodeName = parser.getName();
                    if (nodeName.equals("item")) {
                        currentProduct = new AsciiArtItem();
                        int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        parser.next();
                        String name = parser.nextText();
                        parser.next();
                        String data = parser.nextText();
                        currentProduct.setId(id);
                        currentProduct.setName(name);
                        currentProduct.setData(data);
                    }
//                    if (name == "product"){
//                        currentProduct = new Item();
//                    } else if (currentProduct != null){
//                        if (name == "name"){
//                            currentProduct.name = parser.nextText();
//                        } else if (name == "data"){
//                            currentProduct.data = parser.nextText();
//                        }
//                    }
                    break;
                case XmlPullParser.END_TAG:
                    nodeName = parser.getName();
                    if (nodeName.equalsIgnoreCase("item") && currentProduct != null){
                        products.add(currentProduct);
                    }
            }
            eventType = parser.next();
        }

        return products;
        //printProducts(products);
    }
}
