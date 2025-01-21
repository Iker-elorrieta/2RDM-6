package vista;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageUtils {

    public static void ajustarImagenLabel(JLabel label, String rutaImagen) {
        // Cargar la imagen desde la ruta
        ImageIcon icon = new ImageIcon(rutaImagen);

        // Escalar la imagen al tamaño del JLabel
        Image scaledImage = icon.getImage().getScaledInstance(
                label.getWidth(), 
                label.getHeight(), 
                Image.SCALE_SMOOTH
        );

        // Crear un nuevo ImageIcon con la imagen escalada
        label.setIcon(new ImageIcon(scaledImage));
    }
}
