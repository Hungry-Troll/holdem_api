package net.lodgames.message.repository;

import net.lodgames.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // 쪽지 읽기 및 삭제
    Optional<Message> findByIdAndReceiverIdAndDeletedAtIsNull(long id, long receiverId);
    // 쪽지 리스트 읽기
    List<Message> findByIdInAndReceiverIdAndDeletedAtIsNull(List<Long> ids, long receiverId);
    // 쪽지 수정
    Optional<Message> findByIdAndReadAtIsNullAndDeletedAtIsNull(long id);
}
