package HomeWork.HomeWork8.Task2.Recipe;

import java.util.Date;

public class Recipe implements Cloneable {
    private Doctor doctor;
    private int series;
    private int recipeNumber;
    private Patient patient;
    private Date dateOfDischarge;
    private Date expireDate;

    public Recipe(Date dateOfDischarge, Doctor doctor, Date expireDate, Patient patient, int recipeNumber, int series) {
        this.dateOfDischarge = dateOfDischarge;
        this.doctor = doctor;
        this.expireDate = expireDate;
        this.patient = patient;
        this.recipeNumber = recipeNumber;
        this.series = series;
    }

    public void setExpireDate(Doctor doctor, Date newDate) {
        if (this.doctor == doctor) {
            this.expireDate = newDate;
        }
    }

    public Date getDateOfDischarge() {
        return dateOfDischarge;
    }


    public Doctor getDoctor() {
        return doctor;
    }


    public Date getExpireDate() {
        return expireDate;
    }


    public Patient getPatient() {
        return patient;
    }

    public int getRecipeNumber() {
        return recipeNumber;
    }

    public int getSeries() {
        return series;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
