package br.com.hunter.dao;

import java.util.Optional;

import br.com.hunter.model.Cacador;
import br.com.hunter.model.Papel;
import br.com.hunter.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class CacadorDAO extends GenericDAO<Cacador> {

    public CacadorDAO() {
        super(Cacador.class);
    }

    public Optional<Cacador> buscarPorLoginESenha(String login, String senha) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cacador c WHERE c.login = :login AND c.senha = :senha", Cacador.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getResultStream()
                    .findFirst();
        } finally {
            em.close();
        }
    }

    public boolean existeAdmin() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long total = em.createQuery("SELECT COUNT(c) FROM Cacador c WHERE c.papel = :papel", Long.class)
                    .setParameter("papel", Papel.ADMIN)
                    .getSingleResult();
            return total != null && total > 0;
        } finally {
            em.close();
        }
    }
}
