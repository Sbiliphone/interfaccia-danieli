package com.Test;

import com.Elements.Acciaio;
import com.Elements.Colata;
import com.Elements.Prodotto;
import com.Exeptions.DataNotValid;
import com.Exeptions.NotDeletedFromManager;
import com.Exeptions.PrototypeSystemAvoidance;
import com.Managers.AcciaioManager;
import com.Managers.ColataManager;
import com.Managers.ProdottoManager;
import com.Prototypes.AcciaioPrototype;
import com.Prototypes.ColataPrototype;
import com.Prototypes.ProdottoPrototype;

public class MainTestWithoutDB {

    public static void prepareManagersForTest(AcciaioManager acciaioManager, ColataManager colataManager, ProdottoManager prodottoManager){

        acciaioManager.deleteAllElements();
        colataManager.deleteAllElements();
        prodottoManager.deleteAllElements();

        AcciaioPrototype aP = new AcciaioPrototype("INOX", "Molto resistente");
        Acciaio a = acciaioManager.createInDB(aP);


        ColataPrototype cP = new ColataPrototype(50, a);
        Colata c = colataManager.createInDB(cP);


        ProdottoPrototype pP = new ProdottoPrototype(20, 20, 20, c, "descrizione");
        Prodotto p = prodottoManager.createInDB(pP);

        System.out.println("Managers prepared for test!");
    }


    //TODO: Extra feature Colata: a colata with at least ONE product should not be erased.


    public static boolean testAcciaioExtraFunctions(AcciaioManager acciaioManager, ColataManager colataManager, ProdottoManager prodottoManager){

        System.out.println("testAcciaioExtraFunctions");

        AcciaioPrototype aP = new AcciaioPrototype("TYPEA", "Molto resistente");
        Acciaio aA = acciaioManager.createInDB(aP);

        aP = new AcciaioPrototype("TYPEB", "Non molto resistente");
        Acciaio aB = acciaioManager.createInDB(aP);

        ColataPrototype cP = new ColataPrototype(50, aA);
        colataManager.createInDB(cP);

        System.out.println("Before DELETE\nAcciaio Manager\n" + acciaioManager + "\nColata Manager\n" + colataManager);

        acciaioManager.deleteAllElements();

        System.out.println("After DELETE\nAcciaio Manager\n" + acciaioManager + "\nColata Manager\n" + colataManager);


        return true;
    }

    public static boolean testColataExtraFunctions(ColataManager colataManager, Acciaio a, String existingStatus, String notExistingStatus){

        System.out.println("testColataExtraFunctions");

        try{
            new Colata(colataManager, "e", -1, notExistingStatus, 30, "e", "e", a);
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            System.out.println("PSA thrown.");
        } catch (DataNotValid dataNotValid) {
            System.out.println("Data Not Valid exeption thrown! OK");
        }

        try{
            new Colata(colataManager, "e", -1, existingStatus, 30, "e", "e", a);
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            System.out.println("PSA thrown.");
        } catch (DataNotValid dataNotValid) {
            System.out.println("Data Not Valid exeption thrown! NOT OK");
        }
        System.out.println("Data Not Valid exeption not thrown! OK");
        return true;
    }



    public static boolean testNotDeletedFromManager(AcciaioManager acciaioManager){

        System.out.println("testNotDeletedFromManager");

        AcciaioPrototype aP = new AcciaioPrototype("INOX", "Molto resistente");
        acciaioManager.createInDB(aP);

        acciaioManager.deleteFromDB(acciaioManager.getSize()-1);

        System.out.println("Regular delete works");

        aP = new AcciaioPrototype("INOX", "Molto resistente");
        Acciaio a = acciaioManager.createInDB(aP);

        try {
            a.setDeleted(true, acciaioManager);
        } catch (NotDeletedFromManager notDeletedFromManager) {
            System.out.println("Irregular delete detected");
        }


        return true;
    }

    public static boolean testPrototypeSystem(AcciaioManager acciaioManager, ColataManager colataManager, ProdottoManager prodottoManager){

        System.out.println("testPrototypeSystem");

        AcciaioPrototype aP = new AcciaioPrototype("INOX", "Molto resistente");
        System.out.println("Acciao prototype = " + aP);

        Acciaio a = acciaioManager.createInDB(aP);
        System.out.println("Acciao = " + a);

        try{
            new Acciaio(acciaioManager, "error-name", "error-description", "error-id");
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            System.out.println("The creation was not successful. The system works for Acciaio.");
        }

        ColataPrototype cP = new ColataPrototype(50, a);
        Colata c = colataManager.createInDB(cP);

        try{
            new Colata(colataManager, "e", -1, "FAKE_STATUS", 30, "e", "e", a);
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            System.out.println("The creation was not successful. The system works for Colata.");
        } catch (DataNotValid dataNotValid) {
            dataNotValid.printStackTrace();
        }

        ProdottoPrototype pP = new ProdottoPrototype(20, 20, 20, c, "descrizione");
        Prodotto p = prodottoManager.createInDB(pP);

        try{
            new Prodotto(prodottoManager, "e", "e", "e", 2,2,2,c, "descrizione");
        } catch (PrototypeSystemAvoidance prototypeSystemAvoidance) {
            System.out.println("The creation was not successful. The system works for Prodotto.");
        }

        return true;
    }

    public static void main(String[] args) {

        AcciaioManager acciaioManager = new AcciaioManager();
        ColataManager colataManager = new ColataManager(acciaioManager);
        ProdottoManager prodottoManager = new ProdottoManager(colataManager);

        testPrototypeSystem(acciaioManager,colataManager,prodottoManager);

        testColataExtraFunctions(colataManager, acciaioManager.get(0), "DA_PRODURRE", "AAA");

        testAcciaioExtraFunctions(acciaioManager, colataManager, prodottoManager);

        testNotDeletedFromManager(acciaioManager);

    }

}
