package com.senacor.devconfapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.senacor.devconfapp.R;
import com.senacor.devconfapp.models.Event;

import java.util.ArrayList;

/**
 * Created by saba on 28.10.16.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    private static class ViewHolder {
        TextView id;
        TextView name;
        TextView place;
    }

    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.item_event, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_event, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.value_event_id);
            viewHolder.name = (TextView) convertView.findViewById(R.id.value_event_name);
            viewHolder.place = (TextView) convertView.findViewById(R.id.value_event_place);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(event.getId());
        viewHolder.name.setText(event.getName());
        viewHolder.place.setText(event.getPlace());

        return convertView;
    }
}