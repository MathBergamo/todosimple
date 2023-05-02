package com.matheus.todosimple.controllers;

import com.matheus.todosimple.models.User;
import com.matheus.todosimple.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired//Usar o @AutoWired em uma classe que não tiver tantos métodos/atributos.
    private UserService userService;

    @GetMapping("/{id}")//PathVariable permite que o GetMapping identifique que o id que ele busca dentro dele, é o que está com a anotação @PathVariable
    public ResponseEntity<User> findById(@PathVariable Long id){//ResponseEntity garante o tratamento para a entidade, recomendável utilizar nessa situação
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(User.CreateUser.class)//Validando o parâmetro do método através da interface CreateUser e seus métodos.
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){//@Valid Identificar qual parâmetro quer ser validado
        this.userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
