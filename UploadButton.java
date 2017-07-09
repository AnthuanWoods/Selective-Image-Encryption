// SimpleFileChooser.java
// A simple file chooser to see what it takes to make one of these work.
//

import edp.pictureEnc;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

    import java.io.BufferedInputStream ;
    import java.io.BufferedOutputStream ;
    import java.io.File ;
    import java.io.FileOutputStream ;
    import java.io.OutputStream ;

    import com.jcraft.jsch.Channel ;
    import com.jcraft.jsch.ChannelSftp ;
    import com.jcraft.jsch.JSch ;
    import com.jcraft.jsch.Session ;
    import java.io.FileInputStream ;

public class UploadButton extends JFrame {

    BufferedImage image;
    ImageIcon imageicon;
    JLabel label;
    JPanel pic;
    Graphics graph;
    Container c;
    int imageArray[] = new int[4];
    RegionSelectorListener region;

    public UploadButton() {
        super("File Chooser Test Frame");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        c = getContentPane();
        c.setLayout(new FlowLayout());

        JButton openButton = new JButton("Open");
        JButton encrpytButton = new JButton("Encrpyt");
        JButton decrpytButton = new JButton("Decrpyt");
        final JLabel statusbar = new JLabel("Output of your selection will go here");
        pic = new JPanel(new BorderLayout());

        // Create a file chooser that opens up as an Open dialog
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                int option = chooser.showOpenDialog(UploadButton.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        File sf = chooser.getSelectedFile();
                        String filelist = sf.getPath();
                        statusbar.setText("You chose " + filelist);
                        pic.removeAll();
                        image = ImageIO.read(sf);
                        imageicon = new ImageIcon(new ImageIcon(filelist).getImage());
                        label = new JLabel("", imageicon, JLabel.CENTER);
                        pic.add(label, BorderLayout.CENTER);
                        new RegionSelectorListener(label);
                        imageArray = RegionSelectorListener.imageSelector;
                        /*JComponent comp = new JComponent(){
                    public void paintComponent (Graphics g){
                        this.paintComponent(g);
                        g.setColor(Color.RED);
                        g.drawRect(imageArray[0], imageArray[1], imageArray[2], imageArray[3]);
                    }
                };
                c.add(comp);*/
                    }
                    catch (IOException ex) {
                        Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //paintComponent(graph);
                }
                else {
                    statusbar.setText("You canceled.");
                }
                c.add(pic);
            }
        });

        encrpytButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    System.out.println(imageArray[0]);
                    System.out.println(imageArray[1]);
                    System.out.println(imageArray[2]);
                    System.out.println(imageArray[3]);
                    pictureEnc.encryptButton(imageArray[0], imageArray[1], imageArray[2], imageArray[3]);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                }
                send();
            }
        });

        decrpytButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    download();
                   pictureEnc.decryptButton(imageArray[0], imageArray[1]);

                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UploadButton.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        c.add(openButton);
        c.add(encrpytButton);
        c.add(decrpytButton);
        c.add(statusbar);

    }


        /**
         * @param args
         */
        public void send() {

            String SFTPHOST = "138.197.140.96";
            int SFTPPORT = 22;
            String SFTPUSER = "root";
            String SFTPPASS = "***";
            String SFTPWORKINGDIR = "/";

            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;

            try {
                JSch jsch = new JSch();
                session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
                session.setPassword(SFTPPASS);
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                channel = session.openChannel("sftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
                channelSftp.cd("/");
                File f = new File("C:/Users/Owner/Pictures/combined.jpg");
                channelSftp.put(new FileInputStream(f), f.getName());
              //  channelSftp.get("/rugby.jpg", "/Desktop/rugby.jpg");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        public void download() {

            String SFTPHOST = "138.197.140.96";
            int SFTPPORT = 22;
            String SFTPUSER = "root";
            String SFTPPASS = "***";
            String SFTPWORKINGDIR = "/";

            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;

            try {
                JSch jsch = new JSch();
                session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
                session.setPassword(SFTPPASS);
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                channel = session.openChannel("sftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
                channelSftp.cd("/");
             //   File f = new File("C:/Users/Owner/Pictures/combined.jpg");
           //     channelSftp.put(new FileInputStream(f), f.getName());
                 channelSftp.get("/rugby.jpg","C:/Users/Owner/Desktop/rugby.jpg");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void download(String fileName, String localDir) {

            byte[] buffer = new byte[1024];
            BufferedInputStream bis;

            String SFTPHOST = "138.197.140.96";
            int SFTPPORT = 22;
            String SFTPUSER = "root";
            String SFTPPASS = "***";
            String SFTPWORKINGDIR = "/";

            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;


		try {
			// Change to output directory
			String cdDir = fileName.substring(0, fileName.lastIndexOf("/") + 1);
			channelSftp.cd(cdDir);

			File file = new File(fileName);
			bis = new BufferedInputStream(channelSftp.get(file.getName()));

			File newFile = new File(localDir + "/" + file.getName());

			// Download file
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();
			System.out.println("File downloaded successfully - "+ file.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

        public static void main(String args[]) {
            UploadButton sfc = new UploadButton();
            sfc.setVisible(true);
        }

}
