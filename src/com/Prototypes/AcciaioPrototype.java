package com.Prototypes;

public class AcciaioPrototype extends PrototypeElement {
    private String name;
    private String description;

    public AcciaioPrototype(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "AcciaioPrototype{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
