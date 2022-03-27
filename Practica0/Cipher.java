import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.*;

public class Cipher extends JFrame{
    static JPanel panel;
	static JButton bCifrar, bDescifrar,bFile;
    static JLabel texto1, texto2, texto3;
    static JTextField key;
    static String fileName;
    static String mesg = "";
    static String cipherText;
    static String currentDir = System.getProperty("user.dir");

    public Cipher(){
        iniciaCipher();
    }

    private void iniciaCipher(){
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        Border blackline = BorderFactory.createLineBorder(Color.black);
        texto1 = new JLabel("Selecciona el archivo para cifrar/descifrar");
        texto1.setBounds(10,20,250,20);
        //texto1.setBorder(blackline);
        panel.add(texto1);

        bFile = new JButton("Archivo");
        bFile.setBounds(280, 15, 80, 30);
        bFile.setBackground(new Color(255, 255, 255));
        bFile.setFocusPainted(false);
        bFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                selFile();
            }
        });
        panel.add(bFile);
        
        texto2 = new JLabel("Escribe tu llave para cifrar/descifrar");
        texto2.setBounds(10,60,230,20);
        panel.add(texto2);

        key = new JTextField();
        key.setBounds(250,55,200,30);
        panel.add(key);

        bCifrar = new JButton("Cifrar");
        bCifrar.setBounds(130,110, 80, 30);
        bCifrar.setBackground(new Color(255, 255, 255));
        bCifrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mesg = mesg.toLowerCase();
                String msgCifrado = cipherVigenere(mesg,key.getText());
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName+"_C.txt"));
                    writer.write(msgCifrado);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Tu mensaje se ha cifrado correctamente."); 
                System.exit(0);
            }
        });
        panel.add(bCifrar);

        bDescifrar = new JButton("Descifrar");
        bDescifrar.setBounds(270,110, 100, 30);
        bDescifrar.setBackground(new Color(255, 255, 255));
        bDescifrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //decipherVigenere();
            }
        });
        panel.add(bDescifrar);

        setSize(500,200);
        setTitle("Cifrado y descifrado de Vigenere :D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void selFile(){
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");        
        File dir = new File(currentDir);

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(dir);

        int respuesta = fc.showOpenDialog(null);

        if(respuesta == JFileChooser.APPROVE_OPTION){
            File archivo = fc.getSelectedFile();
            fileName = archivo.getName().replaceFirst("[.][^.]+$", "");
            try {
                mesg = new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            mesg = mesg.replace("\n","").replace("\r","");
            mesg = mesg.replace("\s","").replace("\r","");
            mesg = mesg.replaceAll("[^a-zA-Z0-9]", "");
        }

        /*
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("aaa.txt"));
            writer.write(mesg);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private String cipherVigenere(String text, String key){
        String res = "";
        int j = 0;
        //recorrer todo el text
        for(int i =0; i<text.length(); i++){
            char c = text.charAt(i);
            int ascci = c;
            ascci = ascci - 97;
            //Tenemos el valor de la letra en alfabeto ingles
            if(j== key.length()){
                j = 0;
            }
            char llave = key.charAt(j);
            j++;
            int codllave = llave - 97;
            int cifrado = (codllave + ascci) %25;
            cifrado = cifrado + 97;
            c = (char) cifrado;
            res = res + Character.toString(c);
        }
        return res;
    }

    private String decipherVigenere(String text, String key){
        String res = "";
        
        return res;
    }

    public static void main(String args[]) throws IOException {
        Cipher cifrador = new Cipher();
    }
}
