package com.zano.asciitty.app;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsciiEditorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsciiEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AsciiEditorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ITEM = "item";


    private  AsciiArtItem asciiItem;

    private OnFragmentInteractionListener mListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ascii_editor, container, false);
    }

    public void setEditor(Bundle args) {
        this.asciiItem = args.getParcelable(AsciiEditorFragment.ITEM);
        Activity activity = this.getActivity();
        TextView textView = (TextView) activity.findViewById(R.id.editText);
        if (this.asciiItem != null) {
            textView.setText(this.asciiItem.getData());
        }
        else{
            textView.setText("");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        Button cancel = (Button) activity.findViewById(R.id.buttonCancel);
        Button save = (Button) activity.findViewById(R.id.buttonSave);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) activity;
                listener.onAsciiEditorCancel();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAsciiItem();
            }
        });
    }

    public void SaveAsciiItem() {
        OnAsciiItemSelectionListener listener = (OnAsciiItemSelectionListener) this.getActivity();
        listener.onAsciiEditorSave(this.asciiItem);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
