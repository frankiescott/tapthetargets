package edu.binghamton.project6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScoreScreen implements Screen {
    private final MyGame app;
    private SpriteBatch batch;
    private Texture splashImg;

    private Stage stage;
    private TextButton submit;
    private TextField textField;
    private BitmapFont titleFont;
    private int score;

    public ScoreScreen(final MyGame app, int score) {
        super();
        this.app = app;
        this.score = score;
        batch = new SpriteBatch();
        splashImg = new Texture("bg.png");
        stage = new Stage(new ScreenViewport());
        textField = new TextField("Name", app.skin);
        configureWidgets();

        FreeTypeFontGenerator title = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BRUSHSCI.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 148;
        titleFont = title.generateFont(titleParameter);
    }

    public void configureWidgets() {
        submit = new TextButton("Submit", app.skin,"small");
        submit.getLabel().setFontScale(3.0f);
        submit.setSize(app.col_width*4,app.row_height*1.5F);
        submit.setPosition(app.col_width*4,app.row_height);
        submit.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //handle grabbing name and score and saving to leaderboard then switch back to menu screen
                String name = textField.getText();
                name = name.replaceAll("\\s", ""); //remove spaces
                try {
                    app.saveToLeaderboard(name, score);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                app.setScreen(new MenuScreen(app));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(submit);

        TextField.TextFieldStyle textFieldStyle = app.skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().setScale(3.0F);
        textField = new TextField("", textFieldStyle);
        textField.setSize(app.col_width*4,app.row_height*1.5F);
        textField.setPosition(app.width / 2 - (textField.getWidth() / 2),app.height / 2);

        textField.setMaxLength(8); //so entries aren't super long
        textField.setAlignment(1); //centered
        textField.setMessageText("Enter Name");
        stage.addActor(textField);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(splashImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleFont.draw(batch, "Submit Score",app.col_width*3.8F,Gdx.graphics.getHeight()-50);
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
        splashImg.dispose();
        stage.dispose();
        titleFont.dispose();
    }
}
