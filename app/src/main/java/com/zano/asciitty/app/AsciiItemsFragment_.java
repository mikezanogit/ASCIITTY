package com.zano.asciitty.app;

import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mamanzan on 6/9/2014.
 */
public class AsciiItemsFragment_ extends Fragment implements SampleDataAdapterListener{

    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ascii_items, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        this.lv = (ListView) this.getActivity().findViewById(R.id.listView);
        try {
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.sample_data);



            ArrayList<Item> items = parseXML(xrp);
            SampleDataAdapter sampleData = new SampleDataAdapter(getActivity(), R.layout.fragment_ascii_item, items);
            sampleData.setmListener(this);
            lv.setAdapter(sampleData);



            //shorthand supported on higher level of Java is catch (EX1 | EX2 e)
        } catch (XmlPullParserException e) {

        } catch (IOException e) {

        }
    }

    @Override
    public void OnItemClick(View view, final Item item) {

        Button edit = (Button) view.findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) view.getContext();
                listener.onAsciiItemSelected(item);

            }
        });
    }

    private ArrayList<Item> parseXML(XmlResourceParser parser) throws XmlPullParserException, IOException
    {
        ArrayList<Item> products = null;
        int eventType = parser.getEventType();
        Item currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String nodeName = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    products = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    nodeName = parser.getName();
                    if (nodeName.equals("item")) {
                        currentProduct = new Item();
                        int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        parser.next();
                        String name = parser.nextText();
                        parser.next();
                        String data = parser.nextText();
                        currentProduct.id = id;
                        currentProduct.name = name;
                        currentProduct.data = data;
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
