package com.adotsuarez.ui;

import com.adotsuarez.core.Code;
import com.adotsuarez.core.List;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author adotsuarez
 * (C) Agustin Suarez Martinez, 2020 - adot.c1.biz
 */
public class CLI {
    public void run() {
        char selected;      // Option selected from the menu

        // Get the list ready
        List myCodes = new List();

        // Main loop
        while (true) {
            clearScreen();
            System.out.println(topNavBar(Screen.HOME));

            if (myCodes.amountElements() == 0) {
                System.out.println("  You haven't saved any code yet... :(" +
                        "\n  Start by pressing [N] to add a new one!");
            } else {
                System.out.println("  My codes:\n" + myCodes.toString() +    // ALT: ...(... + myCodes) [without .toString()]
                        "\n  Add a new code by pressing [N].");
            }

            selected = bottomInputChar();

            if (checkInput(selected,optionCreator(Screen.HOME,myCodes))) {
                switch (selected) {
                    case 'N':
                        newCode(myCodes);
                        break;
                    default:
                        codeView(myCodes.searchCode(selected),myCodes);
                }
            }

        }
    }

    // ============== SCREENS

    private static enum Screen {
        HOME("Home","N"),               // New
        NEWCODE("New code","HS"),       // Home, Start
        EDITING("Editing a code",""),   // -
        CODEVIEW("View code","HDEF"),   // Home, Delete, Edit, Fav
        CODEEDIT("Edit code","HS"),     // Home, Start
        CODEDELETE("Delete code","HD"); // Home, Delete

        private String label;
        private String constantOptions;

        private Screen(String label, String constantOptions) {
            this.label = label;
            this.constantOptions = constantOptions;
        }
    }

