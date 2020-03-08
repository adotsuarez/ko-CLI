package com.adotsuarez.core;

/**
 * @author adotsuarez
 * (C) Agustin Suarez Martinez, 2020 - adot.c1.biz
 */
public class List {
    private Code first;
    private Code last;

    // List constructor
    public List() {
        this.first = new Code();
        this.last = new Code();

        first.setNext(last);
        last.setPrev(first);
    }

    // ============== UI - List INTERACTION

    // amountElements
    public int amountElements() {
        int toret = 0;
        Code current = first;

        while (current.getNext() != last) {
            toret++;
            current = current.getNext();
        }

        return toret;
    }

    // searchCode
    public Code searchCode(char posInChar){
        int pos = Character.getNumericValue(posInChar);
        Code current = first.getNext();

        for (int i = 0; i < (pos-1); i++) {
            current = current.getNext();
        }

        return current;
    }

    // toString
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        Code current = first.getNext();
        int i = 1;

        while (current != last) {

            // Identifier
            sb.append("  [").append(i).append("] ");

            // Favourite star
            if (current.isFav()) {
                sb.append("\u001B[43m")
                        .append("\u001B[30m")
                        .append(" ✦ ")
                        .append("\u001B[0m");
            } else {
                sb.append("   ");
            }

            // Title
            sb.append(" ")
                    .append(current.getTitle());

            // Tag
            switch (current.getTag()) {
                case CYAN:
                    sb.append(" \u001B[46m")
                            .append("\u001B[30m")
                            .append(" ● Cyan Tag ")
                            .append("\u001B[0m")
                            .append("\n");
                    break;
                case PURPLE:
                    sb.append(" \u001B[45m")
                            .append("\u001B[30m")
                            .append(" ● Purple Tag ")
                            .append("\u001B[0m")
                            .append("\n");
                    break;
                case GREEN:
                    sb.append(" \u001B[42m")
                            .append("\u001B[30m")
                            .append(" ● Green Tag ")
                            .append("\u001B[0m")
                            .append("\n");
                    break;
                default:
                    sb.append("\n");
            }

            i++;
            current = current.getNext();
        }

        return sb.toString();
    }

    // ============== List MANAGEMENT

    // insertCode
    public void insertCode(String data, String title, String comment, Code.Tag tag, boolean fav) {
        Code toInsert = new Code(data,title,comment,tag,fav,null,null);
        Code current = first;

        if (!fav) {
            while (current.getNext() != last && current.getNext().isFav()) {
                current = current.getNext();
            }
        }
        toInsert.setNext(current.getNext());
        toInsert.setPrev(current);
        current.getNext().setPrev(toInsert);
        current.setNext(toInsert);
    }

    // deleteCode
    public void deleteCode(Code code) {
        code.getPrev().setNext(code.getNext());
        code.getNext().setPrev(code.getPrev());
    }
}
