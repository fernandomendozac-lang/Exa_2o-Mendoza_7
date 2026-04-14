<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Panel de Control WAF ASI MENDOZA</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        tr:nth-child(even) { background-color: #f2 f2 f2; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <h2>Registros de Intrusión Detectados ASI Mena</h2>
    <hr>
    <center>
        <h2>Fecha y hora del sistema Reporte General</h2>
        <h2><i><%java.util.Date fecha = new java.util.Date();%> <%=fecha%></i></h2>
    </center>
        
    <table>
        <tr>
            <th>ID</th>
            <th>IP</th>
            <th>Carga Maliciosa (Payload)</th>
            <th>Tipo de Ataque</th>
            <th>Fecha</th>
        </tr>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/db_ciberseguridad", "root", "");
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM activos ORDER BY event_type DESC");

                while(rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("ip_address") %></td>
            <td><%= rs.getString("payload") %></td>
            <td><%= rs.getString("attack_type") %></td>
            <td><%= rs.getTimestamp("event_type") %></td>
        </tr>
        <% 
                }
                conn.close();
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
        %>
    </table>
</body>
</html>