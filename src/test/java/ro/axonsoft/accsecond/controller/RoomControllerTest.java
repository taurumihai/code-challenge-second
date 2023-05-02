package ro.axonsoft.accsecond.controller;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ro.axonsoft.accsecond.services.FileService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@RunWith(SpringRunner.class)
@WebMvcTest(ImportController.class)
@AutoConfigureMockMvc*/
public class RoomControllerTest {

    private static final String GET_API_ROOM_WITH_ID = "/api/room/";
    private static final String GET_API_ROOMS = "/api/room";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

/*    @BeforeEach
    public void initLists() {

        initMocks(this);
*//*        personEntityList.add(PersonDTO.builder()
                .firstName("Frank")
                .lastName("Supper")
                .title("Dr.")
                .nameAddition("von")
                .ldapUser("fsupper").build());

        personEntityList.add(PersonDTO.builder()
                .firstName("Dennis")
                .lastName("Fischer")
                .title("")
                .nameAddition("")
                .ldapUser("dfischer").build());

        roomEntities.add(RoomDTO.builder().roomNumber("1111").people(personEntityList).build());
        roomEntities.add(RoomDTO.builder().roomNumber("1002").people(personEntityList).build());*//*
    }*/

/*    @Test
    public void testApiImportShouldPass() throws Exception {

        MockMultipartFile file = new MockMultipartFile("uploadedFile", null, "application/json", "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/import")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getRoomDistribution_Test_Ok() throws Exception {
        mockMvc.perform(get(GET_API_ROOM_WITH_ID, "1111"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void getRoomByIdShouldFailWith404() throws Exception {

        String roomId = "9483";
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080" + GET_API_ROOM_WITH_ID + roomId)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(404);

    }

    @Test
    public void addRoomDistribution_test() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/import")
                        .file(file))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }*/

}
