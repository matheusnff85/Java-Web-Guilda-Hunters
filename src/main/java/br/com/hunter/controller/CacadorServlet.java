package br.com.hunter.controller;

import java.io.IOException;
import java.util.List;

import br.com.hunter.dao.CacadorDAO;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.Papel;
import br.com.hunter.util.CampoFormulario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cacadores")
public class CacadorServlet extends HttpServlet {

    private final CacadorDAO cacadorDAO = new CacadorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = req.getParameter("action");
        if ("form".equalsIgnoreCase(action)) {
            Long id = parseLong(req.getParameter("id"));
            Cacador cacador = id == null ? new Cacador() : cacadorDAO.buscarPorId(id);
            if (cacador == null) {
                cacador = new Cacador();
            }
            req.setAttribute("tituloFormulario", id == null ? "Cadastrar Cacador" : "Editar Cacador");
            req.setAttribute("formAction", req.getContextPath() + "/cacadores");
            req.setAttribute("entidadeId", cacador.getId());
            req.setAttribute("rotaVoltar", req.getContextPath() + "/cacadores");
            req.setAttribute("campos", List.of(
                    new CampoFormulario("nome", "Nome", "text", cacador.getNome() == null ? "" : cacador.getNome()),
                    new CampoFormulario("login", "Login", "text",
                            cacador.getLogin() == null ? "" : cacador.getLogin()),
                    new CampoFormulario("senha", "Senha", "password", ""),
                    new CampoFormulario("papel", "Papel (ADMIN ou CACADOR)", "text",
                            cacador.getPapel() == null ? "CACADOR" : cacador.getPapel().name())));
            req.getRequestDispatcher("/WEB-INF/views/form_generico.jsp").forward(req, resp);
            return;
        }

        if ("remover".equalsIgnoreCase(action)) {
            Long id = parseLong(req.getParameter("id"));
            if (id != null) {
                cacadorDAO.remover(id);
            }
            resp.sendRedirect(req.getContextPath() + "/cacadores");
            return;
        }

        req.setAttribute("cacadores", cacadorDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/listar_cacadores.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long id = parseLong(req.getParameter("id"));
        Cacador cacador = id == null ? new Cacador() : cacadorDAO.buscarPorId(id);
        if (cacador == null) {
            cacador = new Cacador();
        }

        cacador.setNome(req.getParameter("nome"));
        cacador.setLogin(req.getParameter("login"));
        String senha = req.getParameter("senha");
        if (senha != null && !senha.isBlank()) {
            cacador.setSenha(senha);
        } else if (cacador.getSenha() == null || cacador.getSenha().isBlank()) {
            cacador.setSenha("123456");
        }

        String papelParam = req.getParameter("papel");
        Papel papel = Papel.CACADOR;
        if (papelParam != null && !papelParam.isBlank()) {
            papel = Papel.valueOf(papelParam.trim().toUpperCase());
        }
        cacador.setPapel(papel);

        cacadorDAO.salvar(cacador);
        resp.sendRedirect(req.getContextPath() + "/cacadores");
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
