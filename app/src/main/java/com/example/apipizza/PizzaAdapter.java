package com.example.apipizza;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pizza> pizzas;

    public PizzaAdapter(Context context, ArrayList<Pizza> pizzas) {
        this.context = context;
        this.pizzas = pizzas;
    }

    @NonNull
    @Override
    public PizzaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pizza_layout,parent,false);
        ViewHolder viewHolder  = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pizza pizza = pizzas.get(position);

        holder.name.setText(pizza.getName());
        holder.price.setText(String.valueOf(pizza.getPrice()));

        Log.d("pizzas", pizzas.toString());


        Glide.with(context)
                .load(pizza.getImageUrl())
                .centerCrop()
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizzas.remove(position);
                notifyItemRemoved(position);
                new ApiActions(context,"delete",pizza);
                notifyDataSetChanged();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity2.class);
                intent.putExtra("pizza",pizza);
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,edit,delete;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
