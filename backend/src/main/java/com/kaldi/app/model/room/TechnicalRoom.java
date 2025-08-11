package com.kaldi.app.model.room;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TECHNICAL")
public class TechnicalRoom extends Room {
    //ToDo: Add if needed.
}
