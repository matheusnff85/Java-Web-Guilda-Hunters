package br.com.hunter.dao;

import br.com.hunter.model.Monstro;
import br.com.hunter.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MonstroDAO extends GenericDAO<Monstro> {

    public MonstroDAO() {
        super(Monstro.class);
    }

    @Override
    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("DELETE FROM HuntLog h WHERE h.monstro.id = :monstroId")
                    .setParameter("monstroId", id)
                    .executeUpdate();

            Monstro monstro = em.find(Monstro.class, id);
            if (monstro != null) {
                em.remove(monstro);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
