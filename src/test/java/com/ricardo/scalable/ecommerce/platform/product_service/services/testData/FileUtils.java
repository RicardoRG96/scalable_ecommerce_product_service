package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileUtils {

    public static byte[] createSamplePNG() throws IOException {
        // Crear una imagen en memoria
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.RED);
        graphics.fillOval(0, 0, 100, 100);
        graphics.dispose();

        // Convertir la imagen a un array de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

}
