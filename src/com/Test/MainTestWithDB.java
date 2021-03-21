package com.Test;

import com.Elements.Acciaio;
import com.Elements.Colata;
import com.Elements.Prodotto;
import com.Managers.AcciaioManager;
import com.Managers.ColataManager;
import com.Managers.ProdottoManager;
import com.Prototypes.AcciaioPrototype;
import com.Prototypes.ColataPrototype;
import com.Prototypes.ProdottoPrototype;

public class MainTestWithDB {

    public static void testCreation(AcciaioManager aM, ColataManager cM, ProdottoManager pM){
        System.out.println("aM before creation = " + aM);

        AcciaioPrototype aP = new AcciaioPrototype("TYPEC", "Final");
        Acciaio aA = aM.createInDB(aP);

        System.out.println("aM after creation = " +  aM);



        System.out.println("cM before creation = " + cM);

        ColataPrototype cP = new ColataPrototype(22, aA);
        Colata cA = cM.createInDB(cP);

        System.out.println("cM after creation = " +  cM);



        System.out.println("pM before creation = " + pM);

        ProdottoPrototype pP = new ProdottoPrototype(14, 25, 35, cA, "Descrizione prodotto");
        Prodotto p = pM.createInDB(pP);

        System.out.println("pM after creation = " +  pM);
    }

    public static void testFetch(AcciaioManager aM, ColataManager cM, ProdottoManager pM){
        aM.fetchFromDB();
        System.out.println("aM = " + aM);

        cM.fetchFromDB();
        System.out.println("cM = " + cM);

        System.out.println("aM = " + aM);

        pM.fetchFromDB();
        System.out.println("pM = " + pM);
    }

    public static void main(String[] args) {
        AcciaioManager aM = new AcciaioManager();
        ColataManager cM = new ColataManager(aM);
        ProdottoManager pM = new ProdottoManager(cM);


        testFetch(aM, cM, pM);

        testCreation(aM, cM, pM);

    }
}
