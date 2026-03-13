package com.mx.PruebaTecnica.Service;

import java.util.List;
import java.util.UUID;

import com.mx.PruebaTecnica.Model.User;

public interface UserService {

	User alta(User user);

	List<User> mostrarUsuarios(String filter ,String sortedBy);

	User actualizar(UUID id, User user);

	boolean eliminar(UUID id);
	
   String login(String taxId, String password);

}
