package com.example.bookcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    // Creating a Firebase Authorization object
    private FirebaseAuth mAuth;
    private static String menuItem;
    ImageButton ib3,ib4;

    public static String getMenuItem() {
        return menuItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Instantiating a Firebase Authorization object
        mAuth = FirebaseAuth.getInstance();

        ib3 = findViewById(R.id.imageButton6);
        ib4 = findViewById(R.id.imageButton7);

        // If user isn't logged in yet
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            // Buttons = Log in, Register
            ib3.setImageResource(R.drawable.log_in);
            ib4.setImageResource(R.drawable.register);
        }
        else {
            // Buttons = Switch account, log out
            ib3.setImageResource(R.drawable.switch_account);
            ib4.setImageResource(R.drawable.log_out);
        }
    }

    public void viewBooks(View view){
        menuItem = "1";
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
    }

    public void buyBook(View view){
        menuItem = "2";
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);

    }

    // Login + Switch Accounts
    public void searchBook(View view){
        menuItem = "3";
        // If user isn't logged in yet
        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this,LogInActivity.class);
            this.startActivity(intent);
        }
        else {
            // Switch account
            mAuth.signOut();
            Toast.makeText(this,"Log-in or register.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,MenuActivity.class);
            this.startActivity(intent);
        }
    }

    public void logOut (View view){
        menuItem = "4";

        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this,RegisterActivity.class);
            this.startActivity(intent);
        }
        // If log out
        else {
            mAuth.signOut();
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }
    }

}