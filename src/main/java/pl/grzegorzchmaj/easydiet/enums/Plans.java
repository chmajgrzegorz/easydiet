package pl.grzegorzchmaj.easydiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plans {
    LOSE_WEIGHT("Chcę stracić wagę", -200),
    MAINTAIN_WEIGHT("Chcę utrzymać wage", 0),
    GAIN_WEIGHT("Chcę przytyć", 200);

    private String description;
    private int calories;

}
