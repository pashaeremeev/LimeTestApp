package com.example.practice;

import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EpgRepo {

    private static final String KEY_EPG = "epg";
    private PreferencesRepo preferencesRepo;

    public EpgRepo(Context context) {
        this.preferencesRepo = new PreferencesRepo(context);
    }

    public void saveEpgs(ArrayList<Epg> epgs) {
        String channelsJson = new Gson().toJson(epgs);
        preferencesRepo.save(channelsJson, KEY_EPG);
    }

    public ArrayList<Epg> getEpgs() {
        String listJsonString = preferencesRepo.get(KEY_EPG);
        Type type = new TypeToken<ArrayList<Epg>>(){}.getType();
        ArrayList<Epg> epgs = new Gson().fromJson(listJsonString, type);
        return (epgs == null) ? new ArrayList<>() : epgs;
    }

    public Epg getById(int id) {
        ArrayList<Epg> epgs = getEpgs();
        for (int i = 0; i < epgs.size(); i++) {
            Epg epg = epgs.get(i);
            if (epg.getChannelId() == id) {
                return epg;
            }
        }
        return null;
    }
}
