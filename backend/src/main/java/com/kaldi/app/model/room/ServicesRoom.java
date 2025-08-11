package com.kaldi.app.model.room;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SERVICES")
public class ServicesRoom extends Room {
    //ToDo: Add if needed.
}