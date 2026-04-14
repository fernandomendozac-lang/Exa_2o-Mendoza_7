package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import java.sql.*;
import config.Conexion;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/filtro_waf.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\n');
      out.write('\n');

    // Configuración de BD
    String dbUrl = "jdbc:mysql://localhost:3307/db_ciberseguridad";
    String dbUser = "root";
    String dbPass = "";

    // 1. Definir patrones de ataque (XSS y SQL Injection básico)
    String[] blacklistedPatterns = {
        "(?i)<script.?>.?</script.*?>", // XSS
        "(?i)UNION\\s+SELECT",             // SQLi
        "(?i)OR\\s+['\"]?\\d+['\"]?\\s*=\\s*['\"]?\\d+['\"]?", // SQLi (OR 1=1)
        "(?i)DROP\\s+TABLE",               // SQLi destructivo
        "(?i)javascript:"                  // XSS en enlaces
    };

    // 2. Analizar todos los parámetros de la solicitud
    Enumeration<String> paramNames = request.getParameterNames();
    boolean attackDetected = false;
    String offendingPayload = "";

    while (paramNames.hasMoreElements()) {
        String paramName = paramNames.nextElement();
        String paramValue = request.getParameter(paramName);

        for (String pattern : blacklistedPatterns) {
            if (Pattern.compile(pattern).matcher(paramValue).find()) {
                attackDetected = true;
                offendingPayload = paramName + "=" + paramValue;
                break;
            }
        }
        if (attackDetected) break;
    }

    // 3. Si hay ataque, registrar en BD y bloquear
    if (attackDetected) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            String sql = "INSERT INTO t_sucesos (ip_address, payload, attack_type) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, request.getRemoteAddr());
            pstmt.setString(2, offendingPayload);
            pstmt.setString(3, "Posible Intento de Intrusión");
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bloquear acceso
        out.println("<h2 style='color:red;'>Acceso Denegado por Políticas de Seguridad.</h2>");
        out.println("<h2 style='color:blue;'>ASI Mena Plataforma Segura.</h2>");
        return; // Detiene la ejecución del resto de la página
    }

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <title>Administración de Seguridad Personal</title>\n");
      out.write("        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("        <style>\n");
      out.write("            body { background-color: #f4f7f6; }\n");
      out.write("            .header-sec { background: #2c3e50; color: white; padding: 20px; margin-bottom: 30px; border-radius: 0 0 20px 20px; }\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <a href=\"admin_attack.jsp\"><h1><i>Consultar Información ASI Mena</i></h1></a>\n");
      out.write("        <div class=\"header-sec text-center shadow\">\n");
      out.write("            <h1>Gestión de Seguridad Informática Personal</h1>\n");
      out.write("            <p>Consejos: Usa MFA, rota claves cada 90 días y conoce la Ley de Protección de Datos.</p>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <div class=\"card shadow-sm p-4 mb-5\">\n");
      out.write("                <form action=\"Controlador\" method=\"POST\">\n");
      out.write("                    <div class=\"row g-3\">\n");
      out.write("                        <div class=\"col-md-4\"><input type=\"text\" name=\"txtServicio\" class=\"form-control\" placeholder=\"Servicio (Ej: Google)\" required></div>\n");
      out.write("                        <div class=\"col-md-4\"><input type=\"text\" name=\"txtUsuario\" class=\"form-control\" placeholder=\"Usuario/Correo\" required></div>\n");
      out.write("                        <div class=\"col-md-4\">\n");
      out.write("                            <select name=\"txtRiesgo\" class=\"form-select\">\n");
      out.write("                                <option value=\"Bajo\">Riesgo Bajo</option>\n");
      out.write("                                <option value=\"Medio\">Riesgo Medio</option>\n");
      out.write("                                <option value=\"Alto\">Riesgo Alto</option>\n");
      out.write("                            </select>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"col-md-4\"><input type=\"date\" name=\"txtFecha\" class=\"form-control\" required></div>\n");
      out.write("                        <div class=\"col-md-4\"><input type=\"text\" name=\"txtMFA\" class=\"form-control\" placeholder=\"Método MFA\"></div>\n");
      out.write("                        <div class=\"col-md-4\"><input type=\"text\" name=\"txtNotas\" class=\"form-control\" placeholder=\"Notas de recuperación\"></div>\n");
      out.write("                        <div class=\"col-12\">\n");
      out.write("                            <button type=\"submit\" name=\"accion\" value=\"agregar\" class=\"btn btn-primary w-100\">Guardar en Base de Datos</button>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <div class=\"table-responsive shadow rounded\">\n");
      out.write("                <table class=\"table table-hover bg-white mb-0\">\n");
      out.write("                    <thead class=\"table-dark\">\n");
      out.write("                        <tr>\n");
      out.write("                            <th>Servicio</th>\n");
      out.write("                            <th>Usuario</th>\n");
      out.write("                            <th>Riesgo</th>\n");
      out.write("                            <th>Fecha</th>\n");
      out.write("                            <th>MFA</th>\n");
      out.write("                            <th>Notas</th>\n");
      out.write("                            <th>Acciones</th>\n");
      out.write("                        </tr>\n");
      out.write("                    </thead>\n");
      out.write("                    <tbody>\n");
      out.write("                        ");

                            try {
                                Conexion cn = new Conexion();
                                Connection con = cn.getConnection();
                                PreparedStatement ps = con.prepareStatement("SELECT * FROM activos ORDER BY id DESC");
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                        
      out.write("\n");
      out.write("                        <tr>\n");
      out.write("                            <td>");
      out.print( rs.getString("servicio") );
      out.write("</td>\n");
      out.write("                            <td>");
      out.print( rs.getString("usuario") );
      out.write("</td>\n");
      out.write("                            <td><span class=\"badge ");
      out.print( rs.getString("riesgo").equals("Alto") ? "bg-danger" : "bg-success" );
      out.write("\">\n");
      out.write("                                ");
      out.print( rs.getString("riesgo") );
      out.write("</span></td>\n");
      out.write("                            <td>");
      out.print( rs.getString("fecha") );
      out.write("</td>\n");
      out.write("                            <td>");
      out.print( rs.getString("mfa") );
      out.write("</td>\n");
      out.write("                            <td>");
      out.print( rs.getString("notas") );
      out.write("</td>\n");
      out.write("                            <td>\n");
      out.write("                                <a href=\"#\" class=\"btn btn-outline-warning btn-sm\">Edit</a>\n");
      out.write("                                <a href=\"#\" class=\"btn btn-outline-danger btn-sm\">Del</a>\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                        ");
 
                                }
                                con.close();
                            } catch (Exception e) {
                                out.print("Error al listar: " + e);
                            }
                        
      out.write("\n");
      out.write("                    </tbody>\n");
      out.write("                </table>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
