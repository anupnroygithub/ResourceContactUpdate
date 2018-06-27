package com.qts.servicelogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qts.model.Resource;

public class UpdateResource {
	static Connection connection = null;
	static Statement statement = null;
	public static void main(String [] args) throws SQLException {
		List<Resource> resourceList = new ArrayList<Resource>();
		try {
			connection = new DBConnection().getConnection();
			if(null != connection) {
				statement = connection.createStatement();
				String selectQueryForUserId = "SELECT user_id,mobile_no FROM resource WHERE resource_type = (SELECT rec_id FROM resource_type WHERE resource_type_name = 'STUDENT' AND is_active = true) AND is_active = true";
				ResultSet result = statement.executeQuery(selectQueryForUserId);
				while(result.next()) {
					Resource resource = new Resource();
					if(null!=result.getString("user_id")) resource.setUserId(result.getString("user_id"));
					if(null!=result.getString("mobile_no")) resource.setMobile(result.getString("mobile_no"));
					resourceList.add(resource);
				}
				System.out.println(resourceList.size());
				String status = updateMobileOfResource(resourceList);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
	}
	
	public static String updateMobileOfResource(List<Resource> resourceList) throws SQLException {
		String status = "fail";
		try {
			for(Resource r : resourceList) {
				String userId = r.getUserId();
				System.out.print("userId:"+userId);
				String mobile = r.getMobile();
				System.out.println("Mobile:"+mobile);
				if(mobile.contains("(") || mobile.contains("-")) {
					mobile = mobile.substring(5, 16);
					mobile = mobile.replace("-", "");
				}
				String updateQueryForMobile = "UPDATE resource SET mobile_no = '"+ mobile +"' WHERE user_id = '"+userId+"' AND is_active = true";
				statement.executeUpdate(updateQueryForMobile);
			}
			status = "success";
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connection.close();
		}
		System.out.println(status);
		return status;
	}
}
