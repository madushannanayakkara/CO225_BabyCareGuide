package com.example.babydevelopmenttrackingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdpter extends RecyclerView.Adapter<CustomAdpter.MyViewHolder> {

    private Context context;
    private DatabaseH dh;
    private ArrayList fname, lname;

    CustomAdpter(Context context, ArrayList fname, ArrayList lname, DatabaseH dh){
        this.context = context;
        this.fname = fname;
        this.lname = lname;
        this.dh = dh;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_acc_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtAccName.setText(String.valueOf(fname.get(position)) + " " + String.valueOf(lname.get(position)));
        holder.card_acc_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) context;
                TextView txtv_title = ma.findViewById(R.id.txtv_title);

                txtv_title.setText(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition())) + " " + String.valueOf(lname.get(holder.getAbsoluteAdapterPosition())));
//                dh.updateLastSavedID(dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition())), String.valueOf(lname.get(holder.getAbsoluteAdapterPosition()))));

            }
        });
    }

    @Override
    public int getItemCount() {
        return fname.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtAccName;
        CardView card_acc_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAccName = itemView.findViewById(R.id.txtAccName);
            card_acc_name = itemView.findViewById(R.id.card_acc_name);
        }
    }
}
