
import config.PropertiesFile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import responsePayload.pojos.ToDos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ToDosTest {

    List<Long> l1 = new ArrayList<>();

    @BeforeTest
    //Setting the baseuri of ToDos from config file
    public void setUp() {
        RestAssured.baseURI = PropertiesFile.getProperties("baseURI");
    }


    @Test
    //Getting the list of userIds who task is completed by 50%
    public void getToDOList() {
        try {
            //Hitting the ToDos GET request and storing the results in Array
            ToDos[] todo = RestAssured.given().contentType(ContentType.JSON).get("/todos").as(ToDos[].class);

            //Converting Array into List
            List<ToDos> toDosList = Arrays.asList(todo);

            //Setting the hashmap of userIds from the list
            Map<Long, List<ToDos>> map = toDosList.stream().collect(Collectors.groupingBy(ToDos::getUserId));

            //Setting the hashmap of userIds who's task is completed
            Map<Long, List<ToDos>> mapCount = map.entrySet().stream().flatMap(x -> x.getValue().stream()).filter(x -> x.isCompleted()).collect(Collectors.groupingBy(ToDos::getUserId));

            map.entrySet().stream().flatMap(x -> x.getValue().stream()).forEach(ToDos -> {
                //      System.out.println(" User Ids List " +ToDos.getUserId());
            });


            mapCount.entrySet().stream().flatMap(x -> x.getValue().stream()).forEach(ToDos -> {
                //        System.out.println("Compleetd Task Count"  +ToDos.getUserId());
            });

            //Printing the Values of UserIds who task is completed by 50% and adding the same in List
            map.entrySet().stream().forEach(ToDos -> {

                        long completedtask = mapCount.get(ToDos.getKey()).size();
                        Double completedtaskd = Double.parseDouble("" + completedtask);
                        long totalCount = map.get(ToDos.getKey()).size();
                        Double totalCoubtd = Double.parseDouble("" + totalCount);

                        if ((((completedtaskd / totalCoubtd)) * 100) > 50) {
                                System.out.println("Final List of Users Ids who task is completed by 50% User ID  -->" + ToDos.getKey());
                            l1.add(ToDos.getKey());

                        }

                        //Setting the value of UserIds who task is completed by 50%
                        PropertiesFile.setProperties("UserIDsLists", l1.toString());
                    }

            );
        } catch (Exception e) {
            System.out.println("Exception Occured is " + e.getMessage());
            Assert.assertTrue(1 == 2);

        }


    }
}
