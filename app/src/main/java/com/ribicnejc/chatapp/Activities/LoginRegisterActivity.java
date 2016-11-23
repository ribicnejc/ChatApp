package com.ribicnejc.chatapp.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ribicnejc.chatapp.Fragments.MLoginFragment;
import com.ribicnejc.chatapp.Fragments.RegisterFragment;
import com.ribicnejc.chatapp.R;

public class LoginRegisterActivity extends AppCompatActivity implements MLoginFragment.OnLoginFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container_login_register);
        if(fragment == null){
            fragment = new MLoginFragment();
            manager.beginTransaction().add(R.id.fragment_container_login_register, fragment).commit();
        }
    }

    public void startRegisterFragment(){
        Toast.makeText(this, "register fragment started", Toast.LENGTH_SHORT).show();

        RegisterFragment registerFragment = new RegisterFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, pickPowerFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login_register, registerFragment).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
