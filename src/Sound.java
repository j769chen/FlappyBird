import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

public class Sound {
    private AudioClip sfx;

    public Sound(String filePath) {
        sfx = new AudioClip(getClass().getResource(filePath).toExternalForm());
    }

    public void play() {
        sfx.play();
    }
}
