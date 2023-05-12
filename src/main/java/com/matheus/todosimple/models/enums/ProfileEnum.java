package com.matheus.todosimple.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
    ADMIN(1, "ROLE_ADMIN"), //ROLE_ palavra reservada do spring
    USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    public static ProfileEnum toEnum(Integer code) {
        if (Objects.isNull(code)){
            return null;
        }

        for (ProfileEnum profile: ProfileEnum.values()) {
            if (code.equals(profile.getCode())){
                return profile;
            }

            throw new IllegalArgumentException("Invalid code" + code);
        }
    }
}
