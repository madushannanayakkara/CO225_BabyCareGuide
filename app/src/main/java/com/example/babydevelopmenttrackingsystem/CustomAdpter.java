package com.example.babydevelopmenttrackingsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class CustomAdpter extends RecyclerView.Adapter<CustomAdpter.MyViewHolder> {

    private Context context;
    private DatabaseH dh;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList fname, lname;

    CustomAdpter(Context context, ArrayList fname, ArrayList lname, DatabaseH dh, BottomSheetDialog bottomSheetDialog){
        this.context = context;
        this.fname = fname;
        this.lname = lname;
        this.dh = dh;
        this.bottomSheetDialog = bottomSheetDialog;
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

                txtv_title.setText(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition()))
                        + " " + String.valueOf(lname.get(holder.getAbsoluteAdapterPosition())));
                dh.updateLastSavedID(dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition())),
                        String.valueOf(lname.get(holder.getAbsoluteAdapterPosition()))));
                bottomSheetDialog.dismiss();
            }
        });

//        holder.card_acc_name.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                        .setTitle("Delete Account")
//                        .setMessage("Are you sure want to delete \'"+ fname.get(holder.getAbsoluteAdapterPosition()) + " " +
//                                lname.get(holder.getAbsoluteAdapterPosition()) +"\' ?")
//                        .setIcon(R.drawable.ic_delete)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                MainActivity ma = (MainActivity) context;
//                                TextView txtv_title = ma.findViewById(R.id.txtv_title);
//
//                                int replacedid = -1;
//                                if(dh.readLastSavedID() == dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition())),
//                                        String.valueOf(lname.get(holder.getAbsoluteAdapterPosition())))){
//                                    if(holder.getAbsoluteAdapterPosition() - 1 >= 0){
//                                        replacedid = dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition() - 1)),
//                                                String.valueOf(lname.get(holder.getAbsoluteAdapterPosition() - 1)));
//                                        txtv_title.setText(dh.readAccName(replacedid));
//                                    } else {
//                                        if(fname.size() > 0){
//                                            replacedid = dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition() + 1)),
//                                                    String.valueOf(lname.get(holder.getAbsoluteAdapterPosition() + 1)));
//                                            txtv_title.setText(dh.readAccName(replacedid));
//                                        }else {
//                                            txtv_title.setText("Account");
//                                            bottomSheetDialog.dismiss();
//                                        }
//
//                                    }
//                                }
//
//                                /*dh.deleteRow(dh.findID(String.valueOf(fname.get(holder.getAbsoluteAdapterPosition())),
//                                        String.valueOf(lname.get(holder.getAbsoluteAdapterPosition()))),
//                                        replacedid);*/
//                                fname.remove(holder.getAbsoluteAdapterPosition());
//                                lname.remove(holder.getAbsoluteAdapterPosition());
//                                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
//
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                builder.show();
//
//                return true;
//            }
//        });

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
