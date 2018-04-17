package pl.grzegorzchmaj.easydiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PhysicalActivity {
    VERY_SMALL("Znikoma(brak ćwiczeń, praca siedząca)",1),
    SMALL("Mała(ćwiczenia raz na tydzień, lekka praca)",2),
    MEDIUM("Srednia(ćwiczenia 2 razy w tygodniu)",3),
    LARGE("Duża(ciężki trening kilka razy w tygodniu)",4),
    VERY_LARGE("Bardzo duża(conajmniej 4 ciężkie treningi w tygodniu, praca fizyczna)",5);


    private String description;
    private int level;
}
