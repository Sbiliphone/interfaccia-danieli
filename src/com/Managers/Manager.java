package com.Managers;

import com.Elements.Element;
import com.Exeptions.DataNotValid;
import com.Exeptions.NotDeletedFromManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public abstract class Manager <T, ProtoT, JSONEl> {
    ArrayList<T> elements = new ArrayList<>();
    ArrayList<ProtoT> prototypes = new ArrayList<>();

    private boolean passNext = false;
    //This is needed as a pass for JSON created objects.

    Gson gson = new Gson();

    public abstract T createInDB(ProtoT element);

    public boolean isPassNext() {
        return passNext;
    }

    public void setPassNext(boolean passNext) {
        this.passNext = passNext;
    }

    public Element caster(T t){
        if (t instanceof Element){
            return (Element)t;
        }
        return null;
    }




    public T get(int index){
        return elements.get(index);
    }


    public ArrayList<T> getAll(){
        return elements;
    }

    public T getByID(String id){
        for(T t : elements){
            Element e = caster(t);
            assert e != null;
            if (e.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    protected void deleteFromDB(int index){
        T t = elements.remove(index);
        Element e = caster(t);
        try {
            e.setDeleted(true, this);
        } catch (NotDeletedFromManager notDeletedFromManager) {
            notDeletedFromManager.printStackTrace();
        }

        String id =  e.getId();

        //TODO: Delete from id
    }




    public void deleteAllElements(){
        for (int i = 0; i < elements.size(); i++) {
            deleteFromDB(i);
        }
    }

    public boolean checkRemoved(T t){
        return elements.indexOf(t) == -1;
    }

    public void putRequestInDB(T oldElement, T newElement){
        int index = elements.indexOf(oldElement);
        elements.set(index, newElement);

        //TODO: Update in DB

    }

    public void putRequestInDB(int indexOldElement, T newElement){
        elements.set(indexOldElement, newElement);

        //TODO: Update in DB
    }


    abstract public void fetchFromDB();

    public abstract boolean checkValidInitialization(T justCreated);


    public int getSize(){
        return elements.size();
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("MANAGER:\n");
        for (int i = 0; i < elements.size(); i++) {
            s.append("[").append(i).append("] ").append(elements.get(i)).append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    abstract public void updateInDB(T oldElement, T newElement);

    abstract public String convertToJSON(T t);

    abstract public String convertPrototypeToJSONforCreation(ProtoT p);

    abstract public T addFromJSON(JSONEl element) throws DataNotValid;

    abstract protected JSONEl uploadToDB(ProtoT t);

    abstract protected void putRequestInDB();
}
