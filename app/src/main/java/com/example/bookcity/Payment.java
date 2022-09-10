package com.example.bookcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Payment extends AppCompatActivity {

    TextView title;
    ImageView image;
    String quantity;
    // Accessing Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef1;
    CardForm cardForm;
    // Text-To-Speech
    MyTTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_payment);
        // Fetching parameters from intent
        String myTitle = intent.getStringExtra("title");
        String myImgPath = intent.getStringExtra("imgPath");
        quantity = intent.getStringExtra("quantity");
        title = findViewById(R.id.textView2);
        image = findViewById(R.id.imageView2);
        title.setText(myTitle);
        Glide.with(this).load(myImgPath).into(image);
        tts = new MyTTS(this);

        // For card
        cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);
    }


    public void confirmPayment(View view){
        // All card fields are required
        if (cardForm.getCardNumber() != "" &&
        cardForm.getExpirationMonth()!= "" &&
        cardForm.getExpirationYear()!= "" &&
        cardForm.getCvv()!= "" &&
        cardForm.getCardholderName()!= "" &&
        cardForm.getPostalCode()!= "" &&
        cardForm.getCountryCode()!= "" &&
        cardForm.getMobileNumber()!= ""){

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("BookList/" + title.getText().toString() + "/quantity/");
            database = FirebaseDatabase.getInstance();
            myRef.setValue(Integer.parseInt(quantity) - 1 );

            myRef1= database.getReference("PurchaseList");

            Purchase purchase = new Purchase(cardForm.getCardNumber(),
                    cardForm.getExpirationMonth(),
                    cardForm.getExpirationYear(),
                    cardForm.getCvv(),
                    cardForm.getCardholderName(),
                    cardForm.getPostalCode(),
                    cardForm.getCountryCode(),
                    cardForm.getMobileNumber(),title.getText().toString(),new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

            String key = myRef1.child("posts").push().getKey();
            Map<String, Object> postValues = purchase.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put( key, postValues);
            myRef1.updateChildren(childUpdates);
            tts.speak("Payment was successful.");
            // showMessage
            Toast.makeText(this,"Payment was successful.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this,"Complete all fields to complete payment.", Toast.LENGTH_SHORT).show();

        }
    }

}