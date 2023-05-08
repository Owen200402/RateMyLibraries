//package ui;
//
//import model.Comment;
//import model.Library;
//import model.Comments;
//import model.User;
//import persistance.JsonReader;
//import persistance.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.*;
//
//// Rate My Library Console Application - Loading data from json and having a back-up original data can be used
//
//public class UISetUp {
//    private static final String JSON_STORE = "./data/libraries.json";
//    private final JsonWriter jsonWriter;
//    private final JsonReader jsonReader;
//
//    private Scanner scanner;
//
//    private User user;
//
//    private Comments asian;
//    private Comments ikb;
//    private Comments koerner;
//    private Comments law;
//    private Comments woodward;
//    private Comments bioMedBranch;
//
//    private model.System system;
//
//    private Library asianL;
//    private Library ikbL;
//    private Library koernerL;
//    private Library lawL;
//    private Library woodwardL;
//    private Library bioMedBranchL;
//
//    private Map<Integer, Library> numberCommentPair;
//
//    private boolean loadedOrNot;
//
//    // EFFECTS: initialize the scanner along with number and comment pair and set up the UI
//    //          ; also runs the UI
//    public UISetUp() {
//        scanner = new Scanner(System.in);
//        numberCommentPair = new HashMap<>();
//        jsonReader = new JsonReader(JSON_STORE);
//        jsonWriter = new JsonWriter(JSON_STORE);
//        system = new model.System();
//        loadedOrNot = false;
//        runSetup();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: main method to run the setup
//    private void runSetup() {
//        librarySetUp();
//        intro();
//        user = new User(Arrays.asList(asianL, ikbL, koernerL, lawL, woodwardL, bioMedBranchL));
//        view();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: set up numberCommentPair and library info and add libraries into the system
//    private void librarySetUp() {
//        basicCommentsSetUp();
//        asianL = new Library(this.asian, "Asian Library", 1);
//
//        ikbL = new Library(this.ikb, "Irving K. Barber Learning Centre", 2);
//        koernerL = new Library(this.koerner, "Koerner Library", 3);
//        lawL = new Library(this.law, "Law Library", 4);
//        woodwardL = new Library(this.woodward, "Woodward Library", 5);
//        bioMedBranchL = new Library(this.bioMedBranch, "Biomedical Branch Library", 6);
//
//        numberCommentPair.put(1, asianL);
//        numberCommentPair.put(2, ikbL);
//        numberCommentPair.put(3, koernerL);
//        numberCommentPair.put(4, lawL);
//        numberCommentPair.put(5, woodwardL);
//        numberCommentPair.put(6, bioMedBranchL);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: set up the default comments presented to the users
//    private void basicCommentsSetUp() {
//        this.asian = new Comments();
//        this.ikb = new Comments();
//        this.koerner = new Comments();
//        this.law = new Comments();
//        this.woodward = new Comments();
//        this.bioMedBranch = new Comments();
//
//
//        addToLibraries(asian, "Clean Environment", 4);
//        addToLibraries(asian, "Very quiet, enjoy studying in the silent space with sound-insulated walls", 5);
//
//        addToLibraries(ikb, "Love the Ridingson since people cannot talk loud there, perfect for studying!", 4);
//        addToLibraries(ikb, "Provides sport equipments and great hardware like i-mac! Great learning experience!", 5);
//
//        addToLibraries(koerner, "Sometimes people do tutoring and start to get noisy downstairs.", 3);
//        addToLibraries(koerner, "Pretty dark inside, and almost no charging areas provided. Even 0/5 is too high.", 0);
//
//        addToLibraries(law, "Close to Rose Garden and most of my econ classes held in buchanan.", 4);
//
//        addToLibraries(woodward, "Love the two lecture halls but would rather studying far from it.", 4);
//        addToLibraries(woodward, "Dirty washroom! Seems like people are smoking in here, but space is bright"
//                + " and comfy for long-time studying", 2);
//        addToLibraries(woodward, "Perfect for Arts students who have classes nearby that area. Also,, it is"
//                + "close to the bus loop and restaurants in university blvd. Best choice ever!", 5);
//
//        addToLibraries(bioMedBranch, "Very new building, think they are still expanding it. Can' wait"
//                + " to see what it will look like after a couple of years!", 4.6);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: add messages to libraries and set default passwords
//    private void addToLibraries(Comments comments, String message, double rating) {
//        Calendar calendar = new GregorianCalendar(2022, Calendar.FEBRUARY, 11);
//        String date = calendar.getTime().toString();
//        Comment comment = new Comment(message, rating, "owen04", date);
//        comments.addToSystem(comment);
//    }
//
//    // EFFECTS: print out the intro statement to users
//    private void intro() {
//        System.out.println("-------------Welcome to Rate My Library-------------");
//        System.out.println();
//        System.out.println("This is a platform to rate each library in UBC in order to "
//                + "provide freshmen and newcomers to UBC insightful ideas that might suit their learning styles.");
//        loadDataRequest();
//    }
//
//    // EFFECTS: helper method that wraps viewListOfComments() and viewAllMessagesPrompt()
//    private void view() {
//        viewListOfComments();
//        Library result = viewAllMessagesPrompt();
//        try {
//            operateAndAdd(result);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // EFFECTS: asks user if they wish to load their data or not.
//    //     If yes, then load and publish all previously saved data to them.
//    //     If no, then do nothing.
//    private void loadDataRequest() {
//        while (true) {
//            System.out.println("Do you want to load all the previously made comments? Or just want to test "
//                    + "the system with some scaffolds?");
//            System.out.println("----Note: If you don't load the system, you won't ba able to save all your data!");
//            System.out.println("Please enter either yes or no");
//            String decision = scanner.nextLine();
//            if (decision.equals("yes")) {
//                loadLibrary();
//                numberCommentPair.replace(1, asianL);
//                numberCommentPair.replace(2, ikbL);
//                numberCommentPair.replace(3, koernerL);
//                numberCommentPair.replace(4, lawL);
//                numberCommentPair.replace(5, woodwardL);
//                numberCommentPair.replace(6, bioMedBranchL);
//                loadedOrNot = true;
//                break;
//            } else if (decision.equals("no")) {
//                System.out.println("Sure! Let's play around!");
//                break;
//            } else {
//                System.out.println("Enter again?");
//            }
//        }
//    }
//
//    // EFFECTS: asks user if they wish to save and publish their comments before quitting
//    //      If yes, then save all previously typed data for them.
//    //      If no, then do nothing.
//    private void saveData() {
//        while (true) {
//            System.out.println("Do you want to save all the changes you made to Rate My Library before quitting?");
//            System.out.println("Please enter either yes or no");
//            String decision = scanner.next();
//            if (decision.equals("yes")) {
//                saveLibrary();
//                break;
//            } else if (decision.equals("no")) {
//                System.out.println("Sure! Thanks for sticking around!");
//                break;
//            } else {
//                System.out.println("Enter again?");
//            }
//        }
//    }
//
//
//
//    // EFFECTS: print out list of comments  made by a particular user or the whole list of libraries overview
//    //      based on their inputs
//    private void viewListOfComments() {
//        while (true) {
//            System.out.println("Enter `list` to view the list of libraries, `v` to view your comments, `q` to quit.");
//            String result = scanner.next();
//
//            if (result.equals("v")) {
//                System.out.print("Please enter the password of your comments made earlier: ");
//                String password = scanner.next();
//                Map<Comment, String> comments = user.viewPreviousCommented(password);
//                validComments(comments);
//            } else if (result.equals("list")) {
//                displayLibraryList();
//                System.out.println("Want to check out library rating? Enter `yes` to proceed or others to go back.");
//                String result2 = scanner.next();
//                if (result2.equals("yes")) {
//                    return;
//                }
//            } else if (result.equals("q")) {
//                if (loadedOrNot) {
//                    saveData();
//                }
//                System.exit(0);
//            }
//        }
//    }
//
//    // EFFECTS: check if this user has made any comments yet.
//    public void validComments(Map<Comment, String> comments) {
//        if (comments == null) {
//            System.out.println("No comments have been made by you yet.");
//        } else {
//            concatPromptForMessage(comments);
//        }
//    }
//
//    // EFFECTS: view all the messages from a particular library after user enters a number
//    private Library viewAllMessagesPrompt() {
//        Library library;
//
//        while (true) {
//            System.out.println("Enter a number in 1 to 6 to view detailed comments from each library");
//            int number = scanner.nextInt();
//
//            System.out.println("---------------------");
//            if (numberCommentPair.containsKey(number)) {
//                library = numberCommentPair.get(number);
//                for (Comment c : library.getListOfComments().getComments()) {
//                    System.out.println(c);
//                }
//                System.out.println("---------------------");
//                return library;
//            }
//        }
//    }
//
//    // EFFECTS: ask users if they wish to add comments to a particular library
//    private void operateAndAdd(Library library) throws InterruptedException {
//        while (true) {
//            System.out.println("Do you wish to contribute to the library rating? \n"
//                    + "Enter `a` to add your comments, `s` to select a library again, `r` to remove your comment, "
//                    + "and 'q' to quit the Rate My Library");
//            String answer = scanner.next();
//
//            if (answer.equals("a")) {
//                promptForOperateAndAdd(library);
//                directBack();
//                break;
//            } else if (answer.equals("q")) {
//                if (loadedOrNot) {
//                    saveData();
//                }
//                System.exit(0);
//            } else if (answer.equals("s")) {
//                viewListOfComments();
//            } else if (answer.equals("r")) {
//                operateAndRemove(library);
//            }
//        }
//    }
//
//    // EFFECTS: println and prompting users next step for adding a comment
//    private void promptForOperateAndAdd(Library library) {
//        System.out.println("Enter your comment: ");
//        String comment = scanner.next();
//        comment += scanner.nextLine();
//        System.out.println("Enter a rating (out of 5): ");
//
//        double rating;
//        while (true) {
//            rating = scanner.nextDouble();
//            if (rating < 0 || rating > 5) {
//                System.out.println("Invalid Rating...Please enter again!");
//                continue;
//            }
//            break;
//        }
//
//        System.out.println("Enter a password linked to this comment so you can view or change it anytime: ");
//        String password = scanner.next();
//
//        library.getListOfComments().addToSystem(new Comment(comment, rating, password, new Date().toString()));
//        library.calculateOverallRating();
//    }
//
//    // EFFECTS: remove comments they have commented before
//    private void operateAndRemove(Library library) {
//        System.out.println("!!! Caution !!! You may only remove the comment (one at a time) with your unique key.");
//        System.out.print("Enter the number of user's comment you may want to get removed: ");
//
//        int commentNum = scanner.nextInt();
//
//        System.out.print("Enter your unique key associated with this comment to remove it: ");
//        String password = scanner.next();
//
//        boolean result = library.getListOfComments().removeFromSystem(commentNum, password);
//
//        if (result) {
//            System.out.println("You've successfully removed your comment");
//        } else {
//            System.out.println("Pin or number entered incorrect! Go back up and try again!");
//        }
//        view();
//    }
//
//
//    // EFFECTS: direct the client to the main/home page
//    private void directBack() throws InterruptedException {
//        System.out.println("You added your comments successfully. Yeah!");
//        Thread.sleep(500);
//        System.out.println("...... System is directing you to Home Page ......");
//        Thread.sleep(800);
//        System.out.println("Please wait.....");
//        Thread.sleep(2500);
//        System.out.println();
//        view();
//    }
//
//
//    // EFFECTS: format the prompt cleanly for user's previous messages
//    public void concatPromptForMessage(Map<Comment, String> comments) {
//        for (Map.Entry<Comment, String> comment : comments.entrySet()) {
//            System.out.println("@" + comment.getValue() + ": " + comment.getKey().getMessage());
//        }
//    }
//
//    // EFFECTS: display library list to users
//    public void displayLibraryList() {
//        asianL.calculateOverallRating();
//        ikbL.calculateOverallRating();
//        koernerL.calculateOverallRating();
//        lawL.calculateOverallRating();
//        woodwardL.calculateOverallRating();
//        bioMedBranchL.calculateOverallRating();
//
//        System.out.println("Here's the library list and ratings so far.");
//        System.out.println("1. Asian Library" + "                       Rating: "
//                + asianL.getRatingDisplayed() + "/5.0");
//        System.out.println("2. Irving K. Barber Learning Centre" + "    Rating: "
//                + ikbL.getRatingDisplayed() + "/5.0");
//        System.out.println("3. Koerner Library" + "                     Rating: "
//                + koernerL.getRatingDisplayed() + "/5.0");
//        System.out.println("4. Law Library" + "                         Rating: "
//                + lawL.getRatingDisplayed() + "/5.0");
//        System.out.println("5. Woodward Library" + "                    Rating: "
//                + woodwardL.getRatingDisplayed() + "/5.0");
//        System.out.println("6. Biomedical Branch Library" + "           Rating: "
//                + bioMedBranchL.getRatingDisplayed() + "/5.0");
//    }
//
//    // EFFECTS: saves the library to file
//    public void saveLibrary() {
//        try {
//            system.add(asianL);
//            system.add(ikbL);
//            system.add(koernerL);
//            system.add(lawL);
//            system.add(woodwardL);
//            system.add(bioMedBranchL);
//
//            jsonWriter.open();
//            jsonWriter.write(system);
//            jsonWriter.close();
//            System.out.println("Saving complete!");
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads libraries from file
//    public void loadLibrary() {
//        try {
//            model.System loadedInfo = jsonReader.read();
//
//            for (Library lib: loadedInfo.getLibraries()) {
//                if (lib.getName().equals("Asian Library")) {
//                    this.asianL = lib;
//                } else if (lib.getName().equals("Irving K. Barber Learning Centre")) {
//                    this.ikbL = lib;
//                } else if (lib.getName().equals("Koerner Library")) {
//                    this.koernerL = lib;
//                } else if (lib.getName().equals("Law Library")) {
//                    this.lawL = lib;
//                } else if (lib.getName().equals("Woodward Library")) {
//                    this.woodwardL = lib;
//                } else if (lib.getName().equals("Biomedical Branch Library")) {
//                    this.bioMedBranchL = lib;
//                }
//            }
//            System.out.println("Loading complete!");
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//
//    // getter
//    public model.System getSystem() {
//        return this.system;
//    }
//
//}
