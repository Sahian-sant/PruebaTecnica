package com.mx.PruebaTecnica.Service;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mx.PruebaTecnica.Model.Address;
import com.mx.PruebaTecnica.Model.User;
import com.mx.PruebaTecnica.seguidad.AesSeg;
/*{
"id": <RANDOM_UUID>,
"email": "user1@mail.com",
"name": "user1",
´phone´: “+1 55 555 555 55”
"password": "7c4a8d09ca3762af61e59520943dc26494f8941b",
“tax_id“: “AARR990101XXX“,
"created_at": "01-01-2026 00:00:00",
‘addresses’: [
{
"id": 1,
"name": "workaddress",
"street": "street No. 1",
"country_code": "UK"
},
{
"id": 2,
"name": "homeaddress",
"street": "street No. 2",
"country_code": "AU"
}
]
}*/
@Service
public class UserServImp implements UserService {

	private List<User> users = new ArrayList<>();

	
	public UserServImp() {
		 ZoneId zoneMadagascar = ZoneId.of("Indian/Antananarivo");
	        ZonedDateTime nowMadagascar = ZonedDateTime.now(zoneMadagascar);
	        
		User usuario1=new User();
		usuario1.setId(UUID.randomUUID());
		usuario1.setName("user1");
        usuario1.setEmail("user1@mail.com");
        usuario1.setPhone(" 55 555 555 55");
        usuario1.setTaxId("AARR990101XXX");
        usuario1.setCreatedAt(nowMadagascar.toLocalDateTime());
        usuario1.setPassword(AesSeg.encrypt("7c4a8d09ca3762af61e59520943dc26494f8941b"));
        
        List<Address> direccion = new ArrayList<>();
        
        Address ad1 = new Address();
		ad1.setId(1); 
		ad1.setName("workaddress"); 
		ad1.setStreet("street No. 1"); 
		ad1.setCountryCode("UK"); 
		direccion.add(ad1);

		Address ad2 = new Address();
		ad2.setId(2); 
		ad2.setName("homeaddress"); 
		ad2.setStreet("street No. 2"); 
		ad2.setCountryCode("AU"); 
		direccion.add(ad2);
        
        usuario1.setAddresses(direccion);
        this.users.add(usuario1);
        
        User usuario2=new User();
		usuario2.setId(UUID.randomUUID());
		usuario2.setName("user2");
        usuario2.setEmail("user2@mail.com");
        usuario2.setPhone(" 55 555 545 66");
        usuario2.setTaxId("QWER9856RTYHUJ");
        usuario2.setCreatedAt(nowMadagascar.toLocalDateTime());
        usuario2.setPassword(AesSeg.encrypt("password456"));
        
        List<Address> direccion2 = new ArrayList<>();
        Address ad3 = new Address();
        ad3.setId(3); ad3.setName("workaddress");
        ad3.setStreet("Main St 100"); 
        ad3.setCountryCode("US");
        direccion2.add(ad3);

        usuario2.setAddresses(direccion2);
        this.users.add(usuario2);
		
        
        User usuario3=new User();
		usuario3.setId(UUID.randomUUID());
		usuario3.setName("user3");
        usuario3.setEmail("user3@mail.com");
        usuario3.setPhone(" 55 585 545 66");
        usuario3.setTaxId("ARTS985674WERT");
        usuario3.setCreatedAt(nowMadagascar.toLocalDateTime());
        usuario3.setPassword(AesSeg.encrypt("password789"));
        
        
        List<Address> direccion3 = new ArrayList<>();
        Address ad4 = new Address();
        ad4.setId(4); ad4.setName("homeaddress");
        ad4.setStreet("Secondary St 200"); 
        ad4.setCountryCode("CA");
        direccion3.add(ad4);
        
        usuario3.setAddresses(direccion3);
        this.users.add(usuario3);
	}

