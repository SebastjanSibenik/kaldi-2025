package com.kaldi.app.model.room;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONVERSATIONAL")
public class ConversationalRoom extends Room {
    //ToDo: Add if needed.
}