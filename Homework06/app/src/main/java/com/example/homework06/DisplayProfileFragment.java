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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DisplayProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public DisplayProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

         String studentJsonobject= prefs.getString("studentJson", null);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        StudentProfile studentobject = gson.fromJson(studentJsonobject, StudentProfile.class);

        View v=getView();
        TextView txtfullname= (TextView) v.findViewById(R.id.txtfullname);
        TextView txtStudentID= (TextView) v.findViewById(R.id.txtStudentID);
        ImageView imgprofile= (ImageView) v.findViewById(R.id.ProfileimageView);

        RadioGroup rg =v.findViewById(R.id.radioGroupDepartment);

          TextView txtdepartment= (TextView) v.findViewById(R.id.txtdepartment);

        txtfullname.setText(studentobject.firstName+" "+studentobject.lastName);
        txtStudentID.setText(studentobject.studentId);
        txtdepartment.setText(studentobject.Department);
         switch (studentobject.Image)
        {
            case "1":
                imgprofile.setImageResource(R.drawable.avatar_f_1);
                break;

            case "2":
                imgprofile.setImageResource(R.drawable.avatar_f_2);
                break;

            case "3":
                imgprofile.setImageResource(R.drawable.avatar_f_3);
                break;

            case "4":
                imgprofile.setImageResource(R.drawable.avatar_m_1);
                break;

            case "5":
                imgprofile.setImageResource(R.drawable.avatar_m_2);
                break;

            case "6":
                imgprofile.setImageResource(R.drawable.avatar_m_3);
                break;

            default:
                imgprofile.setImageResource(R.drawable.select_image);


        }

        getView().findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().replace(R.id.mainContainer,new Myprofile(),"Myprofile").commit();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
