import com.alan.clients.Client;
import java.io.File;
import java.util.Arrays;
import net.minecraft.client.main.Main;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Start {
    public Start() {
    }

    public static void main(String[] var0) {
        if (System.getProperty("os.name", "").toLowerCase().contains("mac")) {
            System.setProperty("java.awt.headless", "false");
            System.setProperty("org.lwjgl.opengl.Display.enableHighDPI", "false");
            startMacWindowRecovery();
        }

        String s = System.getProperty("user.home", ".");
        String s1 = System.getenv("APPDATA");
        String s2 = s1 != null ? s1 : s;
        //add code
        String gameDir = System.getProperty("rise.gameDir");
        File file1 = gameDir != null && !gameDir.isEmpty()
                ? new File(gameDir)
                : new File(s2, ".minecraft/");
        Main.main(
            concat(
                new String[]{
                    "--version",
                    Client.b,
                    "--accessToken",
                    "0",
                    "--gameDir",
                    new File(file1, ".").getAbsolutePath(),
                    "--assetsDir",
                    new File(file1, "assets/").getAbsolutePath(),
                    "--assetIndex",
                    "1.8",
                    "--userProperties",
                    "{}"
                },
                var0
            )
        );
    }

    private static void startMacWindowRecovery() {
        Thread recovery = new Thread(() -> {
            long deadline = System.currentTimeMillis() + 15000L;
            while (System.currentTimeMillis() < deadline && !Display.isCreated()) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            if (!Display.isCreated()) {
                return;
            }

            try {
                if (Display.isFullscreen()) {
                    Display.setFullscreen(false);
                }
                Display.setDisplayMode(new DisplayMode(1280, 720));
                Display.setResizable(true);
                Display.setLocation(100, 100);
                Display.update();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, "macOS display recovery");
        recovery.setDaemon(true);
        recovery.start();
    }

    public static <T> T[] concat(T[] var0, T[] var1) {
        Object[] aobject = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, aobject, var0.length, var1.length);
        return (T[])aobject;
    }
}
