package com.example.hw_06;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SelectAvatar extends Fragment {
    Profile profile;
    private OnFragmentInteractionListener mListener;

    public SelectAvatar() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_select_avatar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Profile profile;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        if (prefs.contains("profileJson")) {
            String profileJsonobject = prefs.getString("profileJson", null);

            profile = gson.fromJson(profileJsonobject, Profile.class);

        } else {
            profile = new Profile();

        }
        final ImageView iv_f1 = getView().findViewById(R.id.iv_f1);
        final ImageView iv_m1 = getView().findViewById(R.id.iv_m1);
        final ImageView iv_f2 = getView().findViewById(R.id.iv_f2);
        final ImageView iv_m2 = getView().findViewById(R.id.iv_m2);
        final ImageView iv_f3 = getView().findViewById(R.id.iv_f3);
        final ImageView iv_m3 = getView().findViewById(R.id.iv_m3);

        iv_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "f1";
                imageSelect(profile);
            }
        });

        iv_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "f2";
                imageSelect(profile);
            }
        });

        iv_f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "f3";
                imageSelect(profile);
            }
        });

        iv_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "m1";
                imageSelect(profile);
            }
        });
        iv_m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "m2";
                imageSelect(profile);
            }
        });
        iv_m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.image = "m3";
                imageSelect(profile);
            }
        });


    }

    public void imageSelect(Profile profileObj) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        SharedPreferences.Editor editor = prefs.edit();
        String profileJson = gson.toJson(profileObj);
        editor.putString("profileJson", profileJson);

        editor.apply();
        getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment(), "profileFragment").commit();


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
        void onAvatarSelect();
    }
}
