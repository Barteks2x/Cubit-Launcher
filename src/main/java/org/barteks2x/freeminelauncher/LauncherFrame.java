package org.barteks2x.freeminelauncher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class LauncherFrame extends JFrame {

	JPanel background;
	private int build = 12;
	JTextField buildF;
	JCheckBox update;
	private static String OS = System.getProperty("os.name").toLowerCase();
	private String fastpng = "https://dl.dropboxusercontent.com/u/54602353/fastpng-1.0.jar",
			jinputjar = "https://dl.dropboxusercontent.com/u/54602353/jinput-2.0.5.jar",
			jinputdx8x64dll = "https://dl.dropboxusercontent.com/u/54602353/jinput-dx8_64.dll",
			jinputdx8dll = "https://dl.dropboxusercontent.com/u/54602353/jinput-dx8.dll",
			jinputraw64dll = "https://dl.dropboxusercontent.com/u/54602353/jinput-raw_64.dll",
			jinputrawdll = "https://dl.dropboxusercontent.com/u/54602353/jinput-raw.dll",
			jinputlinux64 = "https://dl.dropboxusercontent.com/u/54602353/libjinput-linux64.so",
			jinputlinux = "https://dl.dropboxusercontent.com/u/54602353/libjinput-linux.so",
			jinputosx = "https://dl.dropboxusercontent.com/u/54602353/libjinput-osx.jnilib",
			lwjgl64so = "https://dl.dropboxusercontent.com/u/54602353/liblwjgl64.so",
			lwjglosx = "https://dl.dropboxusercontent.com/u/54602353/liblwjgl.jnilib",
			lwjglso = "https://dl.dropboxusercontent.com/u/54602353/liblwjgl.so",
			oal64so = "https://dl.dropboxusercontent.com/u/54602353/libopenal64.so",
			oalso = "https://dl.dropboxusercontent.com/u/54602353/libopenal.so",
			lwjgl64dll = "https://dl.dropboxusercontent.com/u/54602353/lwjgl64.dll",
			utiljar = "https://dl.dropboxusercontent.com/u/54602353/lwjgl_util-2.8.5.jar",
			lwjgljar = "https://dl.dropboxusercontent.com/u/54602353/lwjgl-2.8.5.jar",
			lwjgldll = "https://dl.dropboxusercontent.com/u/54602353/lwjgl.dll",
			oaldll = "https://dl.dropboxusercontent.com/u/54602353/OpenAL32.dll",
			oal64dll = "https://dl.dropboxusercontent.com/u/54602353/OpenAL64.dll",
			oalosx = "https://dl.dropboxusercontent.com/u/54602353/openal.dylib";

	public LauncherFrame() {
		super("FREEMine Launcher");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(640, 480);
		this.setLocation(100, 100);
		this.setLayout(null);
		setBackground();
		addComponents();
	}

	static void start() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LauncherFrame();
			}
		});
	}

	private void addComponents() {
		JButton startButton = new MyButton("Start game");
		startButton.setLocation(640 / 2 - 100, 480 / 2 + 40);
		startButton.setSize(200, 20);
		background.add(startButton);
		startButton.addActionListener((ActionListener) startButton);
		//this.setComponentZOrder(startButton, 0);
		JLabel l = new JLabel("FREEMine");
		l.setText("FREEMine");
		l.setFont(new Font("Times New Roman", Font.PLAIN, 70));
		l.setForeground(new Color(230, 230, 255));
		l.setLocation(0, 100);
		l.setSize(640, 70);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVisible(true);
		background.add(l);
		JTextField nick = new JTextField();
		nick.setLocation(640 / 2 - 100, 480 / 2 - 40);
		nick.setSize(200, 20);
		nick.setText("Nick (Not implemented))");
		background.add(nick);
		JTextField pass = new JTextField();
		pass.setText("Password (Not implemented)");
		pass.setSize(nick.getSize());
		pass.setLocation(640 / 2 - 100, 480 / 2 - 15);
		background.add(pass);

		buildF = new JTextField();
		buildF.setText(String.valueOf(build));
		buildF.setLocation(startButton.getLocation().x + 210, startButton.getLocation().y - 23);
		buildF.setSize(startButton.getSize().height, startButton.getSize().height);
		background.add(buildF);

		JLabel buildText = new JLabel();
		buildText.setText("Build");
		buildText.setLocation(startButton.getLocation().x + 210 + 5 + startButton.getSize().height,
							  startButton.getLocation().y - 23);
		buildText.setSize(150, 16);
		buildText.setOpaque(false);
		buildText.setForeground(Color.white);
		background.add(buildText);

		update = new JCheckBox();
		update.setLocation(startButton.getLocation().x + 210, startButton.getLocation().y + 3);
		update.setText("Update");
		update.setSize(150, 16);
		update.setOpaque(false);
		update.setForeground(Color.white);
		background.add(update);
	}

	private void setBackground() {
		background = new ImagePanel(Thread.currentThread().getContextClassLoader().getResourceAsStream(
				"background.png"));
		background.setLocation(0, 0);
		background.setSize(640, 480);
		this.add(background);
		background.setLayout(null);
	}

	private class MyButton extends JButton implements ActionListener {

		public MyButton(String s) {
			super(s);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(update.isSelected()){
				new File("FREEMine").delete();
			}
			FileDownloader d = new FileDownloader("FREEMine");
			try {
				d.download(new URL(fastpng));
				d.download(new URL(lwjgljar));
				d.download(new URL(jinputjar));
				d.download(new URL(utiljar));
				String os = System.getProperty("os.name");
				FileDownloader d2 = new FileDownloader("FREEMine" + File.separator + "native");
				if (isWindows()) {
					d2.download(new URL(jinputdx8dll));
					d2.download(new URL(jinputdx8x64dll));
					d2.download(new URL(jinputraw64dll));
					d2.download(new URL(jinputrawdll));
					d2.download(new URL(lwjgl64dll));
					d2.download(new URL(lwjgldll));
					d2.download(new URL(oal64dll));
					d2.download(new URL(oaldll));
				}
				if (isUnix()) {
					d2.download(new URL(jinputlinux));
					d2.download(new URL(jinputlinux64));
					d2.download(new URL(lwjgl64so));
					d2.download(new URL(lwjglso));
					d2.download(new URL(oalso));
					d2.download(new URL(oal64so));
				}
				if (isMac()) {
					d2.download(new URL(jinputosx));
					d2.download(new URL(lwjglosx));
					d2.download(new URL(oalosx));
				}
				build = Integer.parseInt(buildF.getText());
				d.download(new URL(
						"https://buildhive.cloudbees.com/job/Barteks2x/job/FREEMine/" + build +
						"/org.Barteks2x$FREEMine/artifact/*zip*/archive.zip"),
						   "FREEMine.zip");
				File f = new File("FREEMine" + File.separator + "native" + File.separator);
				File fr = new File("FREEMine" + File.separator + "FREEMine.zip");
				File fmjar = new File("FREEMine" + File.separator + "FREEMine.jar");
				extractZipNative(fr, new File(d.directory));
				File dir = new File("FREEMine");
				File archivedir1 = new File("FREEMine/archive/org.Barteks2x/FREEMine/");
				File[] adirs = archivedir1.listFiles();
				File[] files = adirs[0].listFiles();
				File jarFile = null;
				for (File cf : files) {
					if (cf.getName().contains(".jar")) {
						jarFile = cf;
						break;
					}
				}
				jarFile.renameTo(fmjar);
				ProcessBuilder b = new ProcessBuilder("java", "-Djava.library.path=" + f.getAbsolutePath(),
													  "-jar", fmjar.getAbsolutePath());
				b.directory(dir);
				b.redirectErrorStream(true);
				b.redirectOutput(new File("log.txt"));
				b.start();
			} catch (MalformedURLException ex) {
				Logger.getLogger(LauncherFrame.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(LauncherFrame.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(LauncherFrame.class.getName()).log(Level.SEVERE, null, ex);

			}
		}
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

	public void extractZipNative(File fileZip, File directory) {
		try {
			byte[] buf = new byte[1024];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(
					new FileInputStream(fileZip));

			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {
				String entryName = zipentry.getName();
				System.out.println("entryname " + entryName);
				int n;
				FileOutputStream fileoutputstream = null;
				File newFile = new File(directory.getPath() + File.separator + entryName);

				if (newFile.isDirectory()) {
					zipinputstream.closeEntry();
					zipentry = zipinputstream.getNextEntry();
					continue;
				}
				newFile.mkdirs();
				newFile.createNewFile();
				try {
					fileoutputstream = new FileOutputStream(newFile);
				} catch (Exception e) {
					zipinputstream.closeEntry();
					zipentry = zipinputstream.getNextEntry();
					continue;
				}


				while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
					fileoutputstream.write(buf, 0, n);
				}

				fileoutputstream.close();
				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();

			}

			zipinputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}