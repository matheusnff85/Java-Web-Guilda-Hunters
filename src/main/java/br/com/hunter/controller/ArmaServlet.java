package br.com.hunter.controller;

import java.io.IOException;
import java.util.List;

import br.com.hunter.dao.ArmaDAO;
import br.com.hunter.model.Arma;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.Papel;
import br.com.hunter.util.CampoFormulario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/armas")
public class ArmaServlet extends HttpServlet {

    private final ArmaDAO armaDAO = new ArmaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equalsIgnoreCase(action)) {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Long id = parseLong(req.getParameter("id"));
            Arma arma = id == null ? new Arma() : armaDAO.buscarPorId(id);
            if (arma == null) {
                arma = new Arma();
            }
            req.setAttribute("tituloFormulario", id == null ? "Cadastrar Arma" : "Editar Arma");
            req.setAttribute("formAction", req.getContextPath() + "/armas");
            req.setAttribute("entidadeId", arma.getId());
            req.setAttribute("rotaVoltar", req.getContextPath() + "/armas");
            req.setAttribute("campos", List.of(
                    new CampoFormulario("nome", "Nome", "text", arma.getNome() == null ? "" : arma.getNome()),
                    new CampoFormulario("tipo", "Tipo", "text", arma.getTipo() == null ? "" : arma.getTipo()),
                    new CampoFormulario("danoBase", "Dano Base", "number",
                            arma.getDanoBase() == null ? "" : arma.getDanoBase().toString())));
            req.getRequestDispatcher("/WEB-INF/views/form_generico.jsp").forward(req, resp);
            return;
        }

        if ("remover".equalsIgnoreCase(action)) {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Long id = parseLong(req.getParameter("id"));
            if (id != null) {
                armaDAO.remover(id);
            }
            resp.sendRedirect(req.getContextPath() + "/armas");
            return;
        }

        req.setAttribute("armas", armaDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/listar_armas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long id = parseLong(req.getParameter("id"));
        Arma arma = id == null ? new Arma() : armaDAO.buscarPorId(id);
        if (arma == null) {
            arma = new Arma();
        }

        arma.setNome(req.getParameter("nome"));
        arma.setTipo(req.getParameter("tipo"));
        arma.setDanoBase(Integer.valueOf(req.getParameter("danoBase")));

        armaDAO.salvar(arma);
        resp.sendRedirect(req.getContextPath() + "/armas");
    }

    private boolean isAdmin(HttpServletRequest req) {
        Cacador usuario = (Cacador) req.getSession().getAttribute("usuarioLogado");
        return usuario != null && usuario.getPapel() == Papel.ADMIN;
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Long.parseLong(value);
    }
}
