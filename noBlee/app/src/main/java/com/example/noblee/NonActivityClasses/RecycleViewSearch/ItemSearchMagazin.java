package com.example.noblee.NonActivityClasses.RecycleViewSearch;

import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchMagazin {
    String magazin;
    public List<ItemProduit> produits = new ArrayList<>();
    public ItemSearchMagazin(String magazin) {
        this.magazin = magazin;
    }
}
