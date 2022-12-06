package com.example.groupbschedulingsoftwaresefall2022;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{
        // creating variables for our ArrayList and context
        private ArrayList<Event> eventsArrList;
        private Context content;


        //constructor for adapter class
        public DayAdapter(ArrayList<Event> eventsArrList, Context content) {
            this.eventsArrList = eventsArrList;
            this.content = content;
        }
        // creating constructor for our adapter class

        @NonNull
        @Override
        public DayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(content).inflate(R.layout.day_item, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull DayAdapter.ViewHolder holder, int position) {
            // setting data to our text views from our modal class.
            Event eventsList = eventsArrList.get(position);
            holder.eventName.setText(eventsList.getEventName());
            holder.eventStartTime.setText(eventsList.getStartTime());
            holder.eventEndTime.setText(eventsList.getEndTime());
        }

        @Override
        public int getItemCount() {
            // returning the size of our array list.
            return eventsArrList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our text views.
            private final TextView eventName;
            private final TextView eventStartTime;
            private final TextView eventEndTime;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our text views.
                eventName = itemView.findViewById(R.id.idEventName);
                eventStartTime = itemView.findViewById(R.id.idStartTime);
                eventEndTime = itemView.findViewById(R.id.idEndTime);
            }
        }
    }

