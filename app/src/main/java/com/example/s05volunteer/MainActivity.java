package com.example.s05volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Activity that shows the user new opportunities
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener, DialogManager.ClickListener {
    DialogManager dialogManager;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView empty;
    ArrayList<VolunteerOpportunity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
        connectRecyclerView(data);
        connectXml();
    }

    private void fetchData() {
        HardcodedDataManager dataManager = HardcodedDataManager.getInstance();
        data = (ArrayList<VolunteerOpportunity>) dataManager.data.clone();
        data.removeIf(i -> i.isRegistered);
    }

    private void connectRecyclerView(ArrayList<VolunteerOpportunity> data) {
        RecyclerView recyclerView = findViewById(R.id.recycler_opportunities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, this, data);
        recyclerViewAdapter.setHasStableIds(true);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void connectXml() {
        empty = findViewById(R.id.text_empty);
        // Show the fallback text if there's nothing to display
        if (data.size() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisteredActivity.class);
            startActivity(intent);
        });
        dialogManager = new DialogManager(this, this);
    }

    @Override
    public void onButtonClick(View v, int position) {
        VolunteerOpportunity selected = recyclerViewAdapter.getItem(position);
        dialogManager.startDialogChain(selected);
    }

    @Override
    public void onComplete(VolunteerOpportunity item) {
        item.isRegistered = true;
        recyclerViewAdapter.removeItem(item);
        // Show the fallback text if there's nothing left
        if (recyclerViewAdapter.getItemCount() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
    }
}