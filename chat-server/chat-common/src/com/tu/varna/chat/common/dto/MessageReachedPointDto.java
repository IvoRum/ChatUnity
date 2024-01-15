package com.tu.varna.chat.common.dto;

import java.util.Date;

public record MessageReachedPointDto(String firstName, int idSender, int idReceiver, int messageOrder, String content) {
}
