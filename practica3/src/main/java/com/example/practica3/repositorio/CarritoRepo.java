package com.example.practica3.repositorio;
import com.example.practica3.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarritoRepo extends JpaRepository<Carrito, Long> {
    Carrito findByIdCarrito(Long idCarrito);


}
