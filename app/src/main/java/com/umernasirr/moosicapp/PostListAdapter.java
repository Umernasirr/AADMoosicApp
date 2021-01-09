package com.umernasirr.moosicapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.myViewHolder> implements Filterable {

    Context context;
    ArrayList<PostModel> postList;
    ArrayList<PostModel> cpyPostList;

    public PostListAdapter(Context ct, ArrayList<PostModel> postList){
        this.context =  ct;
        this.postList = postList;
        this.cpyPostList = new ArrayList<>(postList);
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.post_listitem, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txtTitle.setText(postList.get(position).getTitle());
        holder.txtDescription.setText(postList.get(position).getDescription());
        holder.txtURL.setText(postList.get(position).getUrl());

//         Action listener on button to go to play screen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                //Add your data from method to bundle
                bundle.putString("title", postList.get(position).getTitle());
                bundle.putString("description", postList.get(position).getDescription());
                bundle.putString("url", postList.get(position).getUrl());
                bundle.putString("createdAt", postList.get(position).getCreatedAt());

                Intent intent = new Intent(context, PostViewActivity.class);
                //Add the bundle to the intent
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public Filter getFilter() {
        return songFilter;
    }

    private Filter songFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PostModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(cpyPostList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for( PostModel post: cpyPostList){
                    if(post.getTitle().toLowerCase().contains(filterPattern) ){
                        filteredList.add(post);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postList.clear();
            postList.addAll((ArrayList<PostModel>) results.values );
            notifyDataSetChanged();
        }
    };

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtDescription;
        TextView txtURL;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtURL = itemView.findViewById(R.id.txtURL);
        }
    }
}
