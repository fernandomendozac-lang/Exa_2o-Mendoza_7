package com.proyecto.controlador;

import config.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion != null && accion.equals("agregar")) {
            try {
                String ser = request.getParameter("txtServicio");
                String usu = request.getParameter("txtUsuario");
                String rie = request.getParameter("txtRiesgo");
                String fec = request.getParameter("txtFecha");
                String mfa = request.getParameter("txtMFA");
                String not = request.getParameter("txtNotas");

                con = cn.getConnection();
                ps = con.prepareStatement("INSERT INTO activos(servicio, usuario, riesgo, fecha, mfa, notas) VALUES(?,?,?,?,?,?)");
                ps.setString(1, ser);
                ps.setString(2, usu);
                ps.setString(3, rie);
                ps.setString(4, fec);
                ps.setString(5, mfa);
                ps.setString(6, not);
                ps.executeUpdate();
                
                // Después de guardar, regresamos al index
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.err.println("Error al insertar: " + e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}