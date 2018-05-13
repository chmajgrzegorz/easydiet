package pl.grzegorzchmaj.easydiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PhysicalActivity {
    VERY_SMALL("Prawie brak - brak ćwiczeń, praca biurowa",1.2),
    SMALL("Lekka - lekkie ćwiczenia kilka razy w miesiącu",1.37),
    MEDIUM("Umiarkowana - ćwiczenia 1-2 razy w tygodniu",1.45),
    LARGE("Duża - ćwiczenia 3-5 razy w tygodniu, praca fizyczna",1.7),
    VERY_LARGE("Bardzo duża - codzienne ćwiczenia, praca fizyczna",1.9);

    private String description;
    private double level;
}
