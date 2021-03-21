package com.Managers;

import com.ApiLink.Endpoints;
import com.ApiLink.Requests;
import com.Constants.Constants;
import com.Elements.Colata;
import com.Elements.Prodotto;
import com.Exeptions.DataNotValid;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Exeptions.RequestNotValid;
import com.Prototypes.ProdottoPrototype;

public class ProdottoManager extends Manager<Prodotto, ProdottoPrototype, JSONProdotto> {
    ColataManager colataManager;

    public ProdottoManager(ColataManager colataManager) {
        this.colataManager = colataManager;
        fetchFromDB();
    }

    @Override
    public Prodotto createInDB(ProdottoPrototype element) {

        prototypes.add(element);

        JSONProdotto json = uploadToDB(element);

        if (json == null) return null;

        try {
            Prodotto p =  new Prodotto(this, Constants.fakeId, Constants.timeNotPresent, Constants.timeNotPresent, element.getLength(), element.getWeight(), element.getSectionArea(), element.getColata(), element.getDescription());
            element.getColata().setUsedOnce(true);
            elements.add(p);
            return p;
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            prototypeSystemAvoidance.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean checkValidInitialization(Prodotto justCreated) {
        if (isPassNext()){
            setPassNext(false);
            return false;
        }
        for (ProdottoPrototype p : prototypes){
            if(p.getLength() == justCreated.getLenght()
                    && p.getSectionArea() == justCreated.getSectionArea()
                    && p.getWeight() == justCreated.getWeight()) return false;
        }
        return true;
    }


    @Override
    public void fetchFromDB() {
        elements.clear();

        String[] strings = new String[0];

        try{
            strings = Requests.GETRequest(Endpoints.prodotti);
        }catch(RequestNotValid requestNotValid){
            System.out.println("Couldn't established connection. Data fetch failed for Prodotti.");
        }

        for(String s : strings){
            JSONProdotto jsonProdotto = gson.fromJson(s, JSONProdotto.class);
            try {
                Prodotto p = addFromJSON(jsonProdotto);
                elements.add(p);
            } catch (DataNotValid dataNotValid) {
                dataNotValid.printStackTrace();
            }

        }

    }


    @Override
    public String convertToJSON(Prodotto prodotto) {
        JSONProdotto j = new JSONProdotto();
        j.id = prodotto.getId();
        j.CodiceColata = prodotto.getColata().getId();
        j.Descrizione = prodotto.getDescription();
        j.FineProduzione = prodotto.getEndTime();
        j.InizioProduzione = prodotto.getStartTime();
        j.Lunghezza = prodotto.getLenght();
        j.Peso = prodotto.getWeight();
        j.Sezione = prodotto.getSectionArea();
        return gson.toJson(j);
    }

    @Override
    public String convertPrototypeToJSONforCreation(ProdottoPrototype p) {
        JSONProdotto j = new JSONProdotto();
        j.CodiceColata = p.getColata().getId();
        j.Descrizione = p.getDescription();
        j.Lunghezza = p.getLength();
        j.Peso = p.getWeight();
        j.Sezione = p.getSectionArea();
        return gson.toJson(j);
    }

    @Override
    public Prodotto addFromJSON(JSONProdotto element) throws DataNotValid {
        setPassNext(true);
        try{
            if (element.CodiceColata == null) throw new DataNotValid("Prodotto " + element.id + " has no codice colata!");
            Colata c = colataManager.getByID(element.CodiceColata);
            if (c == null) throw new DataNotValid("Colata wih id " + element.CodiceColata + " does not exist!");
            c.setUsedOnce(true);
            return new Prodotto(this, element.id, element.InizioProduzione, element.FineProduzione, element.Lunghezza, element.Peso, element.Sezione, c, element.Descrizione);
        }catch (PrototypeSystemAvoidance p){
            p.printStackTrace();
        }
        return null;
    }

    @Override
    protected JSONProdotto uploadToDB(ProdottoPrototype prodottoPrototype) {
        String json = convertPrototypeToJSONforCreation(prodottoPrototype);
        try {
            String s = Requests.POSTRequest(json, Endpoints.prodotti);
            return gson.fromJson(s, JSONProdotto.class);
        } catch (RequestNotValid requestNotValid) {
            requestNotValid.printStackTrace();
        }
        return null;
    }

    @Override
    protected void putRequestInDB() {

    }

    @Override
    public void updateInDB(Prodotto oldElement, Prodotto newElement) {

    }


}
