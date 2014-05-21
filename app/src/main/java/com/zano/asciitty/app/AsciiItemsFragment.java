package com.zano.asciitty.app;

import android.app.ListFragment;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import java.util.ArrayList;

/**
 * Created by mamanzan on 5/21/2014.
 */
public class AsciiItemsFragment extends ListFragment {
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) getActivity();
        listener.onAsciiItemSelected(position);
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)  {


        super.onActivityCreated(savedInstanceState);

        //XmlPullParserFactory parserFactory;
        try {
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.sample_data);

//          parserFactory = XmlPullParserFactory.newInstance();
//          XmlPullParser parser = parserFactory.newPullParser();
//          InputStream in_s = getApplicationContext().getAssets().open("temp.xml");
//          parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//          parser.setInput(in_s, null);

            ArrayList<Item> items = parseXML(xrp);
            SampleDataAdapter sampleData = new SampleDataAdapter(getActivity(), R.layout.fragment_ascii_items, items);
            setListAdapter(sampleData);

            //shorthand supported on higher level of Java is catch (EX1 | EX2 e)
        } catch (XmlPullParserException e) {

        } catch (IOException e) {

        }
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
                        parser.next();
                        String name = parser.nextText();
                        parser.next();
                        String data = parser.nextText();
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
