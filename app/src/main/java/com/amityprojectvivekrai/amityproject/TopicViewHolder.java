package com.amityprojectvivekrai.amityproject;


import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class TopicViewHolder extends GroupViewHolder {
    private TextView textView;
    public TopicViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }

    public void bind(Topic topic){
        textView.setText(topic.getTitle());
    }


}
