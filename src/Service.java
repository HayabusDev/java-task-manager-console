import java.time.LocalDateTime;
import java.util.List;

public class Service {
    private final Repository repository;

    public Service (Repository repository){
        this.repository = repository;
    }

    public int generateId(){
        int nextId = repository.maxTaskId();
        nextId++;
        return nextId;
    }

    public boolean createTask(String inputTask){
        if (inputTask == null){
            return false;
        }else if (inputTask.isBlank()){
            return false;
        }

        int generatedId = generateId();
        Task createdTask = new Task(generatedId, inputTask, LocalDateTime.now());

        try {
            repository.saveTask(createdTask);
            return true;
        }catch (IllegalStateException e){
            return false;
        }
    }

    public boolean completeTask(int taskId){
        if (taskId <= 0){
            return false;
        }

        Task foundTask = repository.searchTask(taskId);
        if (foundTask == null){
            return false;
        }else return foundTask.completeTask();
    }

    public List<Task> listTasks(){
        return repository.getTaskList();
    }
}
