package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.dtos.request.MessageRequest;
import com.go2geda.Go2GedaApp.dtos.response.MessageResponse;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;

public interface ChatService {
    MessageResponse sendMessage(MessageRequest messageRequest);
    OkResponse deleteMessage(Long chatId);

}
