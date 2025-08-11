package com.kaldi.app.repository;

import com.kaldi.app.common.enums.RoomType;
import com.kaldi.app.model.room.Room;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class RoomRepository {

    private static final Logger LOGGER = Logger.getLogger(RoomRepository.class);

    @PersistenceContext
    private EntityManager em;

    public List<Room> list() {
        final String query =
                """
                SELECT r
                FROM Room r
                """;
        try {
            return em.createQuery(query, Room.class).getResultList();
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to execute query [%s]: %s", query, e.getMessage());
            throw e;
        }
    }

    public Room getByType(RoomType roomType) {
        final String query =
                """
                SELECT r
                FROM Room r
                WHERE r.roomType = :roomType
                """;
        try {
            return em.createQuery(query, Room.class)
                    .setParameter("roomType", roomType)
                    .getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.errorf("Failed to execute query [%s] with roomType %s: %s", query, roomType, e.getMessage());
            throw e;
        }
    }
}
