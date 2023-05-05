package com.matheus.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)//usar a @table para definir o nome da tabela. user com o "u" minúsculo é preferivel.
@AllArgsConstructor //anotações Lombok
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Definir o número de ID das entidades de forma seguencial (user1, user2, etc..)
    @Column(name = "id", unique = true)//Não tinha tanta necessidade de colocar unique por conta do GenerationType, foi colocado mais por garantia.
    private Long id; //Recomendável utilizar os tipos "Wrapper" para evitar o temido "NullPointException"

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Garantir que a senha seja só de escrita.
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user") //1 "user" pode ter várias Task (colocar o nome da variável de referência da classe)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//buscar apenas o usuário sem necessariamente as tasks
    private List<Task> tasks = new ArrayList<Task>();

}
