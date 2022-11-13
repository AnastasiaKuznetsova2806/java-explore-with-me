package ewmmainservice.request.service;

import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.model.Request;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto createRequest(long userId, long eventId);

    ParticipationRequestDto updateRequest(long userId, long requestId);

    List<ParticipationRequestDto> findAllRequestsByUserId(long userId);

    List<ParticipationRequestDto> findRequestsToParticipateInUsersEvent(long userId, long eventId);

    ParticipationRequestDto confirmationRequestParticipationInEvent(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectRequestParticipationInEvent(long userId, long eventId, long reqId);

    long getConfirmedRequests(long eventId);

    Request getRequestByUserIdAndEventId(long userId, long eventId);
}
