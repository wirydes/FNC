package fnc;
/*
Created by the WaveDash Programation Team
Ángel Millán
LJ Rivera
Aurio Robles
Duzho Garcia
*/
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FNC extends JFrame implements ActionListener, KeyListener{
    Postfijo pos=new Postfijo();
    Formula F = new Formula();
    
    public FNC() {
        Image icon = new ImageIcon(getClass().getResource("/Recursos/Icon.png")).getImage();
        setIconImage(icon);
        setTitle("Evaluar");
        setSize(360,470);
        setBackground(Color.white);
        Ventana();
        setResizable(false);
       setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        infijo.requestFocus();
    }
    
    public void Ventana(){        
        fondo = new JPanel ();
        fondo.setBackground(color);
        getContentPane().add(fondo);
        fondo.setLayout(null); 
        
        Abrir = new JButton();
        Abrir.setIcon(new ImageIcon((this.getClass().getResource("/Recursos/Folder.png"))));
        Abrir.setBorder(null);
        Abrir.setBounds(20, 30, 24, 22);
        Abrir.setBackground(color);
        Abrir.setToolTipText("Seleccionar TXT");
        Abrir.addActionListener(this);
        fondo.add(Abrir);
        
        archi = new JLabel();
        archi.setText("Cargar .txt");
        archi.setForeground(Color.WHITE);
        archi.setBorder(null);
        archi.setBounds(5, 10, 60, 15);
        fondo.add(archi);
        
        infijo= new JTextField();
        infijo.setBounds(67, 30, 250, 25);
        infijo.setBackground(Color.WHITE);
        fondo.add(infijo);
        
        field = new JLabel();
        field.setText("Ingresar el infijo");
        field.setBorder(null);
        field.setBounds(150, 10, 160, 15);
        field.setForeground(Color.WHITE);
        fondo.add(field);
        
        Run = new JButton();
        Run.setIcon(new ImageIcon((this.getClass().getResource("/Recursos/Run.png"))));
        Run.setBorder(null);
        Run.setBounds(321, 30, 24, 24);
        Run.setBackground(color);
        Run.setToolTipText("Evalúar");
        Run.addActionListener( this );
        fondo.add(Run);
        
        intentos = new JLabel();
        intentos.setText("NÚMERO DE INTENTOS");
        intentos.setBorder(null);
        intentos.setBounds(25, 70, 160, 15);
        intentos.setForeground(Color.WHITE);
        fondo.add(intentos);
        
        intentos = new JLabel();
        intentos.setText("X:");
        intentos.setBorder(null);
        intentos.setBounds(40, 90, 80, 25);
        intentos.setForeground(Color.WHITE);
        fondo.add(intentos);
        
        Int= new JTextField();
        Int.setBounds(65, 90, 80, 25);
        Int.setBackground(Color.WHITE);
        Int.addKeyListener(this);
        fondo.add(Int);
        
        otro = new JLabel();
        otro.setText("NÚMERO DE CAMBIOS");
        otro.setBorder(null);
        otro.setBounds(180, 70, 160, 15);
        otro.setForeground(Color.WHITE);
        fondo.add(otro);
        
        otro = new JLabel();
        otro.setText("Y:");
        otro.setBorder(null);
        otro.setBounds(205, 90, 80, 25);
        otro.setForeground(Color.WHITE);
        fondo.add(otro);
        
        Otro= new JTextField();
        Otro.setBounds(230, 90, 80, 25);
        Otro.setBackground(Color.WHITE);
        Otro.addKeyListener(this);
        fondo.add(Otro);
        
        post = new JLabel();
        post.setText("POSTFIJO:");
        post.setBorder(null);
        post.setBounds(140, 130, 160, 15);
        post.setForeground(Color.WHITE);
        fondo.add(post);
        
        postfijo= new JTextField();
        postfijo.setBounds(25, 150, 307, 25);
        postfijo.setBackground(Color.WHITE);
        postfijo.setEditable(false);
        fondo.add(postfijo);
        
        formulalbl = new JLabel();
        formulalbl.setText("FORMULA:");
        formulalbl.setBorder(null);
        formulalbl.setForeground(Color.WHITE);
        formulalbl.setBounds(140, 185, 160, 15);
        fondo.add(formulalbl);
        
        pFormula = new JTextArea();
        pFormula.setEditable(false);
        terminado = new JScrollPane( pFormula , JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        terminado.setBounds(25, 205, 310, 40);
        fondo.add(terminado);
        
        formulalbl = new JLabel();
        formulalbl.setText("SOLUCIÓN:");
        formulalbl.setBorder(null);
        formulalbl.setForeground(Color.WHITE);
        formulalbl.setBounds(140, 260, 160, 15);
        fondo.add(formulalbl);

        Solucion = new JTextArea();
        Solucion.setEditable(false);
        terminado2 = new JScrollPane( Solucion , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        terminado2.setBounds(25, 285, 310, 80);
        fondo.add(terminado2);
        
        Borrarlbl = new JLabel();
        Borrarlbl.setText("Borrar Campos");
        Borrarlbl.setBorder(null);
        Borrarlbl.setForeground(Color.WHITE);
        Borrarlbl.setBounds(30, 370, 160, 15);
        fondo.add(Borrarlbl);
        
        Borrar = new JButton();
        Borrar.setIcon(new ImageIcon((this.getClass().getResource("/Recursos/Borrar.png"))));
        Borrar.setBorder(null);
        Borrar.setBounds(45, 390, 48, 48);
        Borrar.setBackground(color);
        Borrar.setToolTipText("Limpiar campos");
        Borrar.addActionListener(this);
        fondo.add(Borrar);
        
        Generarlbl = new JLabel();
        Generarlbl.setText("Generar txt");
        Generarlbl.setBorder(null);
        Generarlbl.setForeground(Color.WHITE);
        Generarlbl.setBounds(150, 370, 160, 15);
        fondo.add(Generarlbl);
        
        Archivo = new JButton();
        Archivo.setIcon(new ImageIcon((this.getClass().getResource("/Recursos/Txt.png"))));
        Archivo.setBorder(null);
        Archivo.setBounds(155, 390, 48, 48);
        Archivo.setBackground(color);
        Archivo.setToolTipText("Generar .txr");
        Archivo.addActionListener(this);
        fondo.add(Archivo);
        
        Resultado= new JTextField();
        Resultado.setBounds(245, 370, 90, 25);
        Resultado.setBackground(Color.WHITE);
        Resultado.addKeyListener(this);
        Resultado.setEnabled(false);
        fondo.add(Resultado);
        
        String path = "/Recursos/WaveDash2.png";  
        URL url = this.getClass().getResource(path);  
        ImageIcon icon = new ImageIcon(url);
        logo = new JLabel();
        logo.setIcon(icon);
        logo.setBorder(null);
        logo.setBounds(210, 400, 140, 42);
        fondo.add(logo);        
    }
    
    public void Borrarr(){
        infijo.setText("");
        postfijo.setText("");
        pFormula.setText("");
        Int.setText("");
        Otro.setText("");
        Resultado.setText("");
        Solucion.setText("");
    }
    
    public void abrir( File archivo ){
        try{
            FileReader lector = new FileReader(archivo);
            BufferedReader bufer = new BufferedReader(lector);
            infijo.setText(bufer.readLine());
            bufer.close();
            lector.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Tipo de archivo incorrecto\nDebe ser un .txt");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if( Abrir == e.getSource())
        {
            if( abrirArchivo == null ) abrirArchivo = new JFileChooser();
            abrirArchivo.setFileSelectionMode( JFileChooser.FILES_ONLY );
            int seleccion = abrirArchivo.showOpenDialog( this );
            if( seleccion == JFileChooser.APPROVE_OPTION ){
                File f = abrirArchivo.getSelectedFile();
                abrir(f); 
            }
        }
        
        if(Run == e.getSource()){
            if (!infijo.getText().equals("")){
                String ar[] = new String[4];
                int a=0,b=0;
                if(Int.getText().equals("") || Otro.getText().equals("")){
                    a=-1;
                    b=-1;
                }
                else{
                    a= Integer.parseInt(Int.getText());
                    b= Integer.parseInt(Otro.getText());
                }
                ar=pos.postfijo(infijo.getText(),a,b);
                postfijo.setText(ar[0]);
                pFormula.setText(ar[1]);
                Solucion.setText(ar[2]);
                Resultado.setText(ar[3]);
            }
              else
                JOptionPane.showMessageDialog(null, "Debe Ingresar el infijo");
        }
        
        if(Borrar == e.getSource()){
            Borrarr();
        }
        
        if(Archivo == e.getSource()){
            if (!postfijo.getText().equals("")){
                try {
                    pos.escribir(infijo.getText(),postfijo.getText(),pFormula.getText());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FNC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
              else
                JOptionPane.showMessageDialog(null, "No hay datos");
        }
    }
    
    
              
    
    //Variables
    JPanel fondo = new JPanel ();
    
    JFileChooser abrirArchivo;
    JScrollPane terminado;
    JScrollPane terminado2;
    JTextArea pFormula = new JTextArea();
    JTextArea Solucion = new JTextArea();
    Color color = new Color(33,99,18);
    
    JButton Abrir = new JButton();
    JButton Run = new JButton();
    JButton Archivo = new JButton();
    JButton Borrar = new JButton();
    
    JTextField infijo = new JTextField();
    JTextField postfijo = new JTextField();
    JTextField Int = new JTextField();
    JTextField Otro = new JTextField();
    JTextField Resultado = new JTextField();
    
    JLabel archi = new JLabel();
    JLabel field = new JLabel();
    JLabel post = new JLabel();
    JLabel formulalbl = new JLabel();
    JLabel Borrarlbl = new JLabel();
    JLabel Generarlbl = new JLabel();
    JLabel logo = new JLabel();
    JLabel intentos = new JLabel();
    JLabel otro = new JLabel();

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource()==Otro){
            if(Otro.getText().length()>=4) e.consume();
            char car = e.getKeyChar();
            if(car!='0' && car!='1' && car!='2' && car!='3' && car!='4' && car!='5' && 
            car!='6' && car!='7' && car!='8' && car!='9') 
                e.consume();
        }
        if(e.getSource()==Int){
            if(Int.getText().length()>=4) e.consume();
            char car = e.getKeyChar();
            if(car!='0' && car!='1' && car!='2' && car!='3' && car!='4' && car!='5' && 
            car!='6' && car!='7' && car!='8' && car!='9') 
                e.consume();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}