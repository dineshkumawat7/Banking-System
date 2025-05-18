package com.bank.common.lib.utils;

import com.bank.common.lib.model.Metadata;
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
