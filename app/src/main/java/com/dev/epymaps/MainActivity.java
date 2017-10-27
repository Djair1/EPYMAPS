package com.dev.epymaps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;


    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener fireAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            handlefacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.erro_login,Toast.LENGTH_LONG).show();
            }
        });



       firebaseAuth = FirebaseAuth.getInstance();
        // obs usando fireAuthStateListener em vez de AuthStateListener

        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user!=null){
                    goPrincipalScrenn();
                }


            }
            };
        }

    private void handlefacebookAccessToken(AccessToken accessToken) {



//sendo implementado nao apagar


        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken()) ;
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if (!task.isSuccessful()){
    Toast.makeText(getApplicationContext(),"Erro ao realizar Login",Toast.LENGTH_LONG).show();
}
            }
        });

    }

    private void goPrincipalScrenn() {
        Intent it = new Intent(this,PrincipalActivity.class);
it.addFlags(it.FLAG_ACTIVITY_CLEAR_TOP | it.FLAG_ACTIVITY_CLEAR_TASK |it.FLAG_ACTIVITY_NEW_TASK);
startActivity(it);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);







    }

    public void onSart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);

    }

    public void onStop(){
        super.onStop();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);

    }

    public void logout(View view) {

        LoginManager.getInstance().logOut();
        goPrincipalScrenn();

    }
}

