package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projection.UserDetailsProjection;
import com.devsuperior.demo.repositories.UserRepository;

@Service
public class UserServiceSecurity implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Carrega informações do usuário com base no nome de usuário fornecido.
	 *
	 * Este método é parte de uma implementação da interface UserDetailsService,
	 * usado para recuperar detalhes do usuário, como credenciais e autorizações,
	 * com base no nome de usuário fornecido durante o processo de autenticação.
	 *
	 * @param username O nome de usuário para o qual as informações do usuário estão
	 *                 sendo carregadas.
	 * @return Um objeto UserDetails contendo informações sobre o usuário ou null se
	 *         o usuário não for encontrado.
	 * @throws UsernameNotFoundException Exceção lançada se o nome de usuário não
	 *                                   for encontrado.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

		if(result.size() == 0) {
			throw new UsernameNotFoundException(" User not found");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		
		for (UserDetailsProjection Projection : result) {
			user.addRole(new Role(Projection.getRoleId() , Projection.getAuthority()));
		}
		
		return user;
	}

}
