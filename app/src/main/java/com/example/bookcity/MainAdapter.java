package com.example.bookcity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.BreakIterator;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder>{

    public List<Book> mainRecycle_lists;
    private ConstraintLayout linearLayout;
    Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author;
        ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            author  = view.findViewById(R.id.tv_author);
            imageView = view.findViewById(R.id.image_profile);
            linearLayout = view.findViewById(R.id.clickRecyclerView);
        }
    }
    public MainAdapter(List<Book> mainRecycle_lists, Context context) {
        this.mainRecycle_lists = mainRecycle_lists;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Book recycleList = mainRecycle_lists.get(position);
        holder.title.setText(recycleList.getTitle());
        holder.author.setText(recycleList.getAuthor());

        Glide.with(context).load(recycleList.getImgPath()).into(holder.imageView);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book recycleList = mainRecycle_lists.get(position);
                // If user presses on a book, show more details
                String title = recycleList.getTitle();
                Toast.makeText(context,"Title : " + title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("imgPath",recycleList.getImgPath());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mainRecycle_lists.size();
    }

}
