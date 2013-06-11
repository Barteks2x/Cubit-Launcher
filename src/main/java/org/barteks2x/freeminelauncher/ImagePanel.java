package org.barteks2x.freeminelauncher;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private BufferedImage img;
	public ImagePanel(InputStream is){
		super();
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(is);
			img = ImageIO.read(iis);
			
		} catch (IOException ex) {
			Logger.getLogger(LauncherFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		Dimension dimension = new Dimension(img.getWidth(), img.getHeight());
        setPreferredSize(dimension);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, this);
    }
}
