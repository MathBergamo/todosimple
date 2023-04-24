package com.matheus.todosimple.repositories;

import com.matheus.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {//Classe que irá ser repository e o tipo do seu identificador (id que é tipo Long, chave primária)


}
