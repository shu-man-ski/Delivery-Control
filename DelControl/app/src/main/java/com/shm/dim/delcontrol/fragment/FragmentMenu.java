package com.shm.dim.delcontrol.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.activity.AboutApplicationActivity;
import com.shm.dim.delcontrol.service.LocationService;

public class FragmentMenu extends Fragment {

    private TextView mCourierName;

    private Spinner mCourierStatus;

    private Switch mLocationTracking;

    private LinearLayout mEditAccountMenuItem;

    private LinearLayout mLogoutOfAccountMenuItem;

    private LinearLayout mAboutApplicationMenuItem;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_NAME = "AССOUNT_NAME";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        initMenuItems(view);
        initCourierName();
        initCourierStatus(view);
        initLocationTrackingSwitch(view);
    }

    private void initMenuItems(View view) {
        mCourierName = view.findViewById(R.id.courier_name);
        mEditAccountMenuItem = view.findViewById(R.id.edit_account_menu_item);
        mLogoutOfAccountMenuItem = view.findViewById(R.id.log_out_of_account_menu_item);
        mAboutApplicationMenuItem = view.findViewById(R.id.about_application_menu_item);
        setMenuItemsClickListener();
    }

    private void setMenuItemsClickListener() {
        mEditAccountMenuItem.setOnClickListener(getMenuItemClickListener());
        mLogoutOfAccountMenuItem.setOnClickListener(getMenuItemClickListener());
        mAboutApplicationMenuItem.setOnClickListener(getMenuItemClickListener());
    }

    private View.OnClickListener getMenuItemClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClickAnimation(view);
                onMenuItemClick(view);
            }
        };
    }

    private void startClickAnimation(View view) {
        Animation itemClick = AnimationUtils.loadAnimation(getContext(), R.anim.menu_item_click);
        view.startAnimation(itemClick);
    }

    private void onMenuItemClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.edit_account_menu_item : {
                //intent = new Intent(getActivity(), NewActivity.class);
            } break;
            case R.id.log_out_of_account_menu_item :{
                //intent = new Intent(getActivity(), NewActivity.class);
            } break;
            case R.id.about_application_menu_item : {
                intent = new Intent(getActivity(), AboutApplicationActivity.class);
            } break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    private void initCourierName() {
        mSharedPreferences = this.getActivity().getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        String courierName = mSharedPreferences.getString(AССOUNT_NAME, "");
        mCourierName.setText(courierName);
    }

    private void initCourierStatus(View view) {
        mCourierStatus = view.findViewById(R.id.courier_status);
        setCourierStatusSelectedListener();
    }

    private void initLocationTrackingSwitch(View view) {
        mLocationTracking = view.findViewById(R.id.switch_location_tracking);
    }

    private void setCourierStatusSelectedListener() {
        mCourierStatus.setOnItemSelectedListener(getCourierStatusSelectedListener());
    }

    private AdapterView.OnItemSelectedListener getCourierStatusSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String[] courierStatus = getResources().getStringArray(R.array.courier_status);
                String statusName = courierStatus[position];
                boolean isLastItem = (statusName != getLastItem(courierStatus));
                setLocationTrackingStatus(isLastItem);
                mLocationTracking.setChecked(isLastItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        };
    }

    private String getLastItem(String[] array) {
        return array[array.length - 1];
    }

    private void setLocationTrackingStatus(boolean value) {
        Intent intent = new Intent(getContext(), LocationService.class);
        if(value) {
            startLocationTracking(intent);
        } else {
            stopLocationTracking(intent);
        }
    }

    private void startLocationTracking(Intent intent) {
        getContext().startService(intent);
    }

    private void stopLocationTracking(Intent intent) {
        getContext().stopService(intent);
    }

}