package com.Managers;

import com.ApiLink.Endpoints;
import com.ApiLink.Requests;
import com.Elements.Acciaio;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Exeptions.RequestNotValid;
import com.Prototypes.AcciaioPrototype;

public class AcciaioManager extends Manager<Acciaio, AcciaioPrototype, JSONAcciaio> {

    public AcciaioManager() {
        fetchFromDB();
    }

    @Override
    public Acciaio createInDB(AcciaioPrototype element) {
        prototypes.add(element);

        JSONAcciaio json  = uploadToDB(element);

        if (json == null) return null;

        try {
            Acciaio a =  new Acciaio(this, element.getName(), element.getDescription(), json.id);
            elements.add(a);
            return a;
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            prototypeSystemAvoidance.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean checkValidInitialization(Acciaio justCreated) {
        if (isPassNext()){
            setPassNext(false);
            return false;
        }
        for (AcciaioPrototype p : prototypes){
            if(justCreated.getDescription().equals(p.getDescription()) &&
                    justCreated.getName().equals(p.getName())) return false;
        }
        return true;
    }


    @Override
    public void updateInDB(Acciaio oldElement, Acciaio newElement) {

    }


    @Override
    public void deleteFromDB(int index) {
        Acciaio a = elements.get(index);
        if (a.isUsedOnce()){
            a.setNotUsable(true);
            //TODO: Update DB
        }else{
            super.deleteFromDB(index);
        }
    }



    @Override
    public void fetchFromDB() {
        elements.clear();
        String[] strings = new String[0];
        try {
            strings = Requests.GETRequest(Endpoints.acciai);
        } catch (RequestNotValid requestNotValid) {
            System.out.println("Couldn't established connection. Data fetch failed");
        }
        for (String s : strings) {
            JSONAcciaio jsonAcciaio = gson.fromJson(s, JSONAcciaio.class);
            Acciaio a = addFromJSON(jsonAcciaio);
            elements.add(a);
        }
    }

    @Override
    public String convertToJSON(Acciaio acciaio) {
        JSONAcciaio j = new JSONAcciaio();
        j.Nome = acciaio.getName();
        j.Descrizione = acciaio.getDescription();
        j.id = acciaio.getId();
        return gson.toJson(j);
    }

    @Override
    public String convertPrototypeToJSONforCreation(AcciaioPrototype p) {
        JSONAcciaio j = new JSONAcciaio();
        j.Nome = p.getName();
        j.Descrizione = p.getDescription();
        return gson.toJson(j);
    }


    @Override
    public Acciaio addFromJSON(JSONAcciaio element) {
        setPassNext(true);
        try {
            return new Acciaio(this, element.Nome, element.Descrizione, element.id);
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            prototypeSystemAvoidance.printStackTrace();
        }
        return null;
    }

    @Override
    protected JSONAcciaio uploadToDB(AcciaioPrototype acciaio) {
        String json = convertPrototypeToJSONforCreation(acciaio);
        try {
            String s = Requests.POSTRequest(json, Endpoints.acciai);
            return gson.fromJson(s, JSONAcciaio.class);
        } catch (RequestNotValid requestNotValid) {
            System.out.println("POST Request of acciaio with name = " + acciaio.getName() + " failed. It won't be created!");
            return null;
        }
    }

    @Override
    protected void putRequestInDB() {

    }


}
