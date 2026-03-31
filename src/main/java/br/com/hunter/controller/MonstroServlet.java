package br.com.hunter.controller;

import java.io.IOException;
import java.util.List;

import br.com.hunter.dao.MonstroDAO;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.Monstro;
import br.com.hunter.model.Papel;
import br.com.hunter.util.CampoFormulario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/monstros")
public class MonstroServlet extends HttpServlet {

    private final MonstroDAO monstroDAO = new MonstroDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equalsIgnoreCase(action)) {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Long id = parseLong(req.getParameter("id"));
            Monstro monstro = id == null ? new Monstro() : monstroDAO.buscarPorId(id);
            if (monstro == null) {
                monstro = new Monstro();
            }
            req.setAttribute("tituloFormulario", id == null ? "Cadastrar Monstro" : "Editar Monstro");
            req.setAttribute("formAction", req.getContextPath() + "/monstros");
            req.setAttribute("entidadeId", monstro.getId());
            req.setAttribute("rotaVoltar", req.getContextPath() + "/monstros");
            req.setAttribute("campos", List.of(
                    new CampoFormulario("nome", "Nome", "text",
                        monstro.getNome() == null ? "" : monstro.getNome()),
                    new CampoFormulario("nivelAmeaca", "Nivel de Ameaca", "number",
                            monstro.getNivelAmeaca() == null ? "" : monstro.getNivelAmeaca().toString()),
                    new CampoFormulario("fraqueza", "Fraqueza", "text",
                            monstro.getFraqueza() == null ? "" : monstro.getFraqueza())));
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
                monstroDAO.remover(id);
            }
            resp.sendRedirect(req.getContextPath() + "/monstros");
            return;
        }

        req.setAttribute("monstros", monstroDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/listar_monstros.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long id = parseLong(req.getParameter("id"));
        String nome = req.getParameter("nome");
        Integer nivel = Integer.valueOf(req.getParameter("nivelAmeaca"));
        String fraqueza = req.getParameter("fraqueza");

        Monstro monstro = id == null ? new Monstro() : monstroDAO.buscarPorId(id);
        if (monstro == null) {
            monstro = new Monstro();
        }
        monstro.setNome(nome);
        monstro.setNivelAmeaca(nivel);
        monstro.setFraqueza(fraqueza);

        monstroDAO.salvar(monstro);
        resp.sendRedirect(req.getContextPath() + "/monstros");
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
