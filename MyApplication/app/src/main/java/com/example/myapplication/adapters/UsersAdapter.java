package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.List;

public class UsersAdapter extends
        RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView userPoints;

        public ViewHolder(View itemView){
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userPoints = (TextView) itemView.findViewById(R.id.user_points);
        }
    }

    private List<User> mUsers;

    public UsersAdapter(List<User> users){
        mUsers = users;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View userView = inflater.inflate(R.layout.item_user, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        User user = mUsers.get(position);

        // Set item views based on your views and data model
        TextView userNameTV = holder.userName;
        userNameTV.setText(user.getUserName());

        TextView userPointsTV = holder.userPoints;
        userPointsTV.setText(String.valueOf(user.getPoints()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}
