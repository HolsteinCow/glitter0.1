package Game;

import Game.ENTITY_TYPE;

public abstract class Entity {
    protected final long ENTITY_ID;
    int x, y;

    Entity(long EntityID){
        this.ENTITY_ID = EntityID;
    }

    abstract public int getX();
    abstract public int getY();

    abstract ENTITY_TYPE getEntityType();

}
