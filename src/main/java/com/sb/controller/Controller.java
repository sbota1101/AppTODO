package com.sb.controller;

import com.sb.model.Project;
import com.sb.model.SubTask;
import com.sb.model.Task;
import com.sb.model.User;
import com.sb.repository.ProjectRepository;
import com.sb.repository.SubTaskRepository;
import com.sb.repository.TaskRepository;
import com.sb.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//done:Add scenes for diff.operations:e.g.login,register
//done:Make all the tabs hidden.Login window opens when you click an option in the menu.
//done:After login success show others tabs.Clear text field.Show message
//done:After clicking register,empty textboxes,show message user registered
//done:Create others entities.Task,substask+repository for each
//done:Create tab for registering tasks
public class Controller {
    @FXML
    public Label lblUsernameLogin;
    public Label lblUsernameRegister;
    public Tab tabLogin;
    public Tab tabRegister;
    public TabPane tabPane;
    public MenuItem menuItemLogin;
    public CheckMenuItem menuItemRegister;
    public Label lblConfirmPasswordRegister;
    public Label lblPasswordRegister;
    public TextField txtFieldPwdConfirmRegister;
    public TextField txtFieldPwdRegister;
    public PasswordField pwdFieldRegister;
    public Button btnRegister;
    public Button btnShowPwdRegister;
    public Button btnShowConfirmRegister;
    public Button btnLogin;
    public PasswordField pwdFieldConfirmRegister;
    public PasswordField pwdFieldLogin;
    public TextField txtFieldPwdLogin;
    public CheckMenuItem menuItemTask;
    public TextField txtFieldTODO;
    public VBox vBoxTasks;
    public Button btnInsert;
    public TableColumn colTaskDesc;
    public TableColumn colTaskId;
    public TableView tblView;
    public CheckMenuItem menuItemAllTasks;
    public TableColumn colUsername;
    public Tab tabShowTasks;
    public Tab tabAddTask;
    public Tab tabTasks;
    public VBox vBoxTaskList;
    public VBox vBoxTaskAllocated;
    public CheckMenuItem menuItemTaskList;
    public Tab tabTasks2;
    public VBox vBoxTaskInProgress;
    public VBox vBoxTaskDone;
    public Tab tabSubTask;
    public Button btnSubTask;
    public TextField txtFieldTODOSubtask;
    public Button btnInsertsubtask;
    public VBox vBoxSubTask;
    public Tab tabShowSubTasks;
    public TableView tblViewSubTask;
    public TableColumn colTask;
    public TableColumn colSubTaskDesc;
    public TableColumn colSubTaskId;
    public Button btnlogin;
    public Button btnregister;
    public Tab tabAddProject;
    public Button btnInsertProject;
    public TextField txtFieldProject;
    public VBox vBoxProjects;
    public CheckMenuItem menuItemProject;
    public Tab tabShowProjects;
    public TableView tblViewProject;
    public TableColumn colTasks;
    public TableColumn colUser;
    public TableColumn colProjectName;
    public TableColumn colProjectId;
    public CheckBox checkDev;
    public CheckBox checkAdmin;
    public ComboBox comboProject;
    public Label txtRolle;
    public ComboBox comboTask;


    @FXML
    private Label lblInformation;
    @FXML
    private TextField txtFieldUsernameRegister;
    @FXML
    private TextField txtFieldUsernameLogin;
    private UserRepository userRepository;
    private boolean isConnectionSuccessful = false;
    private TaskRepository taskRepository;
    private User loggedInUser;
    private SubTaskRepository subTaskRepository;
    private ProjectRepository projectRepository;

