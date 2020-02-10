package com.example.hw_06;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class DisplayFragment extends Fragment {

    TextView tv_name;
    TextView tv_id;
    ImageView iv_dp;
    RadioGroup rg_department;
    TextView tv_dept;
    Profile profile;

    private OnFragmentInteractionListener mListener;

    public DisplayFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        String profileJsonobject = prefs.getString("profileJson", null);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        Profile profileobject = gson.fromJson(profileJsonobject, Profile.class);

        View view = getView();
        tv_name = view.findViewById(R.id.tv_name);
        tv_id = view.findViewById(R.id.tv_id);
        iv_dp = view.findViewById(R.id.iv_dp);
        rg_department = view.findViewById(R.id.rg_department);
        tv_dept = view.findViewById(R.id.tv_dept);

        tv_name.setText(profileobject.first + " " + profileobject.last);
        tv_id.setText(profileobject.id);
        tv_dept.setText(profileobject.department);
        switch (profileobject.image) {
            case "f1":
                iv_dp.setImageResource(R.drawable.avatar_f_1);
                break;

            case "f2":
                iv_dp.setImageResource(R.drawable.avatar_f_2);
                break;

            case "f3":
                iv_dp.setImageResource(R.drawable.avatar_f_3);
                break;

            case "m1":
                iv_dp.setImageResource(R.drawable.avatar_m_1);
                break;

            case "m2":
                iv_dp.setImageResource(R.drawable.avatar_m_2);
                break;

            case "m3":
                iv_dp.setImageResource(R.drawable.avatar_m_3);
                break;

            default:
                iv_dp.setImageResource(R.drawable.select_image);


        }

        getView().findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment(), "profileFragment").commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDisplayFragmentInteraction();
    }
}
