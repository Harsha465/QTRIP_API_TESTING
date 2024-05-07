package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
@Test(groups= {"API Tests"})
public class testCase_API_02 {

    public void testcase_02(){
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/cities";
        JSONObject obj = new JSONObject();
        
        Response resp = RestAssured.given().queryParam("q", "beng").log().all().when().get();
        Assert.assertEquals(resp.getStatusCode(), 200);
    
        JsonPath jp = new JsonPath(resp.body().asString());
        System.out.println(jp.getString("[0].id"));
        List<JSONObject> list = jp.getList("$");
        
        Assert.assertEquals(list.size(),1);
    
        File schemaFile =  new File("src/test/resources/schema.json");
        // JsonSchemaValidator matcher = JsonSchemaValidator.matchesJsonSchema(schemaFile);
    
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
        System.out.println("resp body"+resp.body().asString());
        System.out.println(jp.getString("[0].description"));
    
        Assert.assertEquals(jp.getString("[0].description"), "100+ Places");
    }




}
