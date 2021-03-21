package com.Elements;

import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.AcciaioManager;

public class Acciaio extends Element {
    private String name;
    private String description;
    private boolean notUsable = false;
    private boolean usedOnce = false;

    public Acciaio(AcciaioManager acciaioManager, String name, String description, String id) throws PrototypeSystemAvoidance {
        super(id);
        this.name = name;
        this.description = description;
        if (acciaioManager.checkValidInitialization(this)) throw new PrototypeSystemAvoidance();
    }

    public void setNotUsable(boolean notUsable) {
        this.notUsable = notUsable;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNotUsable() {
        return notUsable;
    }

    public boolean isUsedOnce() {
        return usedOnce;
    }

    public void setUsedOnce(boolean usedOnce) {
        this.usedOnce = usedOnce;
    }

    @Override
    public String toString() {
        return "Acciaio{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", notUsable=" + notUsable +
                ", usedOnce=" + usedOnce +
                ", id='" + id + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
