package com.game.dialog;

import com.game.Bomb;
import com.game.text.FontGame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URISyntaxException;

public class DiaLogGame extends Control {
    private Text text;
    private boolean showing;
    private int time;
    private Runnable runnable;
    private Media gamestartMusic;
    private Media gameplayMusic;
    private MediaPlayer mediaPlayer;
    private boolean audioPlaying;

    public DiaLogGame(Canvas camera, String text) {
        setCamera(camera);
        setX(0);
        setY(0);
        this.text = new Text(text);
        this.text.setFont(FontGame.MCHIGHER);
        showing = false;
        runnable = () -> {};
        try {
            gamestartMusic = new Media(getClass()
                    .getResource("/image/sound/gamestart1.mp3").toURI().toString());
            gameplayMusic = new Media(getClass()
                    .getResource("/image/sound/gameaudio.wav").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        audioPlaying = true;
    }

    public void draw(GraphicsContext render, GraphicsContext statusRender) {
        statusRender.clearRect(0, 0, 748, 48);
        statusRender.setFill(Color.BLACK);
        statusRender.fillRect(0, 0, 748, 48);
        render.setFill(Color.BLACK);
        render.fillRect(getX(), getY(), 1000, 1000);
        render.setFill(Color.WHITE);
        render.setFont(FontGame.MCHIGHER);
        render.fillText(text.getText(),
                getX() + 374 - text.getBoundsInLocal().getWidth() / 2,
                getY() + 312 - text.getBoundsInLocal().getHeight() / 2);
        time ++;
        if (time > 240) {
            showing = false;
            runnable.run();
        }
    }

    public void setOnEndOfDialog(Runnable e) {
        runnable = e;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public boolean isShowing() {
        return showing;
    }

    public void hide() {
        this.showing = false;
    }

    public void show() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(gamestartMusic);
        mediaPlayer.setOnEndOfMedia(this::createSound);
        mediaPlayer.play();
        this.showing = true;
        time = 0;
        audioPlaying = true;
    }

    public void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            audioPlaying = false;
        }
    }

    public boolean isAudioPlaying() {
        return audioPlaying;
    }

    public void createSound() {
        mediaPlayer = new MediaPlayer(gameplayMusic);
        mediaPlayer.setOnEndOfMedia(this::createSound);
        mediaPlayer.play();
    }
}
