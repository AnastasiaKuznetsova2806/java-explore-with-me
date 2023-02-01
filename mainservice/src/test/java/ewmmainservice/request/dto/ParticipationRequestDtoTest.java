package ewmmainservice.request.dto;

import ewmmainservice.request.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class ParticipationRequestDtoTest {
    @Autowired
    JacksonTester<ParticipationRequestDto> json;

    @Test
    void participationRequestDto() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        ParticipationRequestDto requestDto = new ParticipationRequestDto(
                dateTime, 1L, 1L, 1L, Status.PENDING
        );

        JsonContent<ParticipationRequestDto> result = json.write(requestDto);

        assertThat(result).extractingJsonPathValue("$.created").isEqualTo(dateTime.toString());
        assertThat(result).extractingJsonPathNumberValue("$.event").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.requester").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.status").isEqualTo(Status.PENDING.toString());
    }

}