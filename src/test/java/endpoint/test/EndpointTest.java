package endpoint.test;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EndpointTest {

    private static final String baseURI = "http://some_domain.com";
    private static final int port = 8080;
    private static final String relativeURIFormat = "/company/%s/users?name=%s";

    @Parameter
    public String companyId;

    @Parameter(1)
    public String userName;

    @Parameter(2)
    public int statusCode;

    @Parameters
    public static Collection<Object[]> data() { //imagine that there is user mark in 1st company, anna in 2nd and frank in 3rd
        return Arrays.asList(new Object[][]{
                //  companyId       |   userName            |   statusCode  |   "something else?"
                //                  |                       |               |
                {   "",                 "mark",                 400                             }, //illegal company id
                {   "illegalId",        "mark",                 400                             },
                {   "2illegalId",       "mark",                 400                             },
                {   1,                  "illegalUserName^",     400                             }, //illegal user name
                {   1,                  "",                     400                             },
                {   0,                  "mark",                 404                             }, //nonexistent company id
                {   9999999999L,        "mark",                 404                             },
                {   1,                  "anna",                 404                             }, //username from other company
                {   1,                  "frank",                404                             },
                {   2,                  "frank",                404                             },
                {   2,                  "mark",                 404                             },
                {   3,                  "anna",                 404                             },
                {   3,                  "mark",                 404                             },
                {   1,                  "mark",                 200                             }, //normal behaviour
                {   2,                  "anna",                 200                             },
                {   3,                  "frank",                200                             },
        });
    }

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;
    }

    private String getRelativePath() {
        return String.format(relativeURIFormat, companyId, userName);
    }

    @Test
    public void testEndpoints() {
        assertEquals("Company id = " + companyId + "; User name = " + userName,
                RestAssured.get(getRelativePath()).statusCode(),
                statusCode);
    }

}
