package br.com.hunter.filter;

import java.io.IOException;

import br.com.hunter.model.Cacador;
import br.com.hunter.model.Papel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String path = req.getRequestURI().substring(contextPath.length());

        if (isRotaPublica(path) || path.startsWith("/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Cacador usuario = session == null ? null : (Cacador) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.sendRedirect(contextPath + "/login");
            return;
        }

        if (usuario.getPapel() == Papel.CACADOR && !acessoPermitidoParaCacador(req, path)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Acesso negado: apenas ADMIN pode cadastrar, atualizar ou remover.");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isRotaPublica(String path) {
        return "/".equals(path)
                || "/index.jsp".equals(path)
                || "/login".equals(path)
                || "/login.jsp".equals(path);
    }

    private boolean acessoPermitidoParaCacador(HttpServletRequest req, String path) {
        String method = req.getMethod();
        String action = req.getParameter("action");
        String normalizedAction = action == null ? "" : action.trim().toLowerCase();

        if ("/logout".equals(path) || "/dashboard".equals(path)) {
            return true;
        }

        if ("/cacar".equals(path)) {
            return "GET".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method);
        }

        if ("/monstros".equals(path)) {
            if (!"GET".equalsIgnoreCase(method)) {
                return false;
            }
            return normalizedAction.isBlank() || "listar".equals(normalizedAction);
        }

        return false;
    }
}
