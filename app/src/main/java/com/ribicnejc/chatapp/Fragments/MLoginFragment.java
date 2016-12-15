package com.ribicnejc.chatapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ribicnejc.chatapp.Activities.LoginRegisterActivity;
import com.ribicnejc.chatapp.Activities.MainChatActivity;
import com.ribicnejc.chatapp.Objects.User;
import com.ribicnejc.chatapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MLoginFragment.OnLoginFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MLoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressBar progressBar;
    private ScrollView scrollView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnLoginFragmentInteractionListener mListener;

    public MLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MLoginFragment newInstance(String param1, String param2) {
        MLoginFragment fragment = new MLoginFragment();
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
        final View view2 = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        // Handle sign in
        final Button loginBtn = (Button) view2.findViewById(R.id.email_sign_in_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = (ProgressBar) view2.findViewById(R.id.login_progress1);
                progressBar.setVisibility(View.VISIBLE);
                scrollView = (ScrollView) view2.findViewById(R.id.login_form1);
                scrollView.setVisibility(View.GONE);
                AutoCompleteTextView email = (AutoCompleteTextView) view2.findViewById(R.id.fragment_email);
                EditText password = (EditText) view2.findViewById(R.id.fragment_password);
                attemptLogin(email.getText().toString(), password.getText().toString());
            }
        });
        // Handle register
        Button registerBtn = (Button) view2.findViewById(R.id.email_register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
                loginRegisterActivity.startRegisterFragment();
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
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
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
    public interface OnLoginFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void attemptLogin(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        scrollView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getContext(), "could not login", Toast.LENGTH_SHORT).show();
                        }else{
                            User.email = email;
                            User.password = password;
                            Intent intent = new Intent(getContext(), MainChatActivity.class);
                            startActivity(intent);
                        }

                        // ...
                    }
                });
    }
}
