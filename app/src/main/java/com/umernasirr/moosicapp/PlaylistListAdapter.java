package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistListAdapter.myViewHolder> implements Filterable {

    Context context;
    ArrayList<PlaylistModel> playlistList;
    ArrayList<PlaylistModel> cpyPlaylistList;

    public PlaylistListAdapter(Context ct, ArrayList<PlaylistModel> playlistList){
        this.context =  ct;
        this.playlistList = playlistList;
        this.cpyPlaylistList = new ArrayList<>(playlistList);

    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.playlist_listitem, parent, false);
        return new PlaylistListAdapter.myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txtPlaylistName.setText(playlistList.get(position).getName());
        holder.txtPlaylistCount.setText(playlistList.get(position).getSongsList().size() + "");

// Action listener on button
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    @Override
    public Filter getFilter() {
        return playlistFilter;
    }


    private Filter playlistFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PlaylistModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(cpyPlaylistList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for( PlaylistModel playlist: cpyPlaylistList){
                    if(playlist.getName().toLowerCase().contains(filterPattern) ){
                        filteredList.add(playlist);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            playlistList.clear();
            playlistList.addAll((ArrayList<PlaylistModel>) results.values );
            notifyDataSetChanged();
        }
    };

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView txtPlaylistName;
        TextView txtPlaylistCount;
        ImageView btnPlayPlaylist;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPlaylistName = itemView.findViewById(R.id.txtPlaylistName);
            txtPlaylistCount = itemView.findViewById(R.id.txtPlaylistCount);
            btnPlayPlaylist = itemView.findViewById(R.id.btnPlayPlaylist);
        }
    }


}
