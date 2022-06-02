package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    ArrayList<PlayerGlobal> data;
    OnItemClickListener onItemClickListener;


    public PlayerAdapter(ArrayList<PlayerGlobal> data, Ranking onItemClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder vh = new ViewHolder(v, onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        PlayerGlobal currentPlayer = data.get(position);
        holder.tv_llistat_nick.setText(String.valueOf(currentPlayer.getNickname()));
        holder.tv_llistat_punt.setText(String.valueOf(currentPlayer.getPuntuacio()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_llistat_nick;
        public TextView tv_llistat_punt;
        public ImageButton btn_info;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.tv_llistat_nick = itemView.findViewById(R.id.tv_llista_nickname);
            this.tv_llistat_punt = itemView.findViewById(R.id.tv_llista_puntuacio);
            this.btn_info = itemView.findViewById(R.id.btn_info);
            this.onItemClickListener = onItemClickListener;

            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
