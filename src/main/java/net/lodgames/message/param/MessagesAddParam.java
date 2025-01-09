package net.lodgames.message.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessagesAddParam {
    @JsonIgnore
    private long senderId;
    private List<Long> receiverIds;
    private List<String> contents;
}
