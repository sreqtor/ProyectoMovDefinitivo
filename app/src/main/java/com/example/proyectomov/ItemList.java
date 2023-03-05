package com.example.proyectomov;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ItemList {
    private static ArrayList<Item> items;
    private String FILENAME = "items4.sav";
    public ItemList() {
        items = new ArrayList<Item>();
    }
    public void setItems(ArrayList<Item> item_list) {
        items = item_list;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void deleteItem(int index) {
        items.remove(index);
    }
    public Item getItem(int index) {
        return items.get(index);
    }
    public void editItem(Item item, int index) { items.set(index, item); }

    public int getIndex(Item item) {
        int pos = 0;
        for (Item i : items) {
            if (item.getId().equals(i.getId())) {
                return pos;
            }

            pos++;
        }

        return -1;
    }

    public int getSize() {
        return items.size();
    }

    public void loadItems(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
            items = gson.fromJson(isr, listType);
            fis.close();
        } catch (FileNotFoundException e) {
            items = new ArrayList<Item>();
        } catch (IOException e) {
            items = new ArrayList<Item>();
        }
    }

    public void saveItems(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(items, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}