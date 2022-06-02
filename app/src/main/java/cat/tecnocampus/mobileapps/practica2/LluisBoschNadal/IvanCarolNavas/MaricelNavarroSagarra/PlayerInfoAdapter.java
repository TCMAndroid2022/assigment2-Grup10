package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoAdapter.ViewHolder> {
    ArrayList<PlayerGlobal> data;

    public PlayerInfoAdapter(ArrayList<PlayerGlobal> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PlayerInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info, parent, false);
        PlayerInfoAdapter.ViewHolder vh = new PlayerInfoAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfoAdapter.ViewHolder holder, int position) {
        PlayerGlobal currentPlayer = data.get(position);
        holder.tv_llistat_partides.setText(String.valueOf(currentPlayer.getPartides()));
        holder.tv_llistat_punt.setText(String.valueOf(currentPlayer.getPuntuacio()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_llistat_partides;
        public TextView tv_llistat_punt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_llistat_partides = itemView.findViewById(R.id.partida_info);
            this.tv_llistat_punt = itemView.findViewById(R.id.puntuacio_info);
        }
    }
}
