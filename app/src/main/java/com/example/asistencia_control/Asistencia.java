package com.example.asistencia_control;

public class Asistencia {

    private int id;
    private String fecha;
    private Integer cedula;
    private String entrada;
    private String salida;

    public Asistencia() {
    }

    public Asistencia(int id, String fecha, Integer cedula, String entrada, String salida) {
        this.id = id;
        this.fecha = fecha;
        this.cedula = cedula;
        this.entrada = entrada;
        this.salida = salida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }
}
