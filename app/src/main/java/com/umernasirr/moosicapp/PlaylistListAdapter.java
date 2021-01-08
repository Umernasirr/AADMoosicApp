package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistListAdapter.myViewHolder> implements Filterable {
    Retrofit retrofit = RetrofitFactory.getRetrofit();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Context context;
    ArrayList<PlaylistModel> playlistList;
    ArrayList<PlaylistModel> cpyPlaylistList;
    private final Filter playlistFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PlaylistModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cpyPlaylistList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PlaylistModel playlist : cpyPlaylistList) {
                    if (playlist.getName() == null) {
                        playlist.setName("Unnamed");
                    }
                    if (playlist.getName().toLowerCase().contains(filterPattern)) {
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
            playlistList.addAll((ArrayList<PlaylistModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public PlaylistListAdapter(Context ct, ArrayList<PlaylistModel> playlistList) {
        this.context = ct;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongsListActivity.class);
                intent.putExtra("playlistid", playlistList.get(position).getId());
                context.startActivity(intent);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                String deleteLink = "https://moosikk.herokuapp.com/api/v1/playlist/" + playlistList.get(position).getId();
                Call<Object> call = apiInterface.deletePlaylist(deleteLink);

                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Toast.makeText(context, "Playlist " + playlistList.get(position).getName() + " Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, PlaylistListActivity.class);

                        context.startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("myTag", "Error");
                    }
                });
                return true;
            }
        });


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

    public static class myViewHolder extends RecyclerView.ViewHolder {

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
