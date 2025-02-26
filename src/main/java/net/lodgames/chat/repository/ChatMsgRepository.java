package net.lodgames.chat.repository;

import net.lodgames.chat.model.ChatMsg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMsgRepository extends CrudRepository<ChatMsg, Long> {
}
