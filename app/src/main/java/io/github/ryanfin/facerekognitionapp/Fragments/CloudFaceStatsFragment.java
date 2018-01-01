package io.github.ryanfin.facerekognitionapp.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.ryanfin.facerekognitionapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloudFaceStatsFragment extends Fragment {

    public static TextView cloudStatsTxt;


    public CloudFaceStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cloud_face_stats, container, false);

        String strtext = getArguments().getString("cloud_stats");

        cloudStatsTxt = (TextView) view.findViewById(R.id.cloud_stats);
        cloudStatsTxt.setText(strtext);



        return view;
    }

}
