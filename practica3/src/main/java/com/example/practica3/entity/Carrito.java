package com.example.practica3.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idCarrito;

    @Column(nullable = false) public String idUsuario;
    @Column(nullable = false) public String correoUsuario;

    @Column
    public Long precioTotal;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoLinea> carritoLineas = new ArrayList<>();



    public Long getIdCarrito(){
        return this.idCarrito;
    }

    public List<CarritoLinea> getCarritoLineas(){
        return carritoLineas;
    }

    public void setPrecioTotal(Long precio){
        this.precioTotal = precio;
    }

    public void addCarritoLinea(CarritoLinea cl){
        if(carritoLineas != null){
            carritoLineas.add(cl);
            cl.setCarrito(this);
        }
    }

    public String getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
}
