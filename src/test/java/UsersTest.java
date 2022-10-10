import com.fasterxml.jackson.databind.ObjectMapper;
import config.PropertiesFile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import responsePayload.pojos.Users;

import java.util.*;


public class UsersTest extends ToDosTest {

    @BeforeTest
    ////Setting the baseuri of Users from config file
    public void setUp() {
        RestAssured.baseURI = PropertiesFile.getProperties("baseURI");
    }

    //Using TestNG depends On Methods
    @Test(dependsOnMethods = {"getToDOList"})
    //This Method is depend on ToDOs API Request
    public void getUsersList() {
        try {
            boolean flag = false;
            //Hitting the Users GET request and storing the results in Array
            Users[] userlist = RestAssured.given().contentType(ContentType.JSON).get("/users").as(Users[].class);

            //Getting the Property value of ToDos Completed ID List
            List<String> proplist = new ArrayList<String>();
            proplist.add(PropertiesFile.getProperties("UserIDsLists"));

            //Iterating through the List
            for (String s : proplist
            ) {
                //Splitting the list using regex
                String splitvalue[] = s.split(",");

                //Setting the Set from the Split List
                Set<String> userIdList = new HashSet<>(Arrays.asList(splitvalue));

                //Checking the Latitude and Longitude condition and putting the unique results in HashSet
                for (int i = 0; i < userlist.length; i++) {
                    if ((((userlist[i].getAddress().getGeo().getLat() < -40.0) && (userlist[i].getAddress().getGeo().getLat() < 5))
                            && ((userlist[i].getAddress().getGeo().getLng() > 5) && (userlist[i].getAddress().getGeo().getLat() < 100))) && !userIdList.add(userlist[i].getId().toString())) {
                        System.out.println("ID is " + userlist[i].getId() + "Name is " + userlist[i].getName() + "Country is" + userlist[i].getAddress().getCity());
                        userIdList.add(userlist[i].getName());
                        flag = true;
                    }
                }


            }
            if(flag == false)
                System.out.println("Completed Task Found but City Latitide and Longitude condition not satisfied");

        } catch (Exception e) {
            System.out.println("Exception Occured is " + e.getMessage());
            Assert.assertTrue(1 == 2);
        }
    }
}

