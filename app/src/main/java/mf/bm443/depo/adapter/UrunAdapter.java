package mf.bm443.depo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.models.UrunlerimModel;


public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.UrunHolder> implements Filterable{
    Context context;
    ArrayList<UrunlerimModel> urunlerimList;
    ArrayList<UrunlerimModel> urunlerimFullList;


    public UrunAdapter(Context context, ArrayList<UrunlerimModel> urunlerimList) {
        this.context = context;
        this.urunlerimFullList = urunlerimList;
        this.urunlerimList = new ArrayList<>(urunlerimFullList);


    }

    @NonNull
    @Override
    public UrunAdapter.UrunHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.urun_item, parent, false);
        return new UrunAdapter.UrunHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunAdapter.UrunHolder holder, int position) {

        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);


        UrunlerimModel urunlerimmodel = urunlerimList.get(position);
        holder.urunAdi.setText(urunlerimmodel.getUrunAdi());
        holder.urunDeposu.setText(urunlerimmodel.getUrunDeposu());
        //holder.urunKategori.setText(urunlerimmodel.getUrunKategori());
        holder.urunMiktar.setText(urunlerimmodel.getUrunMiktar());


        //Animation
        holder.itemView.startAnimation(anim);
        //CardView Dinleyicisi
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "İçeriğe tıklandı.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return urunlerimList.size();
    }

    @Override
    public Filter getFilter() {
        return urunlerFilter;
    }


    private final Filter urunlerFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<UrunlerimModel> filteredUrunList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0 ){
                filteredUrunList.addAll(urunlerimFullList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(UrunlerimModel m : urunlerimFullList){

                   if(m.getUrunAdi().toLowerCase().contains(filterPattern)){
                       filteredUrunList.add(m);
                   }

                }

            }

            FilterResults sonuc = new FilterResults();
            sonuc.values = filteredUrunList;
            sonuc.count = filteredUrunList.size();


            return sonuc;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults sonuc) {

            urunlerimList.clear();
            urunlerimList.addAll((ArrayList) sonuc.values);
            notifyDataSetChanged();

        }
    };










    public static class UrunHolder extends RecyclerView.ViewHolder {

        TextView urunAdi, urunDeposu, urunMiktar;

        public UrunHolder(@NonNull View itemView) {
            super(itemView);
            urunAdi = itemView.findViewById(R.id.UrunAdi);
            urunDeposu = itemView.findViewById(R.id.UrunDeposu);
            urunMiktar = itemView.findViewById(R.id.UrunMiktari);


        }
    }








}
