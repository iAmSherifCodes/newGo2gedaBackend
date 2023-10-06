package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Chat;
import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.MessageRequest;
import com.go2geda.Go2GedaApp.dtos.response.MessageResponse;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.repositories.ChatRepository;
import com.go2geda.Go2GedaApp.repositories.CommuterRepository;
import com.go2geda.Go2GedaApp.repositories.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatServiceImplementation implements ChatService{
    private final CommuterRepository commuterRepository;
    private final TripRepository tripRepository;
    private final ChatRepository chatRepository;

    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        Optional<Commuter> commuter = commuterRepository.findById(messageRequest.getCommuterId());
        Commuter foundCommuter = null;
        try {
            foundCommuter = commuter.orElseThrow(()-> new NotFoundException("Commuter Not Found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        User foundUser = foundCommuter.getUser();
        String firstName = foundUser.getBasicInformation().getFirstName();
        String lastName = foundUser.getBasicInformation().getLastName();
        String message = messageRequest.getMessage();
        Optional<Trip> trip = tripRepository.findById(messageRequest.getTripId());

        Chat chat = new Chat();
        chat.setSender(foundUser);
        chat.setChatMessage(message);
        chatRepository.save(chat);

        trip.get().getGroupChat().getMessages().add(chat);
        return null;
    }

    @Override
    public OkResponse deleteMessage(Long chatId) {
        return null;
    }
}
