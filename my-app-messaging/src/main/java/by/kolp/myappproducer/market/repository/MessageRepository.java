package by.kolp.myappproducer.market.repository;

import by.kolp.myappproducer.market.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
            value = "select u.email from User u",
            countQuery = "select count(u) from User u"
    )
    Page<String> findAllEmails(Pageable pageable);

}
