package com.Prototypes;

import com.Elements.Acciaio;

public class ColataPrototype extends PrototypeElement {
    private int usableWeight;
    private Acciaio acciao;

    public ColataPrototype(int usableWeight, Acciaio acciao) {
        this.usableWeight = usableWeight;
        this.acciao = acciao;
    }

    public int getUsableWeight() {
        return usableWeight;
    }

    public Acciaio getAcciao() {
        return acciao;
    }

    @Override
    public String toString() {
        return "ColataPrototype{" +
                "usableWeight=" + usableWeight +
                ", acciao=" + acciao +
                '}';
    }
}
