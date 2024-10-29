package net.lodgames.message.repository;

import net.lodgames.message.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

//    @Query("SELECT m FROM message m WHERE m.receiverId = :receiverId")
//    public List<Message> findByReceiverId(long receiverId);
    Optional<List<Message>> findByReceiverId(long receiverId, Pageable pageable);

//    @Query("SELECT m FROM message m WHERE m.senderId = :senderId")
//    public List<Message> findBySenderId(long senderId);
    Optional<List<Message>> findBySenderId(long senderId);



    // 쪽지 읽기 및 삭제
    Optional<Message> findByIdAndReceiverIdAndDeletedAtIsNull(long id, long receiverId);

    // 쪽지 삭제
    //Optional<Message> findByIdAndReceiverIdAndDeletedAtIsNull(long id, long receiverId);

    // 쪽지 수정
    Optional<Message> findByIdAndCreatedAtIsNotNullAndReadAtIsNullAndDeletedAtIsNull(long id);
}
