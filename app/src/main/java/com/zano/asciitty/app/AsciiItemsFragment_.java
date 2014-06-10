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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamanzan on 6/9/2014.
 */
public class AsciiItemsFragment_ extends Fragment implements SampleDataAdapterListener{

    private ListView lv;
    private AsciiArtDataSource dataSource;


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

            ArrayList<AsciiArtItem> items = parseXML(xrp);
            List<AsciiArtItem> values = null;

            this.dataSource = new AsciiArtDataSource(this.getActivity());
            try {
                this.dataSource.open();
                values = this.dataSource.getAllAsciiArtItems();

                this.dataSource.close();
            }
            catch(SQLException e) {

            }

            SampleDataAdapter sampleData = new SampleDataAdapter(getActivity(), R.layout.fragment_ascii_item, (ArrayList<AsciiArtItem>) values);
            sampleData.setmListener(this);
            lv.setAdapter(sampleData);

            //shorthand supported on higher level of Java is catch (EX1 | EX2 e)
        } catch (XmlPullParserException e) {

        } catch (IOException e) {

        }




        Button add = (Button) this.getActivity().findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) view.getContext();
                listener.onAsciiCreateNewItem();
            }
        });
    }

    public void CreateNewAsciiItem() {

    }

    @Override
    public void OnItemClick(View view, final AsciiArtItem item) {

        Button edit = (Button) view.findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) view.getContext();
                listener.onAsciiItemSelected(item);

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
