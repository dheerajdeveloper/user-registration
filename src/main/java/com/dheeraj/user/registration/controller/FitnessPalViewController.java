package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.pojo.DayData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("calorie")
@Controller
public class FitnessPalViewController {

  @Autowired CalorieRestController calorieRestController;

  @GetMapping("/getDetail")
  public String content(Model model) {
    List<DayData> data;
    try {
      data = calorieRestController.getMyfitnessPalDetails();
    } catch (Exception e) {
      data = new ArrayList<>();
    }

    model.addAttribute("data", data);
    return "calorieDetails";
  }
}
