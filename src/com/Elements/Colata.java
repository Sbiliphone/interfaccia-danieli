package com.Elements;

import com.Constants.Constants;
import com.Exeptions.DataNotValid;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.ColataManager;

import java.util.Arrays;

public class Colata extends Element{
    private int positionInList;
    private String status;
    private int usableWeight;
    private String endTime;
    private String startTime;
    private Acciaio acciao;
    private boolean usedOnce = false;

    private boolean checkStatusValidity(){
        for(String s: Constants.acceptableStatuses){
            if (s.equals(this.status)) return true;
        }
        return false;
    }

    public Colata(ColataManager colataManager, String id, int positionInList, String status, int usableWeight, String endTime, String startTime, Acciaio acciao) throws PrototypeSystemAvoidance, DataNotValid {
        super(id);
        this.positionInList = positionInList;
        this.status = status;
        this.usableWeight = usableWeight;
        this.endTime = endTime;
        this.startTime = startTime;
        this.acciao = acciao;

        if (acciao.isNotUsable()) throw new DataNotValid("The acciaio is not usable! It has been deleted!");

        //Make acciaio not deletable
        acciao.setUsedOnce(true);

        if (!checkStatusValidity()) throw new DataNotValid("Colata status not valid!");
        if (colataManager.checkValidInitialization(this)) throw new PrototypeSystemAvoidance();
    }

    public boolean isUsedOnce() {
        return usedOnce;
    }

    public void setUsedOnce(boolean usedOnce) {
        this.usedOnce = usedOnce;
    }

    public String getId() {
        return id;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public String getStatus() {
        return status;
    }

    public int getUsableWeight() {
        return usableWeight;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public Acciaio getAcciao() {
        return acciao;
    }


    @Override
    public String toString() {
        return "Colata{" +
                "id='" + id + '\'' +
                ", positionInList=" + positionInList +
                ", status='" + status + '\'' +
                ", usableWeight=" + usableWeight +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", acciao=" + acciao +
                ", usedOnce=" + usedOnce +
                ", acceptableStatuses=" + Arrays.toString(Constants.acceptableStatuses) +
                ", deleted=" + deleted +
                '}';
    }
}
