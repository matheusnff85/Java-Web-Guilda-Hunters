package br.com.hunter.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import br.com.hunter.dao.HuntLogDAO;
import br.com.hunter.dao.LocalCacadaDAO;
import br.com.hunter.dao.MonstroDAO;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.HuntLog;
import br.com.hunter.model.LocalCacada;
import br.com.hunter.model.Monstro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cacar")
public class CacarServlet extends HttpServlet {

    private final MonstroDAO monstroDAO = new MonstroDAO();
    private final LocalCacadaDAO localDAO = new LocalCacadaDAO();
    private final HuntLogDAO huntLogDAO = new HuntLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object mensagem = session.getAttribute("flash");
            if (mensagem != null) {
                req.setAttribute("flash", mensagem);
                session.removeAttribute("flash");
            }
        }

        req.setAttribute("monstros", monstroDAO.listarTodos());
        req.setAttribute("locais", localDAO.listarTodos());
        req.setAttribute("huntLogs", huntLogDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/cacar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cacador cacadorLogado = (Cacador) req.getSession().getAttribute("usuarioLogado");
        Long monstroId = parseLong(req.getParameter("monstroId"));
        Long localId = parseLong(req.getParameter("localId"));

        if (cacadorLogado == null || monstroId == null || localId == null) {
            req.getSession().setAttribute("flash", "Dados inválidos para registrar a caçada.");
            resp.sendRedirect(req.getContextPath() + "/cacar");
            return;
        }

        Monstro monstro = monstroDAO.buscarPorId(monstroId);
        LocalCacada local = localDAO.buscarPorId(localId);

        if (monstro == null || local == null) {
            req.getSession().setAttribute("flash", "Monstro ou local não encontrado.");
            resp.sendRedirect(req.getContextPath() + "/cacar");
            return;
        }

        HuntLog log = new HuntLog();
        log.setCacador(cacadorLogado);
        log.setMonstro(monstro);
        log.setLocalCacada(local);
        log.setDataCacada(LocalDateTime.now());

        huntLogDAO.salvar(log);
        req.getSession().setAttribute("flash", "Caçada registrada com sucesso.");
        resp.sendRedirect(req.getContextPath() + "/cacar");
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Long.parseLong(value);
    }
}
