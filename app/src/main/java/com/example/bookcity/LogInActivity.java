package com.example.bookcity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class LogInActivity extends AppCompatActivity {

    // Creating a Firebase Authorization object
    private FirebaseAuth mAuth;
    EditText email, password;
    MyTTS tts;
    String textable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // Instantiating a Firebase Authorization object
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPassword3);
        tts = new MyTTS(this);
    }

    public void signIn(View view){
        // Checking that required fields are completed
        if (!email.getText().toString().matches("") && !password.getText().toString().matches("")) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                tts.speak("Successful Log in");
                                // If sign in is successful, enter Main menu
                                Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                                intent.putExtra("UserID", mAuth.getUid());
                                startActivity(intent);
                            } else {
                                tts.speak(task.getException().getLocalizedMessage());
                                showMessage("Error", task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
        else{
            showMessage("Error", "All fields are required to sign in.");
        }
    }

    public void signUp(View view){
        // If sign up button is pressed, open MainActivity4 -> Sign up activity
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }


    // Speech-To-Text
    public void voiceRec(View view){
        int b = view.getId();
        if (b == R.id.imageButton2){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
            startActivityForResult(intent,123);
        }
        else if(b == R.id.imageButton3){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
            startActivityForResult(intent,124);
        }
        else {
            password.setText(textable);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            textable = matches.get(0).toString();
            //showMessage("Recognized Text", matches.get(0).toString());
            email.setText(textable);
        }
        else if (requestCode == 124 && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            textable = matches.get(0).toString();
            //showMessage("Recognized Text", matches.get(0).toString());
            password.setText(textable);
        }
    }

}
