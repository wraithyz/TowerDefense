package com.mygdx.towerdefense.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.towerdefense.TowerDefense;
import com.mygdx.towerdefense.sprites.Enemy;

import java.util.ArrayList;

public class WorldCreator
{
    private BodyDef bdef;
    private Body platform;
    private Body playerBody;
    private FixtureDef fdef;
    private Body ball;

    private ImageButton imageButton;
    private ImageButton.ImageButtonStyle imageButtonStyle;

    private ArrayList<Body> playerBodies = new ArrayList<Body>();

    private static float ppt = 0;

    public WorldCreator(World world, TiledMap map, Enemy enemy)
    {
        buildShapes(map, world, enemy);
    }

    public Array<Body> buildShapes(TiledMap map, World world, Enemy enemy)
    {
        ppt = TowerDefense.PPM;
        MapObjects objects = map.getLayers().get("Collision").getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects)
        {

            if (object instanceof TextureMapObject)
            {
                continue;
            }

            Shape shape;
            float x = 0f;
            float y = 0f;
            float width = 0f;
            float height = 0f;

            if (object.getName().equals("start"))
            {
                Rectangle startPos = ((RectangleMapObject) object).getRectangle();
                continue;
            }

            else if (object instanceof RectangleMapObject)
            {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                x = rect.getX();
                y = rect.getY();
                width = rect.getWidth();
                height = rect.getHeight();
                shape = getRectangle((RectangleMapObject) object);
            }
            else if (object instanceof PolygonMapObject)
            {
                shape = getPolygon((PolygonMapObject) object);
            }
            else if (object instanceof PolylineMapObject)
            {
                shape = getPolyline((PolylineMapObject) object);
            }
            else if (object instanceof CircleMapObject)
            {
                shape = getCircle((CircleMapObject) object);
            }
            else
            {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.position.set((x + width * 0.5f) / ppt, (y + height  * 0.5f) / ppt);
            fdef = new FixtureDef();
            fdef.shape = shape;

            if (object.getName().equals("start") || object.getName().equals("test"))
            {
                bd.type = BodyDef.BodyType.DynamicBody;
                //fdef.filter.categoryBits = B2DVars.BIT_BALL;
                //fdef.filter.maskBits = B2DVars.BIT_GROUND;
            }
            else
            {
                bd.type = BodyDef.BodyType.StaticBody;
                //fdef.filter.categoryBits = B2DVars.BIT_GROUND;
                //fdef.filter.maskBits =  B2DVars.BIT_BALL;
            }
            platform = world.createBody(bd);

            platform.createFixture(fdef);

            bodies.add(platform);

            shape.dispose();

        }
        return bodies;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject)
    {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                rectangle.height * 0.5f / ppt,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject)
    {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject)
    {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i)
        {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

}
