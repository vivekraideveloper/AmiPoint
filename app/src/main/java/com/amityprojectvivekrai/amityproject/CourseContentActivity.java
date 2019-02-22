package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseContentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
         final String content = bundle.getString("content");
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);


        FirebaseApp.initializeApp(this);
        //Define recycleview
        recyclerView = findViewById(R.id.recycler_view_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference parentReference = FirebaseDatabase.getInstance().getReference().child("courseContent").child(content);


        //reading data from firebase
        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<ParentList> Parent = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){


                    final String ParentKey = snapshot.getKey().toString();

                    snapshot.child(ParentKey).getValue();

                    DatabaseReference childReference =
                            FirebaseDatabase.getInstance().getReference().child("courseContent").child(content).child(ParentKey);
                    childReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<ChildList> Child = new ArrayList<>();
                            //numberOnline = 0;

                            for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                            {
                                final HashMap<String, String> ChildValue = (HashMap<String, String>)snapshot1.getValue();
//
//                                snapshot1.child(String.valueOf(ChildValue)).getValue();
//                                snapshot1.child(ChildValue.get(""));

                                Child.add(new ChildList(ChildValue));

                            }

                            Parent.add(new ParentList(ParentKey, Child));

                            DocExpandableRecyclerAdapter adapter = new DocExpandableRecyclerAdapter(Parent);

                            recyclerView.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            System.out.println("Failed to read value." + error.toException());
                        }

                    });}}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<MyParentViewHolder,MyChildViewHolder> {


        public DocExpandableRecyclerAdapter(List<ParentList> groups) {
            super(groups);
        }

        @Override
        public MyParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
            return new MyParentViewHolder(view);
        }

        @Override
        public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
            return new MyChildViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(MyChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            final ChildList childItem = (ChildList) group.getItems().get(childIndex);
            holder.onBind(childItem.getValues().get("name"));
            final String TitleChild=group.getTitle();
            holder.listChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = childItem.getValues().get("name");
                    String videoId = childItem.getValues().get("videoId");
                    String notesLink = childItem.getValues().get("notesLink");

                    Intent intent = new Intent(CourseContentActivity.this, VideoActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("videoId", videoId);
                    intent.putExtra("notesLink", notesLink);
                    startActivity(intent);
                }

            });

        }

        @Override
        public void onBindGroupViewHolder(MyParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if(group.getItems()==null)
            {
                holder.listGroup.setOnClickListener(  new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast toast = Toast.makeText(CourseContentActivity.this, "Hehehe", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
