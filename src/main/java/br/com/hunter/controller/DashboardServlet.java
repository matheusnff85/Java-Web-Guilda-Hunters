package br.com.hunter.controller;

import java.io.IOException;

import br.com.hunter.dao.ArmaDAO;
import br.com.hunter.dao.CacadorDAO;
import br.com.hunter.dao.LocalCacadaDAO;
import br.com.hunter.dao.MonstroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final MonstroDAO monstroDAO = new MonstroDAO();
    private final ArmaDAO armaDAO = new ArmaDAO();
    private final LocalCacadaDAO localDAO = new LocalCacadaDAO();
    private final CacadorDAO cacadorDAO = new CacadorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("totalMonstros", monstroDAO.listarTodos().size());
        req.setAttribute("totalArmas", armaDAO.listarTodos().size());
        req.setAttribute("totalLocais", localDAO.listarTodos().size());
        req.setAttribute("totalCacadores", cacadorDAO.listarTodos().size());
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}
