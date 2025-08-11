package com.kaldi.app.repository;

import com.kaldi.app.common.enums.ConversationStatus;
import com.kaldi.app.model.conversation.Conversation;
import com.kaldi.app.model.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConversationRepository {

    private static final Logger LOGGER = Logger.getLogger(ConversationRepository.class);

    @PersistenceContext
    private EntityManager em;

    public Conversation getByUuid(UUID conversationUuid) {
        final String query =
                """
                SELECT c
                FROM Conversation c
                LEFT JOIN FETCH c.room
                LEFT JOIN FETCH c.operator
                WHERE c.uuid = :conversationUuid
                """;
        try {
            return em.createQuery(query, Conversation.class)
                    .setParameter("conversationUuid", conversationUuid)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.errorf("Failed to get Conversation by UUID [%s], with error: %s", conversationUuid, e.getMessage());
            return null;
        }
    }

    public List<Conversation> list() {
        final String query =
                """
                SELECT DISTINCT c
                FROM Conversation c
                LEFT JOIN FETCH c.room
                LEFT JOIN FETCH c.operator
                """;
        try {
            return em.createQuery(query, Conversation.class)
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to list conversations with error: %s", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void persist(Conversation conversation) {
        try {
            em.persist(conversation);
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to persist conversation with error: %s", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public boolean claimConversation(UUID conversationUuid, User operator) {
        final String query =
                """
                UPDATE Conversation c
                SET c.status = :claimed, c.operator = :operator, c.updated = :now
                WHERE c.uuid = :uuid
                AND c.status = :pending
                """;
        try {
            int updated = em.createQuery(query)
                    .setParameter("claimed", ConversationStatus.CLAIMED)
                    .setParameter("operator", operator)
                    .setParameter("now", Instant.now())
                    .setParameter("uuid", conversationUuid)
                    .setParameter("pending", ConversationStatus.PENDING)
                    .executeUpdate();
            return updated == 1;
        } catch (Exception e) {
            LOGGER.errorf("Failed to claim conversation [%s], with error: %s", conversationUuid, e.getMessage());
            return false;
        }
    }
}
