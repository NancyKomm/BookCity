package com.example.bookcity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    TextView title, author,pages, language, price, quantity;
    Button button;
    ImageView image;
    // Accessing Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    public EditText info;
    // To save parameters from intent
    String myTitle;
    String myImgPath;
    String myQuantity;
    // Get menu item clicked by user
    String menuItem = MenuActivity.getMenuItem();
    // Text-To-Speech
    MyTTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Fetching parameters from intent
        Intent intent = getIntent();
        myTitle = intent.getStringExtra("title");
        myImgPath = intent.getStringExtra("imgPath");

        title = findViewById(R.id.textView);
        author = findViewById(R.id.textView10);
        pages = findViewById(R.id.textView11);
        language = findViewById(R.id.textView12);
        price = findViewById(R.id.textView13);
        image = findViewById(R.id.imageView);
        button = findViewById(R.id.button2);
        info = findViewById(R.id.editTextTextMultiLine);
        info.setFocusable(false);   //make it readonly
        quantity = findViewById(R.id.textView3);
        title.setText(myTitle);
        Glide.with(this).load(myImgPath).into(image);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("BookList/" + myTitle);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loading all book's info
                    Book book = dataSnapshot.getValue(Book.class);
                    info.setText(book.getInfo());
                    author.setText("By:  " + book.getAuthor());
                    pages.setText("Pages:  " + book.getPages());
                    language.setText("Language:  " + book.getLanguage());
                    price.setText("Price:  " + book.getPrice());
                    // If quantity = 0
                    if (book.getQuantity() == 0){
                        quantity.setText("Currently Expired");
                        button.setVisibility(View.GONE);
                    }
                    else {
                        quantity.setText("Quantity:  " + String.valueOf(book.getQuantity()));
                        myQuantity = String.valueOf(book.getQuantity());
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
        }});

        // if button pressed = view books, not buy book
        if( menuItem == "1" ){
            button.setVisibility(View.GONE);
        }

        tts = new MyTTS(this);
    }

    public void buyBook(View view){
        // If user isn't logged in yet
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText(this,"Log in before you buy a book", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LogInActivity.class);
            this.startActivity(intent);
        }
        else
        {
            // Stop Text-To-Speech when buy button is pressed
            tts.stop();
            Intent intent = new Intent(this,Payment.class);
            intent.putExtra("title", myTitle);
            intent.putExtra("imgPath", myImgPath);
            intent.putExtra("quantity", myQuantity);
            this.startActivity(intent);
        }
    }

    // Text-To-Speech
    public void readInfo(View view){
        String allInfo = title.getText().toString() +
                author.getText().toString() +
                pages.getText().toString() +
                language.getText().toString() +
                price.getText().toString() +
                quantity.getText().toString() +
                "Plot " +
                info.getText().toString();
        tts.speak(allInfo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Stop Text-To-Speech when back button is pressed
        tts.stop();
    }
}