package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessagesAddParam {
    @JsonIgnore
    private Long senderId;
    private List<Long> receiverIds;
    private String content;
}
