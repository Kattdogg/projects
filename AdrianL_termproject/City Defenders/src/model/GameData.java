package model;

import java.util.ArrayList;

public class GameData {
    public ArrayList<GameObject> enemyObjects= new ArrayList<>();
    public ArrayList<GameObject> friendObjects= new ArrayList<>();
    public ArrayList<GameObject> fixedObjects= new ArrayList<>();
    public ArrayList<GameObject> removedObjects= new ArrayList<>();

    public void update() {

        if(removedObjects.isEmpty()==false) removedObjects.clear();



        removeDoneObjects(removedObjects, fixedObjects);

        removeDoneObjects(removedObjects, friendObjects);

        removeDoneObjects(removedObjects, enemyObjects);


    }

    public void removeDoneObjects(ArrayList<GameObject> removedObjects, ArrayList<GameObject> gameObjects) {
        for(var obj : gameObjects)
        {
            if (obj.done) removedObjects.add(obj);
            else obj.update();
        }
        gameObjects.removeAll(removedObjects);
        removedObjects.clear();
    }

    public void clear()
    {
        fixedObjects.clear();
        friendObjects.clear();
        enemyObjects.clear();
    }



}
