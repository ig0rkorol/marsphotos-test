package com.ringlabskiev.features.search;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ikorol on 01.09.2017.
 */
public class BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private static final String API_KEY = "6iWyEcfR5lKmYIZMJmvd1pEBmRQPr0dIbsJd2oaU";
    private static final String ROVER_CURIOSITY = "curiosity";

    @BeforeClass
    public static void setUp(){
        // TODO : extract to resources as configuration data
        RestAssured.baseURI = "https://api.nasa.gov";
        RestAssured.basePath = String.format("/mars-photos/api/v1/rovers/%s/photos", ROVER_CURIOSITY);
    }

    /**
     * Request endpoint with sol query parameters
     * @param sol
     * @return Response
     */
    protected Response getCurisityDataBySol(int sol){
        LOGGER.info("Get Curiosity data use sol parameter value {}", sol);
        return SerenityRest.get("?sol={sol1}&api_key={api_key}", sol, API_KEY)
                .andReturn();
    }

    /**
     * Request endpoint with earth_date query parameters
     * @param date
     * @return Response
     */
    protected Response getCuriosityDataByEarthDate(String date){
        LOGGER.info("Get Curiosity data use earth_date parameter value {}", date);
        return SerenityRest.get("?earth_date={earth_date}&api_key={api_key}", date, API_KEY)
                .andReturn();
    }

    /**
     * Verify response code
     * @param actual value of code
     * @param expected value of code
     * @param parameterName name of query parameter
     * @return Response
     */
    protected void verifyResponseCode(int actual, int expected, String parameterName){
        assertThat("Wrong response status code for request by "+ parameterName +" parameter", actual, equalTo(expected));
    }
}

