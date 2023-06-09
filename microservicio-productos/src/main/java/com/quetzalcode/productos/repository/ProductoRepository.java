package com.quetzalcode.productos.repository;

import com.quetzalcode.productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
