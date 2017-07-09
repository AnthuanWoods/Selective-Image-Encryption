package edp;

//all imports needed for the different encryptions
import static edp.EDP.ALGORITHM;
import static edp.EDP.PRIVATE_KEY_FILE;
import static edp.EDP.PUBLIC_KEY_FILE;
import static edp.EDP.areKeysPresent;
import static edp.EDP.decrypt;
import static edp.EDP.encrypt;
import static edp.EDP.generateKey;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class pictureEnc {
    //loads global variables, files needed for multiple functions
    public static BufferedImage bimg;
    public static BufferedImage bimg1;
    public static BufferedImage originalImgage;
    public static BufferedImage SubImgage;
    public static byte[] cipherText2;
    public static String enc_pic_string2;
    public static Key key;
    public static byte[] encrypted2;

    
    public static int orig_img[][];
    public static final String ALGORITHM = "RSA";
    public static final String PRIVATE_KEY_FILE = "C:/keys/private.key";
    public static final String PUBLIC_KEY_FILE = "C:/keys/public.key";
    
    public static File f = new File("C:\\Users\\Owner\\Pictures\\rugby.jpg");
    public static File z = new File("C:/Users/Owner/Pictures/rugby-enc.jpg");
    public static File encry = new File("C:/Users/Owner/Pictures/rugbyCropped-enc.jpg");
    public static File n = new File("C:/Users/Owner/Pictures/rugbyCropped.jpg");
    
    //used to generate keys for RSA algorithm
    public static void generateKey() {
        try {
            //initializes the key pairs
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            //creates the files to hold the keys
            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            // Create directories for the Private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();
            
            // Create directories for the Public key
            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Save the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Save the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checks to see if the keys are saved
    public static boolean areKeysPresent() {
        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);
        
        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    //return the file to be encrypted
    public static byte[] getFile2() {
        InputStream is = null;
        try {
            
            is = new FileInputStream(n);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        byte[] content = null;
        try {
            content = new byte[is.available()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            is.read(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public static byte[] getFile() {
        InputStream is = null;
        try {
            
            is = new FileInputStream(f);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        byte[] content = null;
        try {
            content = new byte[is.available()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            is.read(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    //encrypts the picture, returning a grayed picture
     public static byte[] encryptPicture2(Key key, byte[] content, int a, int b, int c, int d) throws FileNotFoundException, IOException {
        Cipher cipher;
        byte[] encrypted2 = null;
        try {
             cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SubImgage = originalImgage.getSubimage(a, b, c, d);
            File outputfile2 = new File("C:/Users/Owner/Pictures/rugbyCropped.jpg");
            ImageIO.write(SubImgage, "jpg", outputfile2); 
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted2 = cipher.doFinal(content);
            
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted2;
     }
     
     
    public static byte[] encryptPicture(Key key, byte[] content, int a, int b, int c, int d) throws FileNotFoundException, IOException {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            //encrypts the bytes from the image 
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
             
            //CREATING SUBIMAGE FOR ENCRYPTION
            originalImgage = ImageIO.read(new File("C:/Users/Owner/Pictures/rugby.jpg"));
            SubImgage = originalImgage.getSubimage(a, b, c, d);
            
             File outputfile1 = new File("C:/Users/Owner/Pictures/rugby.jpg");
             File outputfile2 = new File("C:/Users/Owner/Pictures/rugbyCropped.jpg");
        
	    ImageIO.write(originalImgage, "jpg", outputfile1);
            ImageIO.write(SubImgage, "jpg", outputfile2); 
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(content);
            
//grayscales the image and saves the image into the encryption file
            int count = 0;
            if (count == 0)
            {
                bimg = ImageIO.read(outputfile2);
           int width = bimg.getWidth();
            int height = bimg.getHeight();
            Graphics enc_grap = bimg.createGraphics();
            enc_grap.setColor(Color.gray);
            enc_grap.fillRect(0, 0, width, height);
            ImageIO.write(bimg, "jpg", encry);
            count = 1;
            }
            if(count ==1)
            {
            bimg1 = ImageIO.read(outputfile1);
           int width1 = bimg1.getWidth();
            int height1 = bimg1.getHeight();
            Graphics enc_grap = bimg1.createGraphics();
            enc_grap.setColor(Color.gray);
            enc_grap.fillRect(0, 0, width1, height1);
            ImageIO.write(bimg1, "jpg", z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    //decrypts the bytes from the Picture with the AES key
    public static byte[] decryptPicture(Key key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    //saves the decrypted file in the folder, commented line deletes the encrypted file
    public static void saveFile(byte[] bytes) throws IOException {
        //encry.delete();
        FileOutputStream fos = new FileOutputStream("C:/Users/Owner/Pictures/rugby-dec.jpg");
       
        fos.write(bytes);
        
        fos.close();

    }
    public static void saveFile2(byte[] bytes) throws IOException{
        FileOutputStream fos1 = new FileOutputStream("C:/Users/Owner/Pictures/rugbyCropped-dec.jpg");
        fos1.write(bytes);
                fos1.close();
    }
    
public static void mergePics(int orix, int oriy) throws IOException{
     //MERGE PICTURES
            
            BufferedImage combined = new BufferedImage(480, 270, BufferedImage.TYPE_INT_ARGB); 
            BufferedImage large = null;
            BufferedImage small = null;
            large =ImageIO.read(new File("C:/Users/Owner/Pictures/rugby.jpg"));
            small = ImageIO.read(new File("C:/Users/Owner/Pictures/rugbyCropped-enc.jpg"));
            Graphics g = combined.getGraphics();
            g.drawImage(large, 0, 0, null);
            g.drawImage(small, orix, oriy, null);
            ImageIO.write(combined, "jpg", new File("C:/Users/Owner/Pictures", "combined.jpg"));
            
}
public static void mergePics2(int orix, int oriy) throws IOException{
     //MERGE PICTURES
            
            BufferedImage combined2 = new BufferedImage(480, 270, BufferedImage.TYPE_INT_ARGB); 
            BufferedImage large = null;
            BufferedImage small = null;
            large =ImageIO.read(new File("C:/Users/Owner/Pictures/combined.jpg"));
            small = ImageIO.read(new File("C:/Users/Owner/Pictures/rugbyCropped-dec.jpg"));
            Graphics g = combined2.getGraphics();
            g.drawImage(large, 0, 0, null);
            g.drawImage(small, orix, oriy, null);
            ImageIO.write(combined2, "jpg", new File("C:/Users/Owner/Pictures", "FINALCOMBINED-DEC.jpg"));
            
}
    //encrypts the key input with the public key
    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    //decrypts the text input using the private key
    public static String decrypt(byte[] text, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String(dectyptedText);
    }

    public static void encryptButton(int a, int b, int c, int d)
            
            throws NoSuchAlgorithmException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
       
        try {

            // Check if the pair of keys are present else generate those.
            if (!areKeysPresent()) {
                generateKey();
            }
            
            //generates AES symmetric key to be used on the image
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            key = keyGenerator.generateKey();
            System.out.println(key);

            //sets content to equal the picture
            byte[] content = getFile();
            System.out.println(content);
            //converts the AES key to a string to be used in the RSA algorithm
            String aes_string = key.toString();
            ObjectInputStream inputStream = null;

            //encrypts the picture and returns the encrypted bytes
            byte[] encrypted = encryptPicture(key, content, a, b, c, d);
            System.out.println(encrypted);
            //converts encrypted picture to string
            String enc_pic_string = encrypted.toString();
            // Encrypt the encryptred picture AES string using the public key
            inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            final PublicKey publicKey = (PublicKey) inputStream.readObject();
            final byte[] cipherText = encrypt(enc_pic_string, publicKey);
            // Decrypt the cipher text using the private key
            inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
            final String plainText = decrypt(cipherText, privateKey);
            //if the AES string is equal to plaintext then the correct user has the files
            if (enc_pic_string.equals(plainText)) {
                //decrypts the picture using the original AES key
                mergePics(a,b);
                byte[] decrypted = decryptPicture(key, encrypted);
                System.out.println(decrypted);
                saveFile(decrypted);
                
            }
            //sets content to equal the picture
            byte[] content2 = getFile2();
            System.out.println(content2);
            //converts the AES key to a string to be used in the RSA algorithm
            aes_string = key.toString();
            ObjectInputStream inputStream2 = null;

            //encrypts the picture and returns the encrypted bytes
            encrypted2 = encryptPicture(key,content2, a, b, c, d);
            System.out.println(encrypted2);
            //converts encrypted picture to string
            enc_pic_string2 = encrypted2.toString();
            // Encrypt the encryptred picture AES string using the public key
            inputStream2 = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            final PublicKey publicKey2 = (PublicKey) inputStream2.readObject();
            cipherText2 = encrypt(enc_pic_string2, publicKey2);
        
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
            
            
    public static void decryptButton(int a, int b)            
            throws NoSuchAlgorithmException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        try {
            // Decrypt the cipher text using the private key
            ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            final PrivateKey privateKey2 = (PrivateKey) inputStream2.readObject();
            final String plainText2 = decrypt(cipherText2, privateKey2);
          
            if(enc_pic_string2.equals(plainText2)) {
                //decrypts the picture using the original AES key
               
                byte[] decrypted2 = decryptPicture(key, encrypted2);
                System.out.println(decrypted2);
                saveFile2(decrypted2);
                mergePics2(a,b); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
