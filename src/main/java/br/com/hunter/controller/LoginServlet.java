package br.com.hunter.controller;

import java.io.IOException;
import java.util.Optional;

import br.com.hunter.dao.CacadorDAO;
import br.com.hunter.model.Cacador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final CacadorDAO cacadorDAO = new CacadorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");

        Optional<Cacador> autenticado = cacadorDAO.buscarPorLoginESenha(login, senha);
        if (autenticado.isPresent()) {
            req.getSession(true).setAttribute("usuarioLogado", autenticado.get());
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        req.setAttribute("erro", "Login ou senha invalidos.");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
