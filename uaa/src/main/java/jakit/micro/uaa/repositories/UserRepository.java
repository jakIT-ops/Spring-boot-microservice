package jakit.micro.uaa.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakit.micro.uaa.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findOneByUsername(String username) {

        List<User> resultList = em
                .createQuery("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)", User.class)
                .setParameter("username", username)
                .getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
    @Transactional
    public void save(User user){
        em.merge(user);
        em.flush();
        return;
    }
}
