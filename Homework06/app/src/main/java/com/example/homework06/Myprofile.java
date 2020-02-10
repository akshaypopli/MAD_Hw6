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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Myprofile extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Myprofile() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
     //prefs.edit().remove("studentJson").commit();

        StudentProfile studentobject;
         if(prefs.contains("studentJson")) {
            final String studentJsonobject = prefs.getString("studentJson", null);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting().serializeNulls();
            Gson gson = builder.create();
            studentobject = gson.fromJson(studentJsonobject, StudentProfile.class);

            View v=getView();
            EditText etfirstName=(EditText)v.findViewById(R.id.etFirstName);
            EditText etlastName=(EditText)v.findViewById(R.id.etLastName);
            EditText etstudentId=(EditText)v.findViewById(R.id.etStudentID);

            ImageView profileImage=(ImageView) v.findViewById(R.id.selectIv);
            RadioGroup radioGroup=(RadioGroup) v.findViewById(R.id.radioGroupDepartment);
            etfirstName.setText(studentobject.firstName);
            etlastName.setText(studentobject.lastName);
            etstudentId.setText(studentobject.studentId);
            if(studentobject.Department!=null) {
                if (studentobject.Department.contains("CS")) {
                    radioGroup.check(R.id.radioButtonCS);
                } else if (studentobject.Department.contains("SIS")) {
                    radioGroup.check(R.id.radioButtonSIS);
                } else if (studentobject.Department.contains("BIO")) {
                    radioGroup.check(R.id.radioButtonBIO);
                } else if (studentobject.Department.contains("Other")) {
                    radioGroup.check(R.id.radioButtonOther);
                }
            }
             switch (studentobject.Image) {
                 case "1":
                     profileImage.setImageResource(R.drawable.avatar_f_1);
                     break;

                 case "2":
                     profileImage.setImageResource(R.drawable.avatar_f_2);
                     break;

                 case "3":
                     profileImage.setImageResource(R.drawable.avatar_f_3);
                     break;

                 case "4":
                     profileImage.setImageResource(R.drawable.avatar_m_1);
                     break;

                 case "5":
                     profileImage.setImageResource(R.drawable.avatar_m_2);
                     break;

                 case "6":
                     profileImage.setImageResource(R.drawable.avatar_m_3);
                     break;

                 default:
                     profileImage.setImageResource(R.drawable.select_image);
             }


         }
        else

        {
            studentobject = new StudentProfile();
            studentobject.studentId="";
            studentobject.lastName="";
            studentobject.firstName="";
            studentobject.Image="";
            studentobject.Department="";

             SharedPreferences.Editor editor = prefs.edit();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String studentJson = gson.toJson(studentobject);
            editor.putString("studentJson", studentJson);
            editor.apply();
        }





        getView().findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v=getView();
                boolean isError = false;
                EditText firstName=(EditText)v.findViewById(R.id.etFirstName);
                EditText lastName=(EditText)v.findViewById(R.id.etLastName);
                EditText studentId=(EditText)v.findViewById(R.id.etStudentID);
                ImageView profileImage=(ImageView) v.findViewById(R.id.selectIv);
                RadioGroup radioGroup=(RadioGroup) v.findViewById(R.id.radioGroupDepartment);

                if(firstName.getText().toString().matches("")){
                    firstName.setError("please fill this field");
                    isError = true;
                }
                if(lastName.getText().toString().matches("")){
                    lastName.setError("please fill this field");
                    isError = true;
                }

                if(!isError){
                    if(!studentId.getText().toString().isEmpty() && Integer.parseInt(studentId.getText().toString()) != 0){
                        int id=  radioGroup.getCheckedRadioButtonId();

                        if(id == -1){
                            Toast.makeText(getActivity(), "Please select department", Toast.LENGTH_SHORT).show();
                        }else{
                            String dept="";
                            RadioButton rb=(RadioButton)v.findViewById(id);
                            if(rb!=null)
                                dept=rb.getText().toString();
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            GsonBuilder builder = new GsonBuilder();

                            builder.setPrettyPrinting().serializeNulls();
                            Gson gson = builder.create();
                            StudentProfile studentProfile = new StudentProfile();


                            String studentJsonobject= prefs.getString("studentJson", null);

                            builder.setPrettyPrinting().serializeNulls();
                            studentProfile = gson.fromJson(studentJsonobject, StudentProfile.class);

                            studentProfile.Department = dept;
                            studentProfile.firstName = firstName.getText().toString();
                            studentProfile.lastName = lastName.getText().toString();
                            studentProfile.studentId = studentId.getText().toString();


                            String studentJson = gson.toJson(studentProfile);
                            editor.putString("studentJson", studentJson);
                            editor.apply();
                            getFragmentManager().beginTransaction().replace(R.id.mainContainer,new DisplayProfileFragment(),"DisplayProfileFragment").commit();
                        }
                    }else{
                        studentId.setError("Please enter value other than 0");
                    }

                }else{
                    Toast.makeText(getActivity(), "Please fill in all the values", Toast.LENGTH_SHORT).show();
                }



            }
        });

        getView().findViewById(R.id.selectIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainContainer,new SelectAvatar(),"SelectAvatar").commit();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myprofile, container, false);
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
