package mini_project;
import java.util.*;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
public class JDBC {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/634_Assgn1";
    static final String USER = "root";
    static final String PASS = "Usavkare@2004";
    
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int ch = 0, s = 0,m;
        String query;
        Scanner sc = new Scanner(System.in);
        Timer timer = new Timer();
       
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            do {
            System.out.println("=========MENU=========");
            System.out.println("1. LOG IN\n2. CREATE NEW USER\n3.Exit\nEnter your choice");
            s = sc.nextInt();
            sc.nextLine(); // Consume the newline
            
            switch(s) {
                case 1:
                    System.out.println("---------------------------------");
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    System.out.println("---------------------------------");
                    
                    query = "SELECT * FROM user WHERE username = ? AND password = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    
                    resultSet = pstmt.executeQuery();
                    
                    if (resultSet.next()) {
                        System.out.println("Login successful!");
                        int userId = resultSet.getInt("user_id");
                      /*  NotificationTask notificationTask = new NotificationTask();
                        timer.scheduleAtFixedRate(notificationTask, 0, 2 * 60 * 60 * 1000); // 2 hours interval

                       */
                        

                      
                        do {
                            System.out.println("What would you like to do today?");
                            System.out.println("1. View my profile\n2. View today's activity\n3. View today's sleep pattern\n4. View my vitals");
                            System.out.println("5. View my stress percentage\n6. Give me exercise based on my BMI");
                            System.out.println("7. Update Profile\n8.Log Sleep Activity\n9.Recommend Diet based on my BMI\n10.Record Water Intake\n11.Add Activity\n12.Update Vitals\n13.Log Out");
                            System.out.println("Enter your choice: ");
                            ch = sc.nextInt();
                            sc.nextLine();
                            
                            switch(ch) {
                                case 1:
                                    query = "SELECT email, age, height, weight FROM user WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    resultSet = pstmt.executeQuery();
                                    
                                    if (resultSet.next()) {
                                        String email = resultSet.getString("email");
                                        int age = resultSet.getInt("age");
                                        double height = resultSet.getDouble("height");
                                        double weight = resultSet.getDouble("weight");
                                        System.out.println("=============YOUR INFO=============");
                                        System.out.println("Email: " + email);
                                        System.out.println("Age: " + age);
                                        System.out.println("Height: " + height);
                                        System.out.println("Weight: " + weight);
                                        System.out.println("===================================");
                                    }
                                    break;

                                case 2:

                                	 query = "SELECT * FROM activity_log WHERE user_id = ?";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, userId);

                                	 resultSet = pstmt.executeQuery();



                                	 System.out.println("=============TODAY'S ACTIVITY LOG=============");

                                	 if (resultSet.next()) {

                                	 do {

                                	 int logId = resultSet.getInt("log_id");

                                	 String activityType = resultSet.getString("activity_type");

                                	 int duration = resultSet.getInt("duration");

                                	 double caloriesBurned = resultSet.getDouble("calory_burnt");

                                	 Timestamp dateTime = resultSet.getTimestamp("date_time");



                                	 System.out.println("Log ID: " + logId);

                                	 System.out.println("Activity Type: " + activityType);

                                	 System.out.println("Duration: " + duration + " minutes");

                                	 System.out.println("Calories Burned: " + caloriesBurned);

                                	 System.out.println("Date Time: " + dateTime);

                                	 System.out.println("---------------------------------");

                                	 } while (resultSet.next());

                                	 } else {

                                	 System.out.println("No activity logs found for today.");

                                	 System.out.println("Would you like to insert an activity? (yes/no)");

                                	 String choice = sc.next();

                                	 if ("yes".equalsIgnoreCase(choice)) {

                                	 System.out.println("---------------------------------");

                                	 System.out.println("Add Today's Activity");

                                	 System.out.println("---------------------------------");

                                	 System.out.print("Enter log ID: ");

                                	 int logId = sc.nextInt();

                                	 System.out.print("Enter activity type (e.g., running, jogging, cycling): ");

                                	 String activityType = sc.next();

                                	 System.out.print("Enter duration (in minutes): ");

                                	 int duration = sc.nextInt();

                                	 System.out.print("Enter calories burned: ");

                                	 double caloriesBurned = sc.nextDouble();

                                	 

                                	 // Insert into the activity_log table

                                	 query = "INSERT INTO activity_log (log_id, user_id, activity_type, duration, calory_burnt, date_time) VALUES (?, ?, ?, ?, ?, NOW())";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, logId);

                                	 pstmt.setInt(2, userId);

                                	 pstmt.setString(3, activityType);

                                	 pstmt.setInt(4, duration);

                                	 pstmt.setDouble(5, caloriesBurned);

                                	 

                                	 int rowsInserted = pstmt.executeUpdate();

                                	 if (rowsInserted > 0) {

                                	 System.out.println("Activity added successfully!");

                                	 } else {

                                	 System.out.println("Failed to add activity. Please try again.");

                                	 }

                                	 }

                                	 }

                                	 System.out.println("===============================================");

                                	 break;



                                	 
                                case 3:
                                    query = "SELECT * FROM sleep_pattern WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    
                                    resultSet = pstmt.executeQuery();
                                    
                                    System.out.println("=============TODAY'S SLEEP PATTERN=============");
                                    while (resultSet.next()) {
                                        Timestamp sleepStartTime = resultSet.getTimestamp("sleep_start_time");
                                        Timestamp sleepEndTime = resultSet.getTimestamp("sleep_end_time");
                                        int sleepQuality = resultSet.getInt("sleep_quality");
                                        
                                        System.out.println("Sleep Start Time: " + sleepStartTime);
                                        System.out.println("Sleep End Time: " + sleepEndTime);
                                        System.out.println("Sleep Quality: " + sleepQuality);
                                    }
                                    System.out.println("===============================================");
                                    break;

                                case 4:
                                	query = "SELECT * FROM vital_signs WHERE user_id = ?";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, userId);

                                	 resultSet = pstmt.executeQuery();



                                	 System.out.println("=============YOUR VITALS=============");

                                	 if (resultSet.next()) {

                                	 do {

                                	 int signId = resultSet.getInt("sign_id");

                                	 double temperature = resultSet.getDouble("temperature");

                                	 int heartRate = resultSet.getInt("heart_rate");

                                	 int oxygenLevel = resultSet.getInt("oxygen_level");

                                	 Timestamp dateTime = resultSet.getTimestamp("time_stamp");



                                	 System.out.println("Sign ID: " + signId);

                                	 System.out.println("Temperature: " + temperature + " °C");

                                	 System.out.println("Heart Rate: " + heartRate + " bpm");

                                	 System.out.println("Oxygen Level: " + oxygenLevel + " mmHg");

                                	 System.out.println("Date Time: " + dateTime);

                                	 System.out.println("---------------------------------");

                                	 } while (resultSet.next());

                                	 } else {

                                	 System.out.println("No vitals recorded.");

                                	 System.out.println("Would you like to insert vitals? (yes/no)");

                                	 String choice = sc.next();

                                	 if ("yes".equalsIgnoreCase(choice)) {

                                	 System.out.println("---------------------------------");

                                	 System.out.println("Add Today's Vitals");

                                	 System.out.println("---------------------------------");

                                	 System.out.print("Enter sign ID: ");

                                	 int signId = sc.nextInt();

                                	 System.out.print("Enter temperature (in °C): ");

                                	 double temperature = sc.nextDouble();

                                	 System.out.print("Enter heart rate (in bpm): ");

                                	 int heartRate = sc.nextInt();

                                	 System.out.print("Enter oxygen level (in mmHg): ");

                                	 int oxygenLevel = sc.nextInt();
                                	 System.out.print("Enter date time (YYYY-MM-DD HH:MM:SS): ");

                                	 
                                	 String dateTimeStr = sc.next();

                                	 Timestamp dateTime = Timestamp.valueOf(dateTimeStr);


                                	 
                                	 System.out.print("Enter water level (in litres): ");

                                	 double water = sc.nextInt();

                       

                                	 



                                	 // Insert into the vital_signs table

                                	 query = "INSERT INTO vital_signs (sign_id, user_id, temperature, heart_rate, oxygen_level, time_stamp, water_level) VALUES (?, ?, ?, ?, ?, ?,?)";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, signId);

                                	 pstmt.setInt(2, userId);

                                	 pstmt.setDouble(3, temperature);

                                	 pstmt.setInt(4, heartRate);

                                	 pstmt.setInt(5, oxygenLevel);
                                	 
                                	 pstmt.setTimestamp(6, dateTime);
                                	 
                                	 pstmt.setDouble(7, water);

                                



                                	int rowsInserted = pstmt.executeUpdate();

                                	 if (rowsInserted > 0) {

                                	 System.out.println("Vitals added successfully!");

                                	 } else {

                                	 System.out.println("Failed to add vitals. Please try again.");

                                	 }

                                	 }

                                	 }

                                	 System.out.println("====================================");

                                	 break;





                                	 

                                case 5:
                                    query = "SELECT sleep_quality FROM sleep_pattern WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    resultSet = pstmt.executeQuery();
                                    
                                    double totalSleepQuality = 0;
                                    int sleepCount = 0;
                                    while (resultSet.next()) {
                                        totalSleepQuality += resultSet.getInt("sleep_quality");
                                        sleepCount++;
                                    }
                                    
                                    query = "SELECT age FROM user WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    resultSet = pstmt.executeQuery();
                                    int age = 0;
                                    if (resultSet.next()) {
                                        age = resultSet.getInt("age");
                                    }
                                    
                                    query = "SELECT heart_rate, oxygen_level FROM vital_signs WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    resultSet = pstmt.executeQuery();
                                    
                                    double averageHeartRate = 0;
                                    double averageOxygenLevel = 0;
                                    int vitalsCount = 0;
                                    while (resultSet.next()) {
                                        averageHeartRate += resultSet.getInt("heart_rate");
                                        averageOxygenLevel += resultSet.getDouble("oxygen_level");
                                        vitalsCount++;
                                    }
                                    
                                    if (sleepCount > 0 && vitalsCount > 0) {
                                        double averageSleepQuality = totalSleepQuality / sleepCount;
                                        averageHeartRate /= vitalsCount;
                                        averageOxygenLevel /= vitalsCount;
                                        
                                        // Calculate stress percentage based on the formula
                                        double stressPercentage = (averageSleepQuality * 0.4) + (age * 0.3) + (averageHeartRate * 0.15) + (averageOxygenLevel * 0.15);
                                        
                                        System.out.println("=============STRESS CALCULATION=============");
                                        System.out.println("Stress Percentage: " + stressPercentage + "%");
                                        System.out.println("===========================================");
                                    } else {
                                        System.out.println("Insufficient data to calculate stress.");
                                    }
                                    break;
                               
                                case 6:
                                    query = "SELECT height, weight FROM user WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    resultSet = pstmt.executeQuery();
                                    
                                    double height = 0;
                                    double weight = 0;
                                    if (resultSet.next()) {
                                        height = resultSet.getDouble("height") / 100;  // Convert height from cm to meters
                                        weight = resultSet.getDouble("weight");
                                    }
                                    
                                    if (height > 0 && weight > 0) {
                                        double bmi = weight / (height * height);
                                        
                                        System.out.println("=============BMI CALCULATION=============");
                                        System.out.println("Your BMI: " + bmi);
                                        
                                        if (bmi < 18.5) {
                                            System.out.println("You are underweight. Recommended exercise: High-calorie diet and strength training.");
                                        } else if (bmi >= 18.5 && bmi < 25) {
                                            System.out.println("You have a normal weight. Recommended exercise: Cardio exercises like jogging or swimming.");
                                        } else if (bmi >= 25 && bmi < 30) {
                                            System.out.println("You are overweight. Recommended exercise: Aerobic exercises like walking or cycling.");
                                        } else {
                                            System.out.println("You are obese. Recommended exercise: Intense aerobic exercises and a balanced diet.");
                                        }
                                        System.out.println("========================================");
                                    } else {
                                        System.out.println("Insufficient data to calculate BMI.");
                                    }
                                    break;
                                

                                case 7: // Update Profile
                                    System.out.println("---------------------------------");
                                    System.out.print("Enter new email: ");
                                    String newEmail = sc.nextLine(); // Read email
                                    System.out.print("Enter new age: ");
                                    int newAge = sc.nextInt(); // Read age
                                    sc.nextLine(); // Consume the newline character
                                    System.out.print("Enter new height (in cm): ");
                                    double newHeight = sc.nextDouble(); // Read height
                                    sc.nextLine(); // Consume the newline character
                                    System.out.print("Enter new weight (in kg): ");
                                    double newWeight = sc.nextDouble(); // Read weight
                                    sc.nextLine(); // Consume the newline character
                                    System.out.print("Enter new gender: ");
                                    String newGender = sc.nextLine(); // Read gender
                                    System.out.println("---------------------------------");

                                    // Update the user's profile in the database
                                    query = "UPDATE user SET email = ?, age = ?, gender = ?, height = ?, weight = ? WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setString(1, newEmail);
                                    pstmt.setInt(2, newAge);
                                    pstmt.setString(3, newGender);
                                    pstmt.setDouble(4, newHeight);
                                    pstmt.setDouble(5, newWeight);
                                    
                                    pstmt.setInt(6, userId);

                                    int profileRowsUpdated = pstmt.executeUpdate();

                                    if (profileRowsUpdated > 0) {
                                        System.out.println("Profile updated successfully!");
                                    } else {
                                        System.out.println("Failed to update profile.");
                                    }
                                    break;


                                case 8: // Log Sleep Activity
                                    System.out.println("---------------------------------");
                                    System.out.print("Enter pattern ID: ");
                                    int pattern_id = sc.nextInt();
                                    sc.nextLine(); // Consume the newline character

                                    System.out.print("Enter sleep start time (YYYY-MM-DD HH:mm:ss): ");
                                    String sleepStartTimeStr = sc.nextLine();

                                    System.out.print("Enter sleep end time (YYYY-MM-DD HH:mm:ss): ");
                                    String sleepEndTimeStr = sc.nextLine();

                                    System.out.print("Enter sleep quality (out of 10): ");
                                    int sleepQuality = sc.nextInt();
                                    sc.nextLine(); // Consume the newline character
                                    System.out.println("---------------------------------");

                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        dateFormat.setLenient(false); // Set lenient to false to enforce strict parsing

                                        // Parse sleep start and end times into Timestamp objects
                                        Date parsedStartTime = dateFormat.parse(sleepStartTimeStr);
                                        Date parsedEndTime = dateFormat.parse(sleepEndTimeStr);
                                        Timestamp sleepStartTime = new Timestamp(parsedStartTime.getTime());
                                        Timestamp sleepEndTime = new Timestamp(parsedEndTime.getTime());

                                        // Insert the sleep activity log into the database
                                        query = "INSERT INTO sleep_pattern (pattern_id, user_id, sleep_start_time, sleep_end_time, sleep_quality) VALUES (?, ?, ?, ?, ?)";
                                        pstmt = conn.prepareStatement(query);
                                        pstmt.setInt(1, pattern_id);
                                        pstmt.setInt(2, userId);
                                        pstmt.setTimestamp(3, sleepStartTime);
                                        pstmt.setTimestamp(4, sleepEndTime);
                                        pstmt.setInt(5, sleepQuality);

                                        int sleepRowsInserted = pstmt.executeUpdate();

                                        if (sleepRowsInserted > 0) {
                                            System.out.println("Sleep activity logged successfully!");
                                        } else {
                                            System.out.println("Failed to log sleep activity.");
                                        }
                                    } catch (ParseException e) {
                                        System.out.println("Invalid date-time format. Please enter date-time in the format: YYYY-MM-DD HH:mm:ss");
                                    }
                                    break;

