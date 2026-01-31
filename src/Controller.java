import java.util.List;
import java.util.Scanner;
import java.text.Normalizer;

public class Controller {
    Scanner sc = new Scanner(System.in);

    private final Service service;
    public Controller(Service service) { this.service = service; }

    private boolean isRunning = true;

    public void launchMenu(){
        while (isRunning){

            showLaunchMenu();

            int userChoice;

            while (true){
                System.out.println("要件を入力してください。(1~4)");
                try {
                    userChoice = sc.nextInt();
                }catch (Exception e){
                    System.out.println("エラー: 数値を入力してください。");
                    sc.nextLine();
                    continue;
                }
                if (userChoice >= 5 || userChoice == 0){
                    System.out.println("エラー： 1~4 の値を入力してください。");
                }else {
                sc.nextLine();
                break;
                }
            }

            switch (userChoice){
                case 1:
                    registerTask();
                    break;
                case 2:
                    showTaskList();
                    break;
                case 3:
                    doneTask();
                    break;
                case 4:
                    System.out.println("終了します。");
                    isRunning = false;
                    break;
                default:
            }
        }
    }

    public void showLaunchMenu(){
        System.out.println("◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
        System.out.println("タスク管理アプリへようこそ。");
        System.out.println("◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
        System.out.println("1. タスク登録");
        System.out.println("2. タスク一覧");
        System.out.println("3. タスクを完了");
        System.out.println("4. 終了");
        System.out.println("◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
    }

    public void registerTask(){
        String inputTask;
        System.out.println("登録するタスクを入力してください。");
        inputTask = sc.nextLine().trim();

        boolean canRegister = service.createTask(inputTask);

        if (canRegister){
            System.out.println(inputTask + " を登録しました。");
        }else {
            System.out.println("エラー: もう一度やり直してください。");
        }
    }

    public List<Task> takeTaskList(){
        return service.listTasks();
    }

    public void showTaskList(){
        List<Task> taskLists = takeTaskList();
        if (taskLists.isEmpty()){
            System.out.println("現在、登録されているタスクはありません。");
        }else {
            System.out.println("========================== タスク一覧 ==========================");
            for (Task task : taskLists){
                System.out.println(task);
            }
            System.out.println("全 " + taskLists.size() + " 件");
        }
    }

    public void doneTask(){
        int inputID;

        while (true){
            System.out.println("完了するタスクのIDを入力してください。");
            try {
                inputID = sc.nextInt();
                sc.nextLine();
                break;
            }catch (Exception e){
                System.out.println("完了するタスクのIDを半角の数字で入力してください。");
                sc.nextLine();
            }
        }
        askDoneTask(inputID);
    }

    public String unifyUserInput(String userInput){
        String normalizedUserInput = Normalizer.normalize(userInput, Normalizer.Form.NFKC);
        String finalizedUserInput = normalizedUserInput.toUpperCase();
        String[] yChoices = {"Y", "YES", "はい"};
        String[] nChoices = {"N", "NO", "いいえ"};

        for (String yChoice : yChoices) {
            if (finalizedUserInput.equals(yChoice)) {
                return "Y";
            }
        }

        for (String nChoice : nChoices){
            if (finalizedUserInput.equals(nChoice)){
                return "N";
            }
        }
        return "UNKNOWN";
    }

    public void askDoneTask(int inputID){
        boolean isLoop = true;

        while (isLoop) {
            System.out.println(inputID + " のタスクを完了します。　よろしいですか？ y or n");
            String userInput = sc.nextLine().trim();
            String userChoice = unifyUserInput(userInput);

            if (userChoice.equals("Y")) {
                boolean canDone = service.completeTask(inputID);
                if (canDone) {
                    System.out.println(inputID + " のタスクを完了しました。");
                    isLoop = false;
                } else {
                    System.out.println("エラー: タスク完了を正常に処理できませんでした。最初からやり直してください。");
                    isLoop = false;
                }
            } else if (userChoice.equals("N")) {
                System.out.println("タスク完了処理を取り消ししました。");
                isLoop = false;
            } else {
                System.out.println("入力が不正です。" + "\n" + "y or n で入力してください。");
            }
        }
    }
}
