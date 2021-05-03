public class Player extends Entity{
    private final long PID;

    Player(long PID, long EntityID){
        super(EntityID);
        this.PID = PID;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ENTITY_TYPE getEntityType(){
        return ENTITY_TYPE.PLAYER;
    }

}
