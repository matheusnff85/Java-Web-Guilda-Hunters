package br.com.hunter.util;

import br.com.hunter.dao.CacadorDAO;
import br.com.hunter.model.Cacador;
import br.com.hunter.model.Papel;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CacadorDAO dao = new CacadorDAO();
        if (!dao.existeAdmin()) {
            Cacador admin = new Cacador();
            admin.setNome("Administrador da Guilda");
            admin.setLogin("admin");
            admin.setSenha("admin123");
            admin.setPapel(Papel.ADMIN);
            dao.salvar(admin);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.shutdown();
    }
}
