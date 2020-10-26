package model.Bullet;

        import java.awt.*;

public class BulletAnimExploding implements BulletAnimStrategy {
    Bullet context;
    public BulletAnimExploding(Bullet b)
    {
        this.context = b;

    }
    @Override
    public void animate() {
        context.color = Color.white;
        context.size++;
    }
}
