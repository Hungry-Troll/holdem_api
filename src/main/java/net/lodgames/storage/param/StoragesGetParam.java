package net.lodgames.storage.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lodgames.common.param.PagingParam;


@Getter
@Setter
@NoArgsConstructor
public class StoragesGetParam extends PagingParam {
    @JsonIgnore
    private Long receiverId;
}
