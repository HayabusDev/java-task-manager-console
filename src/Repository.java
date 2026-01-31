import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {

    private final ArrayList<Task> taskList = new ArrayList<>();

    public Repository(){
        taskList.add(new Task(1 , "testTask", LocalDateTime.now()));
    }

    public List<Task> getTaskList(){
        return Collections.unmodifiableList(taskList);
    }

    public Task searchTask(int generatedTaskId){
       for (int i = 0; i < taskList.size(); i++){

           Task wantedTask = taskList.get(i);

           if (wantedTask.getTaskId() == generatedTaskId){
                return wantedTask;
            }
       }
        return null;
    }

    public void saveTask(Task createdTask){
        if (createdTask == null){
            throw new IllegalStateException("作成タスクが空です。");
        }else if (existsTaskId(createdTask.getTaskId()))
        {
            throw new IllegalStateException("タスクIDが重複しています。");
        }

        taskList.add(createdTask);
    }

    public boolean existsTaskId(int taskId) {
        for (int i = 0; i < taskList.size(); i++) {
            int wantedTaskId = taskList.get(i).getTaskId();

            if (taskId == wantedTaskId) {
                return true;
            }
        }
        return false;
    }

    public int maxTaskId(){
        int maxTaskId = 0;
        for (int i = 0; i < taskList.size(); i++)
            if (taskList.get(i).getTaskId() > maxTaskId){
                maxTaskId = taskList.get(i).getTaskId();
            }
        return maxTaskId;
    }
}
