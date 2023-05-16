package com.matheus.todosimple.models.projection;

public interface TaskProjection {//Aqui será onde retorna apenas os valores que queremos, ( função similar ao DTO )

    public Long getId();

    public String getDescription();

}
