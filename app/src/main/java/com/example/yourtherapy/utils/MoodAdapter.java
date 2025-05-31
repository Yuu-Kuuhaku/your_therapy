package com.example.yourtherapy.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yourtherapy.R;

import java.util.List;

public class MoodAdapter extends ArrayAdapter<MoodEntry> {

    private Context context;
    private List<MoodEntry> data;

    public MoodAdapter(Context context, List<MoodEntry> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_mood, parent, false);
        }

        MoodEntry item = data.get(position);

        ImageView imageView = view.findViewById(R.id.imageMood);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDescription = view.findViewById(R.id.textDescription);
        TextView textDate = view.findViewById(R.id.textDate);

        int imageRes = R.drawable.emoji; // valor padrão
        switch (item.getMoodEnum()) {
            case 0:
                imageRes = R.drawable.emoji;
                break;
            case 1:
                imageRes = R.drawable.emoji_1;
                break;
            case 2:
                imageRes = R.drawable.emoji_2;
                break;
            case 3:
                imageRes = R.drawable.emoji_3;
                break;
            case 4:
                imageRes = R.drawable.emoji_4;
                break;
            case 5:
                imageRes = R.drawable.emoji_5;
                break;

        }

        imageView.setImageResource(imageRes);
        textTitle.setText(item.getTitle());
        textDescription.setText(item.getDescription());
        textDate.setText(item.getCreatedAt());

        return view;
    }
}
