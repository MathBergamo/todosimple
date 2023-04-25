package com.matheus.todosimple.services;

import com.matheus.todosimple.models.User;
import com.matheus.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    //Basicamente, o @AutoWired está substituindo o papel do construtor nesse caso.
    @Autowired//Não é possível instânciar uma interface, logo, utilizamos a AutoWired para trazer as notações do Spring dela para esta classe.
    private UserRepository userRepository;


    public User findById(Long id){ //findById é o mais simples dos Reads do CRUD
        Optional<User> user = this.userRepository.findById(id);//findById está sendo pego do JpaRepository
        return user.orElseThrow(() -> new RuntimeException(//orElseThrow permite que caso o valor retorne "nada", ele realize uma exceção.
                "Usuário não encontrado! Id:" + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional//Garante a operação atômica. Exemplo: Ou você salva todos os dados do usuário, ou não salva nenhum.
    public User create(User obj) {
        obj.setId(null);//Garantir que o Id é resetado para não utilizar um Id que já exista.
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){//Só será necessário atualizar a senha.
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possível excluir pois há entidades relacioandas!");
        }
    }
}
