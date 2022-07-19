package org.nice.ase.tdd.sentiment;

import java.util.Optional;

public interface InteractionGateway {
    String create(Interaction interaction);

    Optional<Interaction> getById(String id);
}
