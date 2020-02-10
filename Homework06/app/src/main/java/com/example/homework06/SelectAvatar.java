package com.example.homework06;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectAvatar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SelectAvatar extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       final StudentProfile studentobject;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        if(prefs.contains("studentJson")) {
            String studentJsonobject = prefs.getString("studentJson", null);

                  studentobject = gson.fromJson(studentJsonobject, StudentProfile.class);

        }
        else
        {
              studentobject=new StudentProfile();
            studentobject.lastName="";
            studentobject.firstName="";
            studentobject.studentId="";
            studentobject.Department="";
            studentobject.Image="";


        }

        View v = getView();

        v.findViewById(R.id.ivAvtar1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "1";
                saveObject(studentobject);
            }
        });


        v.findViewById(R.id.ivAvtar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "2";
                saveObject(studentobject);
            }
        });

        v.findViewById(R.id.ivAvtar3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "3";
                saveObject(studentobject);
            }
        });

        v.findViewById(R.id.ivAvtar4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "4";
                saveObject(studentobject);            }
        });

        v.findViewById(R.id.ivAvtar5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "5";
                saveObject(studentobject);
            }
        });

        v.findViewById(R.id.ivAvtar6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentobject.Image = "6";
                saveObject(studentobject);
            }
        });


//        SharedPreferences.Editor editor = prefs.edit();
//        String studentJson = gson.toJson(studentobject);
//        editor.putString("studentJson", studentJson);
//
//        editor.apply();
    }

    public void saveObject(StudentProfile studentobject)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        SharedPreferences.Editor editor = prefs.edit();
        String studentJson = gson.toJson(studentobject);
        editor.putString("studentJson", studentJson);

        editor.apply();
        getFragmentManager().beginTransaction().replace(R.id.mainContainer, new Myprofile(), "Myprofile").commit();


    }

    public SelectAvatar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_avatar, container, false);
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
        void UpdateSelectedImage();
    }
}
