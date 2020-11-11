package edu.binghamton.project6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Texture splashImg, logo;

    private Stage stage;
    private TextButton play, controls, leaderboard;
    private OrthographicCamera camera; //used for positioning purposes

    public MenuScreen(final MyGame app) {
        super();
        this.app = app;
        batch = new SpriteBatch();
        splashImg = new Texture("bg.png");
        logo = new Texture("logo.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        configureButtons();
        camera = new OrthographicCamera(app.width, app.height);
        camera.position.set(app.width / 2, app.height / 2, 0);
        camera.update();
    }

    public void configureButtons() {
        play = new TextButton("Play", app.skin,"small");
        controls = new TextButton("How To Play", app.skin,"small");
        leaderboard = new TextButton("Leaderboard", app.skin,"small");

        play.getLabel().setFontScale(3.0f);
        play.setSize(app.col_width*4,app.row_height*1.5F);
        play.setPosition(app.col_width*4,Gdx.graphics.getHeight()-app.row_height*7);

        controls.getLabel().setFontScale(3.0f);
        controls.setSize(app.col_width*4,app.row_height*1.5F);
        controls.setPosition(app.col_width*4,(float) (Gdx.graphics.getHeight() - app.row_height*9));

        leaderboard.getLabel().setFontScale(3.0f);
        leaderboard.setSize(app.col_width*4,app.row_height*1.5F);
        leaderboard.setPosition(app.col_width*4,Gdx.graphics.getHeight() - app.row_height*11);

        play.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(new GameScreen(app));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        controls.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(new HowToPlayScreen(app));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        leaderboard.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                app.setScreen(new LeaderboardScreen(app));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(play);
        stage.addActor(controls);
        stage.addActor(leaderboard);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(logo, camera.position.x - (logo.getWidth() / 2), app.row_height*7);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        splashImg.dispose();
        logo.dispose();
    }
}