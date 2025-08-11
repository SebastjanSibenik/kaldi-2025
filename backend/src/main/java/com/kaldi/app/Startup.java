package com.kaldi.app;

import com.kaldi.app.common.enums.Role;
import com.kaldi.app.model.user.User;
import com.kaldi.app.repository.UserRepository;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {

    @Inject
    UserRepository userRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        userRepository.deleteAll();

        User u1 = new User();
        u1.setUsername("admin1")
                .setPassword(BcryptUtil.bcryptHash("admin1"))
                .setRole(Role.OPERATOR.name());
        userRepository.persist(u1);

        User u2 = new User();
        u2.setUsername("admin2")
                .setPassword(BcryptUtil.bcryptHash("admin2"))
                .setRole(Role.OPERATOR.name());
        userRepository.persist(u2);

        User u3 = new User();
        u2.setUsername("admin3")
                .setPassword(BcryptUtil.bcryptHash("admin3"))
                .setRole(Role.OPERATOR.name());
        userRepository.persist(u3);

        User u4 = new User();
        u4.setUsername("admin4")
                .setPassword(BcryptUtil.bcryptHash("admin4"))
                .setRole(Role.OPERATOR.name());
        userRepository.persist(u4);
    }
}
