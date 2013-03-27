/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author lv
 */
public class EntityManagerHelper {

    private static final EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal;
    private static final Logger logger;

    static {
        emf = Persistence.createEntityManagerFactory("modelAPIPU");   
        threadLocal = new ThreadLocal<EntityManager>();
        logger = Logger.getLogger("modelAPIPU");
        logger.setLevel(Level.ALL);
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commit() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public static Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public static Query createNamedQuery(String query) {
        return getEntityManager().createNamedQuery(query);
    }

    public static void doTransaction(Runnable runnable) {
        try {
            EntityManagerHelper.beginTransaction();
            runnable.run();
            EntityManagerHelper.commit();
            logger.log(Level.INFO, "Transaction execution success");
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Persistence exception", e);
            try {
                EntityManagerHelper.rollback();
                logger.log(Level.INFO, "Transaction rollback success");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Transaction rollback exception", ex);
            }
        } catch (IllegalStateException e) {
            logger.log(Level.SEVERE, "Illegal State Exception", e);
            try {
                EntityManagerHelper.rollback();
                logger.log(Level.INFO, "Transaction rollback success");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Transaction rollback exception", ex);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Illegal Argument Exception", e);
            try {
                EntityManagerHelper.rollback();
                logger.log(Level.INFO, "Transaction rollback success");
            } catch (Exception ex) {

                logger.log(Level.SEVERE, "Transaction rollback exception", ex);
            }
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}
