package com.amityprojectvivekrai.amityproject;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
public class MyChildViewHolder extends ChildViewHolder {

    public TextView listChild;

    public MyChildViewHolder(View itemView) {
        super(itemView);
        listChild = (TextView) itemView.findViewById(R.id.listChild);

    }
    public void onBind(String Sousdoc) {
        listChild.setText(Sousdoc);

    }


}