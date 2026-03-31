package br.com.hunter.controller;

import java.io.IOException;
import java.util.List;

import br.com.hunter.dao.LocalCacadaDAO;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.LocalCacada;
import br.com.hunter.model.Papel;
import br.com.hunter.util.CampoFormulario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/locais")
public class LocalCacadaServlet extends HttpServlet {

    private final LocalCacadaDAO localDAO = new LocalCacadaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equalsIgnoreCase(action)) {
            if (!isAdmin(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Long id = parseLong(req.getParameter("id"));
            LocalCacada local = id == null ? new LocalCacada() : localDAO.buscarPorId(id);
            if (local == null) {
                local = new LocalCacada();
            }
                req.setAttribute("tituloFormulario", id == null ? "Cadastrar Local de Caçada" : "Editar Local de Caçada");
            req.setAttribute("formAction", req.getContextPath() + "/locais");
            req.setAttribute("entidadeId", local.getId());
            req.setAttribute("rotaVoltar", req.getContextPath() + "/locais");
            req.setAttribute("campos", List.of(
                    new CampoFormulario("nome", "Nome", "text", local.getNome() == null ? "" : local.getNome()),
                    new CampoFormulario("bioma", "Bioma", "text", local.getBioma() == null ? "" : local.getBioma()),
                    new CampoFormulario("nivelPerigo", "Nível de Perigo", "number",
                            local.getNivelPerigo() == null ? "" : local.getNivelPerigo().toString())));
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
                localDAO.remover(id);
            }
            resp.sendRedirect(req.getContextPath() + "/locais");
            return;
        }

        req.setAttribute("locais", localDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/listar_locais.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long id = parseLong(req.getParameter("id"));
        LocalCacada local = id == null ? new LocalCacada() : localDAO.buscarPorId(id);
        if (local == null) {
            local = new LocalCacada();
        }

        local.setNome(req.getParameter("nome"));
        local.setBioma(req.getParameter("bioma"));
        local.setNivelPerigo(Integer.valueOf(req.getParameter("nivelPerigo")));

        localDAO.salvar(local);
        resp.sendRedirect(req.getContextPath() + "/locais");
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
