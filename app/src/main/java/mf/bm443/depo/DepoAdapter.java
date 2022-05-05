package mf.bm443.depo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DepoAdapter extends RecyclerView.Adapter<DepoAdapter.DepoHolder> {

    Context context;
    ArrayList<DepolarimModel> depolarimList;


    public DepoAdapter(Context context, ArrayList<DepolarimModel> depolarimList) {
        this.context = context;
        this.depolarimList = depolarimList;
    }

    @NonNull
    @Override
    public DepoAdapter.DepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new DepoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DepoAdapter.DepoHolder holder, int position) {

        DepolarimModel depolarimmodel = depolarimList.get(position);

        holder.depoAdi.setText(depolarimmodel.getDepoAdi());
        holder.depoAdresi.setText(depolarimmodel.getDepoAdresi());
    }

    @Override
    public int getItemCount() {
        return depolarimList.size();
    }


    static class DepoHolder extends RecyclerView.ViewHolder {

        TextView depoAdi, depoAdresi;

        public DepoHolder(@NonNull View itemView) {
            super(itemView);
            depoAdresi = itemView.findViewById(R.id.txtDepoAdresi);
            depoAdi = itemView.findViewById(R.id.txtDepoAdi);


        }

    }
}
