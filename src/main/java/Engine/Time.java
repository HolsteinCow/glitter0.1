package Engine;

public class Time {
    public static float timeStart = System.nanoTime();

    public static float get(){
        return (float)((System.nanoTime() - timeStart) * 1E-9);
    }
}
