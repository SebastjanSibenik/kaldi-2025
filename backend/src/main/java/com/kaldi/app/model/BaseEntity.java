package com.kaldi.app.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "created", nullable = false)
    private Instant  created;

    @Column(name = "updated")
    private Instant  updated;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.created = now;
        this.updated = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public BaseEntity setUuid(UUID id) {
        this.uuid = id;
        return this;
    }

    public Instant  getCreated() {
        return created;
    }

    public BaseEntity setCreated(Instant  created) {
        this.created = created;
        return this;
    }

    public Instant  getUpdated() {
        return updated;
    }

    public BaseEntity setUpdated(Instant  updated) {
        this.updated = updated;
        return this;
    }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
