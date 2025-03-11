package net.lodgames.message.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MessagesDeleteParam {
    private List<Long> messageIds;
    private long receiverId;
}
