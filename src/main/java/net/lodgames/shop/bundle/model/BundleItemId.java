package net.lodgames.shop.bundle.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BundleItemId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long bundleId;
    private Long itemId;
}
