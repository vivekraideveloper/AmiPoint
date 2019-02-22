package com.amityprojectvivekrai.amityproject;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Topic extends ExpandableGroup<Subtopic> {

    public Topic(String title, List<Subtopic> items) {
        super(title, items);
    }
}
