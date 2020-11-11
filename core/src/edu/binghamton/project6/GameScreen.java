package edu.binghamton.project6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Stage stage;
    private Texture splashImg, objectImage;
    private BitmapFont countdownFont, scoreFont;

    private long startCount;
    private int countdown;
    private float w, h;

    private int score;

    private Array<Rectangle> objects;
    private long spawnTime;
    private int misTapPointLoss;
    private int streak;
    private int spawnModifier;

    public void gameEnd(int score) {
        app.setScreen(new ScoreScreen(app, score));
    }

    public GameScreen(final MyGame app) {
        super();
        this.app = app;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        splashImg = new Texture("bg.png");
        objectImage = new Texture("target.png");

        //score related variables
        score = 0;
        streak = 0;
        misTapPointLoss = 5; //will increase by 5 with each mistap
        spawnModifier = 800;
        //take care of fonts
        FreeTypeFontGenerator countDownGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/consola.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter countDownParameterLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter countDownParameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
        countDownParameterLarge.size = 148;
        countDownParameterSmall.size = 54;
        countdownFont = countDownGen.generateFont(countDownParameterLarge);
        scoreFont = countDownGen.generateFont(countDownParameterSmall);

        countdown = 3;
        startCount = TimeUtils.millis();

        objects = new Array<Rectangle>();
    }

    private void spawnObject() {
        Rectangle object = new Rectangle();
        object.x = MathUtils.random(app.col_width, app.width - app.col_width - 130);
        object.y = MathUtils.random(app.row_height, app.height - app.row_height - 130);
        object.width = 130;
        object.height = 130;
        spawnTime = TimeUtils.millis();
        objects.add(object);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //batch rendering
        batch.begin();
        //draw background
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //draw score display
        scoreFont.draw(batch, "[score:" + Integer.toString(score) + " | streak:" + Integer.toString(streak) + "]", 50, app.height - 50);
        //check if we are in the countdown period upon start of the game
        if (countdown > 0) {
            //if so, display countdown before game start
            countdownFont.draw(batch, Integer.toString(countdown),app.width / 2,app.height / 2);
            if (TimeUtils.millis() - startCount > 1000) {
                countdown -= 1;
                startCount = TimeUtils.millis();
            }
            batch.end();
        } else { //if we arent, generate game objects
            //draws array of rectangles to screen
            for (Rectangle object : objects) {
                batch.draw(objectImage, object.x, object.y);
            }
            //spawns a new rectangle every second
            if (TimeUtils.millis() - spawnTime > spawnModifier) {
                spawnObject();
                spawnModifier -= 10;
                if (spawnModifier < 100) {
                    gameEnd(score);
                }
            }
            //listens for input to see if a rectangle is touched
            if (Gdx.input.justTouched()) {
                int touched = 0;
                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                stage.getCamera().unproject(touch);
                for (int i = 0; i < objects.size; ++i) {
                    if (objects.get(i).contains(touch.x, touch.y)) {
                        touched++; //will use for bonus score if user gets rid of 2 boxes at once
                        objects.removeIndex(i);
                    }
                }
                calculateScoreGain(touched);
            }
            batch.end();
        }
        stage.act();
        stage.draw();
    }

    public void calculateScoreGain(int touched) {
        if (touched == 0) {
            score -= misTapPointLoss;
            misTapPointLoss += 5;
            streak = 0;
            if (score < 0) {
                score = 0;
            }
        } else if (touched == 1) {
            score = score + 50 + streak;
            streak += 1;
        } else {
            score = score + 125 + streak;
            streak += touched;
        }
    }

    @Override
    public void resize(int width, int height) {
        w = width;
        h = height;
    }

    @Override
    public void dispose() {
        splashImg.dispose();
        batch.dispose();
        countdownFont.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    //unneeded overrides from interfaces
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
}
