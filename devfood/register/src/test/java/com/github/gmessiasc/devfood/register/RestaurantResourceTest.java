package com.github.gmessiasc.devfood.register;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
public class RestaurantResourceTest {
    
    @Test
    @DataSet("restaurants-scenario1.yaml")
    public void testGetAllRestaurant() {
        String result = given()
                        .when().get("/restaurants")
                        .then()
                        .statusCode(200)
                        .extract().asString();
        Approvals.verifyJson(result);
    }
}
