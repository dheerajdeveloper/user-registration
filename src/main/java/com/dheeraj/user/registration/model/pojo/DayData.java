package com.dheeraj.user.registration.model.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DayData {
    int slno;
    String date;
    int calories;
    int carbs;
    int fat;

    int protein;
    int cholest;
    int sodium;
    int sugar;
    int fiber;

    public DayData(
            int slno,
            String date,
            int calories,
            int carbs,
            int fat,
            int protein,
            int cholest,
            int sodium,
            int sugar,
            int fiber) {
        this.slno = slno;
        this.date = date;
        this.calories = calories;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
        this.cholest = cholest;
        this.sodium = sodium;
        this.sugar = sugar;
        this.fiber = fiber;
    }
}

