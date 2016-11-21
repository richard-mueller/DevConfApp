package com.senacor.devconfapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.senacor.devconfapp.R;
import com.senacor.devconfapp.models.Event;
import com.senacor.devconfapp.models.Speech;

import java.util.ArrayList;

/**
 * Created by Veronika Babic on 14.11.2016.
 */

public class SpeechAdapter extends ArrayAdapter<Speech>{
    public static class ViewHolder{
        TextView speechId;
        TextView speechTitle;
        TextView startTime;
        TextView endTime;
        TextView speechRoom;
        TextView speaker;
        TextView speakerInfo;
        TextView speechSummary;
    }

    public SpeechAdapter(Context context, ArrayList<Speech> speeches) {
        super(context, R.layout.item_speech, speeches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Speech speech = getItem(position);
        SpeechAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new SpeechAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_speech, parent, false);

            viewHolder.speechId= (TextView) convertView.findViewById(R.id.value_speech_id);
            viewHolder.speechTitle= (TextView) convertView.findViewById(R.id.value_speechTitle);
            viewHolder.speechRoom = (TextView) convertView.findViewById(R.id.value_speechRoom);
            viewHolder.speaker = (TextView) convertView.findViewById(R.id.value_speaker);
            viewHolder.speakerInfo = (TextView) convertView.findViewById(R.id.value_speakerInfo);
            viewHolder.speechSummary = (TextView) convertView.findViewById(R.id.value_speechSummary);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SpeechAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.speechId.setText(speech.getSpeechId());
        viewHolder.speechTitle.setText(speech.getSpeechTitle());
        viewHolder.speechRoom.setText(speech.getSpeechRoom());
        viewHolder.speaker.setText(speech.getSpeaker());
        viewHolder.speakerInfo.setText(speech.getSpeakerInfo());
        viewHolder.speechSummary.setText(speech.getSpeechSummary());

        return convertView;
    }
}

