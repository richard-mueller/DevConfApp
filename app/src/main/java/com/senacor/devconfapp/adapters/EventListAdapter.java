package com.senacor.devconfapp.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.senacor.devconfapp.R;
import com.senacor.devconfapp.activities.CreateEventActivity;
import com.senacor.devconfapp.handlers.EventHandler;
import com.senacor.devconfapp.models.Event;

import org.joda.time.LocalDate;

import java.util.ArrayList;
//import org.joda.time.LocalDate;

/**
 * Created by saba on 28.10.16.
 */

public class EventListAdapter extends ArrayAdapter<Event>  {

    EventHandler eventHandler;
    Activity activity;
    Context context;

    private static class ViewHolder {
        //TextView eventId;
        TextView name;
        TextView place;
        TextView date;
        TextView streetAndNumber;
        TextView postalCodeAndCity;
        ImageView editEventButton, deleteEventButton;
    }

    public EventListAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.item_event, events);
        activity = (Activity) context;
        this.context = context;
        eventHandler = new EventHandler(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Event event = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_event, parent, false);

            //viewHolder.eventId = (TextView) convertView.findViewById(R.id.value_event_eventId);
            viewHolder.name = (TextView) convertView.findViewById(R.id.value_event_name);
            viewHolder.place = (TextView) convertView.findViewById(R.id.value_event_place);
            viewHolder.streetAndNumber = (TextView) convertView.findViewById(R.id.value_event_streetAndNumber);
            viewHolder.postalCodeAndCity = (TextView) convertView.findViewById(R.id.value_event_postalCodeAndCity);
            viewHolder.date = (TextView) convertView.findViewById(R.id.value_event_date);
            viewHolder.editEventButton = (ImageView) convertView.findViewById(R.id.button_editEvent);
            viewHolder.deleteEventButton = (ImageView) convertView.findViewById(R.id.button_deleteEvent);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.eventId.setText(event.getEventId());
        viewHolder.name.setText(event.getName());
        viewHolder.place.setText(event.getPlace());
        viewHolder.streetAndNumber.setText(event.getStreetAndNumber());
        viewHolder.postalCodeAndCity.setText(event.getPostalCodeAndCity());
        viewHolder.date.setText(event.dateToString());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        final String role = sharedPref.getString("role", "role");
        if (role.equals("ADMIN") && !(event.getDate().isBefore(LocalDate.now()))){
            viewHolder.deleteEventButton.setVisibility(View.VISIBLE);
            viewHolder.deleteEventButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Alert Dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                System.out.println("Delete Event " + event.getUrl());
                                eventHandler.deleteEvent(event.getUrl());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                System.out.println("Event is not deleted" );
                                dialog.cancel();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you want delete this event?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
            }
        });

        viewHolder.editEventButton.setVisibility(View.VISIBLE);
        viewHolder.editEventButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateEventActivity.class);
                intent.putExtra("name", event.getName());
                intent.putExtra("place", event.getPlace());
                intent.putExtra("date", event.getDate().toString());
                intent.putExtra("streetAndNumber",event.getStreetAndNumber());
                intent.putExtra("postalCodeAndCity", event.getPostalCodeAndCity());
                intent.putExtra("eventId", event.getEventId());
                activity.startActivity(intent);
                activity.finish();
            }
        });

        }
        return convertView;
    }
}
