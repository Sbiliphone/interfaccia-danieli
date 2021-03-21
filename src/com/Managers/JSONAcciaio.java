package com.Managers;

class JSONAcciaio extends JSONElement{
    public String id;
    public String Nome;
    public String Descrizione;
    public boolean NonProducibile;

    @Override
    public String toString() {
        return "JSONAcciaio{" +
                "id='" + id + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Descrizione='" + Descrizione + '\'' +
                ", NonProducibile=" + NonProducibile +
                '}';
    }
}
