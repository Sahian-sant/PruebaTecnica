package com.mx.PruebaTecnica.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.PruebaTecnica.Model.User;
import com.mx.PruebaTecnica.Service.UserService;

@RestController  
@RequestMapping("/users")
public class UserWebService {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> obtenerUsuarios(
			@RequestParam(name = "filter", required = false) String filter,
			@RequestParam(name = "sortedBy", required = false) String sortedBy) {

		try {

			List<User> usuarios = userService.mostrarUsuarios(filter,sortedBy);

			if (usuarios.isEmpty()) {
				return new ResponseEntity<String>("No hay usuarios registrados", HttpStatus.OK);
			}

			return new ResponseEntity<List<User>>(usuarios, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<String>("Error al obtener usuarios: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam(name = "taxId") String taxId,
			@RequestParam(name = "password") String password) {

		try {
			String validado = userService.login(taxId, password);

			if (validado.equals("OK")) {
				return new ResponseEntity<String>("se logro hacer login", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("datos incorrectos o no existe", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error en el login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> crearUsuario(@RequestBody User user) {
		try {
			User nuevoUsuario = userService.alta(user);
			
			if (nuevoUsuario == null) {
				return new ResponseEntity<String>("El usuario con ese Tax ID o Email ya existe", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<User>(nuevoUsuario, HttpStatus.CREATED);

		} catch (RuntimeException e) {
  
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error al crear usuario: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PatchMapping("/{id}")
	public ResponseEntity<?> actualizarUsuario(@PathVariable("id") UUID id, @RequestBody User user) {

		try {

			User usuarioActualizado = userService.actualizar(id, user);

			if (usuarioActualizado == null) {
				return new ResponseEntity<String>("Usuario no encontrado", HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<User>(usuarioActualizado, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<String>("Error al actualizar usuario: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable("id") UUID id) {

		try {

			boolean eliminado = userService.eliminar(id);

			if (eliminado) {
				return new ResponseEntity<String>("Usuario eliminado correctamente", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Usuario no encontrado", HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			return new ResponseEntity<String>("Error al eliminar usuario: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}