    // newCode
    private void newCode(List list){
        char selected;

        do {
            clearScreen();
            System.out.println(topNavBar(Screen.NEWCODE));

            System.out.println("                         You are going to create a new code." +
                    "\n                       Are you sure? Press [S] to get started.");

            selected = bottomInputChar();

            if (selected == 'S') {
                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Enter the title of your new code: ");
                String title = bottomInputString();

                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Now enter the data you want to save:");
                String data = bottomInputString();

                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Why not inserting a comment?");
                String comment = bottomInputString();

                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Let's add a tag to it!" +
                        "\n  \u001B[46m\u001B[30m [C] for cyan   \u001B[0m" +
                        "\n  \u001B[45m\u001B[30m [P] for purple \u001B[0m" +
                        "\n  \u001B[42m\u001B[30m [G] for green  \u001B[0m" +
                        "\n   [Other key] for not tagging it");
                char tagSelection = bottomInputChar();
                Code.Tag tag;
                switch (tagSelection) {
                    case 'C':
                        tag = Code.Tag.CYAN;
                        break;
                    case 'P':
                        tag = Code.Tag.PURPLE;
                        break;
                    case 'G':
                        tag = Code.Tag.GREEN;
                        break;
                    default:
                        tag = Code.Tag.DEFAULT;
                }

                char favSelection;
                boolean fav = false;
                do {
                    clearScreen();
                    System.out.println(topNavBar(Screen.EDITING));
                    System.out.println("\n  Do you want to save your code as \u001B[33mfavourite\u001B[0m" +
                            "\n  so that it appears first on your list?");
                    System.out.println("\n  [Y] for adding it to your favourites" +
                            "\n  [N] for not adding it.");
                    favSelection = bottomInputChar();

                    switch (favSelection) {
                        case 'Y':
                            fav = true;
                            break;
                        case 'N':
                            fav = false;
                            break;
                        default:
                            System.out.println("  HEY! Wrong key, check uppercase. Let's do it again.");
                    }
                } while (favSelection != 'Y' && favSelection != 'N');

                list.insertCode(data, title, comment, tag, fav);
            }
        } while (!checkInput(selected,optionCreator(Screen.NEWCODE)));
    }

    // codeView
    private void codeView(Code code, List list){
        char selected;
        do {
            clearScreen();
            System.out.println(topNavBar(Screen.CODEVIEW));

            StringBuilder sb = new StringBuilder();
            sb.append("  ");

            switch (code.getTag()) {
                case CYAN:
                    sb.append(code.getTitle())
                            .append("   \u001B[46m\u001B[30m ● Cyan tag ")
                            .append("\u001B[0m");
                    break;
                case PURPLE:
                    sb.append(code.getTitle())
                            .append("       \u001B[45m\u001B[30m ● Purple tag ")
                            .append("\u001B[0m");
                    break;
                case GREEN:
                    sb.append(code.getTitle())
                            .append("      \u001B[42m\u001B[30m ● Green tag ")
                            .append("\u001B[0m");
                    break;
                default:
                    sb.append(code.getTitle());
            }

            if (code.isFav()) {
                sb.append("  \u001B[43m")
                        .append("\u001B[30m")
                        .append(" ✦ Saved as favourite ")
                        .append("\u001B[0m");
            }

            sb.append("\n\n  Data stored:\n  ").append(code.getData());

            if (!Objects.equals(code.getComment(), "")) {
                sb.append("\n\n  Comment:\n  ").append(code.getComment());
            }

            sb.append("\n\n  Options:")
                    .append("\n  >  [D] to delete this code.")
                    .append("\n  >  [E] to edit this code.");

            if (code.isFav()) {
                sb.append("\n  >  [F] to delete this code from your favourites.");
            } else {
                sb.append("\n  >  [F] to add this code to your favourites.");
            };


            System.out.println(sb.toString());

            selected = bottomInputChar();

            switch (selected) {
                case 'D':
                    codeDelete(code,list);
                    break;
                case 'E':
                    codeEdit(code);
                    break;
                case 'F':
                    if (code.isFav()) {
                        code.setFav(false);
                    } else {
                        code.setFav(true);
                    }
                    break;
            }
        } while (!checkInput(selected,optionCreator(Screen.CODEVIEW)));
    }

    // codeEdit
    private void codeEdit(Code code){
        char selected;

        do {
            clearScreen();
            System.out.println(topNavBar(Screen.CODEEDIT));

            System.out.println("                           You are going to edit the code." +
                    "\n                       Are you sure? Press [S] to get started.");

            selected = bottomInputChar();

            if (selected == 'S') {
                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Enter the new title of the code: ");
                System.out.println("\n  The old one was: " + code.getTitle());
                code.setTitle(bottomInputString());

                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Change the comment");
                System.out.println("\n  The old one was: " + code.getComment());
                code.setComment(bottomInputString());

                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Change the tag:" +
                        "\n  \u001B[46m\u001B[30m [C] for cyan   \u001B[0m" +
                        "\n  \u001B[45m\u001B[30m [P] for purple \u001B[0m" +
                        "\n  \u001B[42m\u001B[30m [G] for green  \u001B[0m" +
                        "\n   [Other key] for not tagging it");
                if (code.getTag() != Code.Tag.DEFAULT) {
                    System.out.print("\n  The old one was tagged as ");
                    switch (code.getTag()) {
                        case CYAN:
                            System.out.println(" \u001B[46m" + "\u001B[30m" + " ● Cyan " + "\u001B[0m");
                            break;
                        case PURPLE:
                            System.out.println(" \u001B[45m" + "\u001B[30m" + " ● Purple " + "\u001B[0m");
                            break;
                        case GREEN:
                            System.out.println(" \u001B[42m" + "\u001B[30m" + " ● Green " + "\u001B[0m");
                            break;
                    }
                }
                char tagSelection = bottomInputChar();
                switch (tagSelection) {
                    case 'C':
                        code.setTag(Code.Tag.CYAN);
                        break;
                    case 'P':
                        code.setTag(Code.Tag.PURPLE);
                        break;
                    case 'G':
                        code.setTag(Code.Tag.GREEN);
                        break;
                    default:
                        code.setTag(Code.Tag.DEFAULT);
                }
            }
        } while (!checkInput(selected,optionCreator(Screen.CODEEDIT)));
    }

    // codeDelete
    private void codeDelete(Code code, List list){
        char selected;

        do {
            clearScreen();
            System.out.println(topNavBar(Screen.CODEDELETE));

            System.out.println("                         You are going to delete the code." +
                    "\n                       Are you sure? Press [D] to delete it.");

            selected = bottomInputChar();

            if (selected == 'D') {
                clearScreen();
                System.out.println(topNavBar(Screen.EDITING));
                System.out.println("  Deleting code...");
                list.deleteCode(code);
            }
        } while (!checkInput(selected,optionCreator(Screen.CODEDELETE)));
    }

    // appInfo
    private void appInfo(){

    }

    // ============== OTHER METHODS [Not screens]

    // checkInput
    private boolean checkInput(char input, String options) {
        boolean correct = false;

        if (options.indexOf(input) >= 0) {
            correct = true;
        }

        return correct;
    }

    // optionCreator
    private String optionCreator(Screen screen,List list) {
        StringBuilder options = new StringBuilder(screen.constantOptions);

        if (screen == Screen.HOME) {
            options.append(zeroToMax(list.amountElements()));
        }

        return options.toString();
    }

    private String optionCreator(Screen screen) {
        StringBuilder options = new StringBuilder(screen.constantOptions);

        return options.toString();
    }

    // zeroToMax
    private String zeroToMax(int max) {
        StringBuilder items = new StringBuilder();

        for (int i = 0; i < max; i++) {
            items.append(i+1);
        }

        return items.toString();
    }

    // topNavBar
    private String topNavBar(Screen screen) {
        StringBuilder header = new StringBuilder();
        // LOGO
        header.append("  ko  -  CLI Version");

        // Home button
        if (screen == Screen.HOME) {
            header.append("                                                       Home screen");
        } else if (screen == Screen.EDITING) {
            header.append("                                                      Editing code");
        } else {
            header.append("                                      Enter [H] to go back to Home");
        }

        // Break line
        header.append("\n========================================================================================\n");

        return header.toString();
    }

    // bottomInput
    private char bottomInputChar() {
        // Break line
        System.out.println("\n========================================================================================");
        System.out.print("  Input > ");
        // Input
        char selected = '*';
        String temp;
        Scanner charInput = new Scanner(System.in);
        temp = charInput.nextLine();
        if (!Objects.equals(temp, "")) {
            selected = temp.charAt(0);
        }

        return selected;
    }

    private String bottomInputString() {
        // Break line
        System.out.println("\n========================================================================================");
        System.out.print("  Input > ");
        // Input
        String selected;
        Scanner charInput = new Scanner(System.in);
        selected = charInput.nextLine();

        return selected;
    }

    // clearScreen
    private void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
