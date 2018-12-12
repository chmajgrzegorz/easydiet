package pl.grzegorzchmaj.easydiet.enums;

import lombok.Getter;


@Getter
public enum HowManyMeals {
    THREE(3,0.3,0.40,0.30),
    FOUR(4,0.25,0.20,0.35,0.20),
    FIVE(5,0.20,0.15,0.30,0.15,0.20);

    private int numberOfMeals;
    private double caloriesPercentage1;
    private double caloriesPercentage2;
    private double caloriesPercentage3;
    private double caloriesPercentage4;
    private double caloriesPercentage5;

    HowManyMeals(int howMany, double caloriesPercentage1, double caloriesPercentage2, double caloriesPercentage3) {
        this.numberOfMeals = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
    }

    HowManyMeals(int howMany, double caloriesPercentage1, double caloriesPercentage2, double caloriesPercentage3, double caloriesPercentage4) {
        this.numberOfMeals = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
        this.caloriesPercentage4 = caloriesPercentage4;
    }

    HowManyMeals(int howMany, double caloriesPercentage1, double caloriesPercentage2, double caloriesPercentage3, double caloriesPercentage4, double caloriesPercentage5) {
        this.numberOfMeals = howMany;
        this.caloriesPercentage1 = caloriesPercentage1;
        this.caloriesPercentage2 = caloriesPercentage2;
        this.caloriesPercentage3 = caloriesPercentage3;
        this.caloriesPercentage4 = caloriesPercentage4;
        this.caloriesPercentage5 = caloriesPercentage5;
    }
}
