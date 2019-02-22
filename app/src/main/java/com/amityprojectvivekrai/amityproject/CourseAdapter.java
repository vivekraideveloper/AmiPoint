package com.amityprojectvivekrai.amityproject;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ImageViewHolder> {

    private Context context;
    private List<Upload> list;
    private OnItemClickListener mListener;


    public CourseAdapter(Context context, List<Upload> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

        final Upload uploadCurrent = list.get(position);
        Glide.with(context).load(uploadCurrent.getImageUrl()).into(holder.imageView);
        holder.textView.setText(uploadCurrent.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textView;
        public ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.courseTextView);
            imageView = itemView.findViewById(R.id.courseImageView);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem doWHatEver = contextMenu.add(Menu.NONE, 1, 1, "Go Ahead");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "No Action");

            doWHatEver.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            break;

                        case 2:
                            mListener.onDeleteClick(position);
                            break;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


}
