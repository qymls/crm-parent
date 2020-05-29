package cn.nine.crm.service.impl;

import cn.nine.crm.domain.Meal;
import cn.nine.crm.mapper.MealMapper;
import cn.nine.crm.query.MealQuery;
import cn.nine.crm.service.IMealService;
import cn.nine.crm.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * (Meal)表Service层接口
 *
 * @author ReX
 * @since 2020-05-28 14:54:57
 */
 @Service
public class MealServiceImpl extends BaseServiceImpl<Meal,Long,MealQuery> implements IMealService{

    private MealMapper mealMapper;

    @Autowired
    public void setMealMapper(MealMapper mealMapper) {
        this.mealMapper = mealMapper;
    }
}