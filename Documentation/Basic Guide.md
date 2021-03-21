# Basic Guide for implementation


## Fake Server

This application has a fake server that can be started to allow the application to work with an actual server.

To use the server go the folder **server** and execute the following: 

```npm i``` to install the requirements and 

```npm start``` to start the actual server.

Any change to the file db.json will be syncronized.

Go to the page 

``http://localhost:3000/acciai`` to see if the application works.

The file **db.json** is the database. All changes will be visible in that file. 

Fo more reference, go [here](https://github.com/typicode/json-server), the homepage of **json-server**. 

## CRUD Operations 
To see concrete examples, see the examples in the **Examples** folder.

### Read
When AccaioManager, ColataManager and ProdottoManager are constructed, they all fetch the data from the server. 

It should not be necessary, but it's possible to call the function 

```java
public class example{
    public static void main(String[] args){
        AcciaioManager aM = new AcciaioManager();
        //The acciaioManager has already called the function fetchFromDB();
        aM.fetchFromDB();
        //This resets the aM and fetches again the elements from the server.
    }
}
```

### Create

To create an element, for coherence purposes, a Prototype must be created.
 
This assures that every initiated element is surely inside the database.
 
**This means that an element CANNOT be initiated directly. The exception PrototypeSystemAvoidance will be thrown.**

For every element the principle is exactly the same. 

```java

public class example{
    public static void main(String[] args){  
        
        AcciaioManager aM = new AcciaioManager();
        ColataManager cM = new ColataManager(aM);
        ProdottoManager pM = new ProdottoManager(cM);

        //Creation of Acciaio
         AcciaioPrototype aP = new AcciaioPrototype("INOX", "Example");
         Acciaio aA = aM.createInDB(aP);
 
        //Creation of Colata
         ColataPrototype cP = new ColataPrototype(22, aA);
         Colata cA = cM.createInDB(cP);
 
        //Creation of Prodotto
         ProdottoPrototype pP = new ProdottoPrototype(14, 25, 35, cA, "Descrizione prodotto");
         Prodotto p = pM.createInDB(pP);
    }
}
```

### Update

Yet to be implemented.

**The Elements Acciaio, Colata and Prodotto won't be editable! A new element must be created, and given to the manager. The manager will replace the old element with the new one, also in the DB.**

### Delete

Yet to be implemented.


## Extra Features

### State of Colata

An object Colata can only have a determined set of states.

This set is editable in the file **Constanst**.

If the state given is not present in the array, an exception DataNotValid is thrown.

### Removal of Acciao and Colata

If an Accaio has been used in at least one Colata, it will not be deletable. Upon removal, the boolean **Not Selectable** will be set to true. A Colata with a Not Selectable acciaio won't be created, and a DataNotValid exception will be thrown.

A Colata with at least one Prodotto cannot be deleted. 
