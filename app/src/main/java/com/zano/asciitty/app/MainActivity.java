package com.zano.asciitty.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.sql.SQLException;


public class MainActivity extends Activity implements OnAsciiItemSelectionListener {


    ViewFlipper viewFlipper;
    Animation   slide_in_left, slide_out_right;
    public AsciiArtItem mCurrentItem;
    public AsciiArtDataSource dataSource;

    public void onAsciiEditorCancel() {
        viewFlipper.showPrevious();
    }

    public void onAsciiEditorSave(AsciiArtItem item)
    {


        this.dataSource = new AsciiArtDataSource(this);
        try {
            this.dataSource.open();
            if (item.getId() != 0) {
                this.dataSource.updateAsciiArtItem(item);
            }
            else{
                item = this.dataSource.createAsciiArtItem(item.getName(), item.getData());
            }

            this.dataSource.close();

            AsciiItemsFragment_ items = (AsciiItemsFragment_)
                    this.getFragmentManager().findFragmentById(R.id.fragmentAsciiItems);

            items.update(item);

        } catch (SQLException e) {
        }



        viewFlipper.showPrevious();
    }


    public void ShowEditor(AsciiArtItem item) {
        AsciiEditorFragment editor = (AsciiEditorFragment)
                this.getFragmentManager().findFragmentById(R.id.fragmentAsciiEditor);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AsciiEditorFragment.ITEM, item);
        editor.setEditor(bundle);

        viewFlipper.showNext();
    }

    @Override
    public void onAsciiCreateNewItem() {
        this.ShowEditor(null);
    }

    @Override
    public void onAsciiItemSelected(AsciiArtItem item){
        mCurrentItem = item;
        this.ShowEditor(mCurrentItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        slide_in_left = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(slide_in_left);
        viewFlipper.setOutAnimation(slide_out_right);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
}
