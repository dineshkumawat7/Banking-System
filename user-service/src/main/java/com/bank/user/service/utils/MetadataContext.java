package com.bank.user.service.utils;

import com.bank.user.service.model.common.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetadataContext {
    private static final ThreadLocal<Metadata> metadataContext = new ThreadLocal<>();

    public static Metadata getMetadata() {
        return metadataContext.get();
    }

    public static void setMetadata(Metadata metadata) {
        metadataContext.set(metadata);
    }

    public static void clearMetadata() {
        metadataContext.remove();
    }
}
