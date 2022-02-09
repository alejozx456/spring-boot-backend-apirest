package com.fisei.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fisei.springboot.backend.apirest.models.entity.Cliente;
import com.fisei.springboot.backend.apirest.models.entity.Region;
import com.fisei.springboot.backend.apirest.models.services.ClienteServiceImpl;
import com.fisei.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		
		return clienteService.findAll();
	}
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page ){
	Pageable pageable=PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
	}
	
	@GetMapping("/clientes/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {
		//return clienteService.findById(id);
		Cliente cliente=null;
		Map<String, Object> response=new HashMap<String, Object>();
		try {
			 cliente= clienteService.findById(id);
		} catch ( DataAccessException e) {
			response.put("mensaje","Error al realizar la consulta en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(cliente==null) {
			response.put("mensaje","El cliente Id:".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		
	}
	
	@PostMapping("/clientes")
	
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		
		//return clienteService.save(cliente);
		 Map<String, Object> response=new HashMap<String, Object>();
			Cliente clienteNew=null;
		try {
			//cliente.setCreateAt(new Date());
			 clienteNew=clienteService.save(cliente);
			
			
		} catch ( DataAccessException e) {
			response.put("mensaje","Error al realizar el insert en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","el cliente ha sido creado con exito");
		response.put("cliente",clienteNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
	
	public ResponseEntity<?> update(@RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteActual= clienteService.findById(id);
		Cliente clienteUpdated=null;
		Map<String, Object> response=new HashMap<String, Object>();
		if(clienteActual==null) {
			response.put("mensaje","Error no se pudo editar El cliente Id:".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		//clienteActual.setCreateAt(new Date());
		clienteActual.setCreateAt(cliente.getCreateAt());
		clienteActual.setRegion(cliente.getRegion());
		clienteUpdated=clienteService.save(clienteActual);
		} catch ( DataAccessException e) {
			response.put("mensaje","Error al actualizar  en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","el cliente ha sido actualizado con exito");
		response.put("cliente",clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	
	}
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response=new HashMap<String, Object>();
		try {
		clienteService.delete(id);
		} catch ( DataAccessException e) {
			response.put("mensaje","Error al eliminar cliente  en la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El cliente se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam(name =  "archivo") MultipartFile archivo,@RequestParam(name = "id") Long id){
		Map<String, Object> response=new HashMap<String, Object>();
		Cliente cliente=clienteService.findById(id);
		if(archivo.isEmpty()) {
			String nombreArchivo=archivo.getOriginalFilename();
			Path rutaArchivo= Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.put("mensaje","Error al subir"+nombreArchivo);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje","ha subido correctamente"+nombreArchivo);
			}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/clientes/regiones")
	public List<Region> listarRegiones(){
		return clienteService.findAllRegiones();
	}
	
	

	
}
