package ewmmainservice.request;

import ewmmainservice.event.EventMapper;
import ewmmainservice.event.dto.EventFullDto;
import ewmmainservice.request.dto.ParticipationRequestDto;
import ewmmainservice.request.model.Request;
import lombok.experimental.UtilityClass;
import ewmmainservice.user.model.User;

@UtilityClass
public class RequestMapper {
    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }

    public static Request toRequest(ParticipationRequestDto requestDto, User user, EventFullDto event) {
        return new Request(
                0,
                requestDto.getCreated(),
                EventMapper.toEvent(event, user),
                user,
                requestDto.getStatus()
        );
    }
}
