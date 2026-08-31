package util;

import java.awt.Image;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

/**
 * Loads bundled images from the classpath so the app works from any working
 * directory, as a JAR, and on Windows, macOS, and Linux.
 */
public final class Resources {
    private static final String PREFIX = "/Imagenes/";
    private static final Map<String, ImageIcon> CACHE = new ConcurrentHashMap<>();

    private Resources() {
    }

    public static URL url(String relativePath) {
        URL url = Resources.class.getResource(PREFIX + relativePath);
        if (url == null) {
            throw new IllegalStateException("Missing image resource: " + PREFIX + relativePath);
        }
        return url;
    }

    public static boolean exists(String relativePath) {
        return Resources.class.getResource(PREFIX + relativePath) != null;
    }

    public static ImageIcon icon(String relativePath) {
        return CACHE.computeIfAbsent(relativePath, path -> new ImageIcon(url(path)));
    }

    public static Image scaled(String relativePath, int width, int height) {
        return icon(relativePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
