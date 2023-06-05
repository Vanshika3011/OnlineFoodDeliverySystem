package com.narola.onlinefooddeliverysystemV1.dao;

import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.CuisineCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuisineCategoryDao {

    public List<CuisineCategory> getCategory() throws DAOLayerException {
        List<CuisineCategory> cuisineCategoryList = new ArrayList<>();
        try {
            String query = "Select * from cuisine_category";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CuisineCategory cuisineCategory = new CuisineCategory();
                cuisineCategory.setCuisineId(rs.getInt(1));
                cuisineCategory.setCuisineName(rs.getString(2));

                cuisineCategoryList.add(cuisineCategory);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cuisine details", e);
        }
        return cuisineCategoryList;
    }
}
