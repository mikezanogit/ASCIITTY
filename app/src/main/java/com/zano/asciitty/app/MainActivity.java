package com.zano.asciitty.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.sql.SQLException;


public class MainActivity extends Activity implements IAsciiItemActions, IDeleteDialogActions {


    private ViewFlipper mViewFlipper;
    private AsciiArtItem mCurrentItem;
    private AsciiArtDataRepository mDataSource;

    /**
     * This is one of the first methods called when the application starts.
     * Define what layout to use and prepare some animations.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //When you create the main activity you need to set the layout to be used
        setContentView(R.layout.activity_main);

        //Create the animations that will then be applied to the view flipper
        Animation slideInLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation slideOutRight = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        //Add animations to the view flipper
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mViewFlipper.setInAnimation(slideInLeft);
        mViewFlipper.setOutAnimation(slideOutRight);
    }
    /**
     * Returns the application to a state where the list of ascii art items are displayed.
     * Called when the user is viewing the Editor and hits the "Cancel" button.
     */
    public void cancelAsciiItem() {
        mViewFlipper.showPrevious();
    }
    /**
     * Save the newly created or edited item to the sqlite database.
     * Called when the user hits the "Save" button from the editor.
     * @param item AsciiArtItem to be created or updated.
     */
    public void saveAsciiItem(AsciiArtItem item){
        mDataSource = new AsciiArtDataRepository(this);
        try {

            mDataSource.open();
            //Determine if this is an update or creation of an item.
            if (item.getId() != 0) {
                mCurrentItem = mDataSource.updateAsciiArtItem(item);
            }
            else{
                mCurrentItem =
                        mDataSource.createAsciiArtItem(item.getName(), item.getData());
            }
            mDataSource.close();

            //Now that the item is saved update the listed items with the change.
            AsciiItemsFragment items = (AsciiItemsFragment)
                    this.getFragmentManager().findFragmentById(R.id.fragmentAsciiItems);
            items.update(mCurrentItem);


        } catch (SQLException e) {
            Log.e("SQl error", e.getMessage());
        }

        //Since the save came from the editor page, switch back to the list of art items.
        mViewFlipper.showPrevious();
    }
    /**
     * Call the editor fragment and send in an item to be edited.  Bring the
     * editor into view via an animation
     *
     * @param  item AsciiArtItem to be edited.
     */
    private void showEditor(AsciiArtItem item) {
        AsciiEditorFragment editor = (AsciiEditorFragment)
                this.getFragmentManager().findFragmentById(R.id.fragmentAsciiEditor);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AsciiEditorFragment.ITEM, item);
        editor.setEditor(bundle);

        mViewFlipper.showNext();
    }
    /**
     * Prepares the editor to be displayed with a blank slate for creation of a new item.
     * Called when the user hits the "Add New" button.
     */
    @Override
    public void createAsciiItem() {
        mCurrentItem = null;
        this.showEditor(null);
    }
    /**
     * Prepare the editor to be displayed with a previously created item for editing.
     * Called when the user hits the "Edit" button of an existing item.
     *
     * @param item AsciiArtItem to be edited.
     */
    @Override
    public void editAsciiItem(AsciiArtItem item){
        mCurrentItem = item;
        this.showEditor(mCurrentItem);
    }
    @Override
    public void deleteAsciiItem(AsciiArtItem item){

        mCurrentItem = item;
        //This will create and bring up the dialog.
        DeleteDialogFragment dialog = new DeleteDialogFragment();
        dialog.show(getFragmentManager(), "DeleteDialogFragment" );

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

    @Override
    public void positiveDeleteClick() {

        mDataSource = new AsciiArtDataRepository(this);
        try {

            mDataSource.open();
            mDataSource.deleteAsciiArtItem(mCurrentItem);
            mDataSource.close();

        } catch (SQLException e) {
            Log.e("SQl error", e.getMessage());
        }

        //Now that the item is deleted go back and upate the displayed list.
        AsciiItemsFragment items = (AsciiItemsFragment)
                this.getFragmentManager().findFragmentById(R.id.fragmentAsciiItems);
        items.remove(mCurrentItem);
        mCurrentItem = null;
    }

    @Override
    public void negativeDeleteClick() {

    }
}
