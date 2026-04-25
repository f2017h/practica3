package com.example.practica3.repositorio;

import com.example.practica3.entity.Carrito;
import com.example.practica3.entity.CarritoLinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarritoLineaRepo extends JpaRepository<CarritoLinea, Long> {
    List<CarritoLinea> findByCarrito_IdCarrito(Long idCarrito);
    @Modifying
    @Transactional
    @Query("DELETE FROM CarritoLinea cl WHERE cl.carrito.idCarrito = :carritoId AND cl.idArticulo = :articuloId")
    int deleteByCarrito_IdCarritoAndIdArticulo(@Param("carritoId") Long carritoId, @Param("articuloId") Long articuloId);
    
    @Query("SELECT cl FROM CarritoLinea cl JOIN FETCH cl.carrito WHERE cl.idCarritoLinea = :lineaId")
    Optional<CarritoLinea> findByIdWithCarrito(@Param("lineaId") Long lineaId);
}
