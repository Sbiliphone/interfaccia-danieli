package com.Managers;

import com.ApiLink.Endpoints;
import com.ApiLink.Requests;
import com.Elements.Acciaio;
import com.Elements.Colata;
import com.Exeptions.DataNotValid;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Exeptions.RequestNotValid;
import com.Prototypes.ColataPrototype;

public class ColataManager extends Manager<Colata, ColataPrototype, JSONColata> {
    AcciaioManager acciaioManager;

    public ColataManager(AcciaioManager acciaioManager) {
        this.acciaioManager = acciaioManager;
        fetchFromDB();
    }


    @Override
    public Colata createInDB(ColataPrototype element) {
        prototypes.add(element);

        JSONColata json = uploadToDB(element);

        if (json == null) return null;

        try {
            Colata c = new Colata(this, json.id, json.Ordinamento, json.StatoColata, element.getUsableWeight(), json.OraFine, json.OraInizio, element.getAcciao());
            element.getAcciao().setUsedOnce(true);
            elements.add(c);
            return c;
        } catch (PrototypeSystemAvoidance | DataNotValid prototypeSystemAvoidance) {
            prototypeSystemAvoidance.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkValidInitialization(Colata justCreated) {
        if (isPassNext()){
            setPassNext(false);
            return false;
        }
        for (ColataPrototype p : prototypes){
            if (p.getUsableWeight() == justCreated.getUsableWeight()
                && p.getAcciao().getName().equals(justCreated.getAcciao().getName()))
                return false;
        }
        return true;
    }


    @Override
    public void deleteFromDB(int index) {
        Colata c = elements.get(index);
        if (!c.isUsedOnce()) {
            super.deleteFromDB(index);
        }else{
            System.out.println("Colata not deletable! Used in at least one prodotto");
        }
    }

    @Override
    public void fetchFromDB() {
        elements.clear();
        String[] strings = new String[0];
        try {
            strings = Requests.GETRequest(Endpoints.colate);
        } catch (RequestNotValid requestNotValid) {
            System.out.println("Couldn't established connection. Data fetch failed for Colata.");
        }
        for (String s : strings) {
            JSONColata jsonColata = gson.fromJson(s, JSONColata.class);
            try {
                Colata c = addFromJSON(jsonColata);
                elements.add(c);
            } catch (DataNotValid dataNotValid) {
                dataNotValid.printStackTrace();
            }
        }
    }

    @Override
    public String convertToJSON(Colata colata) {
        JSONColata j = new JSONColata();
        j.id = colata.getId();
        j.OraFine = colata.getEndTime();
        j.OraInizio = colata.getStartTime();
        j.Ordinamento = colata.getPositionInList();
        j.PesoProducibile = colata.getUsableWeight();
        j.TipoAcciaio = colata.getAcciao().getId();
        j.StatoColata = colata.getStatus();
        return gson.toJson(j);
    }

    @Override
    public String convertPrototypeToJSONforCreation(ColataPrototype p) {
        JSONColata j = new JSONColata();
        j.Ordinamento = elements.size();
        j.PesoProducibile = p.getUsableWeight();
        j.TipoAcciaio = p.getAcciao().getId();
        j.StatoColata = "DA_PRODURRE";
        return gson.toJson(j);
    }

    @Override
    public Colata addFromJSON(JSONColata element) throws DataNotValid {
        setPassNext(true);
        try {
            if (element.TipoAcciaio == null) throw new DataNotValid("Element " + element + " has  tipoAcciaio = null");
            Acciaio a = acciaioManager.getByID(element.TipoAcciaio);
            if (a == null) throw new DataNotValid("Acciaio with ID = " + element.TipoAcciaio + " doesn't exist");
            a.setUsedOnce(true);
            return new Colata(this, element.id, element.Ordinamento, element.StatoColata, element.PesoProducibile, element.OraFine, element.OraInizio, a);
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            prototypeSystemAvoidance.printStackTrace();
        }
        return null;
    }

    @Override
    protected JSONColata uploadToDB(ColataPrototype colataPrototype) {
        String json = convertPrototypeToJSONforCreation(colataPrototype);
        try {
            String s = Requests.POSTRequest(json, Endpoints.colate);
            return gson.fromJson(s, JSONColata.class);
        } catch (RequestNotValid requestNotValid) {
            System.out.println("POST Request of colata failed. It won't be created!");
            return null;
        }
    }

    @Override
    protected void putRequestInDB() {

    }

    @Override
    public void updateInDB(Colata oldElement, Colata newElement) {

    }


}
