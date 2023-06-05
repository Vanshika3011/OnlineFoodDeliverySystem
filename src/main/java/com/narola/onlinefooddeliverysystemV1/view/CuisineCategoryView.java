package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.dao.CuisineCategoryDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.CuisineCategory;

import java.util.List;

public class CuisineCategoryView {

    private CuisineCategoryDao cuisineCategoryDao = new CuisineCategoryDao();

    public void printCuisineCategoryList(List<CuisineCategory> cuisineCategoryList) {
        System.out.println();
        for (CuisineCategory cuisineCategory : cuisineCategoryList) {
            System.out.println(cuisineCategory.getCuisineId() + " " + cuisineCategory.getCuisineName());
        }
    }

    public int displayCuisineAndTakeInput(){
        int cuisineId = 0;  
        try {
            List<CuisineCategory> cuisineCategoryList = cuisineCategoryDao.getCategory();
            System.out.println("Cuisine Category");
            printCuisineCategoryList(cuisineCategoryList);
            System.out.print("Enter Cuisine Category Id               : ");
            cuisineId = InputHandler.getNumberInput();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return cuisineId;
    }
}
