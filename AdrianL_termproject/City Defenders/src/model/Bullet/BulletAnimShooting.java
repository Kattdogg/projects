package model.Bullet;


import controller.Main;

public class BulletAnimShooting implements BulletAnimStrategy {
    Bullet context;

    public BulletAnimShooting(Bullet b)
    {
        this.context =b;
    }

    @Override
    public void animate() {
        double rad = Math.atan2(context.target.y - context.location.y, context.target.x - context.location.x);
        context.location.x += Main.turret.bulletMove * Math.cos(rad);
        context.location.y += Main.turret.bulletMove * Math.sin(rad);

    }
}
