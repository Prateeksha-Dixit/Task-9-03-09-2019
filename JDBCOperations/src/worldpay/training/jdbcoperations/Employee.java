package worldpay.training.jdbcoperations;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employee {
	
	public static Scanner sc=new Scanner(System.in);
	
	
	public void insertData(Connection con) throws Exception
	{
		System.out.println("Enter employee no,employee name,salary,department of employee.");
		int eno=sc.nextInt();
		String ename=sc.next();
		int sal=sc.nextInt();
		String dept=sc.next();
		String sql="INSERT into emp values(?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, eno);
		ps.setString(2, ename);
		ps.setInt(3, sal);
		ps.setString(4, dept);
		ps.executeUpdate();
		
	}
	
		public static void main(String args[]) throws Exception
		{
			System.out.println("1. Add Emp \n 2.View All Emp \n 3.Remove Emp \n 4. Clear Data \n 5. Change Sal \n 6.Search Emp \n 7. View dept Wise \n 8. Exit");
			
			int choice=sc.nextInt();
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "root");
			
			
			switch(choice)
			{
			case 1:
				   Employee emp=new Employee();
				   emp.insertData(con);
				   break;
			case 2: 
				String sql="SELECT * from emp";
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(sql);
				while(rs.next())
				{
					System.out.println("Eno: "+rs.getInt(1)+"Ename: "+rs.getString(2)+"Esal: "+rs.getInt(3)+"Emp Department: "+rs.getString(4));
				}
				break;
			case 3:
				System.out.println("enter employee number whose data need to be deleted");
				int eno=sc.nextInt();
				String sql1="DELETE from EMP where eno=?";
				PreparedStatement ps=con.prepareStatement(sql1);
				ps.setInt(1, eno);
				ps.executeUpdate();
				System.out.println("Data of Employee number  "+eno +" Deleted Successfuly");
				break;
			
			case 4:
				String sql2="delete from emp";
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate(sql2);
				System.out.println("Data Cleared");
				break;
			case 5:
				System.out.println("Enter Employee Number whose Salary is to be Changed");
				int en= sc.nextInt();
				System.out.println("Enter new Salary");
				double newSal= sc.nextDouble();
				CallableStatement cs= con.prepareCall("{call updateSalary(?,?)}");
				cs.setInt(1, en);
				cs.setDouble(2, newSal);
				cs.executeUpdate();
				System.out.println("Salary Updated");
				break;
			case 6:
				System.out.println("Enter Employee Number to be Searched");
				int en1= sc.nextInt();
				String sql4="Select * from emp where eno="+en1;
				Statement stmt4 = con.createStatement();
				ResultSet rs1 =stmt4.executeQuery(sql4);
				while(rs1.next())
				{
					int x1 = rs1.getInt(1);
					String str = rs1.getString(2);
					double sal=rs1.getDouble(3);
					String des=rs1.getString(4);
					String dep=rs1.getString(5);
					System.out.println("Employee Number: "+x1+" Employee Name: "+str+" Salary: "+sal+" Designation: "+des+" Department: "+dep);
				}
				break;
			case 7:
				System.out.println("Department wise Employee List");
				String sql5="Select ename,dept from emp group by ename,dept";
				Statement stmt5 = con.createStatement();
				ResultSet rs2 =stmt5.executeQuery(sql5);
				while(rs2.next())
				{
					String str = rs2.getString(1);
					String dep=rs2.getString(2);
					System.out.println(" Employee Name: "+str+"  Department: "+dep);
				}
				break;

			
				default:
					System.out.println("Unable to process the Choice");
				
				
				
				
			}
		}
		
		
		
		

	

}
