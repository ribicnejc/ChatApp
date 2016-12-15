package com.ribicnejc.chatapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.ribicnejc.chatapp.Activities.MainChatActivity;
import com.ribicnejc.chatapp.Objects.ChatMessage;
import com.ribicnejc.chatapp.Objects.User;
import com.ribicnejc.chatapp.Objects.UserProfile;
import com.ribicnejc.chatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnRegisterFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRegisterFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        final View view2 = inflater.inflate(R.layout.fragment_register, container, false);
        Button mRegBtn = (Button) view2.findViewById(R.id.register_button);
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = (ProgressBar) view2.findViewById(R.id.register_progress);
                progressBar.setVisibility(View.VISIBLE);
                scrollView = (ScrollView) view2.findViewById(R.id.register_form);
                scrollView.setVisibility(View.GONE);
                final AutoCompleteTextView email = (AutoCompleteTextView) view2.findViewById(R.id.register_email);
                final EditText password = (EditText) view2.findViewById(R.id.register_password);
                final EditText name = (EditText) view2.findViewById(R.id.register_name);
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User.email = email.getText().toString();
                            User.password = password.getText().toString();
                            User.name = name.getText().toString();
                            setData();
                        }
                    }
                });
            }
        });


        return view2;
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
        if (context instanceof OnRegisterFragmentInteractionListener) {
            mListener = (OnRegisterFragmentInteractionListener) context;
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
    public void setData() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(User.email, User.password);
        assert user != null;
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(User.name)
                        .build();

                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance()
                                    .getReference("Users")
                                    .push()
                                    .setValue(new UserProfile(User.name, User.email, true));
                            Intent intent = new Intent(getContext(), MainChatActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }

    public interface OnRegisterFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
