<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ofss.model.InvestmentAdvisor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background-color: white;
            padding: 20px 40px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            width: 250px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
        }
        input[type="email"], input[type="password"] {
            width: 90%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <h2>Admin Login</h2>
        <form action="/admin/login" method="post">
            <input type="email" name="username" placeholder="Enter Email ID" required><br>
            <input type="password" name="password" placeholder="Enter Password" required><br>
            <input type="submit" value="Login">
        </form>
        <c:if test="${not empty error}">
            <div style="color: red;">${error}</div>
        </c:if>
    </div>

</body>
</html>
