package net.lodgames.storage.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;

@Getter
@Setter
@NoArgsConstructor
public class StorageReceiveHistoryParam extends PagingParam {
    @JsonIgnore
    private Long receiverId;
}
