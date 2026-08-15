<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ofss.model.InvestmentAdvisor" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        /* Add your CSS styles here */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Center vertically */
            margin: 100px;
            background-color: #e9ecef; /* Light background for better visual */
            overflow-x: hidden; /* Prevent horizontal overflow */
        }

        .dashboard-container {
            max-width: 1200px;
            margin: 20px 20px; /* Add margin for smaller screens */
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Adds some shadow for styling */
            background-color: #f9f9f9; /* Background color for contrast */
            border-radius: 10px; /* Rounded corners */
            overflow: hidden; /* Prevent overflow from the container */
        }

        .admin-options {
            margin-top: 20px;
        }
        .admin-options a {
            margin-right: 15px;
        }
        .update-form {
            margin-top: 30px;
        }
        .update-form label {
            display: block;
            margin: 10px 0 5px;
        }
        .update-form input {
            padding: 8px;
            width: 100%;
            margin-bottom: 10px;
            box-sizing: border-box;
        }
        .update-form button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }
        .update-form button:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 30px;
            box-sizing: border-box;
            table-layout: fixed; /* Ensures equal column width */
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 12px;
            text-align: left;
            overflow: hidden; /* Prevent text overflow */
            text-overflow: ellipsis; /* Add ellipsis for long text */
            white-space: nowrap; /* Prevent text wrapping */
        }
        thead th {
            background-color: #007bff;
            color: white;
        }
        .delete-btn {
            background-color: #d9534f;
            color: white;
            border: none;
            cursor: pointer;
            padding: 5px 10px;
        }
        .delete-btn:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <h2>Welcome to the Admin Dashboard</h2>
        <p>You are logged in as <strong>Admin</strong>.</p>
        
        <!-- Admin options section -->
        <div class="admin-options">
            <a href="#" class="option">Manage Users</a>
            <a href="#" class="option">View Reports</a>
            <a href="#" class="option">Settings</a>
        </div>

        <!-- Form to update email and password -->
        <div class="update-form">
            <h3>Update Email or Password of Admin</h3>
            <form action="/admin/portal" method="POST">
                <label for="email">New Email:</label>
                <input type="email" id="username" name="username" placeholder="Enter new email" required>

                <label for="password">New Password:</label>
                <input type="password" id="password" name="password" placeholder="Enter new password" required>

                <button type="submit">Update</button>
            </form>
        </div>
        
        <!-- Success/Error message -->
        <div class="message <%= request.getAttribute("status") != null ? request.getAttribute("status").equals("success") ? "success" : "error" : "" %>" 
             style="<%= request.getAttribute("message") != null ? "display:block;" : "display:none;" %>">
            <%= request.getAttribute("message") %>
        </div>

        <!-- Table to display Investment Advisors -->
        <h3>Investment Advisors</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <!-- Use Java for-each loop in JSP -->
                <%
                    // Access the advisors list from the model attribute
                    List<InvestmentAdvisor> advisors = (List<InvestmentAdvisor>) request.getAttribute("advisors");
                    int rowIndex = 1;
                    if (advisors != null && !advisors.isEmpty()) {
                    for (InvestmentAdvisor advisor : advisors) {
                %>
                    <tr>
                        <td><%= advisor.getIAid() %></td> <!-- Row number -->
                        <td><%= advisor.getEmail() %></td>
                        <td><%= advisor.getPassword() %></td>
                        <td>
                            <form action="/admin/portal/deleteAdvisor" method="POST">
                                <input type="hidden" name="advisorUsername" value="<%= advisor.getEmail() %>">
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                <%
                    }}
                %>
                <!-- Row to add a new advisor -->
                <tr>
                    <form action="/admin/portal/addAdvisor" method="POST">
                        <td><input type="number" name="id" placeholder="Enter id" required></td>
                        <td><input type="email" name="username" placeholder="Enter Username" required></td>
                        <td><input type="password" name="password" placeholder="Enter Password" required></td>
                        <td><button type="submit">Add Advisor</button></td>
                    </form>
                </tr>
            </tbody>
        </table>
        <!-- Success/Error message -->
        <div class="message <%= request.getAttribute("statusAdvisor") != null ? request.getAttribute("statusAdvisor").equals("success") ? "success" : "error" : "" %>" 
             style="<%= request.getAttribute("messageAdvisor") != null ? "display:block;" : "display:none;" %>">
            <%= request.getAttribute("messageAdvisor") %>
        </div>
    </div>
</body>
</html>
