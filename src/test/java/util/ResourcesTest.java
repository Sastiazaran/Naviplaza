package util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ResourcesTest {

    @Test
    void bundledSpritesAreOnTheClasspath() {
        assertTrue(Resources.exists("image1.png"));
        assertTrue(Resources.exists("image2.png"));
        assertTrue(Resources.exists("image3.png"));
        assertTrue(Resources.exists("naviplazatittle.png"));
        assertTrue(Resources.exists("naviplazastart.png"));
        assertTrue(Resources.exists("up.jpg"));
        assertTrue(Resources.exists("Resized/image1.png"));
        assertTrue(Resources.exists("StateImages/Santa/santa_posando.png"));
        assertTrue(Resources.exists("StateImages/Cliente/cliente_paseando.jpg"));
        assertTrue(Resources.exists("StateImages/Vendedora/vendedora_cobrando.png"));
    }

    @Test
    void imageDecodesFromClasspathNotFilesystem() throws IOException {
        assertNotNull(ImageIO.read(Resources.url("image3.png")));
        assertNotNull(ImageIO.read(Resources.url("StateImages/Santa/santa_posando.png")));
    }
}