    public void initialize() {
        try {
            colTaskId.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
            colTaskDesc.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
            colUsername.setCellValueFactory(new PropertyValueFactory<Task, String>("user"));
            colSubTaskId.setCellValueFactory(new PropertyValueFactory<SubTask, Integer>("id"));
            colSubTaskDesc.setCellValueFactory(new PropertyValueFactory<SubTask, String>("description"));
            colTask.setCellValueFactory(new PropertyValueFactory<SubTask, String>("task"));
            colProjectId.setCellValueFactory(new PropertyValueFactory<Project, Integer>("project_id"));
            colProjectName.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
            colUser.setCellValueFactory(new PropertyValueFactory<Task, String>("users"));
            colTasks.setCellValueFactory(new PropertyValueFactory<Task, String>("tasks"));
            persistenceConnection();
        } catch (Exception e) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
            e.printStackTrace();
        }
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabRegister);
        tabPane.getTabs().add(tabLogin);
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
        taskRepository = new TaskRepository(entityManager);
        subTaskRepository = new SubTaskRepository(entityManager);
        projectRepository = new ProjectRepository(entityManager);
    }

    @FXML
    // done: Add button to show password
    //done: Check if username already exists
    //done: Check if password contains certain characters
    //TODO: Save encrypted(encoded) password in DB.Be careful how to read them unencrypted
    public void registerUser(ActionEvent actionEvent) {
        User user = userRepository.findByUsername(txtFieldUsernameRegister.getText());
        //parola trebuie sa fie cu 4cifre dif la sfarsit si cu 3 litere la inceput
        String passwordPattern = "([a-zA-Z]+[0-9]...)";

        if (pwdFieldRegister.getText().equals(pwdFieldConfirmRegister.getText()) & user == null) {

            if (txtFieldUsernameRegister.getText().length() < 1) {
                lblUsernameRegister.setTextFill(Color.RED);
                lblInformation.setVisible(true);
                lblInformation.setText("Please fill the username!");
            } else {
                if ((pwdFieldRegister.getText().matches(passwordPattern))) {
                    user = new User();
                    user.setUsername(txtFieldUsernameRegister.getText());
                    user.setPassword(pwdFieldRegister.getText());
                    userRepository.save(user);
                    tabPane.getTabs().remove(tabRegister);
                    tabPane.getTabs().add(tabLogin);
                } else {
                    lblInformation.setVisible(true);
                    lblInformation.setText("Please insert a passwrod with special characters");
                    pwdFieldRegister.clear();
                    pwdFieldConfirmRegister.clear();
                }
                //}
                if ((checkAdmin.isSelected() | checkDev.isSelected())) {
                    tabPane.getTabs().add(tabLogin);
                } else {
                    txtRolle.setVisible(true);
                    txtRolle.setTextFill(Color.RED);
                    txtRolle.setText("Please choose role");
                    tabPane.getTabs().clear();
                }
            }
        } else {

            lblInformation.setVisible(true);
            lblInformation.setTextFill(Color.RED);
            lblInformation.setText("The user" + " " + txtFieldUsernameRegister.getText() + " is already registered");
            lblUsernameRegister.setTextFill(Color.BLACK);
        }


    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        /*System.out.println(txtFieldUsernameLogin.getText());
        System.out.println(pwdFieldLogin.getText());
        tabPane.getTabs().remove(tabLogin);
        tabPane.getTabs().add(tabRegister);*/
        loggedInUser = userRepository.findByUsername(txtFieldUsernameLogin.getText());
        if (loggedInUser != null) {
            tabPane.getTabs().clear();
            tabPane.getTabs().add(tabAddProject);
            tabPane.getTabs().add(tabShowProjects);
            tabPane.getTabs().add(tabAddTask);
            //tabPane.getTabs().add(tabShowTasks);
            tabPane.getTabs().add(tabTasks);
            tabPane.getTabs().add(tabTasks2);
            lblInformation.setVisible(true);
            lblInformation.setText("User login succesfull");
        } else {
            lblInformation.setVisible(true);
            lblInformation.setText("User not existing");
        }
    }

    private void showPassword(TextField showPassword, PasswordField pwdField) {
        showPassword.setText(pwdField.getText());
        showPassword.setEditable(false);
        showPassword.setVisible(true);
        pwdField.setVisible(false);
    }

    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldPwdRegister.isVisible()) {
            btnShowPwdRegister.setText("Hide");
            showPassword(txtFieldPwdRegister, pwdFieldRegister);
        } else {
            btnShowPwdRegister.setText("Show");
            txtFieldPwdRegister.setVisible(false);
            pwdFieldRegister.setVisible(true);
        }
    }

    public void showConfirmPassword(ActionEvent actionEvent) {
        if (!txtFieldPwdConfirmRegister.isVisible()) {
            btnShowConfirmRegister.setText("Hide");
            showPassword(txtFieldPwdConfirmRegister, pwdFieldConfirmRegister);
        } else {
            btnShowConfirmRegister.setText("Show");
            txtFieldPwdConfirmRegister.setVisible(false);
            pwdFieldConfirmRegister.setVisible(true);
        }
    }

    public void showRegisterPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabRegister);
    }

    public void showLoginPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabLogin);
    }

    public void showProjectPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabAddProject);
        tabPane.getTabs().add(tabShowProjects);
    }


    public void showTaskPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabAddTask);
    }

    public void showAllTasksPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabShowTasks);
    }

    public void insertProjectEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            insertProject();
        }
    }

    public void insertTaskEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            insertTask();
        }
    }

    public void insertSubTaskEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            insertSubTask();
        }
    }

    private void insertProject() {
        Project project = new Project();
        project.setCreatedAt(new Date());
        project.setName(txtFieldProject.getText());
        List<User> users = project.getUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(loggedInUser)) {
            users.add(loggedInUser);
        }
        project.setUsers(users);
        projectRepository.save(project);
        txtFieldProject.clear();
        CheckBox checkBox = new CheckBox();
        checkBox.setText(project.getName());
        vBoxProjects.getChildren().add(checkBox);

    }

    private void insertTask() {
        Project project = null;
        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(txtFieldTODO.getText());
//        project = projectRepository.findById(project.getProject_id());
//        task.setProject(project);
        taskRepository.save(task);
        txtFieldTODO.clear();
        CheckBox checkBox = new CheckBox();
        checkBox.setText(task.getDescription());
        vBoxTasks.getChildren().add(checkBox);

    }

    private void insertSubTask() {
        SubTask subTask = new SubTask();
        subTask.setDescription(txtFieldTODOSubtask.getText());
        subTaskRepository.save(subTask);
        txtFieldTODOSubtask.clear();
        CheckBox checkBox = new CheckBox();
        checkBox.setText(subTask.getDescription());
        vBoxSubTask.getChildren().add(checkBox);
    }

    public void insertProject(ActionEvent actionEvent) {
        insertProject();
    }

    public void insertSubTask(ActionEvent actionEvent) {
        insertSubTask();
    }

    public void insertTask(ActionEvent actionEvent) {
        insertTask();
    }

    public void loadSubTask(Event actionEvent) {
        tabPane.getTabs().add(tabSubTask);
        tabPane.getTabs().add(tabShowSubTasks);
    }

    public void loadProjects(Event event) {
        vBoxProjects.getChildren().clear();
        List<Project> projects = projectRepository.findAll();
        final ObservableList<Project> dbProjects = FXCollections.observableList(projects);
        tblViewProject.setItems(dbProjects);
        System.out.println("Loaded Projects");
    }

    public void loadTasks(Event event) {
        vBoxTasks.getChildren().clear();
        List<Task> tasks = taskRepository.findAll();
        final ObservableList<Task> dbTasks = FXCollections.observableList(tasks);
        tblView.setItems(dbTasks);
        System.out.println("Loaded tasks");
    }

    public void loadSubTasks(Event event) {
        vBoxSubTask.getChildren().clear();
        List<SubTask> subTasks = subTaskRepository.findAll();
        final ObservableList<SubTask> dbSubTasks = FXCollections.observableList(subTasks);
        tblViewSubTask.setItems(dbSubTasks);
        System.out.println("Loaded subtasks");
    }


    public void loadTaskList(Event event) {
        vBoxTaskList.getChildren().clear();
        vBoxTaskAllocated.getChildren().clear();
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            CheckBox checkBox = new CheckBox();
            if (task.getUser() != null) {
                checkBox.setText(task.getId() + " " + task.getDescription() + " " + "allocated to" + " " + task.getUser().getUsername());
                checkBox.setDisable(true);
                vBoxTaskAllocated.getChildren().add(checkBox);
                // task.setInProgress(true);
            } else {
                vBoxTaskList.getChildren().add(checkBox);
                checkBox.setText(task.getId() + ". "
                        + task.getDescription());
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    task.setUser(loggedInUser);
                    task.setInProgress(true);
                    taskRepository.save(task);
                    loadTaskList(null);
                });

            }

        }
    }

    public void loadTaskListInProgress(Event event) {
        vBoxTaskAllocated.getChildren().clear();
        vBoxTaskInProgress.getChildren().clear();
        vBoxTaskDone.getChildren().clear();
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.isInProgress()) {
                CheckBox checkBox = new CheckBox(task.getDescription() + " " + "is in progress");
                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    task.setInProgress(true);
                    taskRepository.save(task);
                    vBoxTaskInProgress.getChildren().remove(checkBox);
                    CheckBox checkBox1 = new CheckBox(task.getDescription() + " " + "is done");
                    vBoxTaskDone.getChildren().add(checkBox1);
                });
                vBoxTaskInProgress.getChildren().add(checkBox);
            } else {

                CheckBox checkBox1 = new CheckBox(task.getDescription() + " " + "is done");
                vBoxTaskDone.getChildren().add(checkBox1);
                checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    vBoxTaskDone.getChildren().remove(checkBox1);
                    taskRepository.deleteById(task.getId());
                });
            }

        }
    }

    public void deleteTask(MouseEvent mouseEvent) {
//        vBoxTaskAllocated.getChildren().clear();
//        vBoxTaskInProgress.getChildren().clear();
//        List<Task> tasks = taskRepository.findAll();
//        for (Task task : tasks) {
//            CheckBox checkBox1 = new CheckBox(task.getDescription() + " " + "is done");
//            vBoxTaskDone.getChildren().add(checkBox1);
//            if (checkBox1.isSelected()) {
//                taskRepository.deleteById(task.getId());
//            }
//        }
    }

    public void gotoLogin(ActionEvent actionEvent) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabLogin);
    }

    public void goToRegister(ActionEvent actionEvent) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tabRegister);
    }


    public void developer(ActionEvent actionEvent) {
        if (checkDev.isSelected()) {
            checkAdmin.setSelected(false);
            btnInsertProject.setDisable(true);
        }
    }

    public void admin(ActionEvent actionEvent) {
        if (checkAdmin.isSelected()) {
            checkDev.setSelected(false);
        }
    }
}





