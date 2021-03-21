package com.Elements;

import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.ProdottoManager;

public class Prodotto extends Element {
    private String startTime;
    private String endTime;
    private int lenght;
    private int weight;
    private int sectionArea;
    private Colata colata;

    private String description;

    public Prodotto(ProdottoManager prodottoManager, String id, String startTime, String endTime, int lenght, int weight, int sectionArea, Colata colata, String description) throws PrototypeSystemAvoidance {
        super(id);

        this.startTime = startTime;
        this.endTime = endTime;
        this.lenght = lenght;
        this.weight = weight;
        this.sectionArea = sectionArea;
        this.colata = colata;
        this.description = description;
        if (prodottoManager.checkValidInitialization(this)) throw new PrototypeSystemAvoidance();
    }


    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getLenght() {
        return lenght;
    }

    public int getWeight() {
        return weight;
    }

    public int getSectionArea() {
        return sectionArea;
    }

    public Colata getColata() {
        return colata;
    }


    @Override
    public String toString() {
        return "Prodotto{" +
                "id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", lenght=" + lenght +
                ", weight=" + weight +
                ", sectionArea=" + sectionArea +
                ", colata=" + colata +
                ", deleted=" + deleted +
                '}';
    }
}
