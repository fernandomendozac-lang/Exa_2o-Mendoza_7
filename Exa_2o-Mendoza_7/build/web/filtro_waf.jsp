<%@ page import="java.sql.*, java.util.*, java.util.regex.*" %>

<%
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
%>