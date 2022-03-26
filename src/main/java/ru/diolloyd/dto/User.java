package ru.diolloyd.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
public class User {

    private String username;
    private String password;

}
