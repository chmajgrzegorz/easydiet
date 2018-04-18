package pl.grzegorzchmaj.easydiet.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Sex {
    WOMAN("Kobieta", 'K'),
    MAN("Mężczyzna", 'K');

    private String description;
    private char sex;
}
