package org.anasko.server.objects;

import lombok.Getter;

public record Circle(@Getter double coordX, @Getter double coordY, @Getter double radius) {
}
