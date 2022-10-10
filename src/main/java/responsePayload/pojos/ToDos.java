package responsePayload.pojos;

import lombok.Data;

import java.util.List;

@Data
public class ToDos {

    private Long userId;
    private Long id;
    private String title;
    private boolean completed;


    // private List<ToDos> toDosList;

}
