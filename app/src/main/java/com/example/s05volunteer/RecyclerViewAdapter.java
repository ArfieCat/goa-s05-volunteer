package com.example.s05volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ClickListener clickListener;
    ArrayList<VolunteerOpportunity> data;

    public RecyclerViewAdapter(Context context, ClickListener clickListener, ArrayList<VolunteerOpportunity> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_opportunity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VolunteerOpportunity item = data.get(position);
        holder.name.setText(item.name);
        holder.description.setText(item.description);
        holder.date.setText(item.date);
        holder.location.setText(item.location);
        holder.logo.setImageResource(item.logoDrawable);
        holder.org.setImageResource(item.orgDrawable);
        if (item.isRegistered) {
            holder.registered.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public VolunteerOpportunity getItem(int id) {
        return data.get(id);
    }

    public void removeItem(VolunteerOpportunity item) {
        int id = data.indexOf(item);
        data.remove(id);
        notifyItemRemoved(id);
    }

    public interface ClickListener {
        void onButtonClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView date;
        TextView location;
        ImageView logo;
        ImageView org;
        LinearLayout registered;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            description = itemView.findViewById(R.id.text_desc);
            date = itemView.findViewById(R.id.text_date);
            location = itemView.findViewById(R.id.text_location);
            logo = itemView.findViewById(R.id.image_logo);
            org = itemView.findViewById(R.id.image_org);
            registered = itemView.findViewById(R.id.registered);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(v -> clickListener.onButtonClick(v, getAdapterPosition()));
        }
    }
}