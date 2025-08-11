package com.kaldi.app.repository;

import com.kaldi.app.model.message.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MessageRepository {

    private static final Logger LOGGER = Logger.getLogger(MessageRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Message message) {
        try {
            em.persist(message);
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to persist message: %s", e.getMessage());
            throw e;
        }
    }

    public Message getFirstMessage(UUID conversationUuid) {
        final String query =
                """
                SELECT m
                FROM Message m
                JOIN m.conversation c
                WHERE c.uuid = :conversationUuid
                AND c.operator IS NULL
                AND c.status = 'PENDING'
                ORDER BY m.created ASC
                """;
        try {
            return em.createQuery(query, Message.class)
                    .setParameter("conversationUuid", conversationUuid)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warnf("No first message found for conversation UUID: %s", conversationUuid);
            return null;
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to get first message: %s", e.getMessage());
            throw e;
        }
    }

    public List<Message> getConversationMessages(UUID conversationUuid) {
        final String query =
            """
            SELECT m
            FROM Message m
            WHERE m.conversation.uuid = :conversationUuid
            ORDER BY m.created ASC
            """;
        try {
            return em.createQuery(query, Message.class)
                    .setParameter("conversationUuid", conversationUuid)
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to get conversation messages: %s", e.getMessage());
            throw e;
        }
    }
}
