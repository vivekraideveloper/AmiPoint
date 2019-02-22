package com.amityprojectvivekrai.amityproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SubtopicAdapter extends ExpandableRecyclerViewAdapter<TopicViewHolder, SubtopicViewHolder> {

    public SubtopicAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public TopicViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_topic, parent
                , false);
        return new TopicViewHolder(view);
    }

    @Override
    public SubtopicViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subtopic, parent
                , false);
        return new SubtopicViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(SubtopicViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Subtopic subtopic = (Subtopic) group.getItems().get(childIndex);
        holder.bind(subtopic);
    }

    @Override
    public void onBindGroupViewHolder(TopicViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Topic topic = (Topic) group;
        holder.bind(topic);
    }
}