	@Override
	public List<User> mostrarUsuarios(String filter, String sortedBy) {

		List<User> resultado = new ArrayList<>(users);

		if (filter != null && !filter.isEmpty()) {

			String[] fragmento = filter.split("\\+");
			if (fragmento.length >= 3) {
				String dondeBuscar = fragmento[0];
				String queComparo = fragmento[1];
				String textoBuscado = fragmento[2];

				resultado = resultado.stream().filter(u -> {
					String textoAComparar = "";

					switch (dondeBuscar.toLowerCase()) {
					case "email":
						textoAComparar = u.getEmail();
						break;
					case "name":
						textoAComparar = u.getName();
						break;
					case "tax_id":
						textoAComparar = u.getTaxId();
						break;
					}

					if (textoAComparar == null)
						return false;

					if (queComparo.equalsIgnoreCase("eq"))
						return textoAComparar.equalsIgnoreCase(textoBuscado);
					if (queComparo.equalsIgnoreCase("co"))
						return textoAComparar.toLowerCase().contains(textoBuscado.toLowerCase());
					if (queComparo.equalsIgnoreCase("sw"))
						return textoAComparar.toLowerCase().startsWith(textoBuscado.toLowerCase());

					if (queComparo.equalsIgnoreCase("ew"))
						return textoAComparar.toLowerCase().endsWith(textoBuscado.toLowerCase());

					return true;
				}).collect(Collectors.toList());
			}
		}
		if (sortedBy != null && !sortedBy.isEmpty()) {
			switch (sortedBy.toLowerCase()) {
			case "name":
				resultado.sort((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()));
				break;
			case "email":
				resultado.sort((u1, u2) -> u1.getEmail().compareToIgnoreCase(u2.getEmail()));
				break;
			case "tax_id":
				resultado.sort((u1, u2) -> u1.getTaxId().compareToIgnoreCase(u2.getTaxId()));
				break;
			case "id":
				resultado.sort((u1, u2) -> u1.getId().compareTo(u2.getId()));
				break;
			}
		}

		return resultado;
	}
	@Override
	public User alta(User user) {
		
		if (user.getTaxId() == null || !user.getTaxId().matches("^[A-Z]{4}\\d{6}[A-Z0-9]{3}$")) {
	        throw new RuntimeException("El Tax ID es obligatorio ");
	    }
		
		if (user.getPhone().replaceAll("\\D", "").length() != 10) {
			throw new RuntimeException("El teléfono debe de ser 10 dígitos"); //
		}
		
		for (User u : users) {

			if (u.getTaxId() != null && u.getTaxId().equalsIgnoreCase(user.getTaxId())) {
				System.out.println("Error: Tax ID esta registrado");
				return null;
			}

			if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(user.getEmail())) {
				System.out.println(" Email ya esta registrado");
				return null;
			}
		}

		user.setId(UUID.randomUUID());

		ZoneId zone = ZoneId.of("Indian/Antananarivo");
		user.setCreatedAt(ZonedDateTime.now(zone).toLocalDateTime());

		if (user.getPassword() != null) {
			user.setPassword(AesSeg.encrypt(user.getPassword()));
		}

		if (user.getAddresses() == null) {
			user.setAddresses(new ArrayList<>());
		}

		users.add(user);
		return user;
	}

	@Override
	public User actualizar(UUID id, User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
	        if (u.getId().equals(id)) {
	            if (user.getName() != null) u.setName(user.getName());
	            if (user.getEmail() != null) u.setEmail(user.getEmail());
	            if (user.getPhone() != null) u.setPhone(user.getPhone());
	            if (user.getTaxId() != null) u.setTaxId(user.getTaxId());
	            if (user.getAddresses() != null) u.setAddresses(user.getAddresses());
	           
	            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	                u.setPassword(AesSeg.encrypt(user.getPassword()));
	            }
	            return u;
	        }
	    }
	    return null;
	}

	@Override
	public boolean eliminar(UUID id) {
		// TODO Auto-generated method stub
		for (User u : users) {

			if (u.getId().equals(id)) {
				users.remove(u);
				return true;
			}

		}

		return false;
	}

	@Override
	public String login(String taxId, String password) {
		// TODO Auto-generated method stub
		User usuarioEncontrado = null;

		for (User u : users) {
			if (u.getTaxId().equals(taxId)) {
				usuarioEncontrado = u;
				break;
			}
		}

		if (usuarioEncontrado == null) {
			return "USUARIO_NO_EXISTE";
		}

		String pswCifrada = AesSeg.encrypt(password);
		if (usuarioEncontrado.getPassword().equals(pswCifrada)) {
			return "OK";
		} else {
			return "PASSWORD_INCORRECTO";
		}
	}
}