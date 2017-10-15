package com.example.kolin.fintechhomework3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FirstSecondFragment.OnFirstFragmentListener,
        ThirdFragment.OnThirdFragmentListener {

    private static final int ID_CONTAINER = R.id.main_activity_fragment_container;
    private static final String KEY_NAV = "key_nav_selected";
    private static final String KEY_NAV_ENABLE = "key_nav_enable";
    private static final String KEY_FIRST_RESULT = "key_first_result";
    private static final String KEY_SECOND_RESULT = "key_second_result";
    private static final String KEY_THIRD_RESULT = "key_third_result";
    private static final String KEY_WORK_ARRAY = "key_work_array";


    private BottomNavigationView navigation;

    private int currentNavSelectedId;

    private String resultFirstFragment = null;
    private String resultSecondFragment = null;
    private int resultThirdFragment = -1;
    //work array to save steps that user have done yet
    private boolean[] workArrayEnable = null;

    private boolean blockOnNavItemListener = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            if (currentNavSelectedId == item.getItemId())
                return false;

            currentNavSelectedId = item.getItemId();

            if (blockOnNavItemListener)
                return true;

            Fragment fragment = null;
            String tag = null;

            switch (item.getItemId()) {

                case R.id.navigation_first:
                    tag = FirstSecondFragment.TAG + "1";
                    if ((fragment = getFragmentByTag(tag)) == null)
                        fragment = FirstSecondFragment.newInstance(1);
                    break;

                case R.id.navigation_second:
                    tag = FirstSecondFragment.TAG + "2";
                    if ((fragment = getFragmentByTag(tag)) == null)
                        fragment = FirstSecondFragment.newInstance(2);
                    break;

                case R.id.navigation_third:
                    tag = ThirdFragment.TAG;
                    if ((fragment = getFragmentByTag(tag)) == null)
                        fragment = ThirdFragment.newInstance();
                    break;

                case R.id.navigation_result:
                    tag = ResultFragment.TAG;
                    fragment = ResultFragment.newInstance(
                            resultFirstFragment,
                            resultSecondFragment,
                            resultThirdFragment
                    );
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(ID_CONTAINER, fragment, tag)
                    .addToBackStack(null)
                    .commit();

            return true;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null)
            navigation.setSelectedItemId(R.id.navigation_first);
        else {
            navigation.setSelectedItemId(savedInstanceState.getInt(KEY_NAV));

            boolean[] enableArray = savedInstanceState.getBooleanArray(KEY_NAV_ENABLE);
            for (int i = 0; i < enableArray.length; i++) {
                navigation.getMenu().getItem(i).setEnabled(enableArray[i]);
            }

            resultFirstFragment = savedInstanceState.getString(KEY_FIRST_RESULT);
            resultSecondFragment = savedInstanceState.getString(KEY_SECOND_RESULT);
            resultThirdFragment = savedInstanceState.getInt(KEY_THIRD_RESULT);

            workArrayEnable = savedInstanceState.getBooleanArray(KEY_WORK_ARRAY);
        }

    }

    private Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putInt(KEY_NAV, currentNavSelectedId);
        outState.putBooleanArray(KEY_NAV_ENABLE, getEnableMenuItems());

        outState.putString(KEY_FIRST_RESULT, resultFirstFragment);
        outState.putString(KEY_SECOND_RESULT, resultSecondFragment);
        outState.putInt(KEY_THIRD_RESULT, resultThirdFragment);
        outState.putBooleanArray(KEY_WORK_ARRAY, workArrayEnable);
    }

    private boolean[] getEnableMenuItems() {
        boolean[] enableArray = new boolean[4];

        for (int i = 0; i < enableArray.length; i++) {
            enableArray[i] = navigation.getMenu().getItem(i).isEnabled();
        }
        return enableArray;
    }

    private void returnEnableMenuItem(boolean[] workArrayEnable) {
        for (int i = 0; i < workArrayEnable.length; i++) {
            navigation.getMenu().getItem(i).setEnabled(workArrayEnable[i]);
        }
    }


    @Override
    public void onTextResultFirstSecond(String text, int type) {
        switch (type) {
            case 1:
                if (text == null) {
                    //save steps of user
                    if (workArrayEnable == null)
                        workArrayEnable = getEnableMenuItems();

                    disableMenuItem(1, 2, 3);
                } else {
                    this.resultFirstFragment = text;

                    //if user have not steps , we continue with default next step
                    if (workArrayEnable == null)
                        enableMenuItem(1);
                        //else restore steps of user with saved fragments
                    else
                        returnEnableMenuItem(workArrayEnable);

                    workArrayEnable = null;
                }
                break;
            case 2:
                if (text == null) {
                    if (workArrayEnable == null)
                        workArrayEnable = getEnableMenuItems();

                    disableMenuItem(2, 3);
                } else {
                    this.resultSecondFragment = text;

                    if (workArrayEnable == null)
                        enableMenuItem(2);
                    else
                        returnEnableMenuItem(workArrayEnable);

                    workArrayEnable = null;
                }
                break;
        }
    }

    public void enableMenuItem(int i) {
        navigation.getMenu().getItem(i).setEnabled(true);
    }

    public void disableMenuItem(int i) {
        navigation.getMenu().getItem(i).setEnabled(false);
    }

    public void disableMenuItem(int... i) {
        for (int ii : i)
            disableMenuItem(ii);
    }

    @Override
    public void onClickOkFirstSecond(int type) {
        if (type == 1)
            navigation.setSelectedItemId(R.id.navigation_second);
        else
            navigation.setSelectedItemId(R.id.navigation_third);
    }

    @Override
    public void onCheckOperationThird(int num) {
        if (num != -1) {
            this.resultThirdFragment = num;
            enableMenuItem(3);
        } else
            disableMenuItem(3);
    }

    @Override
    public void onClickOkThird() {
        navigation.setSelectedItemId(R.id.navigation_result);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        supportFinishAfterTransition();
    }
}
