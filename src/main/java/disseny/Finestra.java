package disseny;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controller.Logica;
import model.Client;
import model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Finestra extends JFrame{
    String alfabetString = "trwagmyfpdxbnjzsqvhlcke";

    public char[] alfabet = alfabetString.toCharArray();
    public JPanel panell1;
    public JPanel panell2;
    public JPanel panell3;
    public JTextField dni = new JTextField();
    public JTextField nom = new JTextField();
    public JTextField cognoms = new JTextField();
    public JTextField numPersones = new JTextField();
    public JTextField numNits = new JTextField();
    public JTextField nomHotel = new JTextField();
    public JTextField numHabBack = new JTextField();
    public JTextField numPersBack = new JTextField();
    public JTextField nomClientBack = new JTextField();
    public JTable taulaReservesP, taulaReservesC;
    public DefaultTableModel modelPendents, modelConfirmades;
    public DefaultListModel<Reserva> modelReserva;
    public JList<Reserva> llistaReserva;
    public DefaultListModel<Client> modelClient;
    public JList<Client> llistaClient;
    public JCalendar calendari = new JCalendar();
    public JDateChooser triarData;
    public JButton reserva = new JButton("RESERVA");
    public JButton butoGuardaNomHotel = new JButton("GUARDA!");
    public JButton butoGuardaHabitacio = new JButton("GUARDA!");
    public JButton elimina = new JButton("ELIMINA!");
    public JToggleButton butoSortidaEntrada;
    public JLabel imgDni, imgNom, imgCognoms, imgNumPers, imgNumNits;
    public ImageIcon imgTrue = new ImageIcon(new ImageIcon("true.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));
    public ImageIcon imgFalse = new ImageIcon(new ImageIcon("false.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH));
    public boolean comprovaNom, comprovaCognoms, comprovaDni, comprovaNumPersones, comprovaNumNits;
    public Logica c;
    public Finestra() {
        setVisible(true);
        setSize(1200,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Casa Rural");
        setLocationRelativeTo(null); //null -> centre
        setMinimumSize(new Dimension(1200,700));
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);
        c = new Logica();
        iniciarComponents();

    }

    private void iniciarComponents() {
        crearPanells();
        afegirKeyListenerClient();
        afegirActionListenerNomHotel();
        afegirActionListenerReserva();
        afegirActionListenerHabitacio();
        afegirActionListenerTBSortidaEntrada();
        afegirClickListenerTaulaReserves();
        afegirDateChooserListener();
        afegirMouseListenerLlistaClient();
        afegirKeyListenerNomBack();
        afegirMouseListenerLlistaReserva();
        afegirClickEliminar();
    }

    private void crearPanells() {
        int amplada = this.getWidth()/3;
        int altura = this.getHeight();
        System.out.println(amplada);
        System.out.println(altura);


        panell1 = new JPanel();
        panell1.setBackground(Color.LIGHT_GRAY);
        panell1.setBounds(0,0,amplada-2,altura);
        panell1.setLayout(null);
        this.getContentPane().add(panell1);

        panell2 = new JPanel();
        panell2.setBackground(Color.LIGHT_GRAY);
        panell2.setBounds(amplada,0,amplada-2,altura);
        panell2.setLayout(null);
        this.getContentPane().add(panell2);

        panell3 = new JPanel();
        panell3.setBackground(Color.LIGHT_GRAY);
        panell3.setBounds(amplada*2,0,amplada-2,altura);
        panell3.setLayout(null);
        this.getContentPane().add(panell3);

        System.out.println(panell1.getWidth()+" - "+panell1.getHeight());
        System.out.println(panell2.getWidth()+" - "+panell2.getHeight());
        System.out.println(panell3.getWidth()+" - "+panell3.getHeight());

        // GESTIÓ // PANELL1 //
        JLabel gestio = new JLabel("Gestió");
        gestio.setBounds(0,20,398,20);
        gestio.setFont(new Font("arial",Font.BOLD,24));
        gestio.setHorizontalAlignment(SwingConstants.CENTER);
        panell1.add(gestio);

        JLabel jlReservesP = new JLabel("RESERVES PENDENTS:");
        jlReservesP.setBounds(20, 80, 300, 20);
        jlReservesP.setFont(new Font("arial",Font.PLAIN,16));
        panell1.add(jlReservesP);

        modelPendents = new DefaultTableModel();
        modelPendents.addColumn("Dia");
        modelPendents.addColumn("DNI");
        modelPendents.addColumn("Persones");
        modelPendents.addColumn("Habitació");
        taulaReservesP = new JTable(modelPendents);
        taulaReservesP.setBounds(20, 110, 359, 200);
        taulaReservesP.setEnabled(false);
        panell1.add(taulaReservesP);
        JScrollPane scrollTaulaResP = new JScrollPane(taulaReservesP,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollTaulaResP.setBounds(20, 110, 359, 200);
        panell1.add(scrollTaulaResP);

        butoSortidaEntrada = new JToggleButton("Entrada");
        butoSortidaEntrada.setBounds(20, 320, 140, 20);
        panell1.add(butoSortidaEntrada);

//        JLabel jlReservesC = new JLabel("RESERVES CONFIRMADES:");
//        jlReservesC.setBounds(20, 320, 220, 20);
//        jlReservesC.setFont(new Font("arial",Font.PLAIN,16));
//        panell1.add(jlReservesC);

        triarData = new JDateChooser();
        Date avui = new Date();
        avui.setTime(System.currentTimeMillis());
        triarData.setDate(avui);
        triarData.setBounds(250, 320, 130, 20);
        panell1.add(triarData);

        modelConfirmades = new DefaultTableModel();
        modelConfirmades.addColumn("DNI");
        modelConfirmades.addColumn("Nom");
        modelConfirmades.addColumn("Cognoms");
        modelConfirmades.addColumn("Habitació");
        taulaReservesC = new JTable(modelConfirmades);
        taulaReservesC.setBounds(20, 350, 359, 200);
        panell1.add(taulaReservesC);
        JScrollPane scrollTaulaResC = new JScrollPane(taulaReservesC,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollTaulaResC.setBounds(20, 350, 359, 200);
        panell1.add(scrollTaulaResC);

        // CLIENTS // PANELL2 //

        JLabel clients = new JLabel("Clients");
        clients.setBounds(0,20,398,20);
        clients.setFont(new Font("arial",Font.BOLD,24));
        clients.setHorizontalAlignment(SwingConstants.CENTER);
        panell2.add(clients);

        JLabel jlDni = new JLabel("DNI:");
        jlDni.setBounds(20, 80, 100, 20);
        jlDni.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlDni);

        dni.setBounds(180, 80, 150, 20);
        panell2.add(dni);
        dni.setName("dni");

        imgDni = new JLabel();
        imgDni.setBounds(350, 80, 22, 22);
        panell2.add(imgDni);

        JLabel jlNom = new JLabel("NOM:");
        jlNom.setBounds(20, 110, 100, 20);
        jlNom.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlNom);

        nom.setBounds(180, 110, 150, 20);
        panell2.add(nom);
        nom.setName("Nom");

        imgNom = new JLabel();
        imgNom.setBounds(350, 110, 22, 22);
        panell2.add(imgNom);

        JLabel jlCognom = new JLabel("COGNOMS:");
        jlCognom.setBounds(20, 140, 100, 20);
        jlCognom.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlCognom);

        cognoms.setBounds(180, 140, 150, 20);
        panell2.add(cognoms);
        cognoms.setName("Cognoms");

        imgCognoms = new JLabel();
        imgCognoms.setBounds(350, 140, 22, 22);
        panell2.add(imgCognoms);

        JLabel jlNumPers = new JLabel("NUM. PERSONES:");
        jlNumPers.setBounds(20, 170, 150, 20);
        jlNumPers.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlNumPers);

        numPersones.setBounds(180, 170, 60, 20);
        panell2.add(numPersones);
        numPersones.setName("numPersones");

        imgNumPers = new JLabel();
        imgNumPers.setBounds(250, 170, 22, 22);
        panell2.add(imgNumPers);

        JLabel jlNumNits = new JLabel("NUM. NITS:");
        jlNumNits.setBounds(20, 200, 150, 20);
        jlNumNits.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlNumNits);

        numNits.setBounds(180, 200, 60, 20);
        panell2.add(numNits);
        numNits.setName("numNits");

        imgNumNits = new JLabel();
        imgNumNits.setBounds(250, 200, 22, 22);
        panell2.add(imgNumNits);

        JLabel jlDataEntrada = new JLabel("DATA D'ENTRADA:");
        jlDataEntrada.setBounds(20, 270, 150, 20);
        jlDataEntrada.setFont(new Font("arial",Font.PLAIN,16));
        panell2.add(jlDataEntrada);

        calendari.setBounds(20, 300, 350, 250);
        panell2.add(calendari);

        reserva.setBounds(150, 570, 100, 30);
        reserva.setEnabled(false);
        panell2.add(reserva);


        // BACK // PANELL3 //

        JLabel back = new JLabel("Back");
        back.setBounds(0,20,398,20);
        back.setFont(new Font("arial",Font.BOLD,24));
        back.setHorizontalAlignment(SwingConstants.CENTER);
        panell3.add(back);

        JLabel jlNomHotel = new JLabel("NOM HOTEL:");
        jlNomHotel.setBounds(20, 80, 110, 20);
        jlNomHotel.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlNomHotel);

        nomHotel.setBounds(140, 80, 220, 20);
        panell3.add(nomHotel);

        butoGuardaNomHotel.setBounds(150, 110, 100, 30);
        panell3.add(butoGuardaNomHotel);

        JLabel jlRegNovaHabitacio = new JLabel("REGISTRE NOVA HABITACIÓ:");
        jlRegNovaHabitacio.setBounds(20, 160, 250, 20);
        jlRegNovaHabitacio.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlRegNovaHabitacio);


        JLabel jlNum = new JLabel("NUM:");
        jlNum.setBounds(20, 190, 50, 20);
        jlNum.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlNum);

        numHabBack.setBounds(80, 190, 60, 20);
        panell3.add(numHabBack);

        JLabel jlPers = new JLabel("# PERS:");
        jlPers.setBounds(180, 190, 80, 20);
        jlPers.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlPers);

        numPersBack.setBounds(270, 190, 60, 20);
        panell3.add(numPersBack);

        butoGuardaHabitacio.setBounds(150, 220, 100, 30);
        panell3.add(butoGuardaHabitacio);

        JLabel jlConsultaReserves = new JLabel("CONSULTA RESERVA:");
        jlConsultaReserves.setBounds(20, 270, 180, 20);
        jlConsultaReserves.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlConsultaReserves);

        JLabel jlNomClientBack = new JLabel("NOM CLIENT:");
        jlNomClientBack.setBounds(20, 300, 130, 20);
        jlNomClientBack.setFont(new Font("arial",Font.PLAIN,16));
        panell3.add(jlNomClientBack);

        nomClientBack.setBounds(160, 300, 200, 20);
        panell3.add(nomClientBack);

        modelClient = new DefaultListModel<Client>();
        llistaClient = new JList<Client>(modelClient);
        llistaClient.setBounds(20, 340, 160, 160);
        panell3.add(llistaClient);

        JScrollPane scrollLlistaClient = new JScrollPane(llistaClient,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollLlistaClient.setBounds(20, 340, 160, 160);
        panell3.add(scrollLlistaClient);

        modelReserva = new DefaultListModel<Reserva>();
        llistaReserva = new JList<Reserva>(modelReserva);
        llistaReserva.setBounds(20, 340, 160, 160);
        panell3.add(llistaReserva);

        JScrollPane scrollLlistaReserva = new JScrollPane(llistaReserva,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollLlistaReserva.setBounds(210, 340, 160, 160);
        panell3.add(scrollLlistaReserva);

        elimina.setBounds(150, 520, 100, 30);
        panell3.add(elimina);


    }

    private void afegirActionListenerNomHotel() {
        ActionListener clickNomHotel = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                setTitle(nomHotel.getText());
            }
        };
        butoGuardaNomHotel.addActionListener(clickNomHotel);
    }

    private void afegirActionListenerReserva() {
        ActionListener clickReserva = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!c.comprovarSiClientExisteix(dni, nom, cognoms)) {
                    LocalDate diaEntrada = Logica.calcularLocalDateAmbDate(calendari.getDate());
                    LocalDate diaSortida = diaEntrada.plusDays(Long.parseLong(numNits.getText()));

                    c.afegirClientHotel(c.crearClient(nom, cognoms, dni));
                    Reserva res = c.crearReserva(c.crearClient(nom, cognoms, dni), diaEntrada, diaSortida, numPersones);
                    Reserva resAux = c.comprovarCapacitatHabitacio(res);
//					c.afegirReservaHotel(resAux);
                    String[] rowReserva = new String[4];
                    if (resAux!=null) {
                        String[] options2 = {"Ok"};
                        JOptionPane.showOptionDialog(null, "S'ha creat la reserva amb exit!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
                        rowReserva = resAux.arrayReservaPendent();
                        modelPendents.addRow(rowReserva);
                        for(Component component : panell2.getComponents()) {
                            if(component instanceof JTextField) {
                                ((JTextField) component).setText("");
                            }
                            if(component instanceof JCalendar) {
                                Date avui = new Date();
                                avui.setTime(System.currentTimeMillis());
                                ((JCalendar) component).setDate(avui);
                            }
                            if(component instanceof JLabel) {

                                if(((JLabel) component).getIcon() != null) {

                                    ((JLabel) component).setIcon(null);
                                }

                            }
                        }
                        comprovaDni = false;
                        comprovaNom = false;
                        comprovaCognoms = false;
                        comprovaNumNits = false;
                        comprovaNumPersones = false;
                        reserva.setEnabled(false);
                    }
                    else {
                        String[] options2 = {"Ok"};
                        JOptionPane.showOptionDialog(null, "Hi ha hagut algun problema a l'hora de crear la reserva... Torna-ho a intentar!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
                    }

                }
            }
        };
        reserva.addActionListener(clickReserva);
    }

    private void afegirKeyListenerClient() {
        KeyListener listenerClient = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                comprovaDni = false;
                comprovaNom = false;
                comprovaCognoms = false;
                comprovaNumNits = false;
                comprovaNumPersones = false;
                reserva.setEnabled(false);
                for(Component component : panell2.getComponents()) {
                    if(component instanceof JTextField || component instanceof JCalendar) {
                        switch (component.getName()) {
                            case "dni":
                                if(dni.getText().matches("^[0-9]{8,8}[A-Za-z]$")) {
                                    String numDniString = dni.getText().substring(0, 8);
                                    int numDni = Integer.parseInt(numDniString);
                                    int numAlfabet = numDni%23;
                                    char lletra = dni.getText().charAt(8);
                                    if(Character.toLowerCase(lletra) == Character.toLowerCase(alfabet[numAlfabet])) {
                                        imgDni.setIcon(imgTrue);
                                        comprovaDni = true;
                                    }
                                    else {
                                        imgDni.setIcon(imgFalse);
                                        comprovaDni = false;
                                    }
                                }
                                else {
                                    imgDni.setIcon(imgFalse);
                                }
                                break;
                            case "Nom":
                                if(c.nomesLletres(nom.getText())) {
                                    imgNom.setIcon(imgTrue);
                                    comprovaNom = true;
                                }
                                else {
                                    imgNom.setIcon(imgFalse);
                                    comprovaNom = false;
                                }
                                break;
                            case "Cognoms":
                                if(c.nomesLletres(cognoms.getText())) {
                                    imgCognoms.setIcon(imgTrue);
                                    comprovaCognoms = true;
                                }
                                else {
                                    imgCognoms.setIcon(imgFalse);
                                    comprovaCognoms = false;
                                }
                                break;
                            case "numPersones":
                                if(c.nomesNumeros(numPersones.getText())) {
                                    imgNumPers.setIcon(imgTrue);
                                    comprovaNumPersones = true;
                                }
                                else {
                                    imgNumPers.setIcon(imgFalse);
                                    comprovaNumPersones = false;
                                }
                                break;
                            case "numNits":
                                if(c.nomesNumeros(numNits.getText())) {
                                    imgNumNits.setIcon(imgTrue);
                                    comprovaNumNits = true;
                                }
                                else {
                                    imgNumNits.setIcon(imgFalse);
                                    comprovaNumNits = false;
                                }
                                break;

                            default:
                                break;
                        }

                    }
                    if(comprovaDni && comprovaNom && comprovaCognoms && comprovaNumPersones && comprovaNumNits) {
                        reserva.setEnabled(true);
                    }
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }
        };
        dni.addKeyListener(listenerClient);
        nom.addKeyListener(listenerClient);
        cognoms.addKeyListener(listenerClient);
        numPersones.addKeyListener(listenerClient);
        numNits.addKeyListener(listenerClient);
    }

    private void afegirActionListenerHabitacio() {
        ActionListener clickHabitacio = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(c.comprovarHabitacio(numHabBack)) {
                    int opcio = c.preguntaModificarHabitacio(numHabBack, numPersBack);
                    switch (opcio) {
                        case 0:
                            c.modificarHabitacio(numHabBack, numPersBack);
                            String[] options2 = {"Ok"};
                            JOptionPane.showOptionDialog(null, "S'ha modificat la habitació!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
                            numPersBack.setText("");
                            numHabBack.setText("");
                            break;

                        case 1:
                            String[] options1 = {"Ok"};
                            JOptionPane.showOptionDialog(null, "No s'ha modificat la habitació!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options1, options1[0]);
                            break;
                    }
                }
                else {
                    String[] options = {"Si","No"};
                    int opcio = JOptionPane.showOptionDialog(null, "Estas segur que vols afegir la habitació!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                    switch(opcio) {
                        case 0:
                            String[] options1 = {"Ok"};
                            JOptionPane.showOptionDialog(null, "La habitació s'ha afegit amb exit!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options1, options1[0]);
                            c.crearHabitacio(numHabBack, numPersBack);
                            numPersBack.setText("");
                            numHabBack.setText("");
                            break;
                        case 1:
                            String[] options2 = {"Ok"};
                            JOptionPane.showOptionDialog(null, "La habitació no s'ha afegit!", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options2, options2[0]);
                            break;
                    }
                }

            }
        };
        butoGuardaHabitacio.addActionListener(clickHabitacio);
    }

    private void afegirActionListenerTBSortidaEntrada() {
        ActionListener clickSortidaEntrada = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ArrayList<Reserva> reserves = c.comprovarDataReservaConfirmada(butoSortidaEntrada, triarData);
                while(modelConfirmades.getRowCount()!=0) {
                    modelConfirmades.removeRow(0);
                }
                for(Reserva res : reserves) {
                    String[] rowReserva = res.arrayReservaConfirmada();
                    modelConfirmades.addRow(rowReserva);
                }
                JToggleButton jtb = (JToggleButton)e.getSource();
                if (jtb.isSelected()) {
                    butoSortidaEntrada.setText("Sortida");
                } else {
                    butoSortidaEntrada.setText("Entrada");
                }
            }
        };
        butoSortidaEntrada.addActionListener(clickSortidaEntrada);
    }

    private void afegirClickListenerTaulaReserves() {
        taulaReservesP.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if(e.getClickCount()==2) {
                    c.deReservesPendentsAConfirmades(modelPendents, taulaReservesP.rowAtPoint(e.getPoint()));
                    ArrayList<Reserva> reserves = c.comprovarDataReservaConfirmada(butoSortidaEntrada, triarData);
                    while(modelConfirmades.getRowCount()!=0) {
                        modelConfirmades.removeRow(0);
                    }
                    for(Reserva res : reserves) {
                        String[] rowReserva = res.arrayReservaConfirmada();
                        modelConfirmades.addRow(rowReserva);
                    }
                    modelPendents.removeRow(taulaReservesP.rowAtPoint(e.getPoint()));
                }
            }
        });
    }

    private void afegirDateChooserListener() {
        PropertyChangeListener changeData = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                ArrayList<Reserva> reserves = c.comprovarDataReservaConfirmada(butoSortidaEntrada, triarData);
                while(modelConfirmades.getRowCount()!=0) {
                    modelConfirmades.removeRow(0);
                }
                for(Reserva res : reserves) {
                    String[] rowReserva = res.arrayReservaConfirmada();
                    modelConfirmades.addRow(rowReserva);
                }

            }
        };
        triarData.addPropertyChangeListener(changeData);
    }

    private void afegirMouseListenerLlistaClient() {
        llistaClient.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                llistaClient.getSelectedValue();
                ArrayList<Reserva> reserves = c.buscaReservesClient(nomClientBack);
                modelReserva.clear();
                for(Reserva res : reserves) {
                    if(res.getClient().getDni().equals(llistaClient.getSelectedValue().getDni())) {
                        modelReserva.addElement(res);
                    }
                }
            }
        });
    }

    private void afegirMouseListenerLlistaReserva() {
        llistaReserva.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                llistaReserva.getSelectedValue();
//				llistaReserva.remove(llistaReserva.getSelectedIndex());

            }
        });
    }

    private void afegirClickEliminar() {
        elimina.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(c.eliminarReservaTaula(llistaReserva.getSelectedValue())) {
                    modelPendents.removeRow(c.retornarIndexTaula(llistaReserva.getSelectedValue(), c.eliminarReservaTaula(llistaReserva.getSelectedValue())));
                }
                else {
                    modelConfirmades.removeRow(c.retornarIndexTaula(llistaReserva.getSelectedValue(), c.eliminarReservaTaula(llistaReserva.getSelectedValue())));
                }
                c.eliminarReserva(llistaReserva.getSelectedValue());
                modelReserva.remove(llistaReserva.getSelectedIndex());
                modelClient.clear();
                if(nomClientBack.getText().length()!=0) {
                    ArrayList<Client> clients = c.buscarClient(nomClientBack);
                    for(Client cli : clients) {
                        if(!modelClient.contains(cli)){
                            modelClient.addElement(cli);
                            System.out.println(cli);
                        }
                    }
                }
            }
        });
    }

    private void afegirKeyListenerNomBack() {
        nomClientBack.addKeyListener( new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                modelClient.clear();
                if(nomClientBack.getText().length()!=0) {
                    ArrayList<Client> clients = c.buscarClient(nomClientBack);
                    for(Client cli : clients) {
//						if(!modelClient.contains(cli)){
                        modelClient.addElement(cli);
                        System.out.println(cli);
//						}
                    }
                }
                else {
                    modelReserva.clear();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }
}
