package controller;

import com.toedter.calendar.JDateChooser;
import model.Client;
import model.Habitacio;
import model.Hotel;
import model.Reserva;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Logica {
    private Hotel hotel;
    public Logica() {
        hotel = new Hotel();
    }

    public Client crearClient(JTextField nom, JTextField cognoms, JTextField dni) {
        Client cli = new Client(nom.getText(),cognoms.getText(),dni.getText());
        return cli;
    }

    public Reserva crearReserva(Client cli, LocalDate diaEntrada, LocalDate diaSortida, JTextField numPersones) {
        Reserva res = new Reserva(cli, diaEntrada, diaSortida, Integer.parseInt(numPersones.getText()));
        return res;
    }

    public boolean comprovarHabitacio(JTextField numHabitacio) {
        int numHab = Integer.parseInt(numHabitacio.getText());
        for(Habitacio habi : hotel.getLlistaHab()) {
            if(habi.getNumHabitacio()==numHab) {
                return true;
            }
        }
        return false;
    }

    public void crearHabitacio(JTextField numHabitacio, JTextField numPersones) {
        int numHab = Integer.parseInt(numHabitacio.getText());
        int numPers = Integer.parseInt(numPersones.getText());
        Habitacio hab = new Habitacio(numHab, numPers);
        afegirHabitacioHotel(hab);
    }


    public int preguntaModificarHabitacio(JTextField numHabitacio, JTextField numPersones) {
        int numHab = Integer.parseInt(numHabitacio.getText());
        int numPers = Integer.parseInt(numPersones.getText());
        int opcio = 1;
        for(Habitacio habi : hotel.getLlistaHab()) {
            if(habi.getNumHabitacio()==numHab) {
                String[] options = {"Si","No"};
                opcio = JOptionPane.showOptionDialog(null, "Estas segur que vols modificar de "+habi.getNumPersones()+" persones a "+ numPers+"?", "Avís", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);
                return opcio;
            }
        }
        return opcio;

    }

    public void modificarHabitacio(JTextField numHabitacio, JTextField numPersones) {
        int numHab = Integer.parseInt(numHabitacio.getText());
        int numPers = Integer.parseInt(numPersones.getText());
        for(Habitacio habi : hotel.getLlistaHab()) {
            if(habi.getNumHabitacio()==numHab) {
                habi.setNumPersones(numPers);
            }
        }

    }

    public void afegirClientHotel(Client cli) {
        boolean iguals = true;
        if(hotel.getLlistaClients().size()!=0) {
            for(Client cliIterador : hotel.getLlistaClients()) {
                if(!cliIterador.getDni().equalsIgnoreCase(cli.getDni())) {
                    iguals = false;
                    System.out.println(hotel.getLlistaClients());
                }
            }
            if(!iguals) {
                hotel.getLlistaClients().add(cli);
            }
        }
        else {
            hotel.getLlistaClients().add(cli);
        }

    }

    public void afegirReservaHotel(Reserva res) {
        hotel.getLlistaReservesPendents().add(res);
    }

    public void afegirHabitacioHotel(Habitacio habitacio) {
        hotel.getLlistaHab().add(habitacio);
    }

    public boolean nomesLletres(String text) {
        if (text.matches("^[A-Za-z\\s]{2,}[\\.]{0,1}[A-Za-z\\s]{0,}$")) {
            return true;
        }else {
            return false;
        }
    }

    public boolean nomesNumeros(String text) {
        if (text.isEmpty()) {
            return false;
        }else {
            if (text.matches("^[0-9]*$")) {
                return true;
            }else {
                return false;
            }
        }
    }

    public boolean comprovarSiClientExisteix(JTextField dni, JTextField nom, JTextField cognoms) {
        boolean clientExistent = false;
        for (Client cli : hotel.getLlistaClients()) {
            if(cli.getDni().equalsIgnoreCase(dni.getText())) {
                clientExistent = true;
                if(cli.getNom().equalsIgnoreCase(nom.getText())) {
                    if(cli.getCognoms().equalsIgnoreCase(cognoms.getText())) {
                        clientExistent = false;
                    }
                }
            }
        }
        return clientExistent;
    }

    public static LocalDate calcularLocalDateAmbDate(Date data) {
        long millisDate = data.getTime();
        LocalDate dia = Instant.ofEpochMilli(millisDate).atZone(ZoneId.systemDefault()).toLocalDate();
        return dia;
    }

    public Reserva comprovarCapacitatHabitacio(Reserva res) {
        for(int i=0;i<2;i++) {
            for(Habitacio hab : hotel.getLlistaHab()) {
                Integer numHab = hab.getNumHabitacio();
                if((hab.getNumPersones()-i)==res.getNumPersones()) {
                    if(hotel.getLlistaReservesPendents().size()==0 && hotel.getLlistaReservesConfirmades().size()==0) {
                        Habitacio h = new Habitacio();
                        res.setHabitacio(h);
                        res.getHabitacio().setNumHabitacio(hab.getNumHabitacio());
                        afegirReservaHotel(res);
                        return res;
                    }
                    if(comprovarDisponibilitatHabitacioPendents(res, hab.getNumHabitacio()) && comprovarDisponibilitatHabitacioConfirmades(res, hab.getNumHabitacio())) {
                        System.out.println("He afegit una reserva!");
                        Habitacio h = new Habitacio();
                        res.setHabitacio(h);
                        res.getHabitacio().setNumHabitacio(hab.getNumHabitacio());
                        System.out.println("Habitacio: "+res.getHabitacio());
                        afegirReservaHotel(res);
                        return res;
                    }
                }
            }
            if(i==1) {
                return null;
            }
        }
        return res;
    }

    public boolean comprovarDisponibilitatHabitacioPendents(Reserva res, Integer numHabitacio) {
        boolean comprovarHabitacio = false;
        LocalDate diaEntrada = res.getDiaEntrada();
        LocalDate diaSortida = res.getDiaSortida();
        for(Reserva resIterador : hotel.getLlistaReservesPendents()) {
            System.out.println(resIterador.toString());
            if(numHabitacio==resIterador.getHabitacio().getNumHabitacio()) {
                comprovarHabitacio = true;
                if (diaEntrada.isAfter(resIterador.getDiaSortida()) || diaSortida.isBefore(resIterador.getDiaEntrada()) || diaEntrada.equals(resIterador.getDiaSortida()) || diaSortida.equals(resIterador.getDiaEntrada())) {
                    return true;
                }
            }
        }
        if(comprovarHabitacio) {
            return false;
        }
        return true;
    }

    public boolean comprovarDisponibilitatHabitacioConfirmades(Reserva res, Integer numHabitacio) {
        boolean comprovarHabitacio = false;
        LocalDate diaEntrada = res.getDiaEntrada();
        LocalDate diaSortida = res.getDiaSortida();
        for(Reserva resIterador : hotel.getLlistaReservesConfirmades()) {
            System.out.println(resIterador.toString());
            if(numHabitacio==resIterador.getHabitacio().getNumHabitacio()) {
                comprovarHabitacio = true;
                if (diaEntrada.isAfter(resIterador.getDiaSortida()) || diaSortida.isBefore(resIterador.getDiaEntrada()) || diaEntrada.equals(resIterador.getDiaSortida()) || diaSortida.equals(resIterador.getDiaEntrada())) {
                    return true;
                }
            }
        }
        if(comprovarHabitacio) {
            return false;
        }
        return true;
    }

    public void deReservesPendentsAConfirmades(DefaultTableModel modelPendents, int index) {
        Reserva res = hotel.getLlistaReservesPendents().get(index);
        hotel.getLlistaReservesConfirmades().add(res);
        hotel.getLlistaReservesPendents().remove(res);
    }

    public ArrayList<Reserva> comprovarDataReservaConfirmada(JToggleButton butoSortidaEntrada, JDateChooser triarData) {
        ArrayList<Reserva> reserves = new ArrayList<Reserva>();
        LocalDate dia = calcularLocalDateAmbDate(triarData.getDate());
        for(Reserva res : hotel.getLlistaReservesConfirmades()) {
            if(butoSortidaEntrada.isSelected()) {
                if(res.getDiaSortida().equals(dia)) {
                    reserves.add(res);
                }
            }
            else {
                if(res.getDiaEntrada().equals(dia)) {
                    reserves.add(res);
                }
            }
        }
        return reserves;
    }

    public ArrayList<Reserva> buscaReservesClient(JTextField nomClientBack){
        ArrayList<Reserva> reserves = new ArrayList<Reserva>();
        String nom = nomClientBack.getText();
        for(Reserva res : hotel.getLlistaReservesPendents()) {
            if(res.getClient().getNom().contains(nom)) {
                reserves.add(res);
            }else if(res.getClient().getCognoms().contains(nom)){
                reserves.add(res);
            }else if(res.getClient().getDni().contains(nom)) {
                reserves.add(res);
            }
        }
        for(Reserva res : hotel.getLlistaReservesConfirmades()) {
            if(res.getClient().getNom().contains(nom)) {
                reserves.add(res);
            }else if(res.getClient().getCognoms().contains(nom)){
                reserves.add(res);
            }else if(res.getClient().getDni().contains(nom)) {
                reserves.add(res);
            }
        }
        return reserves;
    }

    public ArrayList<Client> buscarClient(JTextField nomClientBack){
        ArrayList<Client> clients = new ArrayList<Client>();
        String nom = nomClientBack.getText();
        System.out.println(hotel.getLlistaClients());
        for(Client cli : hotel.getLlistaClients()) {
            if(cli.getNom().contains(nom)) {
                clients.add(cli);
            }else if(cli.getCognoms().contains(nom)){
                clients.add(cli);
            }else if(cli.getDni().contains(nom)) {
                clients.add(cli);
            }
        }
        return clients;
    }

    public void eliminarReserva(Reserva reserva) {
        if(hotel.getLlistaReservesPendents().contains(reserva)) {
            hotel.getLlistaReservesPendents().remove(reserva);
        }
        if(hotel.getLlistaReservesConfirmades().contains(reserva)) {
            hotel.getLlistaReservesConfirmades().remove(reserva);
        }
    }

    public Boolean eliminarReservaTaula(Reserva reserva) {
        if(hotel.getLlistaReservesPendents().contains(reserva)) {
            return true;
        }
        if(hotel.getLlistaReservesConfirmades().contains(reserva)) {
            return false;
        }
        return null;
    }

    public Integer retornarIndexTaula(Reserva reserva, boolean bool) {
        if(bool) {
            for(int i = 0; i<hotel.getLlistaReservesPendents().size();i++) {
                if(hotel.getLlistaReservesPendents().contains(reserva)) {
                    return i;
                }
            }
        }
        else {
            for(int i = 0; i<hotel.getLlistaReservesConfirmades().size();i++) {
                if(hotel.getLlistaReservesConfirmades().contains(reserva)) {
                    return i;
                }
            }
        }
        return null;
    }
}
