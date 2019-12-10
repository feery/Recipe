package com.fycedwin.chicken;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private ArrayList<String>dataNama;
    private ArrayList<String>dataCreator;
    private ArrayList<String>dataReview;
    private ArrayList<String>dataRating;
    private ArrayList<String>dataImage;
    private ArrayList<String>dataIngredients2;
    private Context context;

    public RecyclerViewAdapter(
             ArrayList<String>dataNama,
             ArrayList<String>dataCreator,
             ArrayList<String>dataReview,
             ArrayList<String>dataRating,
             ArrayList<String>dataImage,

             ArrayList<String>dataIngredients2,
    Context context) {


        this.dataNama=dataNama;
        this.dataCreator=dataCreator;
        this.dataReview=dataReview;
        this.dataRating=dataRating;
        this.dataImage=dataImage;
        this.dataIngredients2=dataIngredients2;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_rv_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {



        if(Double.parseDouble(dataRating.get(i).toString())<4){

            viewHolder.view_title.setText(dataNama.get(i));
            viewHolder.view_jam.setText("By: "+ dataCreator.get(i));
            viewHolder.view_tipe.setVisibility(View.GONE);
            viewHolder.view_harga.setText(dataReview.get(i));
            Picasso.with(context).load(dataImage.get(i)).into(viewHolder.imageView);
        }
        else {
            viewHolder.view_title.setText(dataNama.get(i));
            viewHolder.view_jam.setText(dataCreator.get(i));
            viewHolder.view_tipe.setText(dataRating.get(i));
            viewHolder.view_harga.setText(dataReview.get(i));
            Picasso.with(context).load(dataImage.get(i)).into(viewHolder.imageView);

        }

    }



    @Override
    public int getItemCount() {
        return dataNama.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView view_title;
        private TextView view_jam;
        private TextView view_harga;
        private TextView view_tipe;
        private CardView cardView;
        private ImageView imageView;



        ViewHolder(View v) {
            super(v);
            view_jam = v.findViewById(R.id.tv_jam);
            view_title = v.findViewById(R.id.tv_title);
            view_harga = v.findViewById(R.id.tv_harga);
            view_tipe = v.findViewById(R.id.tv_tipe);
            cardView = v.findViewById(R.id.cv_main);
            imageView= v.findViewById(R.id.image);

        }
    }

}
