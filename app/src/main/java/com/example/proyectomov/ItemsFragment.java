package com.example.proyectomov;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ItemsFragment extends Fragment {

    ItemList item_list = new ItemList();
    View rootView = null;
    private ListView list_view = null;
    private ArrayAdapter<Item> adapter = null;
    private ArrayList<Item> selected_items;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        item_list.loadItems(context);
        this.inflater = inflater;
        this.container = container;
        return rootView;
    }

    public void setVariables(int resource, int id ) {
        rootView = inflater.inflate(resource, container, false);
        list_view = (ListView) rootView.findViewById(id);
    }
}
