package test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Category;
import models.Pet;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.PetStatus;
import util.RequestCreator;

import java.io.File;

import static constants.Constants.STATUS_CODE_CHECK_FAILED_MESSAGE;
import static constants.Constants.STATUS_CODE_OK;

public class SingleTest {

    private static final RequestSpecification REQUEST_SPECIFICATION_FOR_PET =
            new RequestSpecBuilder()
                    .setBaseUri("https://petstore.swagger.io/v2")
                    .setBasePath("/pet")
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();

    @Test
    public void commonTestForGetAndPostRequests() {

        Pet pet = Pet.builder()
                .name("dog")
                .id(103456)
                .createCategory(Category.builder().id(54).name("Rocky").build())
                .status(PetStatus.AVAILABLE)
                .build();
        Response createPet = RequestCreator.sendPOSTRequest(REQUEST_SPECIFICATION_FOR_PET, "", pet);
        Assert.assertEquals(createPet.getStatusCode(), STATUS_CODE_OK, STATUS_CODE_CHECK_FAILED_MESSAGE);
        Response findPetByStatus = RequestCreator.sendGETRequest(REQUEST_SPECIFICATION_FOR_PET
                .param(String.valueOf(PetStatus.SOLD)), "/findByStatus");
        Assert.assertEquals(findPetByStatus.getStatusCode(), STATUS_CODE_OK, STATUS_CODE_CHECK_FAILED_MESSAGE);
        Response uploadImageForPet = RequestCreator.sendPOSTRequest(REQUEST_SPECIFICATION_FOR_PET
                        .contentType(ContentType.MULTIPART).multiPart(new File("R.jpg")),
                "/90369791/uploadImage", null);
        Assert.assertEquals(uploadImageForPet.getStatusCode(), STATUS_CODE_OK, STATUS_CODE_CHECK_FAILED_MESSAGE);
    }

}