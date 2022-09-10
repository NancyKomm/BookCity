package com.example.bookcity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mCustomerDatabase;

    //recycleView
    private ArrayList<Book> mRecycler = new ArrayList<Book>();
    private RecyclerView recyclerView;
    public MainAdapter mainAdapter;
    //String mProfileImageUrl;
    ProgressDialog progressDialog;
    String title = null, imgPath= null, author= null, info= null, pages= null, language= null, price= null;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomerDatabase = FirebaseDatabase.getInstance().getReference("BookList");

        recyclerView = findViewById(R.id.my_recycler_view);
        mainAdapter = new MainAdapter(mRecycler,getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mainAdapter);
        recyclerData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

    }

    private void recyclerData(){
        mRecycler.clear();
        mCustomerDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allListData(dataSnapshot);
                mainAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    @SuppressLint("NewApi")
    public void allListData(final DataSnapshot dataSnapshot){
        if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {

            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
            if (map.get("title") != null) {
                title = map.get("title").toString();
            }
            if (map.get("author") != null) {
                author = map.get("author").toString();
                System.out.print("author" + author);
            }
            if (map.get("imgPath") != null){
                imgPath = map.get("imgPath").toString();
                //Glide.with(getApplication()).load(mProfileImageUrl).into(profileImage);
            }
        }

        progressDialog.dismiss();

        mRecycler.add(new Book(title, imgPath, author, info, pages, language, price, quantity));
    }

}