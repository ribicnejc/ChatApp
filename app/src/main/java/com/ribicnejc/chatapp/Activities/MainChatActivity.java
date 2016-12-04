package com.ribicnejc.chatapp.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ribicnejc.chatapp.Fragments.FirstChatFragment;
import com.ribicnejc.chatapp.Fragments.MenuFragment;
import com.ribicnejc.chatapp.Fragments.RegisterFragment;
import com.ribicnejc.chatapp.Objects.User;
import com.ribicnejc.chatapp.R;

public class MainChatActivity extends AppCompatActivity implements
        FirstChatFragment.OnFirstChatFragmentInteractionListener,
        MenuFragment.OnMenuFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(User.email, User.password);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    User.firebaseUser = user;
                    FirebaseDatabase.getInstance().getReference("OnlineUsers")
                            .child(User.firebaseUser.getUid())
                            .setValue(User.firebaseUser.getDisplayName());
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

                    if (fragment == null) {
                        fragment = new MenuFragment();
                        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
                    }
                }
            }
        });



    }

    public void startAddFriendFragment(){

    }

    public void startGeneralChatFragment() {
        Toast.makeText(this, "general fragment started", Toast.LENGTH_SHORT).show();
        Fragment fragment = new FirstChatFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
