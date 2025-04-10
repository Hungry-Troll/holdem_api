package net.lodgames.chat.model;

import jakarta.persistence.*;
import lombok.*;
import net.lodgames.chat.constant.DestType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
@Entity(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChatMsg implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dest;
    private Long senderId;
    private DestType destType;
    private String msgType;
    private String msgBody;
    @CreatedDate
    private LocalDateTime sendTime;
}

