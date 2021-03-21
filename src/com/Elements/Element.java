package com.Elements;

import com.Exeptions.NotDeletedFromManager;
import com.Managers.Manager;

public abstract class Element {
    protected String id;
    protected boolean deleted = false;

    public Element(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted, Manager manager) throws NotDeletedFromManager {
        if (manager.checkRemoved(this) != deleted) throw new NotDeletedFromManager();
        this.deleted = deleted;
    }
}
