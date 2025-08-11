package com.kaldi.app.repository;

import com.kaldi.app.model.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void deleteAll() {
        final String query =
                """
                DELETE
                FROM User
                """;
        try {
            em.createQuery(query)
                    .executeUpdate();
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to execute query [%s] with: %s", query, e.getMessage());
            throw e;
        }
    }

    public User getByUsername(String username) {
        final String query =
                """
                SELECT u
                FROM User u
                WHERE u.username = :username
                """;
        try {
            return em.createQuery(query, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to execute query [%s] with username %s: %s", query, username, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void persist(User user) {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to execute user persist query with: %s", e.getMessage());
            throw e;
        }
    }
}
