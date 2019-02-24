package com.amityprojectvivekrai.amityproject;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SubtopicViewHolder extends ChildViewHolder {
    private TextView textView;

    public SubtopicViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        textView.setBackgroundResource(R.drawable.download_button);
        textView.setTextColor(Color.parseColor("#0e385e"));
    }

    public void bind(Subtopic subtopic){
        textView.setText(subtopic.name);
    }
}
