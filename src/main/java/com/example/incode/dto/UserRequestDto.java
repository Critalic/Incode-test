package com.example.incode.dto;

import com.example.incode.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotEmpty(message = "User's name can't be empty")
    @Size(max = 100, message = "User's name can't be longer than 100 characters")
    private String name;

    @NotEmpty(message = "User's email can't be empty")
    @Size(max = 100, message = "User's email can't be longer than 100 characters")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$",
            message = "User's email format is invalid")
    private String email;

    public User mapToUser() {
        return new User(name, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestDto that = (UserRequestDto) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
