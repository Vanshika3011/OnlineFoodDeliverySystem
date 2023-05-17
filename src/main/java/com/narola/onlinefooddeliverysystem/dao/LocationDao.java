package com.narola.onlinefooddeliverysystem.dao;

import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao {

    public static List<City> getCities(int stateId) throws DAOLayerException {
        List<City> cityList = new ArrayList<>();
        try {
            String query = "Select id, city_name from city where state_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, stateId);
            ResultSet rs = stmt.executeQuery();
//            System.out.println("id\t\tcity");
            while (rs.next()) {
                City city = new City();
                city.setCityId(rs.getInt(1));
                city.setCityName(rs.getString(2));
                cityList.add(city);
//                System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2));
            }
            //DONT LOG AND THROW EXCEPTION
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        }
        return cityList;
    }

    //TODO : Change implementation. Return State list. and remove sysout
    public static void getStates() throws DAOLayerException {
        try {
            String query = "Select id, state_name from state";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("id\t\tstate");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching states", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching statesz", e);
        }
    }

    public static boolean isStateExists(int stateId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select count(*) from state where id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, stateId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching states", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching states", e);
        }
    }

    public static boolean isCityExists(int cityId, int stateId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select count(*) from city where id = ? and state_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cityId);
            stmt.setInt(2, stateId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        }
    }
}
