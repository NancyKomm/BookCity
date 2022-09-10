package com.example.bookcity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    // Creating a Firebase Authorization object
    private FirebaseAuth mAuth;
    EditText email, password, repeatPassword;
    CheckBox checkBox;

    // Text-To-Speech
    MyTTS tts;
    String textable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Instantiating a Firebase Authorization object
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        repeatPassword = findViewById(R.id.editTextTextPassword2);
        checkBox = findViewById(R.id.checkBox);
        tts = new MyTTS(this);
    }

    public void register(View view){
        // Checking that required fields are completed
        if (!email.getText().toString().matches("") && !password.getText().toString().matches("") &&
                !repeatPassword.getText().toString().matches("")){
            // Checking that password is the same in both fields
            if (password.getText().toString().matches(repeatPassword.getText().toString())){
                // Checking that required checkbox is checked
                if (checkBox.isChecked()){
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        tts.speak("Account created successfully.");
                                        // If sign up is successful, enter Main menu
                                        Intent intent = new Intent(RegisterActivity.this,MenuActivity.class);
                                        intent.putExtra("UserID",mAuth.getUid());
                                        startActivity(intent);
                                    }
                                    else{
                                        tts.speak(task.getException().getLocalizedMessage());
                                        showMessage("Error", task.getException().getLocalizedMessage());
                                    }
                                }
                            });
                }
                else {showMessage("Error","Checkbox must be checked to continue.");}
            }
            else{showMessage("Error", "Passwords must match.");}
        }
        else{showMessage("Error", "All fields are required to sign up.");}
    }
    void showMessage(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }

    // Speech-To-Text
    public void voiceRec(View view){
        int b = view.getId();
        if (b == R.id.imageButton4){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
            startActivityForResult(intent,123);
        }
        else if(b == R.id.imageButton8){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
            startActivityForResult(intent,124);
        }
        else if(b == R.id.imageButton9){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
            startActivityForResult(intent,125);
        }
        else {
            password.setText(textable);     /////////////////////////
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
        else if (requestCode == 125 && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            textable = matches.get(0).toString();
            //showMessage("Recognized Text", matches.get(0).toString());
            repeatPassword.setText(textable);
        }
    }

}