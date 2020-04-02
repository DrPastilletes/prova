package model;

public class Habitacio {
    private Integer numHabitacio, numPersones;

    public Habitacio(Integer numHabitacio, Integer numPersones) {
        super();
        this.numHabitacio = numHabitacio;
        this.numPersones = numPersones;
    }

    public Habitacio() {
        super();
    }

    public Integer getNumHabitacio() {
        return numHabitacio;
    }

    public void setNumHabitacio(Integer numHabitacio) {
        this.numHabitacio = numHabitacio;
    }

    public Integer getNumPersones() {
        return numPersones;
    }

    public void setNumPersones(Integer numPersones) {
        this.numPersones = numPersones;
    }


}
