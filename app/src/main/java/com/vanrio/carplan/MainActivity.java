package com.vanrio.carplan;

import java.sql.SQLException;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener{

    Button newButton;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        newButton = (Button)findViewById(R.id.newButton);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.newButton){
            startActivity(new Intent(MainActivity.this, SearchGroupsActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }

            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{

        Button newButton;
        LinearLayout groupButtonLayout;
        UserDB uData;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.groups_my, container, false);
            groupButtonLayout = (LinearLayout)getActivity().findViewById(R.id.group_button_layout);

            String username = getActivity().getIntent().getStringExtra("my_username");

            uData = new UserDB(getActivity());
            Cursor cursor;
            try {
                uData.open();
                cursor = uData.query(username);

                if (cursor.moveToFirst()) {
                    cursor.moveToFirst();

                    String groupsArrayString = cursor.getString(7);

                    if(!groupsArrayString.equals("-X-")){
                        String[] groupsArray = UserDB.convertStringToArray(groupsArrayString);

                        for(String groupname : groupsArray){
                            Button groupButton = new Button(getActivity());
                            groupButton.setWidth(1000);
                            groupButton.setHeight(400);
                            groupButton.setBackgroundResource(R.drawable.add_button);
                            groupButton.setText(groupname+"");
                            groupButton.setGravity(0x05);
                            groupButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), newGroupActivity.class);
                                    i.putExtra("button_visiblity", 1);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                    cursor.close();
                }
            }catch(SQLException e){
                System.out.println("Ohno SQL");
            }



            newButton = (Button)rootView.findViewById(R.id.newButton);
            newButton.setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onClick(View v){
            if(v.getId() == R.id.newButton){
                Intent i = new Intent(getActivity(), FindGroupsActivity.class);
                i.putExtra("my_username", getActivity().getIntent().getStringExtra("my_username"));
                startActivity(i);
            }
        }
    }

}
