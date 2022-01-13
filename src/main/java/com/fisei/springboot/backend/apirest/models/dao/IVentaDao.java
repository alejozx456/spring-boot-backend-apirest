package com.fisei.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fisei.springboot.backend.apirest.models.entity.Factura;

public interface IVentaDao extends CrudRepository<Factura, Long> {

}
