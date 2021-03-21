package com.Prototypes;

import com.Elements.Colata;

public class ProdottoPrototype extends PrototypeElement {
    private int length;
    private int weight;
    private int sectionArea;
    private Colata colata;
    private String description;

    public ProdottoPrototype(int length, int weight, int sectionArea, Colata colata, String descrizione) {
        this.length = length;
        this.weight = weight;
        this.sectionArea = sectionArea;
        this.colata = colata;
        this.description = descrizione;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
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
        return "ProdottoPrototype{" +
                "length=" + length +
                ", weight=" + weight +
                ", sectionArea=" + sectionArea +
                ", colata=" + colata +
                '}';
    }
}
