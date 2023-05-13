package com.example.noblee.NonActivityClasses.RecycleViewSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewSearch.ResultRecycleView.ResultAdapter;
import com.example.noblee.R;

import java.util.List;

public class ChercheAdapter extends RecyclerView.Adapter<ChercheHolder> {

    List<ItemSearchMagazin> magazinList;
    Context context;

    public ChercheAdapter(Context context,List<ItemSearchMagazin> magazinList) {
        this.magazinList = magazinList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChercheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChercheHolder(LayoutInflater.from(context).inflate(R.layout.layout_magazin_cherche,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChercheHolder holder, int position) {
        if (magazinList.size() > 0){
            ItemSearchMagazin magazin = magazinList.get(position);

            holder.magazin.setText("Chez :\n" + magazin.magazin);
            setUpRecycleView(magazin,holder);
        }
    }

    private void setUpRecycleView(ItemSearchMagazin magazin, ChercheHolder holder) {
        ResultAdapter resultAdapter = new ResultAdapter(context,magazin.produits);
        holder.resultRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.resultRecycleView.setAdapter(resultAdapter);

    }

    @Override
    public int getItemCount() {
        return magazinList.size();
    }
}
