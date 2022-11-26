package com.example.mobilecourseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Models.Chicken;

public class ChickensRecViewAdapter extends  RecyclerView.Adapter<ChickensRecViewAdapter.ViewHolder> {

    private ArrayList<Chicken> chickens = new ArrayList<>();

    private Context context;

    public ChickensRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chickens_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chicken model = chickens.get(position);
        holder.txtChickenBreed.setText(model.getBreed());
        holder.txtChickenId.setText(Integer.toString(model.getId()));
        holder.txtChickenType.setText(model.getType());
        holder.txtChickenProduction.setText(Integer.toString(model.getProduction()));

        holder.btn_deleteListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(context);

                dbHelper.deleteOne(model.getId());
                chickens.remove(model);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chickens.size();
    }

    public void setChickens(ArrayList<Chicken> chickens) {
        this.chickens = chickens;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtChickenBreed, txtChickenId, txtChickenType, txtChickenProduction;

        private Button btn_deleteListItem;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChickenBreed = itemView.findViewById(R.id.txtChickenBreed);
            txtChickenProduction = itemView.findViewById(R.id.txtChickenProduction);
            txtChickenId = itemView.findViewById(R.id.txtChickenId);
            txtChickenType = itemView.findViewById(R.id.txtChickenType);
            btn_deleteListItem = itemView.findViewById(R.id.btn_deleteListItem);
            parent = itemView.findViewById(R.id.parentChickenView);
        }
    }
}
