package ProjectManagementSystem;

import java.io.FileNotFoundException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

    //After Database integration,  It will be converted to Database.getAdmins(),Database.getManagers... .
    static ArrayList<Admin> admins = new ArrayList<>();
    static ArrayList<ProjectManager> managers = new ArrayList<>();
    static ArrayList<ProjectMember> members = new ArrayList<>();
    static ArrayList<Guest> guests = new ArrayList<>();
    static ArrayList<BoardMember> boardMembers = new ArrayList<>();
    static int adminId=0;
    static int projectManagerId=0;
    static int projectMemberId=0;
    static int guestsId=0;
    static int boardMemberId=0;

    static int projectId=0;



    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("*** GTU PROJECT MANAGEMENT AND PLANNING SYSTEM ***");
        Scanner input = new Scanner(System.in);
        SystemClass system = new SystemClass();

        int opt;
        do{
            System.out.println("1)Register\n2)Login\n3)Exit");
            opt = input.nextInt(); input.nextLine();
            switch (opt) {
                case 1:
                    register(system);
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Wrong info");
            }
        }while(opt!=3);

    }


    public static void register(SystemClass system){

        Scanner input = new Scanner(System.in);
        int opt;

        do {
            System.out.println("Choose Account Type:\n1)Admin\n"+ "2)Project Manager\n3)Board Member\n4)Project Member\n5)Guest\n6)Exit");
            opt = input.nextInt(); input.nextLine();

            String name=null, username = null, pw=null;

            if (opt != 6) {
                System.out.println("Enter full name:");
                name = input.nextLine();
                System.out.println("Enter username:");
                username = input.nextLine();
                System.out.println("Enter password:");
                pw = input.nextLine();
            }
            switch (opt){
                case 1:
                    admins.add(new Admin(username, name, pw ,system));
                    opt=6;
                    break;
                case 2:
                    managers.add(new ProjectManager(username, name, pw ));
                    opt=6;
                    break;
                case 3:
                    boardMembers.add(new BoardMember(username, name, pw ));
                    opt=6;
                    break;
                case 4:
                    members.add(new ProjectMember(username, name, pw ));
                    opt=6;
                    break;
                case 5:
                    guests.add(new Guest( username, name, pw ));
                    opt=6;
                    break;
                case 6:
                    System.out.println("Redirecting to main menu.");
                    opt=6;
                    break;
                default:
                    System.out.println("Invalid selection..");
            }

        }while(opt!=6);

    }

    public static void login(){

        Scanner input = new Scanner(System.in);
        int opt;
        boolean loggedIn = false;
        System.out.println("Choose Account Type:\n1)Admin\n"+ "2)Project Manager\n3)Board Member\n4)Project Member\n5)Guest\n6)Exit");
        opt = input.nextInt(); input.nextLine();
        System.out.println("Your username: ");
        String username = input.nextLine();
        System.out.println("Your password: ");
        String pw = input.nextLine();

        switch (opt){
            case 1:
                for(Admin admin : admins){
                    if(admin.getUsername().equals(username) && admin.getPassword().equals(pw)) {
                        adminMenu(admin);
                        loggedIn = true;
                        break;
                    }
                }
                break;
             case 2:
                for(ProjectManager manager : managers){
                    if(manager.getUsername().equals(username) && manager.getPassword().equals(pw)) {
                        projectManagerMenu(manager);
                        loggedIn = true;
                        break;
                    }
                }
                break;
            case 3:
                for(Guest guest : guests){
                    if(guest.getUsername().equals(username) && guest.getPassword().equals(pw)) {
                        guestMenu(guest);
                        loggedIn = true;
                        break;
                    }
                }
                break;
            case 4:
                for(BoardMember boardMember : boardMembers){
                    if(boardMember.getUsername().equals(username) && boardMember.getPassword().equals(pw)) {
                        boardMemberMenu(boardMember);
                        loggedIn = true;
                        break;
                    }
                }

                break;
            case 5:
                for(ProjectMember member : members){
                    if(member.getUsername().equals(username) && member.getPassword().equals(pw)) {
                        memberMenu(member);
                        loggedIn = true;
                        break;
                    }
                }
                break;
            default:
                System.out.println("Redirecting to main menu.");

        }
        if(!loggedIn)
            System.out.println("Wrong ID or Password.");
    }

    public static void adminMenu(Admin admin){
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome, " + admin.getFullname() + ".\n" );
        int opt;

        do {
            System.out.println("1- Create Project");
            System.out.println("2- Create Issue");
            System.out.println("3- Create User");
            System.out.println("4- Logout");

            opt = input.nextInt();  input.nextLine();

            if (opt == 1) {
                int selectedmanager = -1;
                System.out.println("Select project manager to assign to the project");
                for(int i = 0 ; i < managers.size();i++){
                    System.out.println(i+".manager:"+managers.get(i));
                }
                selectedmanager = input.nextInt();
                if(selectedmanager >= 0 && selectedmanager <= managers.size()){
                    Project project = admin.createEmptyProject(managers.get(selectedmanager));
                    managers.get(selectedmanager).setProject(project);
                }else{
                    System.out.println("Project couldn't be created!");
                }

            } else if (opt == 2) {
                //String title, Enum status, Enum type
                String title = null;
                System.out.println("Enter the title of the issue");
                title = input.nextLine();
                String statussString = null; 
                Issue.Status status = null;
                System.out.println("Enter the status of the issue ( 'development','done','inprogress','inreview','verified'");
                statussString = input.nextLine();
                switch(statussString){
                    case "development":
                        status = Issue.Status.development;
                        break;
                    case "done":
                        status = Issue.Status.done;
                        break;
                    case "inprogress":
                        status = Issue.Status.inProgress;
                        break;
                    case "inreview":
                        status = Issue.Status.inReview;
                        break;
                    case "verified":
                        status = Issue.Status.verified;
                        break;
                    default:
                        status = null;          
                }
                String typeString = null;
                Issue.Type type = null;
                System.out.println("Enter the type of the issue ( 'bug','story','epic','task'");
                typeString = input.nextLine();
                switch(typeString){
                    case "bug":
                        type = Issue.Type.bug;
                        break;
                    case "story":
                        type = Issue.Type.story;
                        break;
                    case "epic":
                        type = Issue.Type.epic;
                        break;
                    case "task":
                        type = Issue.Type.task;
                        break;
                    default:
                        type = null;
                }
                Project project = projectSelection(admin.getSystem(), input);
                if(project == null){
                    System.out.println("No project found, issue couldn't be created");
                    continue;
                }
                Board board = boardSelection(project, input);
                if(board == null){
                    System.out.println("No board found, issue couldn't be created");
                    continue;
                }
                IssueList issueList = issuListSelection(board, input);
                if(issueList == null){
                    System.out.println("No issue list found, issue couldn't be created");
                    continue;
                }
                admin.createIssue(issueList, title, status, type);
            } else if (opt == 3) {
                String userType = null;
                String username = null;
                String fullname = null;
                String password = null;
                System.out.println("Enter username:");
                username = input.nextLine();
                System.out.println("Enter fullname:");
                fullname = input.nextLine();
                System.out.println("Enter the user type (guest,projectmember,projectmanager,boardmember)");
                userType = input.nextLine();
                System.out.println("Enter the password of the user");
                password = input.nextLine();
                switch(userType){
                    case "projectmanager":
                        ProjectManager pm = new ProjectManager( username, fullname,password);
                        managers.add(pm);
                        admin.createUser(userType, username, fullname, password);
                        break;
                    case "projectmember":
                        ProjectMember pMember = new ProjectMember( username, fullname,password);
                        admin.createUser(userType, username, fullname, password);
                        members.add(pMember);
                        break;
                    case "boardmember":
                        BoardMember bm = new BoardMember( username, fullname,password);
                        boardMembers.add(bm);
                        admin.createUser(userType, username, fullname,password);
                        break;
                    case "guest":
                        Guest guest = new Guest( username, fullname,password);
                        guests.add(guest);
                        admin.createUser(userType,  username, fullname, password);
                        break;
                }
            } else if (opt == 4) {
                System.out.println("Good Bye..");
                return;
            }
        }while(opt!=4);
    }

    public static void projectManagerMenu(ProjectManager projectManager){

          Scanner input = new Scanner(System.in);
          System.out.println("Welcome, " + projectManager.getFullname() + ".\n" );
          int opt;

          do {
              System.out.println("1- Create Issue");
              System.out.println("3- Logout");

              opt = input.nextInt();  input.nextLine();

              if (opt == 1) {
                  //String title, Enum status, Enum type
                  String title = null;
                  System.out.println("Enter the title of the issue");
                  title = input.nextLine();
                  String statussString = null;
                  Issue.Status status = null;
                  System.out.println("Enter the status of the issue ( 'development','done','inprogress','inreview','verified'");
                  statussString = input.nextLine();
                  switch(statussString){
                      case "development":
                          status = Issue.Status.development;
                          break;
                      case "done":
                          status = Issue.Status.done;
                          break;
                      case "inprogress":
                          status = Issue.Status.inProgress;
                          break;
                      case "inreview":
                          status = Issue.Status.inReview;
                          break;
                      case "verified":
                          status = Issue.Status.verified;
                          break;
                      default:
                          status = null;
                  }
                  String typeString = null;
                  Issue.Type type = null;
                  System.out.println("Enter the type of the issue ( 'bug','story','epic','task'");
                  typeString = input.nextLine();
                  switch(typeString){
                      case "bug":
                          type = Issue.Type.bug;
                          break;
                      case "story":
                          type = Issue.Type.story;
                          break;
                      case "epic":
                          type = Issue.Type.epic;
                          break;
                      case "task":
                          type = Issue.Type.task;
                          break;
                      default:
                          type = null;
                  }
                  Project project = projectManager.getAssignedProject();
                  if(project == null){
                      System.out.println("No project found, issue couldn't be created");
                      continue;
                  }
                  Board board = boardSelection(project, input);
                  if(board == null){
                      System.out.println("No board found, issue couldn't be created");
                      continue;
                  }
                  IssueList issueList = issuListSelection(board, input);
                  if(issueList == null){
                      System.out.println("No issue list found, issue couldn't be created");
                      continue;
                  }
                  projectManager.createIssue(issueList, title, status, type);
              } else if (opt == 3) {
                  System.out.println("Good Bye..");
                  return;
              }
          }while(opt<1||opt>3);
    }

    public static void boardMemberMenu(BoardMember boardMember){

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, " + boardMember.getFullname() + ".\n" );
        int opt;

        do {
            System.out.println("1- Assign User");
            System.out.println("4- Logout");

            opt = input.nextInt();  input.nextLine();

//            if (opt == 1) {
//                projectManager.createEmptyProject();
//            } else if (opt == 2) {
//                admin.createIssue();
//            } else if (opt == 3) {
//                admin.createUser();
//            } else if (opt == 4) {
//                System.out.println("Good Bye..");
//                return;
//            }
        }while(opt!=4);
    }

    public static void guestMenu(Guest guest){

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, " + guest.getFullname() + ".\n" );
        int opt;

        do {
            System.out.println("1- View board");
            System.out.println("2- Logout");

            opt = input.nextInt();  input.nextLine();

            if (opt == 1) {
                guest.viewBoard();
            } else if (opt == 2) {
                System.out.println("Good Bye..");
                return;
            }
        }while(opt!=2);
    }

    public static void memberMenu(ProjectMember projectMember){

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, " + projectMember.getFullname() + ".\n" );
        int opt;

        do {
            System.out.println("1- View board");
            System.out.println("2- View backlog");
            System.out.println("3- View project");
            System.out.println("4- Change issue title");
            System.out.println("5- Edit issue title");
            System.out.println("6- Change issue status");
            System.out.println("7- Add issue comment");
            System.out.println("8- Edit issue comment");
            System.out.println("9- Remove issue comment");
            System.out.println("10- Logout");

            opt = input.nextInt();  input.nextLine();

            if (opt == 1) {
                projectMember.viewBoard(null);
            } else if (opt == 2) {
                projectMember.viewBacklog();
            } else if (opt == 3) {
                projectMember.viewProject();
            }else if (opt == 4) {
                projectMember.editIssueTitle(null, null);
            } else if (opt == 5) {
                projectMember.editIssueTitle(null, null);
            }else if (opt == 6) {
                projectMember.changeIssueStatus(null, null);
            } else if (opt == 7) {
                projectMember.addIssueComment(null, null);
            }
            else if (opt == 8) {
                projectMember.editIssueTitle(null, null);
            }
            else if (opt == 9) {
                projectMember.removeIssueComment(null);
            }
            else if (opt == 10) {
                System.out.println("Good Bye..");
                return;
            }
        }while(opt!=10);
    }
    public static Project projectSelection(SystemClass system,Scanner input){
        int selectedProject = -1;
        if(system.getProjects() == null){
            System.out.println("No project found");
            return null;
        }
        for(int i = 0; i < system.getProjects().size();i++){
            System.out.println(i+".project:"+system.getProjects().get(i));
        }
        System.out.println("Enter the project number:");
        selectedProject = input.nextInt();
        if(selectedProject < 0 || selectedProject >= system.getProjects().size()){
            System.out.println("Invalid project");
            return null;
        }
        return system.getProjects().get(selectedProject);
    }
    public static Board boardSelection(Project project,Scanner input){
        int selectedBoard = -1;
        if(project.getBoards() == null){
            System.out.println("No board found");
            return null;
        }
        for(int i = 0; i < project.getBoards().size();i++){
            System.out.println(i+".board:"+project.getBoards().get(i));
        }
        System.out.println("Enter the board number:");
        selectedBoard = input.nextInt();
        if(selectedBoard <0 || selectedBoard >= project.getBoards().size()){
            System.out.println("Invalid board");
            return null;
        }
        return project.getBoards().get(selectedBoard);
    }
    public static IssueList issuListSelection(Board board,Scanner input){
        int selectedIssueList = -1;
        if(board.getIssues() == null){
            System.out.println("No issuelist found");
            return null;
        }
        for(int i = 0; i < board.getIssues().size();i++){
            System.out.println(i+".issue list:"+board.getIssues().get(i));
        }
        System.out.println("Enter the number of the issue list:");
        selectedIssueList = input.nextInt();
        if(selectedIssueList < 0 || selectedIssueList >= board.getIssues().size()){
            System.out.println("Invalid issue list");
            return null;
        }
        return board.getIssues().get(selectedIssueList);
    }
    public static User userSelection(SystemClass system,Scanner input){
        int selectedUser = -1;
        if(system.getUsers() == null){
            System.out.println("No issuelist found");
            return null;
        }
        for(int i = 0; i < system.getUsers().size();i++){
            System.out.println(i+".user:"+system.getUsers().get(i));
        }
        System.out.println("Enter the number of the user:");
        selectedUser = input.nextInt();
        if(selectedUser < 0 || selectedUser >= system.getUsers().size()){
            System.out.println("Invalid user");
            return null;
        }
        return system.getUsers().get(selectedUser);
    }
}
