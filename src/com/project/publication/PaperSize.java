package com.project.publication;

public enum PaperSize {
    A1("a1", "1"),
    A2("a2", "2"),
    A3("a3", "3"),
    A4("a4", "4"),
    A5("a5", "5");

    private String type;
    private String backupSize;

    PaperSize(String s, String backup) {
        type = s;
        backupSize = backup;
    }

    public static PaperSize fromString(String s){
        for (PaperSize t : PaperSize.values()) {
            if (t.type.equalsIgnoreCase(s) || (!t.backupSize.isBlank() && t.backupSize.equalsIgnoreCase(s))) {
                return t;
            }
        }
        System.out.println("Could not find corresponding size. Setting to A5.");
        return PaperSize.A5;
    }
}
