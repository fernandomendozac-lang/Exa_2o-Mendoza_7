<%@include file="filtro_waf.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="config.Conexion"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Administración de Seguridad Personal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body { background-color: #f4f7f6; }
            .header-sec { background: #2c3e50; color: white; padding: 20px; margin-bottom: 30px; border-radius: 0 0 20px 20px; }
        </style>
    </head>
    <body>
        <a href="admin_attack.jsp"><h1><i>Consultar Información APTI Mendoza</i></h1></a>
        <div class="header-sec text-center shadow">
            <h1>Gestión de Seguridad Informática Personal</h1>
            <p>Consejos: Usa MFA, rota claves cada 90 días y conoce la Ley de Protección de Datos.</p>
        </div>

        <div class="container">
            <div class="card shadow-sm p-4 mb-5">
                <form action="Controlador" method="POST">
                    <div class="row g-3">
                        <div class="col-md-4"><input type="text" name="txtServicio" class="form-control" placeholder="Servicio (Ej: Google)" required></div>
                        <div class="col-md-4"><input type="text" name="txtUsuario" class="form-control" placeholder="Usuario/Correo" required></div>
                        <div class="col-md-4">
                            <select name="txtRiesgo" class="form-select">
                                <option value="Bajo">Riesgo Bajo</option>
                                <option value="Medio">Riesgo Medio</option>
                                <option value="Alto">Riesgo Alto</option>
                            </select>
                        </div>
                        <div class="col-md-4"><input type="date" name="txtFecha" class="form-control" required></div>
                        <div class="col-md-4"><input type="text" name="txtMFA" class="form-control" placeholder="Método MFA"></div>
                        <div class="col-md-4"><input type="text" name="txtNotas" class="form-control" placeholder="Notas de recuperación"></div>
                        <div class="col-12">
                            <button type="submit" name="accion" value="agregar" class="btn btn-primary w-100">Guardar en Base de Datos</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="table-responsive shadow rounded">
                <table class="table table-hover bg-white mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Servicio</th>
                            <th>Usuario</th>
                            <th>Riesgo</th>
                            <th>Fecha</th>
                            <th>MFA</th>
                            <th>Notas</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            try {
                                Conexion cn = new Conexion();
                                Connection con = cn.getConnection();
                                PreparedStatement ps = con.prepareStatement("SELECT * FROM activos ORDER BY id DESC");
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                        %>
                        <tr>
                            <td><%= rs.getString("servicio") %></td>
                            <td><%= rs.getString("usuario") %></td>
                            <td><span class="badge <%= rs.getString("riesgo").equals("Alto") ? "bg-danger" : "bg-success" %>">
                                <%= rs.getString("riesgo") %></span></td>
                            <td><%= rs.getString("fecha") %></td>
                            <td><%= rs.getString("mfa") %></td>
                            <td><%= rs.getString("notas") %></td>
                            <td>
                                <a href="#" class="btn btn-outline-warning btn-sm">Edit</a>
                                <a href="#" class="btn btn-outline-danger btn-sm">Del</a>
                            </td>
                        </tr>
                        <% 
                                }
                                con.close();
                            } catch (Exception e) {
                                out.print("Error al listar: " + e);
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>