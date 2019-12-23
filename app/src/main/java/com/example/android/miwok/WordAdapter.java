package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    public  int mColorResourceId;

    public WordAdapter(@NonNull Context context, ArrayList<Word> words, int colorResourceId) {
        super(context,0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        ImageView miwokaImageView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        if (currentWord.hasImage()) {
            miwokaImageView.setImageResource(currentWord.getImageResourceId());
            miwokaImageView.setVisibility(View.VISIBLE);
        }
        else {
            miwokaImageView.setVisibility(View.GONE);
        }
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView miwokaTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        miwokaTextView.setText(currentWord.getmMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        englishTextView.setText(currentWord.getmDefaultTranslation());

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
