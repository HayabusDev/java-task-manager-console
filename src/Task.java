import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private final int taskId;
    private final String title;
    private final LocalDateTime createdAt;
    private LocalDateTime doneAt;

    public Task(int taskId, String title, LocalDateTime createdAt){
        this.taskId = taskId;
        this.title = title;
        this.createdAt = createdAt;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDoneAt() {
        return doneAt;
    }

    public boolean isDone(){
        return doneAt != null;
    }

    public boolean completeTask(){
        if (isDone()){
            return false;
        }else {
            doneAt = LocalDateTime.now();
            return true;
        }
    }

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss EE");

        if (doneAt == null){
            return "[" + taskId + "]" + " タスク名: " + title + " | " + "作成日時: " + createdAt.format(formatter) + " | " + "未完了";
        }
        return "[" + taskId + "]" + " タスク名: " + title + " | " + "作成日時: " + createdAt.format(formatter) + " | " + "完了日時: " + doneAt.format(formatter);
    }

}
