package com.example.proyectomov;

public class ItemListSingleton {

    private static final ItemListSingleton INSTANCE = new ItemListSingleton();
    private static ItemList itemList;

    private ItemListSingleton()
    {
        itemList = new ItemList();
    }

    public static ItemListSingleton getInstance() {
        return INSTANCE;
    }

    public static ItemList getItemList() {
        return itemList;
    }

    public static void setItemList(ItemList itemList) {
        ItemListSingleton.itemList = itemList;
    }
}
