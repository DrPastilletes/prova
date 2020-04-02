package model;

import java.time.LocalDate;

public class Reserva {
    private Habitacio habitacio;
    private Client client;
    private LocalDate diaEntrada, diaSortida;
    private Integer numPersones;
    public Reserva(Client client, LocalDate diaEntrada, LocalDate diaSortida, Integer numPersones) {
        super();
        this.client = client;
        this.diaEntrada = diaEntrada;
        this.diaSortida = diaSortida;
        this.numPersones = numPersones;
    }

    public Reserva(Client client, LocalDate diaEntrada, LocalDate diaSortida) {
        super();
        this.client = client;
        this.diaEntrada = diaEntrada;
        this.diaSortida = diaSortida;
    }

    public Reserva(Habitacio habitacio, Client client, LocalDate diaEntrada, LocalDate diaSortida) {
        super();
        this.habitacio = habitacio;
        this.client = client;
        this.diaEntrada = diaEntrada;
        this.diaSortida = diaSortida;
    }
    public Reserva(Habitacio habitacio, Client client) {
        super();
        this.habitacio = habitacio;
        this.client = client;
    }
    public Reserva() {
        super();
    }
    public Habitacio getHabitacio() {
        return habitacio;
    }
    public void setHabitacio(Habitacio habitacio) {
        this.habitacio = habitacio;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public LocalDate getDiaEntrada() {
        return diaEntrada;
    }
    public void setDiaEntrada(LocalDate diaEntrada) {
        this.diaEntrada = diaEntrada;
    }
    public LocalDate getDiaSortida() {
        return diaSortida;
    }
    public Integer getNumPersones() {
        return numPersones;
    }

    public void setNumPersones(Integer numPersones) {
        this.numPersones = numPersones;
    }

    public void setDiaSortida(LocalDate diaSortida) {
        this.diaSortida = diaSortida;
    }

    public String[] arrayReservaPendent() {
        String[] array = new String[4];
        array[0]=this.diaEntrada.getDayOfMonth()+"-"+this.diaEntrada.getMonthValue()+"-"+this.diaEntrada.getYear();
        array[1]=client.getDni();
        array[2]=this.numPersones+"";
        array[3]=this.getHabitacio().getNumHabitacio()+"";
        return array;
    }

    public String[] arrayReservaConfirmada() {
        String[] array = new String[4];
        array[0]=client.getDni();
        array[1]=client.getNom();
        array[2]=client.getCognoms();
        array[3]=this.getHabitacio().getNumHabitacio()+"";
        return array;
    }

    @Override
    public String toString() {
        return habitacio.getNumHabitacio()+" - "+diaEntrada.toString()+" - "+diaSortida.toString();
    }


}
