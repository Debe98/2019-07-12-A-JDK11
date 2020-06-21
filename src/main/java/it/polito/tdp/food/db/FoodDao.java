package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(Map<Integer, Food> foods){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f = new Food(res.getInt("food_code"),
							res.getString("display_name")
							);
					list.add(f);
					foods.put(f.getFood_code(), f);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(Map <Integer, Portion> porzioni){
		String sql = "SELECT * \r\n" + 
				"FROM `portion`;" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Portion p = new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							);
					list.add(p);
					porzioni.put(p.getPortion_id(), p);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<Food> getVertex(Map<Integer, Food> foods, int n) {
		String sql = "SELECT portion.food_code, COUNT(DISTINCT portion.portion_id) AS cnt\r\n" + 
				"FROM `portion`\r\n" + 
				"GROUP BY portion.food_code\r\n" + 
				"HAVING COUNT(DISTINCT portion.portion_id) <= ?;" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, n);
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(foods.get(res.getInt("food_code")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<Arco> getArchi(Map<Integer, Food> foods, int n) {
		String sql = "SELECT fc1.food_code, fc2.food_code, AVG(c1.condiment_calories) AS peso\r\n" + 
				"FROM food_condiment AS fc1, food_condiment AS fc2, condiment AS c1, condiment AS c2\r\n" + 
				"WHERE fc1.condiment_code = c1.condiment_code AND fc2.condiment_code = c2.condiment_code\r\n" + 
				"	AND fc1.food_code < fc2.food_code AND fc1.condiment_code = fc2.condiment_code\r\n" + 
				"	AND fc1.food_code IN (\r\n" + 
				"		SELECT portion.food_code\r\n" + 
				"		FROM `portion`\r\n" + 
				"		GROUP BY portion.food_code\r\n" + 
				"		HAVING COUNT(DISTINCT portion.portion_id) <= ?)\r\n" + 
				"	AND fc2.food_code IN (\r\n" + 
				"		SELECT portion.food_code\r\n" + 
				"		FROM `portion`\r\n" + 
				"		GROUP BY portion.food_code\r\n" + 
				"		HAVING COUNT(DISTINCT portion.portion_id) <= ?)\r\n" + 
				"GROUP BY fc1.food_code, fc2.food_code;\r\n" + 
				"" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, n);
			st.setInt(2, n);
			
			List<Arco> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Arco a = new Arco(foods.get(res.getInt("fc1.food_code")), foods.get(res.getInt("fc2.food_code")), res.getDouble("peso")); 
					list.add(a);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
