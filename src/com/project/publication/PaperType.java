package com.project.publication;

public enum PaperType {
    Default("normal", "default"),
    Glossy("glossy"),
    Newspaper("newspaper");

    private String type;
    private String backupType;

    PaperType(String s, String backup) {
        type = s;
        backupType = backup;
    }

    PaperType(String s){
        type = s;
    }

    public static PaperType fromString(String s){
        for (PaperType t : PaperType.values()) {
            if (t.type.equalsIgnoreCase(s) || (t.backupType != null && t.backupType.equalsIgnoreCase(s))) {
                return t;
            }
        }
        System.out.println("Could not find corresponding type. Setting to default.");
        return PaperType.Default;
    }
}
