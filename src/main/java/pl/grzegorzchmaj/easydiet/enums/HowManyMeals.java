package pl.grzegorzchmaj.easydiet.enums;

import lombok.Getter;


@Getter
public enum HowManyMeals {
    THREE(3,30,45,25),
    FOUR(4,25,35,20,20),
    FIVE(5,25,15,30,10,20);


    private int howMany;
    private int caloriesPercentage1;
    private int caloriesPercentage2;
    private int caloriesPercentage3;
    private int caloriesPercentage4;
    private int caloriesPercentage5;

    HowManyMeals(int howMany, int caloriesPercentage1, int caloriesPercentage2, int caloriesPercentage3) {
        this.howMany = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
    }

    HowManyMeals(int howMany, int caloriesPercentage1, int caloriesPercentage2, int caloriesPercentage3, int caloriesPercentage4) {
        this.howMany = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
        this.caloriesPercentage4 = caloriesPercentage4;
    }

    HowManyMeals(int howMany, int caloriesPercentage1, int caloriesPercentage2, int caloriesPercentage3, int caloriesPercentage4, int caloriesPercentage5) {
        this.howMany = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
        this.caloriesPercentage4 = caloriesPercentage4;
        this.caloriesPercentage5 = caloriesPercentage5;
    }
}
