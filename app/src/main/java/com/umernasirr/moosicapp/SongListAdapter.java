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

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.myViewHolder> implements Filterable {

    Context context;
    ArrayList<SongModel> songsList;
    ArrayList<SongModel> cpySongsList;

    public SongListAdapter(Context ct, ArrayList<SongModel> songsList){
        this.context =  ct;
        this.songsList = songsList;
        this.cpySongsList = new ArrayList<>(songsList);
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.song_listitem, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txtSongName.setText(songsList.get(position).getDescription());
        holder.txtSongCreator.setText(songsList.get(position).getUser().getName());

        // Action listener on button to go to play screen
        holder.btnPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicPlayerActivity.class);

                Bundle bundle = new Bundle();
                //Add your data from method to bundle
                bundle.putString("song_url", songsList.get(position).getUrl());
                bundle.putString("song_name", songsList.get(position).getDescription());
                bundle.putString("song_id", songsList.get(position).get_id());

                //Add the bundle to the intent
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    @Override
    public Filter getFilter() {
        return songFilter;
    }

    private Filter songFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SongModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(cpySongsList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for( SongModel song: cpySongsList){
                    if(song.getDescription().toLowerCase().contains(filterPattern) ){
                        filteredList.add(song);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                songsList.clear();
                songsList.addAll((ArrayList<SongModel>) results.values );
                notifyDataSetChanged();
        }
    };

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtSongName;
        TextView txtSongCreator;
        ImageView btnPlaySong;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSongName = itemView.findViewById(R.id.txtSongName);
            txtSongCreator = itemView.findViewById(R.id.txtSongCreator);
            btnPlaySong = itemView.findViewById(R.id.btnPlaySong);
        }
    }
}
