package com.example.demo.reader;

import com.example.demo.domain.User;
import com.example.demo.domain.UserApplication;
import com.example.demo.writer.JsonLineAggregator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(JUnit4.class)

public class JsonLineMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonLineAggregator<UserApplication> lineAggregator;
    private JsonLineMapper<?> jsonLineMapper;
    private static final String WRONG_JSON = "{wrong}";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        this.lineAggregator = new JsonLineAggregator<>(objectMapper);
        this.jsonLineMapper = new JsonLineMapper<>(objectMapper, UserApplication.class);
    }
    @Test
    public void mapLine_setAsOfRequestDataWithAdHoc_successfullyMapped() throws Exception {
        UserApplication expectedObject = buildUserApplication();

        String json = lineAggregator.aggregate(expectedObject);

        UserApplication userApplication = (UserApplication) jsonLineMapper.mapLine(json, 0);

        assertNotNull(userApplication);

        assertEquals(expectedObject, userApplication);

    }


    @Test
    public void mapLine_setWrongObjectType_expectException() throws Exception {
        //create object with wrong generic type
        jsonLineMapper = new JsonLineMapper<>(objectMapper, User.class);

        UserApplication expectedObject = buildUserApplication();
        //create json value for expected object
        String json = lineAggregator.aggregate(expectedObject);

        exception.expect(Exception.class);
        exception.expectMessage("Error while retrieving Object from json.");

        jsonLineMapper.mapLine(json, 0);

    }

    @Test
    public void mapLine_sendWrongJsonToMap_expectException() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Error while retrieving Object from json.");
        // Execute
        jsonLineMapper.mapLine(WRONG_JSON, 0);
    }
    private UserApplication buildUserApplication() {
        return  UserApplication.builder().applicationID("appID").departmentID("depID").build();
    }

}