                                case 9:

                                	 query = "SELECT height, weight FROM user WHERE user_id = ?";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, userId);

                                	 resultSet = pstmt.executeQuery();

                                	 

                                	 double heightForBMI = 0;

                                	 double weightForBMI = 0;

                                	 if (resultSet.next()) {

                                	 heightForBMI = resultSet.getDouble("height") / 100; // Convert height from cm to meters

                                	 weightForBMI = resultSet.getDouble("weight");

                                	 }

                                	 

                                	 if (heightForBMI > 0 && weightForBMI > 0) {

                                	 double bmi = weightForBMI / (heightForBMI * heightForBMI);

                                	 

                                	 System.out.println("=============DIET RECOMMENDATION=============");

                                	 System.out.println("Your BMI: " + bmi);

                                	 

                                	 if (bmi < 18.5) {

                                	 System.out.println("You are underweight.");

                                	 System.out.println("Recommended Diet Plan:");

                                	 System.out.println("- Increase protein intake (meat, fish, eggs).");

                                	 System.out.println("- Include healthy fats (avocado, nuts, olive oil).");

                                	 System.out.println("- Incorporate carbohydrates (whole grains, fruits, vegetables).");

                                	 } else if (bmi >= 18.5 && bmi < 25) {

                                	 System.out.println("You have a normal weight.");

                                	 System.out.println("Recommended Diet Plan:");

                                	 System.out.println("- Maintain a balanced diet.");

                                	 System.out.println("- Include protein sources (lean meats, legumes, dairy).");

                                	 System.out.println("- Eat plenty of fruits and vegetables.");

                                	 } else if (bmi >= 25 && bmi < 30) {

                                	 System.out.println("You are overweight.");

                                	 System.out.println("Recommended Diet Plan:");

                                	 System.out.println("- Reduce calorie intake by avoiding high-calorie foods.");

                                	 System.out.println("- Increase fiber intake (whole grains, fruits, vegetables).");

                                	 System.out.println("- Drink plenty of water and limit sugary drinks.");

                                	 } else {

                                	 System.out.println("You are obese.");

                                	 System.out.println("Recommended Diet Plan:");

                                	 System.out.println("- Consult a nutritionist for a personalized diet plan.");

                                	 System.out.println("- Focus on reducing calorie intake.");

                                	 System.out.println("- Incorporate regular exercise and maintain hydration.");

                                	 }

                                	 System.out.println("=============================================");

                                	 } else {

                                	 System.out.println("Insufficient data to recommend a diet plan.");

                                	 }

                                	 break;


                              

                                case 10: // Record Water Intake
                                	query = "SELECT water_level FROM vital_signs WHERE user_id = ?";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, userId);

                                	 resultSet = pstmt.executeQuery();



                                	 System.out.println("=============YOUR WATER LEVEL=============");

                                	 if (resultSet.next()) {

                                	 double waterLevel = resultSet.getDouble("water_level");

                                	 System.out.println("Water Level: " + waterLevel + " liters");

                                	 if (waterLevel < 3.0) {

                                	 System.out.println("Your water level is below the recommended amount. Please drink more water.");

                                	 }

                                	 } else {

                                	 System.out.println("No water level recorded.");

                                	 

                                	 }

                                	 System.out.println("=========================================");

                                	
                                   
                                    System.out.print("Enter amount of water intake (in liters): ");
                                    double waterIntake = sc.nextDouble();
                                    System.out.println("---------------------------------");

                                    // Insert the water intake record into the database
                                    query = "INSERT INTO water_intake (user_id, amount, date_time) VALUES (?, ?, NOW())";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, userId);
                                    pstmt.setDouble(2, waterIntake);

                                    int waterRowsInserted = pstmt.executeUpdate();

                                    if (waterRowsInserted > 0) {
                                        System.out.println("Water intake recorded successfully!");
                                    } else {
                                        System.out.println("Failed to record water intake.");
                                    }
                                    System.out.println("---------------------------------");
                                    break;
                                case 11:

                                	 System.out.println("---------------------------------");

                                	 System.out.println("Add Today's Activity");

                                	 System.out.println("---------------------------------");

                                	 System.out.print("Enter log ID: ");

                                	 int logId = sc.nextInt();

                                	 System.out.print("Enter activity type (e.g., running, jogging, cycling): ");

                                	 String activityType = sc.next();

                                	 System.out.print("Enter duration (in minutes): ");

                                	 int duration = sc.nextInt();

                                	 System.out.print("Enter calories burned: ");

                                	 double caloriesBurned = sc.nextDouble();

                                	 

                                	 // Insert into the activity_log table

                                	 query = "INSERT INTO activity_log (log_id, user_id, activity_type, duration, calory_burnt, date_time) VALUES (?, ?, ?, ?, ?, NOW())";

                                	 pstmt = conn.prepareStatement(query);

                                	 pstmt.setInt(1, logId);

                                	 pstmt.setInt(2, userId);

                                	 pstmt.setString(3, activityType);

                                	 pstmt.setInt(4, duration);

                                	 pstmt.setDouble(5, caloriesBurned);

                                	 

                                	 int rowsInserted = pstmt.executeUpdate();

                                	 if (rowsInserted > 0) {

                                	 System.out.println("Activity added successfully!");

                                	 } else {

                                	 System.out.println("Failed to add activity. Please try again.");

                                	 }

                                	 break;
                                case 12:
                                    // Prompt the user to enter new vital signs data
                                    System.out.print("Enter new heart rate: ");
                                    int newHeartRate = sc.nextInt();
                                    System.out.print("Enter new oxygen level: ");
                                    double newOxygenLevel = sc.nextDouble();
                                    System.out.print("Enter new temperature: ");
                                    double newTemperature = sc.nextDouble();
                                    
                                    // Prepare and execute the SQL update query
                                    query = "UPDATE vital_signs SET heart_rate = ?, oxygen_level = ?, temperature = ? WHERE user_id = ?";
                                    pstmt = conn.prepareStatement(query);
                                    pstmt.setInt(1, newHeartRate);
                                    pstmt.setDouble(2, newOxygenLevel);
                                    pstmt.setDouble(3, newTemperature);
                                    pstmt.setInt(4, userId);
                                    
                                    int rowsAffected = pstmt.executeUpdate();
                                    
                                    if (rowsAffected > 0) {
                                        System.out.println("Vital signs updated successfully!");
                                    } else {
                                        System.out.println("Failed to update vital signs.");
                                    }
                                    break;



                            }
                        } while(ch != 13);
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                    break;
                
                case 2:
                    System.out.println("---------------------------------");
                    System.out.print("Enter username: ");
                    String newUsername = sc.nextLine();
                    System.out.print("Enter password: ");
                    String newPassword = sc.nextLine();
                    System.out.print("Enter email: ");
                    String userEmail = sc.nextLine();
                    System.out.print("Enter age: ");
                    int userAge = sc.nextInt();
                    sc.nextLine(); // Consume the newline
                    System.out.print("Enter height (in cm): ");
                    double userHeight = sc.nextDouble();
                    sc.nextLine(); // Consume the newline
                    System.out.print("Enter weight (in kg): ");
                    double userWeight = sc.nextDouble();
                    sc.nextLine(); // Consume the newline
                    System.out.print("Enter gender: ");
                    String userGender = sc.nextLine();
                    System.out.println("---------------------------------");

                    // Insert the new user into the database
                    query = "INSERT INTO user (username, password, email, age, gender, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, newUsername);
                    pstmt.setString(2, newPassword);
                    pstmt.setString(3, userEmail);
                    pstmt.setInt(4, userAge);
                    pstmt.setString(5, userGender);
                    pstmt.setDouble(6, userHeight);
                    pstmt.setDouble(7, userWeight);
                   

                    int rowsInserted = pstmt.executeUpdate();

                    if (rowsInserted > 0) {
                        System.out.println("User created successfully!");
                    } else {
                        System.out.println("Failed to create user.");
                    }

                    break;

            }
            } while(s!=3);
            System.out.println("1");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException se) {
            se.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    static class NotificationTask extends TimerTask {
        private Scanner scanner;

        public NotificationTask() {
            this.scanner = new Scanner(System.in);
        }

        @Override
        public void run() {
        	System.out.println("Notification: It's time to check your activities!");
        	System.out.print("Did you consume a glass of water? (yes/no): ");
            try {
                String choice = scanner.next();
                scanner.nextLine();
                System.out.println();
                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("Great job! Staying hydrated is important for your health.");
                } else if (choice.equalsIgnoreCase("no")) {
                    System.out.println("Please remember to drink water regularly throughout the day.");
                } else {
                    System.out.println("Error: Invalid choice. Please enter 'yes' or 'no'.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter 'yes' or 'no'.");
                scanner.nextLine(); // consume the invalid input
            }
        }
    }
}

