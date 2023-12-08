package com.tu.varna.chat.common.net;

import java.util.List;

public record ChatUpdateRequest(int userId, List<ChatReachedPoint> allChatsToUpdate) {
}
