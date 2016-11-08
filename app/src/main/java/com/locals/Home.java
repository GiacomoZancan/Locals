package com.locals;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ViewFlipper;

/**
 * Created by Giacomo on 25/10/2016.
 */

public class Home extends Fragment {
    ViewFlipper flip;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        flip = (ViewFlipper) view.findViewById(R.id.viewFlipper1);
        lv = (ListView) view.findViewById(R.id.listView);
//        new SchermataLocali.GetAllCustomerTask().execute(new ApiConnector());

        // Setting IN and OUT animation for view flipper
        flip.setInAnimation(getActivity(), R.anim.right_out);
        flip.setOutAnimation(getActivity(), R.anim.left_out);
        flip.setFlipInterval(4000);
        flip.startFlipping();
        getActivity().setTitle("Home");
        return view;
}
}
