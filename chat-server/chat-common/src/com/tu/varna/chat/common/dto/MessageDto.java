package com.tu.varna.chat.common.dto;

import java.util.Date;

public record MessageDto(int idSender, int idReceiver, int order, Date timeStamp, int status, int content) {
}
