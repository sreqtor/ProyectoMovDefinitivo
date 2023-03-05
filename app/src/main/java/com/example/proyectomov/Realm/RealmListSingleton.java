package com.example.proyectomov.Realm;

public class RealmListSingleton {
    private static final RealmListSingleton INSTANCE = new RealmListSingleton();
    private static RealmList RealmList;

    private RealmListSingleton()
    {
        RealmList = new RealmList();
    }

    public static RealmListSingleton getInstance() {
        return INSTANCE;
    }

    public static RealmList getItemList() {
        return RealmList;
    }

    public static void setItemList(RealmList realmList) {
        RealmListSingleton.RealmList = RealmList;
    }
}
