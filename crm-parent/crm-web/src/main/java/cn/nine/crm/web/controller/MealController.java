package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Meal;
import cn.nine.crm.query.MealQuery;
import cn.nine.crm.service.IMealService;
import cn.nine.crm.util.LogAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * (Meal)表Controller
 *
 * @author ReX
 * @since 2020-05-28 14:54:57
 */
@Controller
@RequestMapping("meal")
@LogAnnotations
@SuppressWarnings(value = "all")/*抑制警告*/
public class MealController extends BaseController<Meal,Long, MealQuery>{
    private IMealService mealService;

    @Autowired
    public void setMealService(IMealService mealService) {
        this.mealService = mealService;
    }
}