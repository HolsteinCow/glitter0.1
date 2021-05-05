package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f proj, view;
    private Vector2f pos;

    public Camera(Vector2f pos) {
        this.pos = pos;
        this.proj = new Matrix4f();
        this.view = new Matrix4f();
    }

    public void orthogProj() {
        this.proj.identity();
        this.proj.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);

    }

    public Matrix4f getView() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.view.identity();
        this.view = view.lookAt(new Vector3f(pos.x, pos.y, 20.0f),
                cameraFront.add(pos.x, pos.y, 0.0f),
                cameraUp);
        return this.view;
    }

    public Matrix4f getProj() {
        return this.proj;
    }
}
