package Game;

import Game.ENEMY_TYPE;
import Game.Entity;

abstract public class Enemy extends Entity {
    ENEMY_TYPE eType;

    Enemy(long EntityID, ENEMY_TYPE eType){
        super(EntityID);
        this.eType = eType;
    }
}
